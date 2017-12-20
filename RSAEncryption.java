package encryption;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.swing.JOptionPane;

public class RSAEncryption {
	private BigInteger p;
	private BigInteger q;
	private BigInteger pq;
	private BigInteger pTotient;
	private BigInteger qTotient;
	private BigInteger e;
	private BigInteger d;
	private int bitLength;

	// This method sets the bit length //
	protected void setBitLength() {
		// Gets the bit length of the keys that the user wants to use //
		String userInputBitLengthString = JOptionPane.showInputDialog("Enter the bit length of the encryption keys (Binary Multiples)");
		// Parses the resulting String to an int //
		int userInputBitLengthInt = Integer.parseInt(userInputBitLengthString);
		this.bitLength = userInputBitLengthInt;
	}
	
	// This method gets the bit length //
	protected int getBitLength() {
		return bitLength;
	}
	
	// This method sets the value of prime p //
	protected void setPrimeP(int bitLength) {
		SecureRandom random = new SecureRandom();
		BigInteger p = BigInteger.probablePrime(bitLength, random);
		this.p = p;
	}
	
	// This methods gets the value of prime p //
	protected BigInteger getPrimeP() {
		return p;
	}
	
	// This method sets the value of prime q //
	protected void setPrimeQ(int bitLength) {
		SecureRandom random = new SecureRandom();
		BigInteger q = BigInteger.probablePrime(bitLength, random);
		this.q = q;
	}

	// This method gets the value of prime q //
	protected BigInteger getPrimeQ() {
		return q;
	}
	
	// This method calculates and sets the value of pq //
	protected void setPQ(BigInteger p, BigInteger q) {
		this.pq = p.multiply(q);
	}
	
	// This method gets the value of pq //
	protected BigInteger getPQ() {
		return pq;
	}

	// This sets the totient of prime p //
	protected void setPrimePTotient(BigInteger p) {
		BigInteger one = new BigInteger("" + 1);
		BigInteger pTotient = p.subtract(one);
		this.pTotient = pTotient;
	}
	
	// This gets the totient of prime p //
	protected BigInteger getPrimePTotient() {
		return pTotient;
	}
	
	// This sets the totient of prime q //
	protected void setPrimeQTotient(BigInteger q) {
		BigInteger one = new BigInteger("" + 1);
		BigInteger qTotient = q.subtract(one);
		this.qTotient = qTotient;
	}
	
	// This gets the totient of prime q //
	protected BigInteger getPrimeQTotient() {
		return qTotient;
	}
	
	// This sets the value of e //
	protected void setE() {
		BigInteger e = new BigInteger("" + 65537);
		this.e = e;
	}
	
	// This gets the value of e //
	protected BigInteger getE() {
		return e;
	}
	
	// This calculates and sets the value of d //
	protected void setD(BigInteger e, BigInteger primeP, BigInteger primeQ) {
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger d = e.modInverse(phi);
		this.d = d;
	}
	
	// This gets the value of d //
	protected BigInteger getD() {
		return d;
	}
	
	// This calculates the lowest common multiple of given values //
	protected BigInteger lcm(BigInteger PrimePTotient, BigInteger PrimeQTotient) {
		return PrimePTotient.multiply(PrimeQTotient.divide(gcd(PrimePTotient, PrimeQTotient)));
	}
	
	// This calculates the greatest common divisor of given values //
	protected BigInteger gcd(BigInteger a, BigInteger b) {
		BigInteger zero = new BigInteger("" + 0);
		while(!b.equals(zero)){
			BigInteger temp = b;
			b = a.mod(b);
			a = temp;
		}
		return a;
	}
	// Method that encrypts the users message //
	public byte[] returnEncryptedMessage(byte[] message, BigInteger p, BigInteger q, BigInteger e, BigInteger pq) {
		return (new BigInteger(message)).modPow(e, pq).toByteArray();
	}
	    
	// Method that decrypts the users message //
	public byte[] returnDecryptedMessage(byte[] message, BigInteger d, BigInteger pq) {
		return (new BigInteger(message)).modPow(d, pq).toByteArray();
	}
	
//	// Method that encrypts the users message //
//    public BigInteger returnEncryptedMessage(String message) {
//    	byte[] messageAsByteArray = message.getBytes();
//    	BigInteger messageAsBigInteger = new BigInteger(messageAsByteArray);
//    	BigInteger encryptedMessage = powerMod(messageAsBigInteger, getE(), getPQ());
//    	return encryptedMessage;
//    }
//    
//    // Method that decrypts the users message //
//    public BigInteger returnDecryptedMessage(BigInteger message) {
////    	byte[] messageAsByteArray = toByteArray(message);
//    	BigInteger decryptedMessageAsBigInteger = powerMod(message, d, pq);
////    	return message.modPow(getD(), getPQ());
////    	String decryptedMessageAsString = decryptedMessageAsBigInteger.toString();
////    	char decryptedMessageAsChar = decryptedMessageAsString.charAt(0);
////    	String finalDecryptedMessageAsString = Character.toString((char) decryptedMessageAsChar);
////    	return finalDecryptedMessageAsString;
//    	return decryptedMessageAsBigInteger;
//    }
    
//    // Method that returns the byte value of the encrypted message //
//    protected BigInteger encryptedByteMethod(String userInput) {
//    	BigInteger encrypted = returnEncryptedMessage(userInput);
//    	return encrypted;
//    }
//    
//    // Method that returns the byte value of the decrypted message //
//    protected BigInteger decryptedByteMethod(BigInteger encrypted) {
//    	BigInteger decrypted = returnDecryptedMessage(encrypted);
//    	return decrypted;
//    }
//    
//    // Method that returns the encrypted message //
//    protected BigInteger encrypt(String userInput) {
//    	BigInteger encrypted = returnEncryptedMessage(userInput);
//        return encrypted;
//    }
//    	
//    // Method that returns the decrypted message //
//    protected BigInteger decrypt(BigInteger encryptedString) {
//    	BigInteger decrypted = returnDecryptedMessage(encryptedString);
//    	return decrypted;
//    }
    	
    // Method that converts byte array to a String //
    public String bytesToString(byte[] encrypted) {
    	String test = "";
    	for (byte b : encrypted) {
    		test += Byte.toString(b);
      	}
    	return test;
    }
    
    // Method to convert an object to a byte array //
    public byte[] toByteArray(Object obj) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } finally {
            if (oos != null) {
                oos.close();
            }
            if (bos != null) {
                bos.close();
            }
        }
        return bytes;
    }
	
	// This method casts an int to a BigInteger //
	public BigInteger castBigInteger(int number) {
		BigInteger x = new BigInteger("" + number);
		return x;
	}
	
	// This method powers and modulos the given values //
	public BigInteger powerMod(BigInteger message, BigInteger e, BigInteger pq) {
		BigInteger powerMod = message.modPow(e,
				pq);
		return powerMod; 
	}
}
