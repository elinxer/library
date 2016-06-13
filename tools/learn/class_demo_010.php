<?php
// php去除字符串中的HTML标签

// php自带的函数可以去除/删除字符串中的HTML标签/代码。

//  strip_tags(string,allow)：函数剥去 HTML、XML 以及 PHP 的标签。

//  参数：string,必填,规定要检查的字符串；allow,选填,规定允许存在的标签,这些标签不会被删除。


$str = 'elinx-<span style="color:#f00;">PHP</span>';
$str1 = strip_tags($str);          // 删除所有HTML标签
// $str1 = trim(strip_tags($str)); //加个去掉空格

$str2 = strip_tags($str,'<span>'); // 保留 <span>标签
echo $str1; // 输出 elinx-PHP
echo $str2; // 样式不一样喔
 