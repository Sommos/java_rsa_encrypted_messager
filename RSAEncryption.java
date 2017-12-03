package default_package;
import java.math.BigInteger;
public class RSAEncryption{
	public static void main(String[] args){
	}
	public int getPrimeP(){
		Sieve sieve=new Sieve();
		int p=sieve.randPrimeBetween(50, 99);
		return p;
	}
	public int getPrimeQ(){
		Sieve sieve=new Sieve();
		int q=sieve.randPrimeBetween(50, 99);
		return q;
	}
	public Integer getPQ(int p, int q){
		Integer pq=p*q;
		return pq;
	}
	public int getPrimePTotient(int p){
		int pTotient=p-1;
		return pTotient;
	}
	public int getPrimeQTotient(int q){
		int qTotient=q-1;
		return qTotient;
	}
	public long lcm(long a, long b){
		return a*(b/gcd(a,b));
	}
	public long gcd(long a, long b){
		while(b>0){
			long temp=b;
			b=a%b;
			a=temp;
		}
		return a;
	}
	public int getE(int p, int q){
		int e=0;
		if((p>17)||(q>17)){
			e=17;
		}else{
			e=7;
		}
		return e;
	}
	public int getD(int e, int lcm){
		int d=0;
		int count=0;
		while((e*d%lcm)!=1){
			d=d+1;
			count=count+1;
		}
		d=(d<0)?d*-1:d;
		if(d>3000){
			Main.println("Error Code : 0");
			Main.println(d);
			System.exit(0);
		}
		Main.println(d);
		return d;	
	}
	public BigInteger returnEncryptedMessage(int message){
		BigInteger message0=new BigInteger(""+message);
		BigInteger e0=new BigInteger(""+getE(getPrimeP(),getPrimeQ()));
		BigInteger pq0=new BigInteger(""+getPQ(getPrimeP(),getPrimeQ()));
		BigInteger encryptedMessage=message0.modPow(e0,
				pq0);
		Main.println("Encrypted Message = "+encryptedMessage);
		return encryptedMessage;
	}
	public BigInteger returnDecryptedMessage(BigInteger encryptedMessage){
		// THE LINE BELOW GENERATES NEW VERSIONS OF THE PRIME NUMBERS, THEY NEED TO BE THE SAME AS THE ONES INSTANTIATED ABOVE //
		BigInteger pq0=new BigInteger(""+getPQ(getPrimeP(),getPrimeQ()));
		int lcm=(int)lcm(getPrimePTotient(getPrimeP()),getPrimeQTotient(getPrimeQ()));
		BigInteger d0=new BigInteger(""+getD(getE(getPrimeP(), getPrimeQ()),lcm));
		BigInteger decryptedMessage=encryptedMessage.modPow(d0,
				pq0);
		Main.println("Decrypted Message = "+decryptedMessage);
		return decryptedMessage;
	}
}
