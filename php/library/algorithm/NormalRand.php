<?php

/**
 * 正态分布随机数算法
 *
 *  $NormalRand = new \App\Unity\NormalRand();
 * $NormalRand->demo();
 *
 * 正态分布，即Normal Distribution，又名高斯分布
 * 对应的高斯方程在http://en.wikipedia.org/wiki/Gaussian_function。
 * 本算法主要参考：http://en.wikipedia.org/wiki/Box-Muller_transform
 */
class NormalRand
{

    /*
    * 使用Box-Mueller方法，生成正态分布随机数。
    *
    * @desc 使用Box-Mueller方法，生成正态分布随机数。
    * @return float 随机数
    */
    function makeNormalRand()
    {
        static $last = FALSE;
        static $useLast = FALSE;
        static $n;

        if ($last) {
            $last = FALSE;
            $m = $n;
        } else {
            do {
                // 以下为Box-Mueller方法
                $range = 10000000;
                $u = mt_rand(1, $range) / $range;
                $v = mt_rand(1, $range) / $range;
                $s = sqrt(-2 * log($u));
                $x = $s * sin(2 * M_PI * $v);
                $y = $s * cos(2 * M_PI * $v);
                // 以下为封装
                $width = 1.0; // 分布的宽度。
                $step = 3.0; // 分布的锋锐程度。数字越小，曲线越平。
                $m = $x / ($step * $width * 2.0) + $width / 2.0;
                $n = $y / ($step * $width * 2.0) + $width / 2.0;
                if ($m < $width && $m > 0 && $n < $width && $n > 0) {
                    $useLast = TRUE;
                }
            } while (!$useLast);
        }
        return $m;
    }

    public function demo()
    {
        $all = [];
        foreach (range(0, 10) as $key) {
            $all[$key] = 0;
        }

        for ($i = 0; $i < 10000; $i++) {
            $n = round($this->makeNormalRand() * 10);
            if (!isset($all[$n])) {
                continue;
            }
            $all[$n]++;
        }

        // end/

        // 0, 42
        //1, 151
        //2, 496
        //3, 1120
        //4, 1970
        //5, 2394
        //6, 1962
        //7, 1191
        //8, 498
        //9, 135
        //10, 30
        foreach ($all as $key => $value) {
            echo "$key, $value \n";
        }

    }

}
