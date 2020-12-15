# perl_improve
对Perl语言改进，新增了向量类型、内置的PARAM变量。并且实现了词法解析、语法解析以及符号表和函数子作用域。<br>
示例代码说明：在src文件下，有四个代码文件，分别为我们设计的代码实例<br>
  - 变量定义语句code_definition，并且可以进行类型识别和匹配验证
  - 向量使用语句code_vector，封装了新向量类型的一些基本方法
  - 函数语句code_function，验证了函数的调用以及内部作用域和全局作用域的区别
  - 集成的一个应用实例code_application。<br>
其中的”//”代表注释。其中的代码可以任意更改。<br>
在src下的包muti下的Test类中的filePath可以更改为不同的文件名，方便查看使用。

