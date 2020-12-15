package multi;

public class Token {
    public int type;
    public String text;
    public int value;
    public Token(int type, String text) { this.type = type; this.text = text; this.value=0;}
    public Token(int type, String text,int value) { this.type = type; this.text = text; this.value=value;}
    public String toString() {

        if(text.length()>0){
            String tname = LookaheadLexer.tokenNames[type];
            return "<'"+text+"',"+tname+">";
        }else{
            String s = Integer.toString(value);
            String tname = LookaheadLexer.tokenNames[type];
            return "<'"+s+"',"+tname+">";
        }


    }
}
