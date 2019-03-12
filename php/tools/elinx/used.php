<?php

// php使用自定义函数参数


// 调用时传什么参数就是什么，个数无限制
$a = fun($args_1);

$b = fun($args_1, $args_2);



// 函数没有传参
function fun() {
	foreach (func_get_args() as $agrs) {
		echo $agrs; //输出你的函数参数
	}
}