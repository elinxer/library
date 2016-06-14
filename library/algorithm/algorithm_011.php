<?php
/**
 +-----------------------------------------------------------------------------
 * @ 狱吏问题
 +-----------------------------------------------------------------------------
 * 算法描述：某国王大赦囚犯，让一狱吏n次通过一排锁着的n间牢房，每通过一次，按
 * 所定的规则转动n间牢房的某些门锁，每转动一次，原来锁着的门被打开，原来打开的
 * 门被锁上，通过n次后，门锁开着的，牢房中的犯人放出，否则犯人不得释放。
 +-----------------------------------------------------------------------------
 * 算法分析：转动门锁的规则可以有另一种理解，第一次转动的是编号为1的倍数的牢房；
 * 第二次转动的是编号为2的倍数的牢房；第三次转动的是 编号为3的倍数的牢房。。。
 * 则狱吏问题是一个关于因子个数的问题。令d(n)为自然数n的因子个数，这里不计重复
 * 的因子，如4的因子为1，2，4共3个因子，而非1，2，2，4.则对于不同的n,d(n)有的
 * 奇数，有的为偶数，具体见表：
 * |  n | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 |
 * |d(n)| 1 | 2 | 2 | 3 | 2 | 4 | 2 | 4 | 3 | 4  |  2 |  6 |  2 |  4 |  4 |
 +-----------------------------------------------------------------------------
 * 数学模型2：仔细观察上表，当且仅当n为完全平方数时，d(n)为奇数。这是因为非完全
 * 平方数n的因子是成对出现的，当a是n的因子时，b=n/a也一定是n的因子，即n=a^2，无
 * 论a是否为素数，n的因子a都是单独出现的，其他银子成对出现，因此d(n)为奇数。
 * 当求出每个牢房的编号的不重复的因子个数是奇数时，牢房中的囚犯得到大赦。
 +-----------------------------------------------------------------------------
 */

yuli_03();

/**
 * @param int $n 牢房总个数，假设从1开始
 * @return void
 */
function yuli_03($n = 200) {
	for ($i=1; $i <= $n; $i++) { 
		if ($i * $i <= $n) {/* 求n内的完全平方数 */
			echo $i * $i.' is free.<br>';
		} else {
			break;
		}
	}
}