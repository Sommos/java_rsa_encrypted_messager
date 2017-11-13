import java.math.BigDecimal;
import java.math.BigInteger;
public class RSAEncryption {
	public static void main(String[] args){
//STAGE ONE AND TWO
	Sieve sieve=new Sieve();
	int p=61;
	int q=53;
//STAGE THREE
	Integer pq=p*q;
//STAGE FOUR
	int pTotient=p-1;
	int qTotient=q-1;
//STAGE FIVE
//	int finalTotient=pTotient*qTotient;
	int finalTotient=780;
//STAGE SIX AND SEVEN
	int e=0;
	if((p>17)||(q>17)){
		e=17;
	}else{
		e=7;
	}
//STAGE EIGHT AND NINE
	int d=0;
	int count=0;
	while ((e*d%finalTotient)!=1){
		d=d+1;
		count=count+1;
	}
//STAGE TEN
	int ed=e*d;
	System.out.println("p = "+p);
	System.out.println("q = "+q);
	System.out.println("pq = "+pq);
	System.out.println("final totient = "+finalTotient);
	System.out.println("calculation of ed = "+ed);
	System.out.println("d = "+d);
//ENCRYPTION
	int message=65;
	System.out.println("message = "+message);
	BigInteger message0=new BigInteger(""+message);
	BigInteger e0=new BigInteger(""+e);
	BigInteger pq0=new BigInteger(""+pq);
//PUBLIC KEY
//	int result00 = (int)Math.pow(message, e);
//	System.out.println(result00);
//	int testing00=result00%3233;
//	System.out.println(testing00);
//	BigInteger asfd00=new BigInteger("65");
//	BigInteger testt00=asfd00.pow(17);
//	System.out.println("BigInteger Power Calc: "+testt00);
//	int to1003=testt00.intValue();
	BigInteger encryptedMessage=message0.modPow(e0,
            pq0);
	System.out.println("ENCRYPTED MESSAGE: "+encryptedMessage);
///	Double c=java.lang.Math.pow(message,e);
///	BigInteger f=new BigDecimal(c).toBigInteger();
///	System.out.println("our full public exponent = "+f);
///	BigInteger g=BigInteger.valueOf(pq.intValue());
///	BigInteger h=f.mod(g);
///	System.out.println("our encrypted message = "+h);
//PRIVATE KEY
	BigInteger d0=new BigInteger(""+d);
	BigInteger decryptedMessage=encryptedMessage.modPow(d0,
            pq0);
	System.out.println("DECRYPTED MESSAGE: "+decryptedMessage);
///	int i=h.intValue();
//Another way of doing power
//	Double j=java.lang.Math.pow(i,d);
//	System.out.println(j);
	//1 way of doing power
//	BigInteger k=new BigDecimal(j).toBigInteger();
///	System.out.println("our encrypted message as an int data type =  "+i);
	//another way of doing power
///	BigInteger l=h.pow(d).add(BigInteger.ONE);
///	System.out.println(l);
	//Could store it in an array and bring each integer out one by one, but the run and compile time would be ridiculous.
///	BigInteger m=l.mod(g);
///	System.out.println(m);
	}	
}
