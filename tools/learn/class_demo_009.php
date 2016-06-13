<?php

// php 文件操作

// $root = $_SERVER['DOCUMENT_ROOT'];

// echo $root;

$file_link = './test/1.txt';

// $fp = fopen($file_link, 'w');

$filename = './test/2.txt';
$somecontent = "添加这些文字到文件\n";

echo filesize($filename);

$fp = fopen($filename, 'ab');
flock($fp, LOCK_EX);
fwrite($fp, $somecontent);

flock($fp, LOCK_UN);
fclose($fp);


$a = file("test/16_06_07.log");
foreach ($a as $key => $value) {
	$a[$key] = trim($value);
	
	if ($a[$key] == "") {
		unset($a[$key]);
	}
}
print_r($a);
// var_dump($a);

// if (is_writable($filename)) {
// 	if (!$handle = fopen($filename, 'a')) {
// 		echo "不能打开{$filename}";
// 		exit();
// 	}
// 	if (fwrite($handle, $somecontent) === false) {
// 		echo "不能写入到文件";exit();
// 	}
// 	echo "成功写入文本！";
// 	fclose($handle);
// } else {
// 	echo "文件 {$filename} 不可写";
// }

// var_dump(FileOperation::delete_file($file_link));


/**
* 文件操作类
*/
class FileOperation
{

	/**
	 * 删除文件
	 * @param $file_link 文件路径包括名称 ./test/1.txt
	 * @return boolean
	 */
	public static function delete_file($file_link) {
		if (!file_exists($file_link)) {
			return false;
		}
		return @unlink($file_link);
	}



}