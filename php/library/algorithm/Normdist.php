<?php

/**
 * php 实现excel的normdist函数 | 实现正态分布的累积概率函数
 *
 * 使用方法：
 * $list = array(1.09,1.50,1.31,1.44);
 * $normdist = new Normdist($list);
 * echo $normdist->getCdf($list[0]);
 *
 * // 在实际项目中，遇到需要正态分布算法去计算一个数值在整体的分布区间，例如：
 * // 100,90,80,70,60,50,40,30,20,10共10个数，按从高到低的顺序排序
 * // 总数的10%分布区域为极高频，总数的30%分布区域为高频，总数的40%分布区域为中频，总数的20%分布区域为低频
 * // 比如我新增一个数字88，我如何快速得到新增数字位于那个频段？以及其他数字有那些数字频段发生了变化
 *
 */
class Normdist
{

    public $list = array();
    public $mu;
    public $sigma;

    public function __construct($list = [])
    {
        $this->list = $list;
        $this->mu = $this->getMu($list); // 获取平均值
        $this->sigma = $this->getSigma($list); // 获取标准偏差
    }

    /**
     * 正态分布的累积概率函数
     *
     * @param string|integer $value
     * @return number
     */
    public function getCdf($value)
    {
        $mu = $this->mu;
        $sigma = $this->sigma;
        $t = $value - $mu;
        $y = 0.5 * $this->erfcc(-$t / ($sigma * sqrt(2.0)));
        if ($y > 1.0) {
            $y = 1.0;
        }

        return $y;
    }

    private function erfcc($x)
    {
        $z = abs($x);
        $t = 1. / (1. + 0.5 * $z);
        $r =
            $t * exp(-$z * $z - 1.26551223 +
                $t * (1.00002368 +
                    $t * (.37409196 +
                        $t * (.09678418 +
                            $t * (-.18628806 +
                                $t * (.27886807 +
                                    $t * (-1.13520398 +
                                        $t * (1.48851587 +
                                            $t * (-.82215223 +
                                                $t * .17087277)))))))));
        if ($x >= 0.) {
            return $r;
        }

        return 2 - $r;
    }

    /**
     * 获取平均值
     * @param array $list
     * @return number
     */
    private function getMu($list)
    {
        return array_sum($list) / count($list);
    }

    /**
     * 获取标准差
     * @param array $list
     * @return number
     * @beizhu 标准差  = 方差的平方根
     */
    private function getSigma($list)
    {
        $total_var = 0;
        foreach ($list as $v) {
            $total_var += ($v - $this->getMu($list)) ** 2;
        }

        return sqrt($total_var / (count($list) - 1)); // 这里为什么数组元素个数要减去1
    }
}
