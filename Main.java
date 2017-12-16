package encryption;

import java.math.BigInteger;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		// TRACKS TIME TO RUN IN MS //
		long start = System.currentTimeMillis();

		// GET USER INPUT MESSAGE //
		String userInput = JOptionPane.showInputDialog("Please enter the message you want encrypted");
		
		// TESTING STRINGS //
		println("String = " + userInput);
        RSAEncryption rsa = new RSAEncryption();
        BigInteger p = rsa.getPrimeP();
        println("Prime P = " + p);
        BigInteger q = rsa.getPrimeQ();
        println("Prime Q = " + q);
        EncryptionDecryptionMethods encDecMeth = new EncryptionDecryptionMethods();
        BigInteger e = rsa.getE();
        BigInteger pq = rsa.getPQ(p, q);
        byte[] encrypted = encDecMeth.returnEncryptedMessage(userInput.getBytes(), p, q, e, pq);
        // MAY CHANGE THIS BACK TO DECRYPTED IF BYTE VALUE FAILS //
        println("Byte Value = " + encDecMeth.bytesToString(encrypted));
        byte[] decrypted = encDecMeth.returnDecryptedMessage(encrypted,rsa.getD(e, p, q), pq);
        String finalDecryptedString = new String(decrypted);
        println("Decrypted String = " + finalDecryptedString);	
	
/*   	try {
    		int message = Integer.parseInt(userInput);
    		println("Original User Message = " + userInput + ".");
    		// WHEN USER PRESSES SEND BUTTON DO { //
    		byte[] messageBytes = userInput.getBytes();	
		RSAEncryption rsa = new RSAEncryption();
		BigInteger p = rsa.getPrimeP();
	
		+BigInteger q = rsa.getPrimeQ();
		while(p == q) {
			p = rsa.getPrimeP();
		}
			// TESTING PRINTS //
			println(Arrays.toString(messageBytes));
			println("p = " + p);
			println("q = " + q);
			println("pq = " + rsa.getPQ(p, q));
			BigInteger lcm = rsa.lcm(rsa.getPrimePTotient(p),rsa.getPrimeQTotient(q));
			println("d = " + rsa.getD(rsa.getE(),lcm,p,q));

			EncryptionDecryptionMethods encDecMethods = new EncryptionDecryptionMethods();
			BigInteger encryptedMessage = encDecMethods.returnEncryptedMessage(message, p, q);
			BigInteger decryptedMessage = encDecMethods.returnDecryptedMessage(encryptedMessage, p, q);
			exit(0);
			// } THIS //
		} catch (Exception e) {
			println("ERROR CODE : 0\n");
			println("PLEASE, RESTART THE PROGRAM. A LOGIC FAULT HAS OCCURED.");
			exit(0);
		}	*/
        println("Time to run = " + (System.currentTimeMillis() - start) + "ms.");
        println("Program run without any issues - GG.\n\nEXIT - FINAL TRANSMISSION.");
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
