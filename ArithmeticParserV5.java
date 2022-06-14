import java.util.HashMap;

public class ArithmeticParserV5 {

	// attributes
	String input;
	int pos;
	
	HashMap<String, Integer> variableHashTable = new HashMap<String, Integer>();
	
	// grammar
	// S -> let D in E | E
	// D -> Key = Value {, Key = Value}
	
	
	// methods
	boolean isDigit(char c) {
		return '0' <= c && c <= '9';
	}
	
	
	boolean isLetter(char c) {
		return 'A' <= Character.toUpperCase(c) && Character.toUpperCase(c) <= 'Z';
	}
	
	
	// variable name can only contain letter, number and underscore ('_')	
	boolean isCharacterValidForVariableName(char c) {
		return isLetter(c) || isDigit(c) || c == '_';
	}
	
	
	int parse(String input0) {
		// clear white space
		// \\s: white space
		this.input = input0.replaceAll("\\s", "") + "#";
		this.pos = 0;
		int res = 0;
		
		// letx1=1,x2=2inx1+x2
		// ==: reference equal, equals(): value equal
		// S -> let D in E | E
		if (next() == 'l') {
			match('l'); match('e'); match('t');
			D();
			match('i'); match('n');
			res = E();
			match('#');
		}
		// 43*125
		else {
			res = E();	match('#');
		}
		
		return res;
		
	}
	
	
	void D() {
		String key = "";
		int value;
		key = Key(); match('='); value = Value();
		variableHashTable.put(key, value);
		
		while (next() == ',') {
			match(',');
			key = Key(); match('='); value = Value();
			variableHashTable.put(key, value);
		}
	} 
	
	
	String Key() {
		String res = "";
		
		// variable can only start with a letter
		if (isLetter(next())) {			
			while (next() != '=') {
				// variable name can only contain letter, number and underscore ('_')
				if (isCharacterValidForVariableName(next())) {
					res += Character.toString(next());
					pos++;
				}
				else {
					throw new IllegalArgumentException(next() + " unexpected, variable name can only contain letter, digit and underscore!");
				}
			}
		}	
		else {
			throw new IllegalArgumentException(next() + " unexpected, variable name can only start with a letter!");
		}
		
		return res;
	}
	
	
	int Value() {
		int res = 0;
		int sign = 1;
		
		// sign
		if (next() == '-') {
			match('-');
			sign = -1;
		}
		
		do {
			res = 10 * res + Num();
		} while (next() != 'i' && next() != ',');
		// i in 'in'
		
		return res * sign;
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
			res = NumOrVar();
		
		return res;
	}
	
	
	int NumOrVar() {

		int res = 0;
		int sign = 1;
		String tempString = "";
		
		// sign
		if (next() == '-') {
			match('-');
			sign = -1;
		}
		
		// digit (> 9)
		if (isDigit(next())) {
			do {
				res = 10 * res + Num();
			} while (next() != '+' && next() != '-' && next() != '*' 
					&& next() != '/' && next() != ')' && 
					next() != '#');
			
			return res * sign;
		}
		// variable
		else {
			// parse key
			while (next() != '+' && next() != '-' && next() != '*' 
					&& next() != '/' && next() != ')' && 
					next() != '#') {
				tempString += Character.toString(next());
				pos++;
			}
			return variableHashTable.get(tempString);
		}
		
	}
	
	
	int Num() {
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
		ArithmeticParserV5 p = new ArithmeticParserV5();
			
		// 696 true
		System.out.println(p.parse("let x1 = 42, x2 = -1, y = 17 in y*(x1+x2) - 1"));
		// 7212 true
		System.out.println(p.parse("12*(34+567)"));
		
		// test variable name
		// false !
		//System.out.println(p.parse("let x!2=10 in x!2"));
		// false 1
		//System.out.println(p.parse("let 1+1=6 in 1+1+3"));		
		
		// key not in hash table
		// false, NullPointerException
		//System.out.println(p.parse("let x=1, y=2 in z+2"));
		
	}

}
