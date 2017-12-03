package default_package;
import java.math.BigInteger;
import javax.swing.JOptionPane;
public class Main {
	public static void main(String[] args) {
		RSAEncryption rsa = new RSAEncryption();
		int p = rsa.getPrimeP();
		int q = rsa.getPrimeQ();
		while(p == q){
			p = rsa.getPrimeP();
		}
		// WHEN NEW MESSAGE IS ENTERED BY USER DO { //
		String userInput = JOptionPane.showInputDialog("Please enter your message");
		int message = Integer.parseInt(userInput);
		BigInteger encryptedMessage = EncryptionDecryptionMethods.returnEncryptedMessage(message, p, q);
		BigInteger decryptedMessage = EncryptionDecryptionMethods.returnDecryptedMessage(encryptedMessage, p, q);
		// } THIS //
	}
	// METHODS FOR MAIN CLASS // 
	static void println(Object x){
		System.out.println(x);
	}
}
