<?php

/**
 * 简单加密解密方式
 *
 * Class SimpleSecret
 */
class SimpleSecret
{
    /**
     * 简单对称加密算法之加密
     * @param String $string 需要加密的字串
     * @param String $skey 加密EKY
     * @return String
     */
    public static function encode($string = '', $skey = 'card9377')
    {
        $strArr = str_split(base64_encode($string));
        $strCount = count($strArr);
        foreach (str_split($skey) as $key => $value) {
            $key < $strCount && $strArr[$key] .= $value;
        }
        return str_replace(array('=', '+', '/'), array('O0O0O', 'o000o', 'oo00o'), join('', $strArr));
    }

    /**
     * 简单对称加密算法之解密
     * @param String $string 需要解密的字串
     * @param String $skey 解密KEY
     * @return String
     */
    public static function decode($string = '', $skey = 'card9377')
    {
        $strArr = str_split(str_replace(array('O0O0O', 'o000o', 'oo00o'), array('=', '+', '/'), $string), 2);
        $strCount = count($strArr);
        foreach (str_split($skey) as $key => $value) {
            $key <= $strCount && isset($strArr[$key]) && $strArr[$key][1] === $value && $strArr[$key] = $strArr[$key][0];
        }

        return base64_decode(join('', $strArr));
    }

    /**
     * 简单对称加密算法
     *
     * @param $string
     * @param string $type 加密解密 encode|decode
     * @param string $skey 秘钥
     * @return false|string|string[]
     */
    public function authCode($string, $type = 'encode', $skey = 'xxxxxx')
    {
        if ($type == 'encode') {
            $strArr = str_split(base64_encode($string));
            $strCount = count($strArr);
            foreach (str_split($skey) as $key => $value) {
                $key < $strCount && $strArr[$key] .= $value;
            }
            return str_replace(array('=', '+', '/'), array('O0O0O', 'o000o', 'oo00o'), join('', $strArr));
        } else {
            $strArr = str_split(str_replace(array('O0O0O', 'o000o', 'oo00o'), array('=', '+', '/'), $string), 2);
            $strCount = count($strArr);
            foreach (str_split($skey) as $key => $value) {
                $key <= $strCount && isset($strArr[$key]) && $strArr[$key][1] === $value && $strArr[$key] = $strArr[$key][0];
            }
            return base64_decode(join('', $strArr));
        }
    }

}
