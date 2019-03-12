<?php

// php glob() 函数用法

// 查找单种后缀名
$files = glob('*.php');


// 获取多种后缀名
$files_02 = glob("*.{php,txt}", GLOB_BRACE);



$files_02 = glob("*.{php,txt}", GLOB_BRACE);

// 查看绝对路径
$files_02 = array_map('realpath', $files_02);

echo '<pre>';
print_r($files_02);


