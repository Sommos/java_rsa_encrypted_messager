package default_package;
import java.math.BigInteger;
public class EncryptionDecryptionMethods {
	public static BigInteger returnEncryptedMessage(int message, int p, int q){
		RSAEncryption rsa = new RSAEncryption();
		BigInteger bigIntegerMessage = rsa.castBigInteger(message);
		BigInteger bigIntegerE = rsa.castBigInteger(rsa.getE(p,q));
		BigInteger bigIntegerPQ = rsa.castBigInteger(rsa.getPQ(p,q));
		BigInteger encryptedMessage = rsa.powerMod(bigIntegerMessage, bigIntegerE, bigIntegerPQ);
		Main.println("Encrypted Message = "+ encryptedMessage);
		return encryptedMessage;
	}
	public static BigInteger returnDecryptedMessage(BigInteger encryptedMessage, int p, int q){
		RSAEncryption rsa = new RSAEncryption();
		BigInteger bigIntegerPQ = rsa.castBigInteger(rsa.getPQ(p,q));
		int lcm = (int) rsa.lcm(rsa.getPrimePTotient(p),rsa.getPrimeQTotient(q));
		BigInteger bigIntegerD = rsa.castBigInteger(rsa.getD(rsa.getE(p, q), lcm));
		BigInteger decryptedMessage = rsa.powerMod(encryptedMessage, bigIntegerD, bigIntegerPQ);
		Main.println("Decrypted Message = "+decryptedMessage);
		return decryptedMessage;
	}
}
