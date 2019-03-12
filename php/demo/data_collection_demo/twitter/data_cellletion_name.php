<?php

// 采集推特发布用户


$url = "http://localhost/twitter/forbes.html";

$conn = file_get_contents($url);//获取页面内容

// 匹配当前发布日期
$pattern_01 = "/<strong class=\"fullname js-action-profile-name show-popup-with-id\" data-aria-label-part=\"\">(.*)<\/strong>/iUs";

preg_match_all($pattern_01, $conn, $arr_01);//匹配内容到arr数组

// print_r($arr_01);die();

// 去掉html标签
foreach ($arr_01[1] as $key => $value) {
	// print_r($value);
	$arr_01[$key] = trim(strip_tags($value));
}

echo '<pre>';print_r($arr_01);


