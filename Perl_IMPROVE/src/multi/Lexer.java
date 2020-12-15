package multi;

public abstract class Lexer {
    public static final char EOF = (char)-1; //  represent end of file char
    public static final int EOF_TYPE = 1;    //  represent EOF token type
    String input; // 输入的字符串
    int i = 0;    // 当前输入字符的下标
    char c;       // 当前的字符

    public Lexer(String input) {
        this.input = input;
        c = input.charAt(i); // 获取当前的字符
    }

    /** Move to next non-whitespace character */
    public void consume() {
        advance();
        //WS();
    }

    /** 向前移动一个字符，并看是否文件结束*/
    public void advance() {
        i++;
        if ( i>= input.length() ) c = EOF;
        else c = input.charAt(i);
    }

    /** Ensure x is next character on the input stream */
    public void match(char x) {
        if ( c == x) consume();
        else throw new Error("expecting "+x+"; found "+c);
    }

    public abstract Token nextToken();
    abstract void WS();
    public abstract String getTokenName(int x);
    
}
