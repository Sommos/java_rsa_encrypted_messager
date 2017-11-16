import java.math.BigInteger;
public class SimpleRSA{
	public static void main(String[] args){
		long startTime=System.nanoTime();
		Sieve sieve=new Sieve();
		int p=sieve.randPrimeBetween(20, 50);
		int q=sieve.randPrimeBetween(20, 50);
		Integer pq=p*q;
		while(true){
			if((pq<0)||(p==q)){
				p=sieve.randPrimeBetween(20, 50);
				q=sieve.randPrimeBetween(20, 50);
				pq=p*q;
			}else{
				break;
			}
		}
		int pTotient=p-1;
		int qTotient=q-1;
		int x=Sieve.LCM(pTotient,qTotient);
		int e=0;
		if((p>17)||(q>17)){
			e=17;
		}else{
			e=7;
		}
		int d=0;
		int count=0;
		if(d<=0){
			while((e*d%x)!=1){
				d=d+1;
				count=count+1;
			}
		}
//		int ed=e*d;
//		println("p = "+p);
//		println("q = "+q);
//		println("pq = "+pq);
//		println("final totient(x) = "+x);
//		println("d = "+d);
//		println("calculation of ed = "+ed);
		int message=65;
		println("message = "+message);
		BigInteger message0=new BigInteger(""+message);
		BigInteger e0=new BigInteger(""+e);
		BigInteger pq0=new BigInteger(""+pq);
		BigInteger encryptedMessage=message0.modPow(e0,
	            pq0);
		println("ENCRYPTED MESSAGE: "+encryptedMessage);
		BigInteger d0=new BigInteger(""+d);
		BigInteger decryptedMessage=encryptedMessage.modPow(d0,
	            pq0);
		println("DECRYPTED MESSAGE: "+decryptedMessage);
		long endTime=System.nanoTime();
		println("This program took : "+(endTime-startTime)+" ns to run.");
		}	
	//METHODS USED IN THIS CLASS.
	static void println(Object x){
		System.out.println(x);
		}
}
