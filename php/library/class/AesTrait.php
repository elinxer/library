<?php

namespace App\GameAgent;

use Illuminate\Contracts\Encryption\EncryptException;
use Illuminate\Encryption\Encrypter;

/**
 * AES加密解密处理
 *
 * Trait AesTrait
 *
 * @author Elinx
 * @date 2018-8-9 上午11:50
 */
trait AesTrait {

    /**
     * 加密算法
     *
     * @return string
     */
    public function cipher()
    {
        return 'AES-256-CBC';
    }

    /**
     * 算法加密秘钥key
     *
     * @return string
     */
    public function cipherKey()
    {
        return Encrypter::generateKey($this->cipher());
    }

    /**
     * 获取随机hash(几近随机唯一)
     *
     * @param int $length
     * @return string
     */
    public function makeHash($length = 32)
    {
        // 32位
        return md5(microtime() . rand(199, 99999999) . rand(1, 9999));
    }

    /**
     * 生成随机字符串[区分大小写+存在数字]
     *
     * @param $bit
     * @return bool|string
     */
    public function random_str($bit)
    {
        $str = "QWERTYUIOPASDFGHJKLZXCVBNM1234567890qwertyuiopasdfghjklzxcvbnm";
        str_shuffle($str);
        $key = substr(str_shuffle($str),26, $bit);

        return $key;
    }

    /**
     * 加密数据
     *
     * @param $key
     * @param array|string $data
     * @param int $self 自定义加密算法V2
     * @return string
     */
    public function encrypt($key, $data, $self = 1)
    {
        if ($self == 1) {
            return $this->encryptV2($key, $data, $serialize = false);
        }

        $crypt = new Encrypter($key, $this->cipher());
        return $crypt->encrypt($data, false);
    }

    /**
     * Encrypt the given value.[self]
     *
     * @version V2
     * @param  mixed  $value
     * @param  bool  $serialize
     * @return string
     *
     * @throws
     */
    public function encryptV2($key, $value, $serialize = true)
    {
        // iOS不支持二进制? TODO
        //$iv = random_bytes(openssl_cipher_iv_length($this->cipher()));
        // 采用随机字符串加解密
        $iv = $this->random_str(openssl_cipher_iv_length($this->cipher()));

        // First we will encrypt the value using OpenSSL. After this is encrypted we
        // will proceed to calculating a MAC for the encrypted value so that this
        // value can be verified later as not having been changed by the users.
        $value = \openssl_encrypt(
            $serialize ? serialize($value) : $value,
            $this->cipher(), $key, 0, $iv
        );

        if ($value === false) {
            throw new EncryptException('Could not encrypt the data.');
        }

        // Once we get the encrypted value we'll go ahead and base64_encode the input
        // vector and create the MAC for the encrypted value so we can then verify
        // its authenticity. Then, we'll JSON the data into the "payload" array.
        $mac = $this->hmac($key, $iv = base64_encode($iv), $value);

        $json = json_encode(compact('iv', 'value', 'mac'));

        if (json_last_error() !== JSON_ERROR_NONE) {
            throw new EncryptException('Could not encrypt the data.');
        }

        return base64_encode($json);
    }

    /**
     * 解密原始数据
     *
     * mac不作配对验证,由内部生成
     *
     * @param $key
     * @param string $content
     * @return string|array
     */
    public function decrypt($key, $content = '')
    {
        $crypt = new Encrypter($key, $this->cipher());

        // 解密获取数据
        $encryptArr = base64_decode($content);
        $encryptArr = json_decode($encryptArr, true);

        // 重新匹配数据
        $iv    = array_get($encryptArr, 'iv');
        $value = array_get($encryptArr, 'value');
        $mac   = $this->hmac($key, $iv, $value);

        // 重新加密
        $content = json_encode(compact('iv', 'value', 'mac'));
        $content = base64_encode($content);

        // 解密
        return $crypt->decrypt($content, false);
    }

    /**
     * 获取hash hmac加密串
     *
     * @param $key
     * @param string $ivbase64 确保iv为base64加密数据,并且只有一层base64加密
     * @param $value
     * @return string
     */
    public function hmac($key, $ivbase64, $value)
    {
        return hash_hmac('sha256', $ivbase64 . $value, $key);
    }

}
