public class Server {
    private final ServerSockets websocketServer;
    private final int maxIpConnectionsThreshold = 10;
    private ArrayList<Client> clients = new ArrayList<Client>();
    private HashMap<String, Integer> recentIpAddresses = new HashMap<String, Integer>();
    private MainGame game;
    public boolean isLoadingOrSaving = false;
    private BZLogger messagesRecievedLogger = new BZLogger("messagesReceived", "messagesReceived.log", false);
    private BZLogger messagesSentLogger = new BZLogger("messagesSent", "messagesSent.log", false);
    private BZLogger systemMessagesLogger = new BZLogger("systemMessages", "systemMessages.log", true);
    private int backupCount = 19; //start at 19 so that it will backup early
    public Server() {
        this.systemMessagesLogger.log("Server started");
        this.websocketServer = new ServerSockets(this, 9999);
        this.websocketServer.start();
        this.game = new MainGame();
        this.game.setServer(this);
        this.systemMessagesLogger.log("Game started");
        try {
            this.loadWorld();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.scheduleIpRefresh();
        this.scheduleGameUpdate();
        this.scheduleClientRefresh();
        this.scheduleWorldSave();
    }
    private void scheduleIpRefresh() {
        Runnable updateIpList = new Runnable() {
            public void run() {
                Server.this.ipRefresh();
            }
        };
        int initialDelayToStart = 0;
        int timeBetweenUpdates = 3;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(updateIpList, initialDelayToStart, timeBetweenUpdates, TimeUnit.MINUTES);
    }
    private void scheduleGameUpdate() {
        Runnable updateGame = new Runnable() {
            public void run() {
                Server.this.game.updateWorld();
            }
        };
        int initialDelayToStart = 0;
        int timeBetweenUpdates = 15;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(updateGame, initialDelayToStart, timeBetweenUpdates, TimeUnit.MINUTES);
    }
    private void scheduleClientRefresh() {
        Runnable clientRefresh = new Runnable() {
            public void run() {
                Server.this.refreshAllClients();
            }
        };
        int initialDelayToStart = 1000;
        int timeBetweenUpdates = 100;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(clientRefresh, initialDelayToStart, timeBetweenUpdates, TimeUnit.MILLISECONDS);
    }
    private void scheduleWorldSave() {
        Runnable worldSave = new Runnable() {
            public void run() {
                try {
                    Server.this.saveWorld();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        };
        int initialDelayToStart = 20;
        int timeBetweenUpdates = 600;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(worldSave, initialDelayToStart, timeBetweenUpdates, TimeUnit.SECONDS);
    }
    public ArrayList<Client> getClients() {
        return this.clients;
    }
    private void refreshAllClients() {
        if (this.isLoadingOrSaving) {
            return;
        }
        for (Client client : this.clients) {
            StringBuilder bigMessageBuilder = new StringBuilder();
            for (Message message : client.getAllMessages()) {
                bigMessageBuilder.append(message.getMessageString()).append(Message.delimiter);
            }
            if (!bigMessageBuilder.toString().equals("")) {
                client.connection.send(bigMessageBuilder.toString());
                this.messagesSentLogger.log(("sending client " + client.getPlayer().getName() + ": " + bigMessageBuilder.toString()));
            }
        }
    }
    public void clientConnected(WebSocket conn, ClientHandshake handshake) {
        this.systemMessagesLogger.log("Client opened connection " + conn.getRemoteSocketAddress());
        this.processIpAddress(conn.getRemoteSocketAddress());
        this.systemMessagesLogger.log("total ips connected = " + String.valueOf(this.recentIpAddresses.size()));
        if (!this.hasIpConnectedTooFrequently(conn.getRemoteSocketAddress())) {        
            this.clients.add(new Client(conn, handshake));
            this.sendWelcome(conn);
        } else {
            conn.close(0);
            this.clientDisconnected(conn);
        }
    }
    private void sendWelcome(WebSocket conn) {
        WelcomeMessage welcome = new WelcomeMessage();
        conn.send(welcome.getMessageString());
        this.messagesSentLogger.log("sent conn welcome " + conn.getRemoteSocketAddress()) ;
    }
    public void clientDisconnected(WebSocket conn) {
        this.systemMessagesLogger.log("Client closed connection " + conn.getRemoteSocketAddress());
        Client clientToRemove = null;
        for (Client client : this.clients) {
            if (conn.equals(client.connection)) {
                clientToRemove = client;
            }
        }
        if (clientToRemove != null) {
            if (clientToRemove.getUserName() != null && clientToRemove.getPlayer() != null) {
                try {
                    this.savePlayer(clientToRemove);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.clients.remove(clientToRemove);
        }
    }
    public void processMessage(WebSocket conn, String message) {
        for (Client client : this.clients) {
            if (conn.equals(client.connection)) {
                this.messagesRecievedLogger.log("conn " + conn.getRemoteSocketAddress() + " sent:" + message);
                String delimiter = Message.delimiter;
                String[] messageFrags = message.split(delimiter);
                if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_REGISTRATION_MESSAGE.id()))) {
                    PlayerRegistrationMessage registrationMessage = PlayerRegistrationMessage.decodeMessage(messageFrags[1]);
                    this.processPlayerRegistration(registrationMessage, client);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_LOGIN_MESSAGE.id()))) {
                    PlayerLoginMessage loginMessage = PlayerLoginMessage.decodeMessage(messageFrags[1]);
                    this.processPlayerLogin(loginMessage, client);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_GAME_READY_MESSAGE.id()))) {
                    this.sendStartingPlayerAndRegionData(client);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_MOVED_MESSAGE.id()))) {
                    PlayerMovedMessage decodedMessage = PlayerMovedMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_BUILT_MESSAGE.id()))) {
                    PlayerBuiltMessage decodedMessage = PlayerBuiltMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_SOLD_MESSAGE.id()))) {
                    PlayerSoldMessage decodedMessage = PlayerSoldMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_BOUGHT_MESSAGE.id()))) {
                    PlayerBoughtMessage decodedMessage = PlayerBoughtMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_BOUGHT_ALL_MESSAGE.id()))) {
                    PlayerBoughtAllMessage decodedMessage = PlayerBoughtAllMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_TOOK_MESSAGE.id()))) {
                    PlayerTookMessage decodedMessage = PlayerTookMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_GAVE_ALL_MESSAGE.id()))) {
                    PlayerGaveAllMessage decodedMessage = PlayerGaveAllMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_GAVE_MESSAGE.id()))) {
                    PlayerGaveMessage decodedMessage = PlayerGaveMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_TOOK_REGION_MESSAGE.id()))) {
                    PlayerTookEntireRegionMessage decodedMessage = PlayerTookEntireRegionMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_ADDED_ENERGY_MESSAGE.id()))) {
                    PlayerAddedEnergyMessage decodedMessage = PlayerAddedEnergyMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_ADDED_MAX_ENERGY_MESSAGE.id()))) {
                    PlayerAddedMaxEnergyMessage decodedMessage = PlayerAddedMaxEnergyMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_ADDED_ENERGY_REGION_MESSAGE.id()))) {
                    PlayerAddedEnergyRegionMessage decodedMessage = PlayerAddedEnergyRegionMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_UPDATE_MESSAGE.id()))) {
                    PlayerUpdateMessage decodedMessage = PlayerUpdateMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_BOUGHT_ABILITY_MESSAGE.id()))) {
                    PlayerBoughtAbilityMessage decodedMessage = PlayerBoughtAbilityMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                } else if (messageFrags[0].equals(String.valueOf(MessageType.PLAYER_ACTIVATED_ABILITY_MESSAGE.id()))) {
                    PlayerActivatedAbilityMessage decodedMessage = PlayerActivatedAbilityMessage.decodeMessage(messageFrags[1]);
                    decodedMessage.client = client;
                    this.game.acceptMessage(decodedMessage);
                }
            }
        }
    }
    private void tryToRemoveExistingPlayer(String name) {
        Client clientToRemove = null;
        for (Client existingClient : this.clients) {
            if (existingClient.getPlayer() != null &&
                name.equals(existingClient.getPlayer().getName())) {
                this.systemMessagesLogger.log("found matching player " + existingClient.getPlayer().getName());
                clientToRemove = existingClient;
            }
        }
        if (clientToRemove != null) {
            clientToRemove.connection.close(0);
            this.clientDisconnected(clientToRemove.connection);
        }
    }
    private void sendStartingPlayerAndRegionData(Client client) {
        if (client.getPlayer() == null) {
            String userName = client.getUserName();
            if (userName != null) {
                Player savedPlayer = this.loadPlayerForUserName(userName);
                if (savedPlayer == null) {
                    return;
                }
                client.setPlayer(savedPlayer);
            }
        }
        PlayerMessage playerMessage = new PlayerMessage(client.getPlayer().encodeToString());
        client.connection.send(playerMessage.getMessageString());
        for (Region region : this.game.getClosebyRegionsForClient(client)) {
            RegionMessage regionMessage = new RegionMessage(region.encodeToString(), region.worldPosition());
            client.connection.send(regionMessage.getMessageString());
        }
        this.systemMessagesLogger.log("adding client " + client.getPlayer().getName());
    }
    private void ipRefresh() {
        for (String string : this.recentIpAddresses.keySet()) {
            int persistentlyConnectingThreshold = 12;
            int currentCount = this.recentIpAddresses.get(string);
            if (currentCount > persistentlyConnectingThreshold) {
                this.recentIpAddresses.put(string, 10);
            } else {
                this.recentIpAddresses.put(string, Math.max(this.recentIpAddresses.get(string) - 2, 0));
            }
        }
    }
    private void processIpAddress(InetSocketAddress address) {
        boolean wasInMap = false;
        for (String string : this.recentIpAddresses.keySet()) {
            if (string.equals(address.getHostString())) {
                int currentCount = this.recentIpAddresses.get(string);
                this.recentIpAddresses.put(string, currentCount + 1);
                wasInMap = true;
            }
        }
        if (!wasInMap) {
            this.recentIpAddresses.put(address.getHostString(), 1);
        }
        this.systemMessagesLogger.log("num times connected = " + String.valueOf(this.recentIpAddresses.get(address.getHostString())));
    }
    private boolean hasIpConnectedTooFrequently(InetSocketAddress address) {
        int count = this.recentIpAddresses.get(address.getHostString());
        if (count > this.maxIpConnectionsThreshold) {
            return true;
        }
        return false;
    }
}