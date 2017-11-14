public class Sieve{
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
	public int randPrimeBetween(int start, int end){
		int prime;
		do{
			prime = (int)(start+Math.random()*end);
		}while(!primeTest((long)prime));			
		return prime;
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
