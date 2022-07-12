package multi;

import symtab.Symbol;
import symtab.SymbolTable;
import symtab.vec;

import java.io.*;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        String encoding="GBK";
        String filePath = "F:\\java\\IDEA\\WorkSpace\\Perl_IMPROVE\\src\\code_definition.txt";
        File file=new File(filePath);

        InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String s = null;
        //______________________________________________________
        SymbolTable symTable = new SymbolTable();
        symTable.setScopeName("global");
        symTable.setFatherScope(null);
        boolean innerFunc = false;
        SymbolTable symTableInner = new SymbolTable();
        symTableInner.setScopeName("inner");
        symTableInner.setFatherScope("global");
        SymbolTable[] symbolTableList = new SymbolTable[2];
        symbolTableList[0] = symTable;
        symbolTableList[1] = symTableInner;
        while((s = bufferedReader.readLine()) != null){
            //如果if语句成立后，else的语句不需要执行
            //if(ifCondition) continue;
            //处理每一行的代码
            LookaheadLexer lexer = new LookaheadLexer(s); // parse arg
            LookaheadParser parser = new LookaheadParser(lexer,2,0);

            Symbol sym = new Symbol();

            Token t = parser.lookahead[parser.p];
            if(t.type == 32) continue;   //注释语句
            if(t.type == 14){  //vec
                if(innerFunc) parser.vecPro(symTableInner,sym);
                else parser.vecPro(symTable,sym);
            }else if(t.type == 7){  //var
                if(innerFunc) {
                    parser.varPro(symTableInner, sym, symbolTableList);
                    //System.out.println("innerFunc:"+sym.toString());
                    symTableInner.define(sym);
                }
                else parser.varPro(symTable,sym,symbolTableList);
            }else if(t.type == 2){  // NAME
                if(innerFunc) {
                    parser.symPro(symTableInner, sym);
                    //System.out.println("innerFunc:"+sym.toString());
                    symTableInner.define(sym);
                }
                else parser.symPro(symTable,sym);
            }else if(t.type == 20){ //print
                if(innerFunc) parser.priPro(symTableInner);
                else parser.priPro(symTable);
            }else if(t.type == 15){ //sub
                //parser.priPro(symTable);
                //建立一个新的符号表
                innerFunc = true;
                parser.subPro(symTableInner,sym);

            }else if(t.type == 17){  //}
                innerFunc = false;
            }else if(t.type == 23){  //if
                boolean res = parser.ifPro(symTableInner);
                //System.out.println("name:"+ parser.lookahead[parser.p].text);
                //Symbol symbol = new Symbol();
                //System.out.println("res:"+res);
                if(res){
                    parser.symPro(symTableInner,sym);
                    symTableInner.define(sym);

                }else{
                    continue;
                }
            }else if(t.type == 27){ //函数定义
                parser.functionPro(symTable);
            }else if(t.type == 29){  //return 语句
                //将return后面的变量赋值给global作用域中的类型为return的变量
                parser.returnPro(symTable,symTableInner);
            }
            //System.out.println(sym.toString());
            if(!innerFunc) {
                symTable.define(sym);
            }
        }
        read.close();
    }
}
