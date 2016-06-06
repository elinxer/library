<?php
// 数据库处理类

/*
private 叫私有，类外面无法访问
public 叫公有，类里外都可以访问
protected 受保护，类里面跟继承类可以访问
访问指的是：
$class = new Class();
$class->private;
-----------------------
__construct() 叫做构造函数，用来进行类的初始化

*/

class DB
{
	private $dbhost ="localhost"; //数据库地址
	private $dbuser = "root"; //数据库用户名
	private $dbpwd  = ""; //数据库密码
	private $dbname = "test"; //数据库的名称
	private $charset = "utf-8"; //设置查询字符集gbk,gbk2312,utf-8

	public  $db =''; /*数据库连接句柄*/


	// 初始化数据库连接
	function __construct() {
		// mysqli链接MYSQL数据库，返回数据库句柄$mysqliDB
		$this->db = new mysqli($this->dbhost,$this->dbuser,$this->dbpwd,$this->dbname);
		if(mysqli_connect_errno()){ /* 判断链接错误*/
			echo "数据库连接失败:".mysqli_connect_error(); /*显示错误信息*/
			exit();
		}
		mysqli_set_charset($this->db, "utf8") ; /* 设置与数据库数据交流的编码 */
	}

	// 数据添加
	public function add($sql) {
		if ($this->db->query($sql)) {
			return $this->db->insert_id;
		} else {
			return 0;
		}
	}

	// 数据查询
	public function select ($sql) {
		$result = $this->db->query($sql); /*执行查询并返回给$result */
		// 如果查不到东西就返回0
		if (!$result) { return 0; }
		$arr = array();
		// 把数据库对象转换成数组形式
		while ($row = $result->fetch_assoc()) { /*循环获取数据库的数据*/
			$arr[] = $row; /* 循环每一个并放进去一个数组里面 */
		}
		return $arr;
	}

	
}

