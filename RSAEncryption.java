import java.math.BigInteger;
public class RSAEncryption {
	public static void main(String[] args){
	Sieve sieve=new Sieve();
	int p=sieve.randPrimeBetween(50000, 99999);
	int q=sieve.randPrimeBetween(50000, 99999);
	Integer pq=p*q;
	while(true){
		if(pq<0){
			p=sieve.randPrimeBetween(50000, 99999);
			q=sieve.randPrimeBetween(50000, 99999);
			pq=p*q;
		}else{
			break;
		}
	}
	int pTotient=p-1;
	int qTotient=q-1;
//	int finalTotient=pTotient*qTotient;
	int finalTotient=780;
	int e=0;
	if((p>17)||(q>17)){
		e=17;
	}else{
		e=7;
	}
	int d=0;
	int count=0;
	while ((e*d%finalTotient)!=1){
		d=d+1;
		count=count+1;
	}
	int ed=e*d;
	System.out.println("p = "+p);
	System.out.println("q = "+q);
	System.out.println("pq = "+pq);
	System.out.println("final totient = "+finalTotient);
	System.out.println("calculation of ed = "+ed);
	System.out.println("d = "+d);
	int message=65;
	System.out.println("message = "+message);
	BigInteger message0=new BigInteger(""+message);
	BigInteger e0=new BigInteger(""+e);
	BigInteger pq0=new BigInteger(""+pq);
	BigInteger encryptedMessage=message0.modPow(e0,
            pq0);
	System.out.println("ENCRYPTED MESSAGE: "+encryptedMessage);
	BigInteger d0=new BigInteger(""+d);
	BigInteger decryptedMessage=encryptedMessage.modPow(d0,
            pq0);
	System.out.println("DECRYPTED MESSAGE: "+decryptedMessage);
	}	
}
