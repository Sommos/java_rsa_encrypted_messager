package default_package;

import java.math.BigInteger;
import java.util.Arrays;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		String userInput = JOptionPane.showInputDialog("Please enter your message");
		try {
		int message = Integer.parseInt(userInput);
		Main.println("Original User Message = " + message + ".");
		// WHEN USER PRESSES SEND BUTTON DO { //
		byte[] messageBytes = userInput.getBytes();
		RSAEncryption rsa = new RSAEncryption();
		int p = rsa.getPrimeP();
		int q = rsa.getPrimeQ();
		while(p == q) {
			p = rsa.getPrimeP();
		}
			// TESTING PRINTS //
			println(Arrays.toString(messageBytes));
			println("p = " + p);
			println("q = " + q);
			println("pq = " + rsa.getPQ(p, q));
			int lcm = (int) rsa.lcm(rsa.getPrimePTotient(p),rsa.getPrimeQTotient(q));
			println("d = " + rsa.getD(rsa.getE(),lcm,p,q));

			EncryptionDecryptionMethods encDecMethods = new EncryptionDecryptionMethods();
			BigInteger encryptedMessage = encDecMethods.returnEncryptedMessage(message, p, q);
			BigInteger decryptedMessage = encDecMethods.returnDecryptedMessage(encryptedMessage, p, q);
			exit(0);
			// } THIS //
		} catch (Exception e){
			println("ERROR CODE : 0\n");
			println("PLEASE, RESTART THE PROGRAM. A LOGIC FAULT HAS OCCURED.");
			exit(0);
		}
	}
	// METHODS FOR MAIN CLASS // 
	static void println(Object x) {
		System.out.println(x);
	}
	static void errorPrintln(Object x) {
		System.err.println(x);
	}
	static void exit(int x) {
		System.exit(x);
	}
}
