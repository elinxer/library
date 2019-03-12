<?php			
//数据库操作类

if(!defined('SQL_LAYER'))
{
	define('SQL_LAYER',"mysql");

	class sql_db
	{
		var $db_connect_id;
		var $query_result;
		var $row=array();
		var $rowset=array();
		var $num_queries=0;

		function __construct($sqlserver,$sqluser,$sqlpassword,$database,$persistency=true)
		{
			$this->persistency=$persistency;
			$this->user=$sqluser;
			$this->password=$sqlpassword;
			$this->server=$sqlserver;
			$this->dbname=$database;

			if($this->persistency)
			{
				$this->db_connect_id=mysql_pconnect($this->server,$this->user,$this->password);
			}
			else
			{
				$this->db_connect_id=mysql_connect($this->server,$this->user,$this->password);
			}
			if($this->db_connect_id)
			{
				if($database!="")
				{
					$this->dbname=$database;
					$dbselect=mysql_select_db($this->dbname);
					if(!$dbselect)
					{
						mysql_close($this->db_connect_id);
						$this->db_connect_id=$dbselect;
					}
				}
				return $this->db_connect_id;

			}
			else
			{
				return false;
			}
		}

		//其他基本方法
		function sql_close()//关闭数据库
		{
			if($this->db_connect_id)
			{
				if($this->query_result)
				{
					//mysql_free_result() 仅需要在考虑到返回很大的结果集时会占用多少内存时调用。
					//在脚本结束后所有关联的内存都会被自动释放。
					mysql_free_result($this->query_result);
				}

				//关闭数据库
				$result=mysql_close($this->db_connect_id);
				return $result;
			}
			else
			{
				return false;
			}
		}

		//SQL语句执行函数
		function sql_query($query="",$transaction=FALSE)
		{
			//释放内存
			unset($this->query_result);
			if($query!="")
			{
				$this->num_queries++;
				$this->query_result=mysql_query($query,$this->db_connect_id);
			}
			if($this->query_result)
			{
				//如果存在查询结果，则释放内存
				unset($this->row[$this->query_result]);
				unset($this->rowset[$this->query_result]);
				return $this->query_result;
			}
			else
			{
				return ($transaction==END_TRANSACTION) ? true : false;
			}
		}

		//定义获取数据的行数
		function sql_numrows($query_id=0)
		{
			if(!$query_id)
			{
				$query_id=$this->query_result;
			}
			if($query_id)
			{
				$result=mysql_num_rows($query_id);
				return $result;
			}
			else
			{
				return false;
			}
		}

		//取得前一次 MySQL 操作所影响的记录行数
		function sql_affectedrows()
		{
			if($this->db_connect_id)
			{
				$result=mysql_affected_rows($this->db_connect_id);
				return $result;
			}
			else
			{
				return false;
			}
		}

		//取得结果集中字段的数目
		function sql_numfields($query_id=0)
		{
			if(!$query_id)
			{
				$query_id=$this->query_result;
			}
			if($query_id)
			{
				$result=mysql_num_fields($query_id);
				return $result;
			}
			else
			{
				return false;
			}
		}

		//获取结果中指定字段的字段名
		function sql_fieldname($offset,$query_id=0)
		{
			if(!$query_id)
			{
				$query_id=$this->query_result;
			}
			if($query_id)
			{
				$result=mysql_field_name($query_id,$offset);
				return $result;
			}
			else
			{
				return false;
			}

		}

		//获取结果集中指定字段的类型
		function sql_fieldtype($offset,$query_id=0)
		{
			if(!$query_id)
			{
				$query_id=$this->query_result;
			}
			if($query_id)
			{
				$result=mysql_field_type($query_id,$offset);
				return $result;
			}
			else
			{
				return false;
			}
		}

		function sql_fetchrow($query_id=0)
		{
			if(!$query_id)
			{
				$query_id=$this->query_result;
			}
			if($query_id)
			{
				$this->row[$query_id]=mysql_fetch_array($query_id);
				return $this->row[$query_id];
			}
			else
			{
				return false;
			}
		}

		function sql_fetchrowset($query_id = 0)
		{
			if(!$query_id)
			{
				$query_id = $this->query_result;
			}
			if($query_id)
			{
				unset($this->rowset[$query_id]);
				unset($this->row[$query_id]);
				while($this->rowset[$query_id] = @mysql_fetch_array($query_id))
				{
					$result[] = $this->rowset[$query_id];
				}
				return $result;
			}
			else
			{
				return false;
			}
		}

		function sql_fetchfield($field, $rownum = -1, $query_id = 0)
		{
			if(!$query_id)
			{
				$query_id = $this->query_result;
			}
			if($query_id)
			{
				if($rownum > -1)
				{
					//取得结果的值
					$result = @mysql_result($query_id, $rownum, $field);
				}
				else
				{
					if(empty($this->row[$query_id]) && empty($this->rowset[$query_id]))
					{
						if($this->sql_fetchrow())
						{
							$result = $this->row[$query_id][$field];
						}
					}
					else
					{
						if($this->rowset[$query_id])
						{
							$result = $this->rowset[$query_id][0][$field];
						}
						else if($this->row[$query_id])
						{
							$result = $this->row[$query_id][$field];
						}
					}
				}
				return $result;
			}
			else
			{
				return false;
			}
		}

		//行选择函数
		function sql_rowseek($rownum, $query_id = 0)
		{
			if(!$query_id)
			{	//获取$query_id的值
				$query_id = $this->query_result;
			}
			if($query_id)
			{
				//移动内部结果的指针
				$result = @mysql_data_seek($query_id, $rownum);
				return $result;
			}
			else
			{
				return false;
			}
		}

		function sql_nextid()
		{
			if($this->db_connect_id)
			{	//取得上一步 INSERT 操作产生的 ID
				$result = @mysql_insert_id($this->db_connect_id);
				return $result;
			}
			else
			{
				return false;
			}
		}

		function sql_freeresult($query_id=0)
		{
			if(!$query_id)
			{
				$query_id=$this->query_result;
			}
			if($query_id)
			{
				//释放给定的变量的内存
				unset($this->row[$query_id]);
				unset($this->rowset[$query_id]);
				//释放内存
				mysql_free_result($query_id);
				return true;
			}
			else
			{
				return false;
			}
		}

		//SQL语句执行错误的函数
		function sql_error($query_id=0)
		{
			$result["message"]=mysql_error($this->db_connect_id);

			$result["code"]=mysql_errno($this->db_connect_id);
			return $result;
		}


	}
}

?>
