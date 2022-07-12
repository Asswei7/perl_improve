package multi;

import symtab.Symbol;
import symtab.SymbolTable;
import symtab.vec;

/** */
public class LookaheadParser extends Parser {
    public LookaheadParser(Lexer input, int k, int n) { super(input, k, n); }
    public void vecPro(SymbolTable symbolTable, Symbol sym){
        match(LookaheadLexer.VECTOR);
        Token name = lookahead[p];    //得到的是NAME
        match(LookaheadLexer.NAME);
        sym.setName(name.text);
        match(LookaheadLexer.EQUALS);
        sym.setType("vec");
        if(LA(1)==LookaheadLexer.LROUND){
            match(LookaheadLexer.LROUND);
            int[] num = new int[20];
            int index = 0;
            while (LA(1) != LookaheadLexer.RROUND){  //下一个token不是)
                if(lookahead[p].type==9){  //int
                    num[index++] = lookahead[p].value;
                    match(LookaheadLexer.INT);
                }else if(lookahead[p].type==3){  //,
                    match(LookaheadLexer.COMMA);
                }
            }
            vec vecContent = new vec(num,index);
            //System.out.println(vecContent.toString());
            sym.setVecContent(vecContent);
        }else if(LA(1)==LookaheadLexer.NAME && LA(2)==LookaheadLexer.SEMICOLON){
            Symbol symA = symbolTable.resolve(lookahead[p].text);
            sym.copySymbol(symA);
        }else if(LA(1)==LookaheadLexer.NAME && LA(2)==LookaheadLexer.PLUS){
            Symbol symA = symbolTable.resolve(lookahead[p].text);
            match(LookaheadLexer.NAME);
            match(LookaheadLexer.PLUS);
            Symbol symB = symbolTable.resolve(lookahead[p].text);
            if(symA.type.equals("vec") && symB.type.equals("vec")){
                sym.setType("vec");
                sym.setVecContent(symA.vecContent.addVec(symB.vecContent));
            }else if(symA.type.equals("vec") && symB.type.equals("int")){
                sym.setType("vec");
                sym.setVecContent(symA.vecContent.addNum(symB.value));
            }else if(symA.type.equals("int") && symB.type.equals("vec")){
                sym.setType("vec");
                sym.setVecContent(symB.vecContent.addNum(symA.value));
            }else{
                System.out.println("加号两端类型不匹配！");
            }
        }else if(LA(1)==LookaheadLexer.NAME && LA(2)==LookaheadLexer.MUTI){
            Symbol symA = symbolTable.resolve(lookahead[p].text);
            match(LookaheadLexer.NAME);
            match(LookaheadLexer.MUTI);
            Symbol symB = symbolTable.resolve(lookahead[p].text);
            if(symA.type.equals("vec") && symB.type.equals("int")){
                sym.setVecContent(symA.vecContent.mutiBYnum(symB.value));
            }else if(symA.type.equals("int") && symB.type.equals("vec")){
                sym.setVecContent(symB.vecContent.mutiBYnum(symA.value));
            }else{
                System.out.println("乘号两端类型不匹配！");
            }
        }
    }

    public void symPro(SymbolTable symbolTable,Symbol sym){
        Token name = lookahead[p];
        sym.setName(name.text);
        //System.out.println("name:"+name.text);
        Symbol symA = symbolTable.resolve(name.text);
        if(symA==null) return;
        match(LookaheadLexer.NAME);
        match(LookaheadLexer.EQUALS);
        if(LA(1)==LookaheadLexer.INT && LA(2)==LookaheadLexer.SEMICOLON){
            if(symA.type.equals("int")){
                sym.setValue(lookahead[p].value);
                sym.setType("int");
                sym.setName(name.text);
            }else{
                System.out.println("类型不匹配！");
            }
        }else if(LA(1)==LookaheadLexer.INT && LA(2)==LookaheadLexer.SUBSYM){
            int valueA = lookahead[p].value;
            //System.out.println("value:"+valueA);
            match(LookaheadLexer.INT);
            match(LookaheadLexer.SUBSYM);
            match(LookaheadLexer.LROUND);
            int valueB = lookahead[p].value;
            match(LookaheadLexer.INT);
            match(LookaheadLexer.SUBSYM);
            //System.out.println("var:"+lookahead[p].text);
            Symbol symC = symbolTable.resolve(lookahead[p].text);
            int valueC = symC.value;
            //------------------------------------------
            //System.out.println("value:"+valueC);
            match(LookaheadLexer.NAME);
            int resValue = valueA-(valueB-valueC);
            sym.setValue(resValue);
            sym.setType("int");
            sym.setName(name.text);
        }else if(LA(1)==LookaheadLexer.NAME){
            Symbol symB = symbolTable.resolve(lookahead[p].text);
            if(symA.type.equals(symB.type)){
                sym.copySymbol(symB);
            }else{
                System.out.println("类型不匹配！");
            }
        }else if(LA(1)==LookaheadLexer.DQUOTATION){
            match(LookaheadLexer.DQUOTATION);
            if(symA.type.equals("string")){
                //System.out.println(lookahead[p].text);
                sym.content = lookahead[p].text;
                sym.setType("string");
            }else{
                System.out.println("类型不匹配！");
            }
        }else if(LA(1)==LookaheadLexer.LROUND){
            if(symA.type.equals("vec")){
                match(LookaheadLexer.LROUND);
                int[] num = new int[20];
                int index = 0;
                while (LA(1) != LookaheadLexer.RROUND){  //下一个token不是)
                    if(lookahead[p].type==9){  //int
                        num[index++] = lookahead[p].value;
                        match(LookaheadLexer.INT);
                    }else if(lookahead[p].type==3){  //,
                        match(LookaheadLexer.COMMA);
                    }
                }
                vec vecContent = new vec(num,index);
                //System.out.println(vecContent.toString());
                sym.setVecContent(vecContent);
                sym.setType("vec");
                //将右边的向量值赋给左边
            }else{
                System.out.println("类型不匹配！");
            }
        }
    }

    public void priPro(SymbolTable symbolTable){
        match(LookaheadLexer.PRINT);
        match(LookaheadLexer.LROUND);
        Token name = lookahead[p];
        //System.out.println("name:"+name.text);
        Symbol symA = symbolTable.resolve(name.text);
        System.out.println(symA);
    }
    public void subPro(SymbolTable innerTable,Symbol sym){
        match(LookaheadLexer.SUB);
        Token name = lookahead[p];
        sym.setName(name.text);
        sym.setType("function");
        match(LookaheadLexer.NAME);
        match(LookaheadLexer.LBIG);
    }

    public void varPro(SymbolTable symbolTable, Symbol sym,SymbolTable[] symbolTables){
        match(LookaheadLexer.VAR);
        Token name = lookahead[p];    //得到的是NAME
        match(LookaheadLexer.NAME);
        sym.setName(name.text);
        //System.out.println(lookahead[p].toString());
        match(LookaheadLexer.EQUALS);
        //System.out.println(lookahead[p].type);

        if(LA(1)==LookaheadLexer.INT){               //var a = 5;
            sym.setType("int");
            sym.setValue(lookahead[p].value);
            match(LookaheadLexer.INT);
        }else if(LA(1)==LookaheadLexer.DQUOTATION){   //var a = "qwer";
            match(LookaheadLexer.DQUOTATION);
            sym.setType("string");
            sym.setContent(lookahead[p].text);
        }else if(LA(1)==LookaheadLexer.SUM){     //var a = SUM(vec);
            //System.out.println("OK");
            match(LookaheadLexer.SUM);
            match(LookaheadLexer.LROUND);
            sym.setType("int");
            String varName = lookahead[p].text;
            Symbol symbol = symbolTable.resolve(varName);
            vec v = symbol.vecContent;
            int sum = v.getSum();
            //System.out.println(sum);
            sym.setValue(sum);
        }else if(LA(1)==LookaheadLexer.LROUND){      //var x = (1,2,(2,2),2,3,(2,3),"qwe");
            sym.setType("list");
            boolean innerList = false;
            list(innerList);
            sym.setValue(this.n);
        }else if(LA(1)==LookaheadLexer.NAME && LA(2)==LookaheadLexer.PLUS){
            //解析NAME变量，看其是否被定义，及其类型
            //System.out.println("OK");
            Symbol symA = symbolTable.resolve(lookahead[p].text);
            match(LookaheadLexer.NAME);
            match(LookaheadLexer.PLUS);
            Symbol symB = symbolTable.resolve(lookahead[p].text);
            if(symA.type.equals("int") && symB.type.equals("int")){
                sym.setType("int");
                sym.setValue(symA.value+symB.value);
            }else if(symA.type.equals("string") && symB.type.equals("string")){
                sym.setType("string");
                sym.setContent(symA.content + symB.content);
            }else{
                System.out.println("加号两端变量类型不匹配！");
            }
        }else if(LA(1)==LookaheadLexer.NAME && LA(2)==LookaheadLexer.MUTI){
            Symbol symA = symbolTable.resolve(lookahead[p].text);
            match(LookaheadLexer.NAME);
            match(LookaheadLexer.MUTI);
            //System.out.println(lookahead[p].text);
            Symbol symB = symbolTable.resolve(lookahead[p].text);
            if(symA.type.equals("int") && symB.type.equals("int")){
                sym.setType("int");
                sym.setValue(symA.value * symB.value);
            }else if(symA.type.equals("vec") && symB.type.equals("vec")){
                sym.setType("int");
                sym.setValue(symA.vecContent.mutiBYvec(symB.vecContent));
            }else{
                System.out.println("称号两端类型不匹配！");
            }
        }else if(LA(1)==LookaheadLexer.NAME && LA(2)==LookaheadLexer.SEMICOLON){
            //在函数符号表中找不到变量的定义
            //转到全局符号表中去寻找
            //-----------------------------------------
            //-------------------------------------
            Symbol symA = new Symbol();
            if(symbolTable.resolve(lookahead[p].text)==null){
                //System.out.println("OK");
                String fatherTable = symbolTable.fatherScope;
                //System.out.println("father:"+fatherTable);
                if(symbolTables[0].scopeName.equals(fatherTable)){
                    symA = symbolTables[0].resolve(lookahead[p].text);
                    //System.out.println("symA:"+symA.toString());
                }
            }else{
                symA = symbolTable.resolve(lookahead[p].text);
            }
            //System.out.println(symA);
            match(LookaheadLexer.NAME);
            match(LookaheadLexer.SEMICOLON);
            sym.copySymbol(symA);
        }else if(LA(1)==LookaheadLexer.LENGTH){
            sym.setType("int");
            match(LookaheadLexer.LENGTH);
            match(LookaheadLexer.LROUND);
            name = lookahead[p];
            //System.out.println(name.text);
            Symbol symA = symbolTable.resolve(name.text);
            if(symA.type.equals("int")) sym.setValue(1);
            if(symA.type.equals("vec")) sym.setValue(symA.vecContent.n);
            if(symA.type.equals("string")) sym.setValue(symA.content.length());
            if(symA.type.equals("list")) sym.setValue(symA.value);
        }else if(LA(1)==LookaheadLexer.PARAM){
            //System.out.println("OK");

            Symbol symA = symbolTables[0].resolve("PARAM");
            //System.out.println(symA.toString());
            sym.setType("int");
            sym.setValue(symA.value);
        } else if(LA(1)==LookaheadLexer.NAME && LA(2)==LookaheadLexer.LROUND){   //处理函数
            String funcName = lookahead[p].text;
            match(LookaheadLexer.NAME);
            match(LookaheadLexer.LROUND);
            int paramValue = lookahead[p].value;
            sym.setType("return");
            Symbol param = new Symbol();
            param.value = paramValue;
            param.name = "PARAM";
            param.type = "int";
            symbolTable.define(param);
            param.setType("int");
            //System.out.println("param:"+param.toString());
            //int value = funcPro(param,funcName);
        }


    }
    void returnPro(SymbolTable symbolTable,SymbolTable symbolTableInner){
        match(LookaheadLexer.RETURN);
        String varName = lookahead[p].text;
        Symbol sym = symbolTableInner.resolve(varName);
        for (String str:symbolTable.symbols.keySet()){
            Symbol symbol = symbolTable.symbols.get(str);
            if(symbol.type==null) continue;
            if(symbol.type.equals("return")){
                symbol.value = sym.value;
                symbolTable.define(symbol);
                //System.out.println("ok");
            }
        }
    }
    void functionPro(SymbolTable symbolTable){
        match(LookaheadLexer.FUNCTION);
        Symbol sym = new Symbol();
        String funcName = lookahead[p].text;
        sym.setType("function");
        sym.setName(funcName);
    }
    boolean ifPro(SymbolTable symbolTable){
        match(LookaheadLexer.IF);
        String varName = lookahead[p].text;
        //System.out.println("varName:"+varName);
        Symbol sym = symbolTable.resolve(varName);
        //if(symbolTable.resolve(varName)==null) System.out.println("find it");
        match(LookaheadLexer.NAME);
        boolean bigThan = true;
        if(LA(1)==LookaheadLexer.GT) match(LookaheadLexer.GT);
        else {
            match(LookaheadLexer.ST);
            bigThan = false;
        }
        int value = lookahead[p].value;
        //System.out.println(value);
        match(LookaheadLexer.INT);
        match(LookaheadLexer.COLON);
        if(bigThan) return sym.value > value;
        else return  sym.value < value;
    }

    /** list : '(' elements ')' ; // match bracketed list */
    private void list(boolean innerList) {
        match(LookaheadLexer.LROUND); elements(innerList); match(LookaheadLexer.RROUND); innerList = false;
    }


    /** elements : element (',' element)* ; // match comma-separated list */
    void elements(boolean innerList) {    //用一个布尔值判断是不是内嵌的列表
        element();
        if(!innerList) this.n++;
        while ( LA(1)==LookaheadLexer.COMMA ) {
            match(LookaheadLexer.COMMA);
            element();
            if(!innerList)
                this.n++;
        }
    }

    /** element : NAME '=' NAME | "STR" | list ; assignment, NAME or list */
    void element() {
        if ( LA(1)==LookaheadLexer.DQUOTATION ) {
            match(LookaheadLexer.DQUOTATION);  //说明是字符串
            match(LookaheadLexer.STR);
            match(LookaheadLexer.DQUOTATION);
            //System.out.println(lookahead[p].text);
        }
        //假设列表里只有字符串、整数和列表，没有变量名
        else if ( LA(1)==LookaheadLexer.NAME ) {
            match(LookaheadLexer.NAME);
        }
        else if ( LA(1)==LookaheadLexer.LROUND ) {
            list(true);
        }else if(LA(1)==LookaheadLexer.INT){
            //System.out.println("OK");
            match(LookaheadLexer.INT);
        }
        else throw new Error("expecting name or list; found "+LT(1));
    }
}
