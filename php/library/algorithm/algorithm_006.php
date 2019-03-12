<?php
/**
 +-----------------------------------------------------------------------------
 * @ 猴子选大王 - 约瑟夫环问题
 +-----------------------------------------------------------------------------
 * 算法题目：一群猴子排成一圈，按1，2，…，n依次编号。然后从第1只开始数，数到
 * 第m只,把它踢出圈，从它后面再开始数，再数到第m只，在把它踢出去…，如此不停的
 * 进行下去，直到最后只剩下一只猴子为止，那只猴子就叫做大王。
 * 要求编程模拟此过程，输入m、n, 输出最后那个大王的编号。提示：约瑟夫环问题
 +-----------------------------------------------------------------------------
 */

echo(yuesefu(6,3));

function yuesefu($n, $m) {
	$r = 0;
	// 编号由1开始，循环次数为$n-1，即从第二个开始到$n
	for ($i=2; $i <= $n; $i++) 
	{
		// 循环求出最后踢出者
		$r = ($r+$m)%$i;
	}
	return $r+1;
}