<?php
require_once('function/function.php');
require_once('function/tool_function.php');


// 罗列文件夹和文件
$list = get_dirs(".");
echo "<ul>";
foreach ($list['dir'] as $key => $value) {
	echo "<li><a href=".$value." style=''>&nbsp;".$value."</a></li><br>";
}
foreach ($list['file'] as $key => $value) {
	echo "<li><a href=".$value." style=''>&nbsp;".$value."</a><br></li>";
}
echo "</ul>";
// print_r($list);

/**
 * 列出目录
 * @var $dir  目录名
 * @return 目录数组
 * 列出文件夹下内容，返回数组 $dirArray['dir']:存文件夹；$dirArray['file']：存文件
 */
function get_dirs($dir) 
{
	$dir = rtrim($dir,'/').'/';
	$dirArray [][] = NULL;
	if (false != ($handle = opendir ( $dir )))
	{
		$i = 0;
		$j = 0;
		while ( false !== ($file = readdir ( $handle )) ) 
		{
			if (is_dir ( $dir . $file )) 
			{ //判断是否文件夹
				$dirArray ['dir'] [$i] = $file;
				$i ++;
			} 
			else 
			{
				$dirArray ['file'] [$j] = $file;
				$j ++;
			}
		}
		closedir ($handle);
	}
	return $dirArray;
}
