import java.util.*;
public class HillNumber {

	static long[][] rise, fall;

	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		int t = scan.nextInt();
		rise = new long[10][72];
		fall = new long[10][72];
		for(int i = 0; i < rise.length; i++) {
			Arrays.fill(rise[i], -1);
			Arrays.fill(fall[i], -1);
		}
		for(int len = 1; len <= 71; len++) {
			rise[0][len] = fall[0][len] = 1;
			for(int i = 1; i < 10; i++) {
				rise(i, len);
				fall(i, len);
			}
		}
		for(int casenum = 1; casenum <= t; casenum++) {
			String s = scan.next();
			if(!is(s)) {
				System.out.println(-1);
				continue;
			}
			long ret = 0;
			for(int i = 1; i < s.length(); i++) {
				for(int j = 1; j <= 9; j++) {
					ret += rise(j, i);
				}
			}
			ret += count(s, -1, true, true);
			System.out.println(ret+1);
		}
	}

	public static long count(String s, int lastDigit, boolean first, boolean canRise) {
		if(s.length() == 1) return s.charAt(0) - '0';
		long ret = 0;
		for(int i = 0; i < s.charAt(0) - '0'; i++) {
			if(i > lastDigit) {
				if(i == 0 && first) continue;
				if(canRise) {
					ret += rise(i, s.length());
				}
			}
			else if(i == lastDigit) {
				if(canRise) {
					ret += rise(i, s.length());
				}
				else {
					ret += fall(i, s.length());
				}
			}
			else if(i < lastDigit) {
				ret += fall(i, s.length());
			}
		}
		ret += count(s.substring(1), s.charAt(0) - '0', false, canRise && s.charAt(0) <= s.charAt(1));
		return ret;
	}

	public static long rise(int dig, int len) {
		if(len == 1) {
			return 1;
		}
		if(rise[dig][len] >= 0) return rise[dig][len];
		rise[dig][len] = 1;
		for(int a = len-1; a >= 1; a--) {
			for(int b = dig+1; b < 10; b++) {
				rise[dig][len] += rise(b, a);
			}
			for(int b = dig-1; b >= 0; b--) {
				rise[dig][len] += fall(b, a);
			}
		}
		return rise[dig][len];
	}

	public static long fall(int dig, int len) {
		if(len == 1) {
			return 1;
		}
		if(fall[dig][len] >= 0) return fall[dig][len];
		fall[dig][len] = 1;
		for(int a = len-1; a >= 1; a--) {
			for(int b = dig-1; b >= 0; b--) {
				fall[dig][len] += fall(b, a);
			}
		}
		return fall[dig][len];
	}

	public static boolean is(String s) {
		int lastRise = 0;
		int firstFall = Integer.MAX_VALUE;
		for(int i = 0; i < s.length()-1; i++) {
			if(s.charAt(i) < s.charAt(i+1)) {
				lastRise = i;
			}
			if(s.charAt(i) > s.charAt(i+1)) {
				firstFall = Math.min(firstFall, i);
			}
		}
		return lastRise <= firstFall;
	}
}

