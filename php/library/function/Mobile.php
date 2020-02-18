<?php

class Mobile
{
    /**
     * 获取指定手机号运营商信息+归属信息
     *
     * @param $phone
     * @return int
     */
    public function getMobileInfo($phone)
    {
        $isChinaMobile = "/^134[0-8]\d{7}$|^(?:13[5-9]|147|15[0-27-9]|178|18[2-478])\d{8}$/"; //移动方面最新答复
        $isChinaUnion  = "/^(?:13[0-2]|145|15[56]|176|18[56])\d{8}$/"; //向联通微博确认并未回复
        $isChinaTelcom = "/^(?:133|153|177|173|18[019])\d{8}$/"; //1349号段 电信方面没给出答复，视作不存在
        if (preg_match($isChinaMobile, $phone)) {
            $type = 0; //0 中国移动
        } else if (preg_match($isChinaUnion, $phone)) {
            $type = 1;//1 中国联通
        } else if (preg_match($isChinaTelcom, $phone)) {
            $type = 2;//2 中国电信
        } else {
            $type = 3;//3 未知
        }
        if ($type == 3) {
            $info = $this->getMobileInfoByApi($phone);
            switch ($info['type']) {
                case '中国移动':
                    $type = 0;
                    break;
                case '中国联通':
                    $type = 1;
                    break;
                case '中国电信':
                    $type = 2;
                    break;
            }
        }
        return $type;
    }

    /**
     * 从接口获取手机号归属信息+运营商信息
     *
     * @param $mobile
     * @return array|string
     */
    function getMobileInfoByApi($mobile)
    {
        if (!preg_match("/^1[34578]\d{9}$/", $mobile)) {
            return '请输入正确手机号码！';
        } else {
            $phone_json = file_get_contents('http://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query={' . $mobile . '}&resource_id=6004&ie=utf8&oe=utf8&format=json');
            $phone_array = json_decode($phone_json, true);
            $phone_info = array();
            $phone_info['mobile'] = $mobile;
            $phone_info['type'] = $phone_array['data'][0]['type'];
            $phone_info['location'] = $phone_array['data'][0]['prov'] . $phone_array['data'][0]['city'];

            return $phone_info;
        }
    }
}
