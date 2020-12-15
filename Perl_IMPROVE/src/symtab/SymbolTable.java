package symtab;

import java.util.*;
public class SymbolTable { // single-scope symtab
    public Map<String, Symbol> symbols = new HashMap<String, Symbol>();
    public String scopeName;
    public String fatherScope ;
    // Satisfy Scope interface
    public String getScopeName() { return this.scopeName; }

    public void setFatherScope(String fatherScope) {
        this.fatherScope = fatherScope;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }

    public void define(Symbol sym) { symbols.put(sym.name, sym); }
    public Symbol resolve(String name) {
        if(symbols.get(name)==null){
            System.out.println("符号"+name+"在作用域内符号未定义！");
            return null;
        }else{
            return symbols.get(name);
        }
    }

    public String toString() { return getScopeName()+":"+symbols; }
}
