

public class PalindromeParser {

	String input;
	int pos;
	
	boolean parse(String input0) {
		input = input0 + "#";
		pos = 0;
		return S() && match('#');
	}
	
	// rekursiv Funktion
	boolean S() {
		if (next() == 'a')
			return match('a') && S() && match('a');
		else if (next() == 'b')
			return match('b') && S() && match('b');
		else if (next() == 'c')
			return match('c') && S() && match('c');
		else
			return match('x');
	}
	
	char next() {
		return input.charAt(pos);
	}
	
	boolean match(char c) {
		if (next() == c) {
			pos++;
			return true;
		}
		else
			return false;
	}
	
	public static void main(String[] args) {
		PalindromeParser p = new PalindromeParser();
		System.out.println(p.parse("abcxcba"));
		System.out.println(p.parse("acbaxabca"));
		System.out.println(p.parse("abba"));

	}

}
