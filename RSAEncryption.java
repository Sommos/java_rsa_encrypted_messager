package encryption;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAEncryption {
	// THIS GETS THE FIRST PRIME (P) //
	protected BigInteger getPrimeP(int bitLength) {
//		Sieve sieve = new Sieve();
//		int p = sieve.randPrimeBetween(1000000000, 999999999);
		int finalBitLength = bitLength;
		SecureRandom random = new SecureRandom();
		BigInteger p = BigInteger.probablePrime(finalBitLength, random);
		// DEFAULT VALUE USED FOR TESTING BASIC LOGIC //
//		int p = 61;
		return p;
	}
	
	// THIS GETS THE SECOND PRIME (Q) //
	protected BigInteger getPrimeQ(int bitLength) {
//		Sieve sieve = new Sieve();
//		int q = sieve.randPrimeBetween(1000000000, 999999999);
		int finalBitLength = bitLength;
		SecureRandom random = new SecureRandom();
		BigInteger q = BigInteger.probablePrime(finalBitLength, random);
		// DEFAULT VALUE USED FOR TESTING BASIC LOGIC //
//		int q = 53;
		return q;
	}
	
	// THIS CALCULATES THE MULTIPLICATION OF P AND Q //
	protected BigInteger getPQ(BigInteger p, BigInteger q) {
		BigInteger pq = p.multiply(q);
		return pq;
	}
	
	// THIS GETS THE TOTIENT OF PRIME P //
	protected BigInteger getPrimePTotient(BigInteger p) {
		BigInteger one = new BigInteger("" + 1);
		BigInteger pTotient = p.subtract(one);
		return pTotient;
	}
	
	// THIS GETS THE TOTIENT OF PRIME Q //
	protected BigInteger getPrimeQTotient(BigInteger q) {
		BigInteger one = new BigInteger("" + 1);
		BigInteger qTotient = q.subtract(one);
		return qTotient;
	}
	
	// THIS CALCULATES THE TOTIENTS MULTIPLED //
	protected int getMultipliedTotients(int primeP, int primeQ) {
		int multipliedTotients = primeP * primeQ;
		return multipliedTotients;
	}
	
	// THIS CALCULATES LCM //
	protected BigInteger lcm(BigInteger a, BigInteger b) {
		return a.multiply(b.divide(gcd(a,b)));
	}
	
	// THIS CALCULATES GCD //
	protected BigInteger gcd(BigInteger a, BigInteger b) {
		BigInteger zero = new BigInteger("" + 0);
		while(!b.equals(zero)){
			BigInteger temp = b;
			b = a.mod(b);
			a = temp;
		}
		return a;
	}
	
	// THIS CALCULATES E FOR THE PROGRAM //
	protected BigInteger getE() {
		BigInteger e = new BigInteger("" + 65537);
		return e;
// LEAVE THIS ALONE FOR THE MOMENT - MAY NEED IT LATER //
/*		int e = 0;
		for (e=0; e<lcm; e++){
			
		} 
		if((p > 17) || (q > 17)) {
			e = 257;
		}else{
			e = 7;
		}
		return e; */		
	}
	
	// THIS CALCULATES D FOR THE PROGRAM //
	protected BigInteger getD(BigInteger e, BigInteger p, BigInteger q) {
// MIGHT NEED THIS LATER // 
/*		int multipliedTotients = getMultipliedTotients(getPrimePTotient(p), getPrimeQTotient(q));
		int d = 1;
		int de = e * d;
		int x =  % multipliedTotients;
		Main.println(x);
		//int y = 1 + ()%
		//int x = 
		d = 0;
		int count = 0;
		while((e * d % lcm) != 1) {
			d = d + 1;
			count = count + 1;
			if((e * d % lcm) == 1) {
				if(d > 1000000) {
					Main.errorPrintln("Error Code : 0");
					Main.println(d);
					System.exit(0);
				}
			}
		}
		d = (d < 0) ? d * - 1 : d;
*/
		BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
		BigInteger d = e.modInverse(phi);
		return d;

	}
	
	// THIS METHOD CASTS AN INT TO A BIGINTEGER DATA TYPE (FOR EASE OF USE) //
	public BigInteger castBigInteger(int number) {
		BigInteger x = new BigInteger("" + number);
		return x;
	}
	
	// THIS METHOD POWERS AND MODS GIVEN VALUES //
	public BigInteger powerMod(BigInteger message, BigInteger e, BigInteger pq) {
		BigInteger powerMod = message.modPow(e,
				pq);
		return powerMod; 
	}
}
