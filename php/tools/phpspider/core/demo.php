<?php

require_once __DIR__ . '/phpquery.php';
require_once __DIR__ . '/requests.php';
require_once __DIR__ . '/selector.php';

use \phpspider\core\requests;
use \phpspider\core\selector;

$request = requests::get('http://www.mafengwo.cn/mdd/ajax_photolist.php?act=getPoiPhotoList&poiid=8789817&page=1');

$selector = selector::select($request, '//li/a/img/@src');
var_dump($selector);


/*

$spider->on_handle_img = function($fieldname, $img)
{
    $regex = '/src="(https?:\/\/.*?)"/i';
    preg_match($regex, $img, $rs);
    if (!$rs)
    {
        return $img;
    }

    $url = $rs[1];
    $img = $url;

    //$pathinfo = pathinfo($url);
    //$fileext = $pathinfo['extension'];
    //if (strtolower($fileext) == 'jpeg')
    //{
        //$fileext = 'jpg';
    //}
    //// 以纳秒为单位生成随机数
    //$filename = uniqid().".".$fileext;
    //// 在data目录下生成图片
    //$filepath = PATH_ROOT."/images/{$filename}";
    //// 用系统自带的下载器wget下载
    //exec("wget -q {$url} -O {$filepath}");

    //// 替换成真是图片url
    //$img = str_replace($url, $filename, $img);
    return $img;
};

 */
