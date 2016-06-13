<?php
/**
 +-----------------------------------------------------------------------------
 * @ 求三个数的最小公倍数 - 4种方法
 +-----------------------------------------------------------------------------
 * 算法描述：3个数据最小公倍数的定义为3个数的公倍数中最小的一个。用蛮力法直接
 * 用最小公倍数的定义进行算法设计，逐步从小到大扩大1，2，3，4，5，...测试，直到
 * 它的某一倍数正好也是其他两个数据的倍数，也就是说能被其他两个数据整除，这就找
 * 到了问题的解。为了提高求解的效率，先选出3个数的最大值，然后对这个最大值从1
 * 开始，对其扩大自然数的倍数，直到这个积能被全部3个数整除为止，这个积就是它们的
 * 最小公倍数了。
 +-----------------------------------------------------------------------------
 */

$least_01 = least_common_multiple_01();
echo "<br>1.穷举求得三个数的最小公倍数为：".$least_01.'<br>';


//+---------------------------------------------------------------------------

/* 蛮力穷举求解，当数过大时效率低下 */
function least_common_multiple_01($a = 11112, $b = 21444, $c = 4326) {
	$i = 1; /* 从 1 开始*/
	$max = 0;
	if ($a > $b && $a > $c) { /* 寻找最大值*/
		$max = $a;
	} else  if ($b > $a && $b >$c) {
		$max = $b;
	} else {
		$max = $c;
	}
	while (1) { /* 死循环直至内部打断 */
		/* 整除 */
		$j = $max * $i;
		if (($j%$a) == 0 and ($j%$b) == 0 and ($j%$c) == 0) {
			break;
		}
		$i++;
	}
	return $j;
}
