package default_package;
import java.math.BigInteger;
public class RSAEncryption {
	public int getPrimeP() {
		Sieve sieve = new Sieve();
		int p = sieve.randPrimeBetween(50, 99);
		return p;
	}
	public int getPrimeQ() {
		Sieve sieve = new Sieve();
		int q = sieve.randPrimeBetween(50, 99);
		return q;
	}
	public Integer getPQ(int p, int q) {
		Integer pq = p * q;
		return pq;
	}
	public int getPrimePTotient(int p) {
		int pTotient = p-1;
		return pTotient;
	}
	public int getPrimeQTotient(int q) {
		int qTotient = q-1;
		return qTotient;
	}
	public long lcm(long a, long b) {
		return a * (b / gcd(a,b));
	}
	public long gcd(long a, long b) {
		while(b > 0){
			long temp = b;
			b = a % b;
			a = temp;
		}
		return a;
	}
	public int getE(int p, int q) {
		int e = 0;
		if((p > 17) || (q > 17)) {
			e = 17;
		}else{
			e = 7;
		}
		return e;
	}
	public int getD(int e, int lcm){
		int d = 0;
		int count = 0;
		while((e * d % lcm) != 1){
			d = d + 1;
			count = count + 1;
			if((e * d % lcm) == 1){
				d = (d < 0) ? d * - 1 : d;
				if(d > 10000){
					Main.println("Error Code : 0");
					Main.println(d);
					System.exit(0);
				}
			}
		}
		return d;	
	}
	public BigInteger castBigInteger(int number){
		BigInteger x = new BigInteger(""+number);
		return x;
	}
	public BigInteger powerMod(BigInteger message, BigInteger e, BigInteger pq){
		BigInteger powerMod = message.modPow(e,
				pq);
		return powerMod; 
	}
}
