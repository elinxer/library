
<?php
$url = "https://twitter.com/aoi_sola";

$conn = file_get_contents($url);//获取页面内容


die();
// $pattern = "/<small class=\"time\"><a href=\"(.*)\" class=\"tweet-timestamp js-permalink js-nav js-tooltip\" data-original-title=\"(.*)\"><span class=\"_timestamp js-short-timestamp\" data-aria-label-part=\"last\" data-time=\"(.*)\" data-time-ms=\"(.*)\" data-long-form=\"true\">(.*)</span></a> </small>/iUs";

// 匹配当前发布日期
$pattern_01 = "/<small class=\"time\">(.*)<\/span>/iUs";

// 匹配姓名
$pattern_02 = "/<p class=\"TweetTextSize TweetTextSize--(.*?)px js-tweet-text tweet-text\" lang=\"(.*)\" data-aria-label-part=\"(.*)\">(.*)<\/p>/iUs";

// 匹配

preg_match_all($pattern_01, $conn, $arr_01);//匹配内容到arr数组

preg_match_all($pattern_02, $conn, $arr_02);//匹配内容到arr数组


foreach ($arr_01[1] as $key => $value) {
	// print_r($value);
	$arr[$key] = trim(strip_tags($value));
}



echo '<pre>';print_r($arr_01);


