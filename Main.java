package default_package;

import java.util.Arrays;
import java.math.BigInteger;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		String userInput = JOptionPane.showInputDialog("Please enter your message");
//		try {
//		int message = Integer.parseInt(userInput);
//		println("Original User Message = " + userInput + ".");
		// WHEN USER PRESSES SEND BUTTON DO { //
//		byte[] messageBytes = userInput.getBytes();
        println("Encrypting String: " + userInput);
        println("String in Bytes: " + bytesToString(userInput.getBytes()));
        RSAEncryption rsa = new RSAEncryption();
        BigInteger p = rsa.getPrimeP();
        BigInteger q = rsa.getPrimeQ();
        EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
        byte[] encrypted = encDecMeth.returnEncryptedMessage(userInput.getBytes(), p, q);
        byte[] decrypted = encDecMeth.returnDecryptedMessage(encrypted,rsa.getD(rsa.getE(), p, q), rsa.getPQ(p, q));
        println("Decrypting Bytes: " + bytesToString(decrypted));
        println("Decrypted String: " + new String(decrypted));
//		RSAEncryption rsa = new RSAEncryption();
//		BigInteger p = rsa.getPrimeP();
//		BigInteger q = rsa.getPrimeQ();
//		while(p == q) {
//			p = rsa.getPrimeP();
//		}
//			// TESTING PRINTS //
//			println(Arrays.toString(messageBytes));
//			println("p = " + p);
//			println("q = " + q);
//			println("pq = " + rsa.getPQ(p, q));
//			BigInteger lcm = rsa.lcm(rsa.getPrimePTotient(p),rsa.getPrimeQTotient(q));
//			println("d = " + rsa.getD(rsa.getE(),lcm,p,q));
//
//			EncryptionDecryptionMethods encDecMethods = new EncryptionDecryptionMethods();
//			BigInteger encryptedMessage = encDecMethods.returnEncryptedMessage(message, p, q);
//			BigInteger decryptedMessage = encDecMethods.returnDecryptedMessage(encryptedMessage, p, q);
//			exit(0);
//			// } THIS //
//		} catch (Exception e) {
//			println("ERROR CODE : 0\n");
//			println("PLEASE, RESTART THE PROGRAM. A LOGIC FAULT HAS OCCURED.");
//			exit(0);
//		}
	}
	// METHODS FOR MAIN CLASS //
	// METHOD TO CONVERT BYTES TO A STRING //
    private static String bytesToString(byte[] encrypted) {
        String test = "";
        for (byte b : encrypted) {
            test += Byte.toString(b);
        }
        return test;
    }
    
    // METHOD TO PRINT OUT A LINE IN WHITE // 
	static void println(Object x) {
		System.out.println(x);
	}
	
	// METHOD TO PRINT OUT AN ERROR IN RED //
	static void errorPrintln(Object x) {
		System.err.println(x);
	}
	
	// METHOD TO EXIT THE PROGRAM // 
	static void exit(int x) {
		System.exit(x);
	}
}
