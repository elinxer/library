<?php
header("Content-type:text/html;charset=utf-8");
include_once("db.php");

$db = new DB();

if(@$_GET['id'] <=100 && @$_GET['id']){
	$id=$_GET['id'];
	$conn=file_get_contents("http://www.93moli.com/news_list_4_$id.html");//获取页面内容

	$pattern="/<li><a title=\"(.*)\" target=\"_blank\" href=\"(.*)\">/iUs";//正则

	preg_match_all($pattern, $conn, $arr);//匹配内容到arr数组

	//print_r($arr);die;
	echo "正在采集URL数据列表$id...请稍后...";
	foreach ($arr[1] as $key => $value) {//二维数组[2]对应id和[1]刚好一样,利用起key
		$url="http://www.93moli.com/".$arr[2][$key];
		$sql="insert into list(title,url) value ('$value', '$url')";
		$db->add($sql);
	}
	$id++;
	echo "<script>window.location='caiji.php?id=$id'</script>";

}else{
	echo "采集数据结束。";
}

?>
