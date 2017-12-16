package encryption;

import java.math.BigInteger;

public class EncryptionDecryptionMethods {
//	// THIS METHOD ENCRYPTS THE USERS MESSAGE //
//	public BigInteger returnEncryptedMessage(int message, BigInteger p, BigInteger q) {
//		RSAEncryption rsa = new RSAEncryption();
//		BigInteger bigIntegerMessage = rsa.castBigInteger(message);
//		BigInteger bigIntegerE = rsa.getE();
//		BigInteger bigIntegerPQ = rsa.getPQ(p, q);
//		BigInteger encryptedMessage = rsa.powerMod(bigIntegerMessage, bigIntegerE, bigIntegerPQ);
//		Main.println("Encrypted Message = " + encryptedMessage + ".");
//		return encryptedMessage;
//	}
	
	// THIS METHOD ENCRYPTS THE USERS MESSAGE //
    public byte[] returnEncryptedMessage(byte[] message, BigInteger p, BigInteger q, BigInteger e, BigInteger pq) {
//    	RSAEncryption rsa = new RSAEncryption();
//    	BigInteger e = rsa.getE();
//    	BigInteger pq = rsa.getPQ(p, q);
        return (new BigInteger(message)).modPow(e, pq).toByteArray();
    }
    
//	// THIS METHOD DECRYPTS THE USERS MESSAGE //     
//	public BigInteger returnDecryptedMessage(BigInteger encryptedMessage, BigInteger p, BigInteger q) {
//		RSAEncryption rsa = new RSAEncryption();
//		BigInteger bigIntegerPQ = rsa.getPQ(p,q);
//		BigInteger lcm = rsa.lcm(rsa.getPrimePTotient(p),rsa.getPrimeQTotient(q));
//		BigInteger bigIntegerD = rsa.getD(rsa.getE(), lcm,p,q);
//		BigInteger decryptedMessage = rsa.powerMod(encryptedMessage, bigIntegerD, bigIntegerPQ);
//		Main.println("Decrypted Message = " + decryptedMessage + ".");
//		return decryptedMessage;
//	}
	
    // THIS METHOD DECRYPTS THE USERS MESSAGE //
    public byte[] returnDecryptedMessage(byte[] message, BigInteger d, BigInteger pq) {
    	return (new BigInteger(message)).modPow(d, pq).toByteArray();
    }
    
    // THIS METHOD RETURNS THE BYTE VALUE OF THE ENCRYPTED MESSAGE //
    protected byte[] encryptedByteMethod(String userInput) {
    	RSAEncryption rsa = new RSAEncryption();
    	BigInteger e = rsa.getE();
		BigInteger p = rsa.getPrimeP();
		BigInteger q = rsa.getPrimeQ();
		BigInteger pq = rsa.getPQ(p, q);
    	byte[] encrypted = returnEncryptedMessage(userInput.getBytes(), p, q, e, pq);
    	return encrypted;
    }
    
    // THIS METHOD RETURNS THE BYTE VALUE OF THE DECRYPTED MESSAGE //
    protected byte[] decryptedByteMethod(byte[] encrypted) {
    	RSAEncryption rsa = new RSAEncryption();
    	BigInteger e = rsa.getE();
		BigInteger p = rsa.getPrimeP();
		BigInteger q = rsa.getPrimeQ();
		BigInteger pq = rsa.getPQ(p, q);
//		byte[] encrypted = encryptedString.getBytes()
    	byte[] decrypted = returnDecryptedMessage(encrypted,rsa.getD(e, p, q), pq);
    	return decrypted;
    }
    
    // METHOD TO ENCRYPT USER INPUT //
    protected String encrypt(String userInput) {
    	RSAEncryption rsa = new RSAEncryption();
        BigInteger p = rsa.getPrimeP();
//      Main.println("Encryption Prime P = " + p);
        BigInteger q = rsa.getPrimeQ();
//      Main.println("Encryption Prime Q = " + q);
        EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
        BigInteger e = rsa.getE();
        BigInteger pq = rsa.getPQ(p, q);
        byte[] encrypted = encDecMeth.returnEncryptedMessage(userInput.getBytes(), p, q, e, pq);
//      Main.println("Byte Value: " + bytesToString(encrypted) + ".");
        String encryptedString = bytesToString(encrypted);
        return encryptedString;
    }
    	
    // METHOD TO DECRYPT USER INPUT //
    protected String decrypt(byte[] enc) {
    	RSAEncryption rsa = new RSAEncryption();
    	BigInteger p = rsa.getPrimeP();
//   	Main.println("Decryption Prime P = " + p);
    	BigInteger q = rsa.getPrimeQ();
//    	Main.println("Decryption Prime Q = " + q);
    	BigInteger pq = rsa.getPQ(p, q);
    	BigInteger e = rsa.getE();
    	EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
    	byte[] decrypted = encDecMeth.returnDecryptedMessage(enc,rsa.getD(e, p, q), pq);
//    	Main.println("Decrypted String: " + new String(decrypted) + ".");
    	String decryptedString = new String(decrypted);
    	return decryptedString;
    }
    	
    // METHOD TO CONVERT BYTES TO A STRING //
    public String bytesToString(byte[] encrypted) {
    	String test = "";
    	for (byte b : encrypted) {
    		test += Byte.toString(b);
      	}
    	return test;
    }
}
