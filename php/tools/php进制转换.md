#PHP进制转换
#PHP函数篇详解十进制、二进制、八进制和十六进制转换函数说明

中文字符编码研究系列第一期，PHP函数篇详解十进制、二进制、八进制和十六进制互相转换函数说明，主要掌握各进制转换的方法，以应用于实际开发

一，十进制（decimal system）转换函数说明 
1，十进制转二进制 decbin() 函数，如下实例 

echo decbin(12); //输出 1100 
echo decbin(26); //输出 11010 
decbin 
(PHP 3, PHP 4, PHP 5) 
decbin -- 十进制转换为二进制 
说明 
string decbin ( int number ) 
返回一字符串，包含有给定 number 参数的二进制表示。所能转换的最大数值为十进制的 4294967295，其结果为 32 个 1 的字符串。 

2，十进制转八进制 decoct() 函数 

echo decoct(15); //输出 17 
echo decoct(264); //输出 410 
decoct 
(PHP 3, PHP 4, PHP 5) 
decoct -- 十进制转换为八进制 
说明 
string decoct ( int number ) 
返回一字符串，包含有给定 number 参数的八进制表示。所能转换的最大数值为十进制的 4294967295，其结果为 "37777777777"。 

3，十进制转十六进制 dechex() 函数 

echo dechex(10); //输出 a 
echo dechex(47); //输出 2f 
dechex 
(PHP 3, PHP 4, PHP 5) 
dechex -- 十进制转换为十六进制 
说明 
string dechex ( int number ) 
返回一字符串，包含有给定 number 参数的十六进制表示。所能转换的最大数值为十进制的 4294967295，其结果为 "ffffffff"。 

二，二进制（binary system）转换函数说明 
1，二进制转十六制进 bin2hex() 函数 

$binary = "11111001"; 
$hex = dechex(bindec($binary)); 
echo $hex;//输出f9 
bin2hex 
(PHP 3 >= 3.0.9, PHP 4, PHP 5) 
bin2hex -- 将二进制数据转换成十六进制表示 
说明 
string bin2hex ( string str ) 
返回 ASCII 字符串，为参数 str 的十六进制表示。转换使用字节方式，高四位字节优先。 

2，二进制转十制进 bindec() 函数 

echo bindec('110011'); //输出 51 
echo bindec('000110011'); //输出 51 
echo bindec('111'); //输出 7 
bindec 
(PHP 3, PHP 4, PHP 5) 
bindec -- 二进制转换为十进制 
说明 
number bindec ( string binary_string ) 
返回 binary_string 参数所表示的二进制数的十进制等价值。 
bindec() 将一个二进制数转换成 integer。可转换的最大的数为 31 位 1 或者说十进制的 2147483647。PHP 4.1.0 开始，该函数可以处理大数值，这种情况下，它会返回 float 类型。 

三，八进制（octal system）转换函数说明 
八进制转十进制 octdec() 函数 

echo octdec('77'); //输出 63 
echo octdec(decoct(45)); //输出 45 
octdec 
(PHP 3, PHP 4, PHP 5) 
octdec -- 八进制转换为十进制 
说明 
number octdec ( string octal_string ) 
返回 octal_string 参数所表示的八进制数的十进制等值。可转换的最大的数值为 17777777777 或十进制的 2147483647。PHP 4.1.0 开始，该函数可以处理大数字，这种情况下，它会返回 float 类型。 

四，十六进制（hexadecimal）转换函数说明 
十六进制转十进制 hexdec()函数 

var_dump(hexdec("See")); 
var_dump(hexdec("ee")); 
// both print "int(238)" 

var_dump(hexdec("that")); // print "int(10)" 
var_dump(hexdec("a0")); // print "int(160)" 
hexdec 
(PHP 3, PHP 4, PHP 5) 
hexdec -- 十六进制转换为十进制 
说明 
number hexdec ( string hex_string ) 
返回与 hex_string 参数所表示的十六进制数等值的的十进制数。hexdec() 将一个十六进制字符串转换为十进制数。所能转换的最大数值为 7fffffff，即十进制的 2147483647。PHP 4.1.0 开始，该函数可以处理大数字，这种情况下，它会返回 float 类型。 
hexdec() 将遇到的所有非十六进制字符替换成 0。这样，所有左边的零都被忽略，但右边的零会计入值中。

五，任意进制转换 base_convert() 函数 

$hexadecimal = 'A37334'; 
echo base_convert($hexadecimal, 16, 2);//输出 101000110111001100110100 
base_convert 
(PHP 3 >= 3.0.6, PHP 4, PHP 5) 

base_convert -- 在任意进制之间转换数字 
说明 
string base_convert ( string number, int frombase, int tobase ) 
返回一字符串，包含 number 以 tobase 进制的表示。number 本身的进制由 frombase 指定。frombase 和 tobase 都只能在 2 和 36 之间（包括 2 和 36）。高于十进制的数字用字母 a-z 表示，例如 a 表示 10，b 表示 11 以及 z 表示 35。 

这里主要是把PHP进制转换函数进行整理，便于开发查找，相关具体函数说明请参考PHP手册。请关注下一期中文字符编码研究系列。
