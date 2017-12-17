package encryption;

import java.math.BigInteger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

// DON'T GET MAD AT THIS CODE - IT WAS WRITTEN BY AN IDIOT. YOU. //
public class Main {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		
		String userInput = "This is a test. Nothing but a test. I swear ;)";
		String userInputBitLengthString = JOptionPane.showInputDialog("Please enter the bit length of the encryption keys.");
		int userInputBitLengthInt = Integer.parseInt(userInputBitLengthString);
		RSAEncryption rsa = new RSAEncryption();
	
		println("Original string = " + userInput);
        BigInteger p = rsa.getPrimeP(userInputBitLengthInt);
        BigInteger q = rsa.getPrimeQ(userInputBitLengthInt);
   
        println("\nThese are the original RSA encryption keys (using " + userInputBitLengthInt + " encryption):\n");
        println("-----BEGIN RSA PRIVATE KEY-----");
        println("Prime P = " + p);
        println("Prime Q = " + q + "");
        println("-----END RSA PRIVATE KEY-----\n");
   
        EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
        BigInteger e = rsa.getE();
        BigInteger pq = rsa.getPQ(p, q);
        byte[] encrypted = encDecMeth.returnEncryptedMessage(userInput.getBytes(), p, q, e, pq);
        
        // MAY CHANGE THIS BACK TO DECRYPTED IF BYTE VALUE FAILS BUT WE SHOULD BE GOOD //
        println("Byte Value (Encrypted) = " + encDecMeth.bytesToString(encrypted) + "\n");
        
        byte[] decrypted = encDecMeth.returnDecryptedMessage(encrypted,rsa.getD(e, p, q), pq);
        String finalDecryptedString = new String(decrypted);
        println("Decrypted String = " + finalDecryptedString);	
		println("\nTime to run = " + (System.currentTimeMillis() - start) + "ms.");
        println("\nProgram ran without any issues - GG WP. EXIT CODE: 0.\nEXIT - FINAL TRANSMISSION. (M83 - INTRO).");
		
        Client client = new Client("127.0.0.1");
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.startRunning();
	}
	
	// METHODS FOR MAIN CLASS //
    
    // METHOD TO PRINT OUT A LINE IN WHITE // 
	public static void println(Object x) {
		System.out.println(x);
	}
	
	// METHOD TO PRINT OUT AN ERROR IN RED //
	public static void errorPrintln(Object x) {
		System.err.println(x);
	}
	
	// METHOD TO EXIT THE PROGRAM // 
	public static void exit(int x) {
		System.exit(x);
	}
}
