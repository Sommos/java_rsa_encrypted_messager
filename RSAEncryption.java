import java.math.BigDecimal;
import java.math.BigInteger;
public class RSAEncryption {
	public static void main(String[] args){
//STEP ONE AND TWO
	int p=61;
	int q=53;
//STEP THREE
	Integer pq=p*q;
//STEP FOUR
	int pTotient=p-1;
	int qTotient=q-1;
//STEP FIVE
	int finalTotient=pTotient*qTotient;
//STEP SIX AND SEVEN
	int e=0;
	if((p>17)||(q>17)){
		e=17;
	}else{
		e=7;
	}
//STEP EIGHT AND NINE
	int d=0;
	int count=0;
	while ((e*d%finalTotient)!=1){
		d=d+1;
		count=count+1;
	}
//STEP TEN
	int ed=e*d;
	System.out.println("p = "+p);
	System.out.println("q = "+q);
	System.out.println("pq = "+pq);
	System.out.println("final totient = "+finalTotient);
	System.out.println("calculation of ed = "+ed);
	System.out.println("d = "+d);
//ENCRYPTION
	int message=123;
//PUBLIC KEY
	Double c=java.lang.Math.pow(message,e);
	BigInteger f=new BigDecimal(c).toBigInteger();
	System.out.println("our full public exponent = "+f);
	BigInteger g=BigInteger.valueOf(pq.intValue());
	BigInteger h=f.mod(g);
	System.out.println("our encrypted message = "+h);
//PRIVATE KEY
	int i=h.intValue();
//Another way of doing power
//	Double j=java.lang.Math.pow(i,d);
//	System.out.println(j);
	//1 way of doing power
//	BigInteger k=new BigDecimal(j).toBigInteger();
	System.out.println("our encrypted message as an int data type =  "+i);
	//another way of doing power
	BigInteger l=h.pow(d).add(BigInteger.ONE);
	System.out.println(l);
	//Could store it in an array and bring each integer out one by one, but the run and compile time would be ridiculous.
	BigInteger m=l.mod(g);
	System.out.println(m);
	}	
}
