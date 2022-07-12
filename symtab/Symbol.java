package symtab;

/***
 * Excerpted from "Language Implementation Patterns",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/tpdsl for more book information.
***/
public class Symbol { // A generic programming language symbol
    public String name;      // All symbols at least have a name
    public String type;      //int,string,vec,list,return,function
    public String content;
    public int value;       //当符号的类型为list时，其value值存储的是个数
    public vec vecContent;

    public Symbol(String name, String type,  int value) {
        this.name = name;
        this.type = type;
        this.content = "";
        this.value = value;
    }
    public Symbol(String name, String type, String content) {
        this.name = name;
        this.type = type;
        this.content = content;
        this.value = 0;
    }
    public Symbol() { }

    public void setVecContent(vec vecContent) {
        this.vecContent = vecContent;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public Symbol(String name) { this.name = name; }
    public Symbol(String name, String type) {this(name); this.type = type;}
    public String getName() { return name; }
    public void copySymbol(Symbol sym){  //语句中变量赋值时使用
        this.vecContent = sym.vecContent;
        this.content = sym.content;
        this.value = sym.value;
        this.type = sym.type;
    }
    public String toString() {
        if ( type!=null ) return '<'+getName()+":"+type+ ':'+ value+':'+content+' '+ vecContent+'>';
        return getName();
    }
}
