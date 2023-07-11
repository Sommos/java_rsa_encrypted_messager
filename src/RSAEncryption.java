package src;

import java.security.SecureRandom;
import javax.swing.JOptionPane;
import java.math.BigInteger;

public class RSAEncryption {
	static String userInput;
	static int bitLength;
	static BigInteger p;
	static BigInteger q;
	static BigInteger pq;
	static BigInteger pTotient;
	static BigInteger qTotient;
	static BigInteger e;
	static BigInteger d;
	
	// this method sets the user input //
	protected void setUserInput() {
		// gets the message from the user //
		String userInput = "";
		while (userInput.length() < 1 || userInput.length() >= 50) {
			userInput = JOptionPane.showInputDialog("Enter your message");
		}
		RSAEncryption.userInput = userInput;
	}
	
	// this method gets the user input //
	protected String getUserInput() {
		return userInput;
	}
	
	// this method sets the bit length //
	protected void setBitLength() {
		// gets the bit length of the keys that the user wants to use //
		String userInputBitLengthString = JOptionPane.showInputDialog("Enter the bit length of the encryption keys (Binary Multiples)");
		// parses the resulting String to an int //
		int userInputBitLengthInt = Integer.parseInt(userInputBitLengthString);
		RSAEncryption.bitLength = userInputBitLengthInt;
	}
	
	// this method sets the bit length to a default value //
	protected void setBitLengthDefault() {
		RSAEncryption.bitLength = 1024;
	}
	
	// this method gets the bit length //
	protected int getBitLength() {
		return bitLength;
	}
	
	// this method sets the value of prime p //
	protected void setPrimeP(int bitLength) {
		SecureRandom random = new SecureRandom();
		BigInteger p = BigInteger.probablePrime(bitLength, random);
		RSAEncryption.p = p;
	}
	
	// this method gets the value of prime p //
	protected BigInteger getPrimeP() {
		return p;
	}
	
	// this method sets the value of prime q //
	protected void setPrimeQ(int bitLength) {
		SecureRandom random = new SecureRandom();
		BigInteger q = BigInteger.probablePrime(bitLength, random);
		RSAEncryption.q = q;
	}

	// this method gets the value of prime q //
	protected BigInteger getPrimeQ() {
		return q;
	}
	
	// this method calculates and sets the value of pq //
	protected void setPQ(BigInteger p, BigInteger q) {
		BigInteger pq = p.multiply(q);
		RSAEncryption.pq = pq;
	}
	
	// this method gets the value of pq //
	protected BigInteger getPQ() {
		return pq;
	}

	// this sets the totient of prime p //
	protected void setPrimePTotient(BigInteger p) {
		BigInteger one = new BigInteger("" + 1);
		BigInteger pTotient = p.subtract(one);
		RSAEncryption.pTotient = pTotient;
	}
	
	// this gets the totient of prime p //
	protected BigInteger getPrimePTotient() {
		return pTotient;
	}
	
	// this sets the totient of prime q //
	protected void setPrimeQTotient(BigInteger q) {
		BigInteger one = new BigInteger("" + 1);
		BigInteger qTotient = q.subtract(one);
		RSAEncryption.qTotient = qTotient;
	}
	
	// this gets the totient of prime q //
	protected BigInteger getPrimeQTotient() {
		return qTotient;
	}
	
	// this sets the value of e //
	protected void setE() {
		BigInteger e = new BigInteger("" + 65537);
		RSAEncryption.e = e;
	}
	
	// this gets the value of e //
	protected BigInteger getE() {
		return e;
	}
	
	// this calculates and sets the value of d //
	protected void setD(BigInteger e, BigInteger primeP, BigInteger primeQ) {
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger d = e.modInverse(phi);
		RSAEncryption.d = d;
	}
	
	// this gets the value of d //
	protected BigInteger getD() {
		return d;
	}
	
	// this calculates the lowest common multiple of given values //
	protected BigInteger lcm(BigInteger PrimePTotient, BigInteger PrimeQTotient) {
		return PrimePTotient.multiply(PrimeQTotient.divide(gcd(PrimePTotient, PrimeQTotient)));
	}
	
	// this calculates the greatest common divisor of given values //
	protected BigInteger gcd(BigInteger a, BigInteger b) {
		BigInteger zero = new BigInteger("" + 0);
		while(!b.equals(zero)) {
			BigInteger temp = b;
			b = a.mod(b);
			a = temp;
		}
		return a;
	}
	
	// method that encrypts the users message //
	public byte[] returnEncryptedMessage(byte[] message, BigInteger p, BigInteger q, BigInteger e, BigInteger pq) {
		return (new BigInteger(message)).modPow(e, pq).toByteArray();
	}
	    
	// method that decrypts the users message //
	public byte[] returnDecryptedMessage(byte[] message, BigInteger d, BigInteger pq) {
		return (new BigInteger(message)).modPow(d, pq).toByteArray();
	}
    	
    // method that converts byte array to a String //
    public String bytesToString(byte[] encrypted) {
    	String test = "";
    	for (byte b : encrypted) {
			test += Byte.toString(b);
		}
		return test;
	}
	
	// this method casts an int to a BigInteger //
	public BigInteger castBigInteger(int number) {
		BigInteger x = new BigInteger("" + number);
		return x;
	}
	
	// this method powers and modulos the given values //
	public BigInteger powerMod(BigInteger message, BigInteger e, BigInteger pq) {
		BigInteger powerMod = message.modPow(e, pq);
		return powerMod; 
	}
}
