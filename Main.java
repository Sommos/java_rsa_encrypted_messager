package default_package;
import java.math.BigInteger;
import javax.swing.JOptionPane;
public class Main{
	public static void main(String[] args){
		RSAEncryption rsa=new RSAEncryption();
		// WHEN NEW MESSAGE IS ENTERED BY USER DO { //
		int p=rsa.getPrimeP();
		int q=rsa.getPrimeQ();
		while(p==q){
			p=rsa.getPrimeP();
		}
		println(p);
		println(q);
		String userInput=JOptionPane.showInputDialog("Please enter your message");
		int message=Integer.parseInt(userInput);
		BigInteger x00=returnEncryptedMessage(message, p, q);
		returnDecryptedMessage(x00, p, q);
		// } THIS //	
	}
	// METHODS FOR MAIN // 
	static void println(Object x){
		System.out.println(x);
	}
	public static BigInteger returnEncryptedMessage(int message, int p, int q){
		RSAEncryption rsa=new RSAEncryption();
		BigInteger message0=new BigInteger(""+message);
		BigInteger e0=new BigInteger(""+rsa.getE(p,q));
		BigInteger pq0=new BigInteger(""+rsa.getPQ(p,q));
		BigInteger encryptedMessage=message0.modPow(e0,
				pq0);
		Main.println("Encrypted Message = "+encryptedMessage);
		return encryptedMessage;
	}
	public static BigInteger returnDecryptedMessage(BigInteger encryptedMessage, int p, int q){
		RSAEncryption rsa=new RSAEncryption();
		BigInteger pq0=new BigInteger(""+rsa.getPQ(p,q));
		int lcm=(int)rsa.lcm(rsa.getPrimePTotient(p),rsa.getPrimeQTotient(q));
		BigInteger d0=new BigInteger(""+rsa.getD(rsa.getE(p, q), lcm));
		BigInteger decryptedMessage=encryptedMessage.modPow(d0,
				pq0);
		Main.println("Decrypted Message = "+decryptedMessage);
		return decryptedMessage;
	}
}
