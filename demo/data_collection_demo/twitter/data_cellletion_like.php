<?php

// 采集推特推文内容的点赞数，和转推数


$url = "http://localhost/twitter/forbes.html";

$conn = file_get_contents($url);//获取页面内容

$pattern_01 = "/<span class=\"ProfileTweet-actionCountForPresentation\" aria-hidden=\"true\">(.*)<\/span>/iUss";

preg_match_all($pattern_01, $conn, $arr_01);//匹配内容到arr数组

// print_r($arr_01);die();


// 去掉html标签
foreach ($arr_01[1] as $key => $value) {
	// print_r($value);
	$arr_01[$key] = trim(strip_tags($value));
}
$co = count($arr_01);
for ($i=1; $i < $co; $i=$i+2) { /* 删除偶数次序数组 */
	unset($arr_01[$i]);
}

foreach ($arr_01 as $key => $value) { /*初始化数组下标*/
	$arr_02[] = $value;
}


$co2 = count($arr_01);

for ($j=1; $j < $co2; $j=$j+2) { 
	$is_tui[] = $arr_02[$j-1];//已推数量
	$likes[] = $arr_02[$j];//喜欢数量
}

print_r($arr_01);
echo '<pre>';print_r($arr_02);
print_r($likes);
echo "<hr>";
print_r($is_tui);


