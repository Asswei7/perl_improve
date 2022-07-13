# perlImprove
### Perl语言的改进点
1. 取消定义时在变量名前分别加$、@、% 三个符号，通过关键字“var”来统一定义所有变量
2. Perl是一种弱类型语言，某些代码非常容易引起歧义，例如：$a = @list; 其中a是标量，list是数组，按照常规逻辑来说，当我们赋值的时候，我们隐含地就认定两者相等了。也就是说，执行完$a = @list之后，$a应该就是@list。而相等又意味着自反和传递。也就是说如果a==b，那么b==a；同时如果 a==b，b==c，那么a==c。针对这个问题，我们将Perl语言改成强类型语言，不同类型变量完全不能相互赋值，并且我们引入length函数，让a = length(list); 来返回list的长度。
3. 标量之间运算不安全。对于$a=1,  $a.1和$a+1一个得到“11”，一个得到2.针对这个问题，我们去除 . 运算，在定义字符串时必须加上引号，通过+运算来连接两个字符串，这样更加合理更加易读易懂。在+运算时，需要进行类型判断，若+前后量是字符串就连接两个字符串，若是整型数字，就相加得到新的数值。
4. 合并数组的问题。具体见参考文档
5. 增加向量类型
### 主体框架
文件读取
token类
词法分析
语法分析
符号类
向量类
符号表
### 示例代码
对Perl语言改进，新增了向量类型、内置的PARAM变量。并且实现了词法解析、语法解析以及符号表和函数子作用域。<br>
  - 变量定义语句code_definition，并且可以进行类型识别和匹配验证
  - 向量使用语句code_vector，封装了新向量类型的一些基本方法
  - 函数语句code_function，验证了函数的调用以及内部作用域和全局作用域的区别
  - 集成的一个应用实例code_application。<br>
其中的”//”代表注释。其中的代码可以任意更改。<br>
在src下的包muti下的Test类中的filePath可以更改为不同的文件名，方便查看使用。

