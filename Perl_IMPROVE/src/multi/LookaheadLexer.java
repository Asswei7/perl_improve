package multi;

public class LookaheadLexer extends Lexer {
    public static int NAME = 2;
    public static int COMMA = 3;
    public static int LBRACK = 4;  //[
    public static int RBRACK = 5;   //]
    public static int EQUALS = 6;
    public static int VAR = 7;
    public static int SEMICOLON = 8;
    public static int INT = 9;
    public static int STR = 10;
    public static int LROUND = 11;     //(
    public static int RROUND = 12;     //)
    public static int DQUOTATION = 13;  //"
    public static int VECTOR = 14;
    public static int SUB = 15;
    public static int LBIG = 16;
    public static int RBIG = 17;
    public static int MUTI = 18;
    public static int PLUS = 19;
    public static int PRINT = 20;
    public static int LENGTH = 21;
    public static int PARAM = 22;
    public static int IF = 23;
    public static int ST = 24;
    public static int GT = 25;
    public static int COLON = 26;
    public static int FUNCTION = 27;
    public static int SUBSYM = 28;
    public static int RETURN = 29;
    public static int INTSTR = 30;
    public static int SUM = 31;
    public static int COMMENT = 32;
    public static String[] tokenNames =
        { "n/a", "<EOF>", "NAME", ",", "[", "]", "=" ,"var",";","int","str","(",")","\"","vec","sub","{","}","*","+","print","length","PARAM","if","<",">",":","function","-","return","INT"};
    public String getTokenName(int x) { return LookaheadLexer.tokenNames[x]; }
    public boolean haveEqual = false;  //是否已经出现等号，用以区分是变量名还是字符串的内容
    public boolean haveDQUOTATION = false;  //是否已经出现双引号，用以区分是整数还是字符串的整数
    public LookaheadLexer(String input) { super(input); }
    boolean isLETTER() { return c>='a'&&c<='z' || c>='A'&&c<='Z'; }
    boolean isNUM() {return c>='0' && c<='9';}
    public Token nextToken() {
        while ( c!=EOF) {
            switch ( c ) {
                case ' ': case '\t': case '\n': case '\r': WS(); continue;
                case ',' : consume(); return new Token(COMMA, ",");
                case '-' : consume(); return new Token(SUBSYM, "-");
                case ':' : consume(); return new Token(COLON, ":");
                case '[' : consume(); return new Token(LBRACK, "[");
                case ']' : consume(); return new Token(RBRACK, "]");
                case '>' : consume(); return new Token(GT, ">");
                case '<' : consume(); return new Token(ST, "<");
                case '=' : consume(); haveEqual = true;
                                        return new Token(EQUALS, "=");
                case ';' : consume(); return new Token(SEMICOLON, ";");
                case '(' : consume(); return new Token(LROUND, "(");
                case ')' : consume(); return new Token(RROUND, ")");
                case '"' : consume(); haveDQUOTATION = true;
                                        return new Token(DQUOTATION, "\"");
                case '{' : consume(); return new Token(LBIG, "{");
                case '}' : consume(); return new Token(RBIG, "}");
                case '*' : consume(); return new Token(MUTI, "*");
                case '+' : consume(); return new Token(PLUS, "+");
                case '/' : consume(); return new Token(COMMENT, "/");
                //case if(isNUM()):
                default:
                    if ( isLETTER()) return getName();
                    if (isNUM() && !haveDQUOTATION) return getValue();
                    if (isNUM() && haveDQUOTATION) return int2String();
                    throw new Error("invalid character: "+c);
            }
        }
        return new Token(EOF_TYPE,"<EOF>");
    }
    Token int2String(){
        int sum = 0;
        do{
            sum = sum*10 + c-'0';
            NUM();
        }while (isNUM());
        String s = String.valueOf(sum);
        return new Token(STR,s);
    }
    Token getValue(){
        int sum = 0;
        do{
            sum = sum*10 + c-'0';
            NUM();
        }while (isNUM());
        //System.out.println("sum:"+sum);
        return new Token(INT,"",sum);
    }
    /** name : LETTER+ ; // name is sequence of >=1 letter */
    Token getName() {
        StringBuilder buf = new StringBuilder();
        do { buf.append(c); consume(); } while ( isLETTER() || isNUM());
        if(buf.toString().equals("var")){
            return new Token(VAR,"var");
        }
        if(buf.toString().equals("return")){
            return new Token(RETURN,"return");
        }
        if(buf.toString().equals("SUM")){
            return new Token(SUM,"SUM");
        }
        if(buf.toString().equals("int")){
            return new Token(INTSTR,"INT");
        }
        if(buf.toString().equals("function")){
            return new Token(FUNCTION,"function");
        }
        if(buf.toString().equals("PARAM")){
            return new Token(PARAM,"param");
        }
        if(buf.toString().equals("if")){
            return new Token(IF,"if");
        }
        if(buf.toString().equals("vec")){
            //System.out.println("OK");
            return new Token(VECTOR,"vec");
        }
        if(buf.toString().equals("sub")){
            //System.out.println("OK");
            return new Token(SUB,"sub");
        }
        if(buf.toString().equals("length")){
            //System.out.println("OK");
            return new Token(LENGTH,"length");
        }
        if(buf.toString().equals("print")){
            //System.out.println("OK");
            return new Token(PRINT,"print");
        }
        if(!haveEqual || !haveDQUOTATION){
            return new Token(NAME, buf.toString());
        }
        else
            return new Token(STR, buf.toString());
    }

    /** LETTER   : 'a'..'z'|'A'..'Z'; // define what a letter is (\w) */
    void LETTER() {
        if ( isLETTER() ) consume();
        else throw new Error("expecting LETTER; found "+c);
    }
    void NUM(){
        if(isNUM()) consume();

    }

    /** WS : (' '|'\t'|'\n'|'\r')* ; // ignore any whitespace */
    void WS() {
        while ( c==' ' || c=='\t' || c=='\n' || c=='\r' ) advance();
    }
}
