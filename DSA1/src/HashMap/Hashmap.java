package HashMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Hashmap {

	public static void main(String[] args) {
//		String st1 = "anagram";
//		String st2 = "nagaram";
//		boolean st = isAnagram(st1,st2);
//		System.out.println(st);
		
		System.out.println(isHappy(19));
	}
	
	public static int DigSquare(int n) {
		int sum = 0;
		while (n > 0) {
			int dig = n % 10;
			sum += Math.pow(dig, 2);
			n /= 10;
		}
		return sum;
	}
	
	public static boolean isHappy(int n) {
		if (n == 1 || n == 7)
			return true;
		else if (n < 10)
			return false;
		else {
			n = DigSquare(n);
			return isHappy(n);
		}
	}
	
	public static boolean isAnagram(String s, String t) {
		if (s.length() != t.length())
			return false;
		char[] tr = t.toCharArray();
		char[] sr = s.toCharArray();
		Arrays.sort(tr); Arrays.sort(sr);
		String stt = new String(sr);
		String trr = new String(tr);
		if (stt.equals(trr))
			return true;
		return false;
	 }
	
	public static boolean Isomorphic(String s, String t) {
		HashMap<Character, Character> map = new HashMap<>();
		if (s.length() != t.length()) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			char key = s.charAt(i);
			char value = t.charAt(i);
			if (map.containsKey(key)) {
				if (!map.get(key).equals(value))
					return false;
			} else if (map.containsValue(value)) {
				return false;
			} else  {
				map.put(key, value);
			}
		}
		return true;
	}
	
	public static boolean Pattern(String s, String pattern) {
		HashMap<Character, String> map = new HashMap<>();
		String[] st = s.split(" ");
		if (st.length != pattern.length())
			return false;
		
		for (int i = 0; i < st.length; i++) {
			char ch = pattern.charAt(i);
			String val = st[i];
			if (map.containsKey(ch)) {
				if (!map.get(ch).equals(val))
					return false;
			} else if (map.containsValue(val)) {
				return false;
			} else {
				map.put(ch, val);
			}
		}	
		return true;
	}

}
