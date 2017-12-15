package default_package;

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
}
