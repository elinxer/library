<html>
<head>
<title>登录验证</title>
<meta http-equiv="content-type" content="text/html;charset=utf-8">
</head>
<body>
<?php
include("db.php");

$db = new DB();

$name = $_POST['username'];

$pwd  = $_POST['password'];


echo $sql  = "select * from users where username='".$name."' and password='".$pwd."'";

$query = $db->select($sql);
echo '<br>';
// var_dump($query);

if ($query) {
      echo "登陆成功";
} else {
      echo "登陆失败";
}

?>
</body>
</html>