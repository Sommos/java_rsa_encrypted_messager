//import java.security.SecureRandom;
public class PractiseSieve{
//	randomFunction(){
//	SecureRandom random=new SecureRandom();
//	byte[] values=new byte[20];
//	random.nextBytes(values);
//	System.out.println("random = "+random);
//	}
	public static void main(String[] args){
		while(true){
			long p=(long)(Math.random()*(1000000000-2)+2);
	        if(primeTest(p)){
	        System.out.println("prime p:"+p);
	        break;
	        }
		}
	    while(true){
	        long q=(long)(Math.random()*(1000000000-2)+2);
	        if(primeTest(q)){
	        	System.out.println("prime q:"+q);
	        	break;
	        }
	    }
	}
	private static boolean primeTest(long n){
		int i;
		for(i=2;i<=Math.sqrt(n);i++){
			if(n%i==0){
				return false;
			}
		}
	       return true;
	}
}