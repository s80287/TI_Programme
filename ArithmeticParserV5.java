import java.util.HashMap;

public class ArithmeticParserV5 {

	// attributes
	String input;
	int pos;

	// grammar
	// S -> let D in E | E
	// D -> Key = Value {, Key = Value}
	
	
	// methods
	int parse(String input0, HashMap<String, Integer> variableHashTable) {
		// \\s: white space
		this.input = input0.replaceAll("\\s", "") + "#";
		this.pos = 0;
		int res = 0;
		
		//res = E(variableHashTable);	match('#');
		
		// letx1=1,x2=2inx1+x2
		// ==: reference equal, equals(): value equal
		// S -> let D in E | E
		if (next() == 'l') {
			match('l'); match('e'); match('t');
			D(variableHashTable);
			match('i'); match('n');
			res = E(variableHashTable);
			match('#');
		}
		// 43*125
		else {
			res = E(variableHashTable);	match('#');
		}
		
		return res;
		
	}
	

	void D(HashMap<String, Integer> variableHashTable) {
		String key = "";
		int value;
		key = Key(variableHashTable); match('='); value = Value(variableHashTable);
		variableHashTable.put(key, value);
		
		while (next() == ',') {
			match(',');
			key = Key(variableHashTable); match('='); value = Value(variableHashTable);
			variableHashTable.put(key, value);
		}
	}
	
	
	String Key(HashMap<String, Integer> variableHashTable) {
		String res = "";
		
		while (next() != '=') {
			res += Character.toString(next());
			pos++;
		}
		
		return res;
	}
	
	int Value(HashMap<String, Integer> variableHashTable) {
		int res = 0;
		int sign = 1;
		
		// sign
		if (next() == '-') {
			match('-');
			sign = -1;
		}
		
		do {
			res = 10 * res + Num2();
		} while (next() != 'i' && next() != ',');
		
		return res * sign;
	}
	
	
	
	int E(HashMap<String, Integer> variableHashTable) {
		int res = T(variableHashTable);
		
		while (next() == '+' || next() == '-') {
			if (next() == '+') {
				match('+');
				res += T(variableHashTable);
			}
			else {
				match('-');
				res -= T(variableHashTable);
			}
		}
		
		return res;
	}
	
	
	int T(HashMap<String, Integer> variableHashTable) {
		int res = F(variableHashTable);
		
		while (next() == '*' || next() == '/') {
			if (next() == '*') {
				match('*');
				res *= F(variableHashTable);
			}
			else {
				match('/');
				res /= F(variableHashTable);
			}
		}
		
		return res;
	}
	
	
	int F(HashMap<String, Integer> variableHashTable) {
		int res;
		
		if (next() == '(') {
			match('(');	res = E(variableHashTable);	match(')');
		}
		else
			res = Num1(variableHashTable);
		
		return res;
	}
	
	
	boolean isDigit(char c) {
		return '0' <= c && c <= '9';
	}
	
	
	int Num1(HashMap<String, Integer> variableHashTable) {

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
				res = 10 * res + Num2();
			} while (next() != '+' && next() != '-' && next() != '*' 
					&& next() != '/' && next() != ')' && 
					next() != '#');
			
			return res * sign;
		}
		// variable
		else {
			while (next() != '+' && next() != '-' && next() != '*' 
					&& next() != '/' && next() != ')' && 
					next() != '#') {
				tempString += Character.toString(next());
				pos++;
			}
			return variableHashTable.get(tempString);
		}
		
	}

	int Var(HashMap<String, Integer> variableHashTable, String variable) {
		return variableHashTable.get(variable);
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
			//case 'x': match('x'); return x;
			//case 'y': match('y'); return y;
			//case 'z': match('z'); return z;
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
		
		HashMap<String, Integer> variableHashMap = new HashMap<String, Integer>();
		
		//variables.put("x", 10);
		//variables.put("y", 17);
		//variables.put("z", 12);
		
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
		//System.out.println(p.parse("y*(x1+x2) - 1"));
	
		//System.out.println(p.parse("let x1=1,x2=2 in x1+x2"));
		
		// 12 true
		//System.out.println(p.parse("z", variables));
		
		// 25 true
		//System.out.println(p.parse("let x1=10, y1=15 in x1+y1", variables));
		
		// 696 true
		System.out.println(p.parse("let x1 = 42, x2 = -1, y = 17 in y*(x1+x2) - 1", variableHashMap));
		
		// 7212 true
		System.out.println(p.parse("12*(34+567)", variableHashMap));
	}

}
