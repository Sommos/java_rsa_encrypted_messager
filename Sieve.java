package default_package;
public class Sieve{
	public static void main(String[] args){
	}
	public int randPrimeBetween(int start, int end){
		int prime;
		do{
			prime=(int)(start+Math.random()*end);
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
