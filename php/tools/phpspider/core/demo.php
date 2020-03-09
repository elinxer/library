<?php

require_once __DIR__ . '/phpquery.php';
require_once __DIR__ . '/requests.php';
require_once __DIR__ . '/selector.php';

use \phpspider\core\requests;
use \phpspider\core\selector;

$request = requests::get('http://www.mafengwo.cn/mdd/ajax_photolist.php?act=getPoiPhotoList&poiid=8789817&page=1');

$selector = selector::select($request, '//li/a/img/@src');
var_dump($selector);
