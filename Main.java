package default_package;

import java.math.BigInteger;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		String userInput = JOptionPane.showInputDialog("Please enter your message");
		int message = Integer.parseInt(userInput);
		Main.println("Original User Message = " + message + ".");
		// WHEN NEW MESSAGE IS ENTERED BY USER DO { //
		RSAEncryption rsa = new RSAEncryption();
		int p = rsa.getPrimeP();
		int q = rsa.getPrimeQ();
		while(p == q){
			p = rsa.getPrimeP();
		}
		EncryptionDecryptionMethods encDecMethods = new EncryptionDecryptionMethods();
		BigInteger encryptedMessage = encDecMethods.returnEncryptedMessage(message, p, q);
		BigInteger decryptedMessage = encDecMethods.returnDecryptedMessage(encryptedMessage, p, q);
		// } THIS //
	}
	// METHODS FOR MAIN CLASS // 
	static void println(Object x) {
		System.out.println(x);
	}
	static void errorPrintln(Object x) {
		System.err.println(x);
	}
}
