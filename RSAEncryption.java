package default_package;

import java.math.BigInteger;

public class RSAEncryption {
	
	// THIS GETS THE FIRST PRIME (P) //
	protected int getPrimeP() {
		Sieve sieve = new Sieve();
		int p = sieve.randPrimeBetween(50, 60);
		return p;
	}
	
	// THIS GETS THE SECOND PRIME (Q) //
	protected int getPrimeQ() {
		Sieve sieve = new Sieve();
		int q = sieve.randPrimeBetween(50, 60);
		return q;
	}
	
	// THIS CALCULATES THE MULTIPLICATION OF P AND Q //
	public Integer getPQ(int p, int q) {
		Integer pq = p * q;
		return pq;
	}
	
	// THIS GETS THE TOTIENT OF PRIME P //
	public int getPrimePTotient(int p) {
		int pTotient = p-1;
		return pTotient;
	}
	
	// THIS GETS THE TOTIENT OF PRIME Q //
	public int getPrimeQTotient(int q) {
		int qTotient = q-1;
		return qTotient;
	}
	
	// THIS CALCULATES LCM //
	public long lcm(long a, long b) {
		return a * (b / gcd(a,b));
	}
	
	// THIS CALCULATES GCD //
	public long gcd(long a, long b) {
		while(b != 0){
			long temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}
	
	// THIS CALCULATES E FOR THE PROGRAM //
	protected int getE() {
		int e = 65537;
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
	public int getD(int e, int lcm){
		int d = 0;
		int count = 0;
		while((e * d % lcm) != 1){
			d = d + 1;
			count = count + 1;
			if((e * d % lcm) == 1){
				if(d > 1000000){
					Main.errorPrintln("Error Code : 0");
					Main.println(d);
					System.exit(0);
				}
			}
		}
		d = (d < 0) ? d * - 1 : d;
		return d;	
	}
	
	// THIS METHOD CASTS AN INT TO A BIGINTEGER DATA TYPE (FOR EASE OF USE) //
	public BigInteger castBigInteger(int number){
		BigInteger x = new BigInteger("" + number);
		return x;
	}
	
	// THIS METHOD POWERS AND MODS GIVEN VALUES //
	public BigInteger powerMod(BigInteger message, BigInteger e, BigInteger pq){
		BigInteger powerMod = message.modPow(e,
				pq);
		return powerMod; 
	}
}
