public class ArithmeticParserV4 {

	// attributes
	String input;
	int pos;
	// variables
	int x = 10;
	//int y = 11;
	int z = 12;
	int x1 = 42;
	int x2 = -1;
	int y = 17;
	
	// methods
	int parse(String input0) {
		// \\s: white space
		this.input = input0.replaceAll("\\s", "") + "#";
		this.pos = 0;
		int res = E();	match('#');
		return res;
	}
	
	
	int E() {
		int res = T();
		while (next() == '+' || next() == '-') {
			if (next() == '+') {
				match('+');
				res += T();
			}
			else {
				match('-');
				res -= T();
			}
		}
		return res;
	}
	
	
	int T() {
		int res = F();
		while (next() == '*' || next() == '/') {
			if (next() == '*') {
				match('*');
				res *= F();
			}
			else {
				match('/');
				res /= F();
			}
		}
		return res;
	}
	
	
	int F() {
		int res;
		if (next() == '(') {
			match('(');	res = E();	match(')');
		}
		else
			res = Num1();
		return res;
	}
	
	
	boolean isDigit(char c) {
		return '0' <= c && c <= '9';
	}
	
	
	boolean isXYZ(char c) {
		return c == 'x' || c == 'y' || c == 'z';
	}
	
	
	int Num1() {
		int res = 0;
		
		// digit > 9
		if (isDigit(next())) {
			//while (pos + 1 < input.length() && isDigit(input.charAt(pos + 1))) {
			while (isDigit(input.charAt(pos + 1))) {
				res = 10 * res + Num2();
			}
			// last position from input
			res = 10 * res + Num2();
			
			return res;
		}
		// x1, x2
		else if (isXYZ(next()) && isDigit(input.charAt(pos + 1))) {
			pos++;
			switch(next()) {
				case '1': match('1'); return x1;
				case '2': match('2'); return x2;
			}	
		}
		// 
		else {
			return Num2();
		}
		
		return res;
	}

	
	int Num2() {
		switch(next()) {
			case '0': match('0'); return 0;
			case '1': match('1'); return 1;
			case '2': match('2'); return 2;
			case '3': match('3'); return 3;
			case '4': match('4'); return 4;
			case '5': match('5'); return 5;
			case '6': match('6'); return 6;
			case '7': match('7'); return 7;
			case '8': match('8'); return 8;
			case '9': match('9'); return 9;
			case 'x': match('x'); return x;
			case 'y': match('y'); return y;
			case 'z': match('z'); return z;
			default: throw new IllegalArgumentException(next() + " unexpected");
		}
		
	}
	
	
	char next() {
		return input.charAt(pos);
	}
	
	void match(char c) {
		if (next() == c) {
			pos++;
		}
		else
			throw new IllegalArgumentException(c + " unexpected");
	}
	
	public static void main(String[] args) {
		ArithmeticParserV4 p = new ArithmeticParserV4();
		
		// test
		// 1234 true
		//System.out.println(p.parse("1234"));
		// 42 true
		//System.out.println(p.parse("x1"));
		// -1 true
		//System.out.println(p.parse("x2"));
		// a unexpected true
		//System.out.println(p.parse("a"));
		// 2 true
		//System.out.println(p.parse("38/(17+2)"));
		// 696 true
		//System.out.println(p.parse("y*(x1+x2)-1"));
		// 7212 true
		//System.out.println(p.parse("12*(34+567)"));
		// 696 with white space
		System.out.println(p.parse("y*(x1+x2) - 1"));
	}

}
