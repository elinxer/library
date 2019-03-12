<?php
// $url = "https://www.baidu.com/";
// $curl=curl_init();
// curl_setopt($curl,CURLOPT_URL, $url);
// curl_setopt($curl,CURLOPT_HEADER,1);
// curl_setopt($curl,CURLOPT_RETURNTRANSFER,1);
// curl_setopt($curl,CURLOPT_POST,1);
// curl_setopt($curl,CURLOPT_PROXYTYPE,CURLPROXY_SOCKS5);//使用了SOCKS5代理
// curl_setopt($curl, CURLOPT_PROXY, "104.128.237.191:0000");	

// curl_setopt($curl, CURLOPT_POSTFIELDS, "elinx:");

// curl_setopt($curl, CURLOPT_HTTPPROXYTUNNEL, 1);//如果是HTTP代理

// //curl_setopt($curl, CURLOPT_COOKIEJAR, 'cookie.txt');cookie你懂的

// $request = curl_exec($curl);
// var_dump($request);
// curl_close($curl);

// die();

// 采集推特推文内容列表

// $url = "https://www.twitter.com/Forbes";
// 如果无法远程采集，可以尝试将网页另存下来再进行采集本地地址的网页数据
// 推特采集有可能会有限制，如封采集IP，禁止采集，服务器无法访问墙外网站等
$url = "https://www.twitter.com/";

$conn = file_get_contents($url); //获取页面内容

$arr = _get_twitter_push_li($conn); //获取推文列表

foreach ($arr[0] as $key => $value) {
	$name    = _get_twitter_push_name($value); //获取发布者名称
	$user_id = _get_twitter_push_id($value); //获取发布者id
	$content = _get_twitter_push_content($value); //获取发布内容
	$time 	 = _get_twitter_push_time($value); //获取发布时间
	$avatar  = _get_twitter_push_avater($value); //获取发布者头像
	// $content_img = @_get_twitter_push_conimg($value); //获取内容图片
	$likes_tui   = _get_twitter_push_likes($value); // 获取喜欢数和转推数

	// var_dump($likes_tui);

	$twitter[$key]['name'] 		= $name[1][0];
	$twitter[$key]['user_id'] 	= $user_id[2][0];
	$twitter[$key]['push_time'] = $time[1][0];
	$twitter[$key]['avatar']	= $avatar[2];
	$twitter[$key]['content'] 	= $content[1][0];
	// $twitter[$key]['content_img'] = $content_img[2];
	$twitter[$key]['likes']		= $likes_tui['like'][0];
	$twitter[$key]['is_tui']	= $likes_tui['is_tui'][0];

}



echo "<pre>";print_r($twitter);die();

// 获取单页面所有推文
function _get_twitter_push_li($conn) {
	$pattern = "/<li class=\"js-stream-item stream-item stream-item
\" data-item-id=\"(.*)\" id=\"stream-item-tweet-(.*)\" data-item-type=\"tweet\">(.*)<\/li>/iUs";
	preg_match_all($pattern, $conn, $arr);//匹配内容到arr数组
	return $arr;
}

// 获取推文点赞数和已转推数
function _get_twitter_push_likes($conn) {
	$pattern = "/<span class=\"ProfileTweet-actionCountForPresentation\" aria-hidden=\"true\">(.*)<\/span>/iUss";
	preg_match_all($pattern, $conn, $arr_01);//匹配内容到arr数组
	foreach ($arr_01[1] as $key => $value) {/*去掉html标签*/
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
	return array('like'=>$likes, 'is_tui'=>$is_tui);
}

// 获取推文发布内容图片
function _get_twitter_push_conimg($conn, $avatar_save_url="./images/") {
	// 匹配当前发布日期
	$pattern = "/<div class=\"AdaptiveMedia-photoContainer js-adaptive-photo \" data-image-url=\"(.*)\" data-element-context=\"platform_photo_card\">/iUs";
	//匹配内容到arr数组，pre_match_all可以保留数据原编码
	preg_match_all($pattern, $conn, $arr);
	// 保存图片
	if ($arr[1][0] != null) {
		$new_name = save_image($arr[1][0], "", $avatar_save_url);
		$arr[2] = $avatar_save_url.$new_name;
	} else {
		$arr[2] = '';
	}
	return $arr;
}

// 获取推文发布者头像
function _get_twitter_push_avater($conn, $avatar_save_url="./images/avatar/") {
	// 匹配正则
	$pattern = "/<img class=\"avatar js-action-profile-avatar\" src=\"(.*)\" alt=\"\">/iUs";
	//匹配内容到arr数组，pre_match_all可以保留数据原编码
	preg_match_all($pattern, $conn, $arr);
	// 保存图片到本地
	$new_name = save_image($arr[1][0], "", $avatar_save_url);
	$arr[2] = $avatar_save_url.$new_name;
	return $arr;
}

// 获取推文发布时间
function _get_twitter_push_time($conn) {
	// 匹配当前发布日期
	$pattern = "/<span class=\"_timestamp js-short-timestamp js-relative-timestamp\" data-time=\"(.*)\" data-time-ms=\"(.*)\" data-long-form=\"true\" aria-hidden=\"true\">/iUs";
	//匹配内容到arr数组，pre_match_all可以保留数据原编码
	preg_match_all($pattern, $conn, $arr);
	if (empty($arr[0])) {/* 采用第二种正则 */
		$pattern = "/<span class=\"_timestamp js-short-timestamp \" data-aria-label-part=\"last\" data-time=\"(.*)\" data-time-ms=\"(.*)\" data-long-form=\"true\">/iUs";
		preg_match_all($pattern, $conn, $arr);//匹配内容到arr数组
	}
	return $arr;
}

// 获取推文发布内容
function _get_twitter_push_content($conn) {
	// 匹配当前发布内容
	$pattern = "/<div class=\"js\-tweet-text-container\">(.*)<\/div>/iUs";
	//匹配内容到arr数组，pre_match_all可以保留数据原编码
	preg_match_all($pattern, $conn, $arr);
	if (empty($arr[0])) {/* 采用第二种正则 */
		$pattern = "/<strong class=\"fullname js-action-profile-name show-popup-with-id\">(.*)<\/strong>/iUs";
		preg_match_all($pattern, $conn, $arr);//匹配内容到arr数组
	}
	return $arr;
}

// 获取推文用户id
function _get_twitter_push_id($conn) {
	// 匹配当前发布者id
	$pattern = "/<a class=\"account-group js-account-group js-action-profile js-user-profile-link js-nav\" href=\"(.*)\" data-user-id=\"(.*)\">/iUs";
	//匹配内容到arr数组，pre_match_all可以保留数据原编码
	preg_match_all($pattern, $conn, $arr);
	if (empty($arr[0])) {/* 采用第二种正则 */
		$pattern = "/<a class=\"pretty-link js-user-profile-link\" href=\"(.*)\" data-user-id=\"(.*)\">/iUs";
		preg_match_all($pattern, $conn, $arr);//匹配内容到arr数组
	}
	if (empty($arr[0])) {/* 采用第三种正则 */
		$pattern = "/<a data-impression-cookie=\"(.*)\" class=\"account-group js-account-group js-action-profile js-user-profile-link js-nav\" href=\"(.*)\" data-user-id=\"(.*)\">/iUs";
		preg_match_all($pattern, $conn, $arr);//匹配内容到arr数组
		$tmp = $arr[2][0];
		$arr[2][0] = $arr[3][0];
		$arr[3][0] = $tmp;
	}
	return $arr;
}

// 获取推文发布者
function _get_twitter_push_name($conn) {
	// 匹配当前发布者名称
	$pattern = "/<strong class=\"fullname js-action-profile-name show-popup-with-id\" data-aria-label-part=\"\">(.*)<\/strong>/iUs";
	//匹配内容到arr数组，pre_match_all可以保留数据原编码
	preg_match_all($pattern, $conn, $arr);
	if (empty($arr[0])) {/* 采用第二种正则 */
		$pattern = "/<strong class=\"fullname js-action-profile-name show-popup-with-id\">(.*)<\/strong>/iUs";
		preg_match_all($pattern, $conn, $arr);//匹配内容到arr数组
	}
	// 去掉html标签
	// foreach ($arr[0] as $key => $value) {
	// 	$arr[$key] = trim(strip_tags($value));
	// }
	return $arr;
}

//+------------------------------------------------

/**
 +-----------------------------------------------------------------------------
 * 保存指定路径图片
 +-----------------------------------------------------------------------------
 * @param string $url 远程的完整图片地址
 * @param string $filename 是另存为的图片名字
 * @param string $save_dir 保存指定路径
 * @return mixed
 * @author elinx <654753115@qq.com> 2016-06-11
 * save_image("http://www.w3school.com.cn/ui/bg.gif");
 +-----------------------------------------------------------------------------
 */
function save_image($url, $filename="", $save_dir="./images/"){ 
	if($url == ""){return false;}
	$pic_ext = strrchr($url, ".");/*得到图片的扩展名*/
	$ext 	 = array(".gif", ".png", ".jpg", ".bmp", ".jpeg"); /* 支持格式 */
	if(!in_array($pic_ext, $ext)){echo "格式不支持！ ".$url."<br>";return false;} 
	if($filename == ""){$filename = time().$pic_ext;} /*间戳另起名*/
	if (!is_dir($save_dir)) { echo "保存 {$save_dir} 目录不存在！";return false; }
	/*开始捕捉*/
	ob_start(); /*图片数据缓存*/
	readfile($url);
	$img  = ob_get_contents();
	ob_end_clean();/*图片数据缓存清除*/
	$size = strlen($img);
	$fp   = fopen($save_dir.$filename , "a");/*打开路径*/
	fwrite($fp, $img);/*写入图片数据流*/
	fclose($fp);
	return $filename;
}


