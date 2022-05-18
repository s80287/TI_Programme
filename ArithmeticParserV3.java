public class ArithmeticParserV3 {

	// attributes
	String input;
	int pos;
	// variables
	int x = 10;
	int y = 11;
	int z = 12;
	
	
	// methods
	int parse(String input0) {
		this.input = input0 + "#";
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
			res = Num();
		return res;
	}
	
	int Num() {
		switch(next()) {
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
		ArithmeticParserV3 p = new ArithmeticParserV3();
		// 1	true
		System.out.println(p.parse("(1+2)/3"));
		// 2	true
		System.out.println(p.parse("(1+2*3+1)/(2*2)"));
		// 2	true
		System.out.println(p.parse("(x+y-1)/(z-2)"));
	}

}
