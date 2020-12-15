package multi;

public abstract class Parser {
    Lexer input;       // 待处理的词法单元来源
    Token[] lookahead; // 环形缓冲区
    int k;             // 向前看符号的个数
    int p = 0;         // 环形缓冲区装填下一个词法单元的位置
    public int n = 0;
    public Parser(Lexer input, int k, int n) {
        this.input = input;
        this.k = k;
        lookahead = new Token[k];           // 开辟数组，作为环形缓冲区
        for (int i=1; i<=k; i++) consume(); // 初始化环形缓冲区
    }
    public void consume() {
        lookahead[p] = input.nextToken();   // 在下一个位置上放入词法单元
        p = (p+1) % k;                      // 自增下标
    }
    public Token LT(int i) {return lookahead[(p+i-1) % k];} // 环式取值，得到其词法单元
    public int LA(int i) { return LT(i).type; }            //得到词法单元的类型
    public void match(int x) {
        if ( LA(1) == x ) consume();
        else throw new Error("expecting "+input.getTokenName(x)+
                             "; found "+LT(1));
    }
}
