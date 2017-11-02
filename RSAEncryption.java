import java.lang.Math;
import java.math.BigDecimal;
import java.math.BigInteger;
public class RSAEncryption {
	public static void main(String[] args){
	int p=61;
	int q=53;
	int n=p*q;
	int pTotient=p-1;
	int qTotient=q-1;
	int finalTotient=pTotient*qTotient;
	int e=17;
	int d=0;
	int count=0;
	while ((e*d%3120)!=1){
		d=d+1;
		count=count+1;
	}
	System.out.println("n = "+n);
	System.out.println("final totient = "+finalTotient);
	System.out.println("calculation of e*d = "+e*d);
	System.out.println("d = "+d);
	int message=123;
//	public key
	Double c=java.lang.Math.pow(message,e);
	System.out.println(c);
	BigInteger f=new BigDecimal(c).toBigInteger();
	System.out.println(f);
	int ca=intC+15;
//private key
	}	
}
