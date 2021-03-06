<?php
/**
 +-----------------------------------------------------------------------------
 * @ 杨辉三角形 - 倒推法
 +-----------------------------------------------------------------------------
 * 算法题目：输出杨辉三角形，限定用一个一维数组完成
 +-----------------------------------------------------------------------------
 * 数学模型：杨辉三角形规则为上下层数据之间的关系比较明显，中间的数据等于其上
 * 一行左上、右上两数据之和。
 *  1
 *  1 1
 *	1 2 1 
 *	1 3 3 1 
 *	1 4 6 4 1 
 *	1 5 10 10 5 1 
 +-----------------------------------------------------------------------------
 * 问题分析：一般考虑用两个一维数组来完成要求，即由前一行（存储在一个一维数组
 * 中）递推后一行（存储在另一一维数组中），依次进行可以比较容易地完成题目。
 +-----------------------------------------------------------------------------
 * 算法设计：由分析得，第i层有i列需要求解i个数据。若从第1列向后计算，求第i行时，
 * 由于用一个一维数组存储，每求出一个数将覆盖第i-1行对应列存储的值，这将导致下
 * 一个数无法计算。而从第i个元素倒着向前计算就能正常进行，则可避免这种情况出现。
 * 迭代表达式如下（下标代表列号）：
 * A[1] = A[i] = 1
 * A[j] = A[j] + A[j-1] ,j = i-1,i-2,i-3,...,2
 * i行  i-1行  i-1行
 +-----------------------------------------------------------------------------
 */

$n = 6;
$a = array();

echo 1;
echo "<br>";
$a[1] = $a[2] = 1;

echo $a[1].' '.$a[2];
echo "<br>";
for ($i=3; $i <=$n; $i++) 
{ 
	$a[1] = $a[$i] = 1;
	for ($j=$i-1; $j>1; $j=$j-1) 
	{ 
		$a[$j] = $a[$j] + $a[$j-1];
	}
	for ($j=1; $j <=$i; $j++) 
	{ 
		echo $a[$j].' ';
	}
	echo "<br>";
}

