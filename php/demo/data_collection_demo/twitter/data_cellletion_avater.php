<?php

// 采集推特推文内容的所有用户的头像


$url = "http://localhost/twitter/forbes.html";

$conn = file_get_contents($url);//获取页面内容

$pattern_01 = "/<img class=\"avatar js-action-profile-avatar\" src=\"(.*)\" alt=\"\">/iUs";

preg_match_all($pattern_01, $conn, $arr_01);//匹配内容到arr数组

// print_r($arr_01);die();

// 去掉html标签
foreach ($arr_01[1] as $key => $value) {
	// print_r($value);
	$arr_01[$key] = trim(strip_tags($value));
}

echo '<pre>';print_r($arr_01);


