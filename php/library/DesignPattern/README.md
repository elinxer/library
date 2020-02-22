### PHP 八大常用 设计模式

实际工作中我们很少会定性地去使用设计模式，多数情况会直接编码，尤其是在业务忙碌的时候，但设计模式要是能熟练于胸
并且快速的应用到业务上的话，能大大提高业务的可维护性以及降低出错的风险。

- 8大设计模式

包括：

1.单例模式

2.工厂模式

3.注册模式

4.适配器模式

5.策略模式

6.观察者模式

7.原型模式

8.装饰器模式

#### 1.单例模式

单例模式解决的是如何在整个项目中创建唯一对象实例的问题，工厂模式解决的是如何不通过new建立实例对象的方法（可以直接获取对象）

> 1.$_instance必须声明为静态的私有变量 
>
> 2.构造函数和析构函数必须声明为私有,防止外部程序new 类从而失去单例模式的意义
>
> getInstance()方法必须设置为公有的,必须调用此方法 以返回实例的一个引用
>
> ::操作符只能访问静态变量和静态函数
>
> new对象都会消耗内存
>
> 使用场景:最常用的地方是数据库连接。
>
> 使用单例模式生成一个对象后， 该对象可以被其它众多对象所使用。
>
> 私有的__clone()方法防止克隆对象
>

单例模式，使某个类的对象仅允许创建一个。构造函数private修饰， 
申明一个static getInstance方法，在该方法里创建该对象的实例。如果该实例已经存在，则不创建。比如只需要创建一个数据库连接。 

```
<?php
// 单例模式
trait Singleton
{
    private static $instance;

    static function getInstance(...$args)
    {
        if(!isset(self::$instance)){
            self::$instance = new static(...$args);
        }
        return self::$instance;
    }
}

class UserService
{
    use Singleton;

    private function __clone()
    {
        // TODO: Implement __clone() method.
    }
}

$service = UserService::getInstance();

```
#### 2. 工厂模式

工厂模式可以直接跟进已经编写好的方法根据类名或其它规则直接初始化一个类，而不是直接new。
使用工厂模式，可以避免当改变某个类的名字或者方法之后，在调用这个类的所有的代码中都修改它的名字或者参数
因此可以将类名或规则作为参数传到初始化里面作为某一个模块的调用接口等。

由于命名空间的普遍适用性，以下都默认加载了自动加载类，命名空间自行对应。

> 如果某个类在很多的文件中都new ClassName()，那么万一这个类的名字
> 发生变更或者参数发生变化，如果不使用工厂模式，就需要修改每一个PHP
> 代码，使用了工厂模式之后，只需要修改工厂类或者方法就可以了。

```
<?php

use ReflectionClass;
use Exception;

class ChannelFactory
{
    /**
     * 因为静态类，可以加入静态数组进行复用不需要重新创建对象
     *
     * @var array
     */
    private static $classObjArr = [];

    /**
     * 在工厂中创建对象并将其返回
     *
     * @param string $channel_code 渠道号
     * @return mixed|object
     * @throws
     */
    public static function create($channelCode) : ChannelBase
    {
        // 类命名空间地址 项目默认已采用命名空间加载类地址
        $className = 'Common\ChannelBlend\Channel\Channel_' . $channelCode;

        $key = md5($className);
        if (isset(self::$classObjArr[$key]) && !empty(self::$classObjArr[$key])) {
            return self::$classObjArr[$key];
        }

        if (!class_exists($className)) {
            throw new Exception('channel code not exist!');
        }

        // 反射获取类信息
        $ref = new ReflectionClass($className);

        self::$classObjArr[$key] = $obj = $ref->newInstance();

        return $obj;
    }
    
    // 手动加载的普通案例, 跟进参数创建不同的数据库连接类 TODO
    // 确保创建方法入口一致，但创建的数据类可以来源不同，如mysql，sqlite，Oracle等
    public static function normalCreateDb($db = 1) 
    {
        if ($db == 1) {
            $db = new DB1();   
        }
        
        return $db;
    }

}
```

#### 注册模式

注册模式，解决全局共享和交换对象。对已经创建好的对象，挂载到某个全局可以使用的数组上，
在需要使用的时候可以直接从该数组中获取即可。或将对象注册到全局树上，任何地方都可以直接去访问。

```
<?php

class Register
{
    protected static $objects;

    function set($alias, $object)//将对象注册到全局的树上
    {
        self::$objects[$alias] = $object;//将对象放到树上
    }

    static function get($name){
        return self::$objects[$name];//获取某个注册到树上的对象
    }

    function _unset($alias)
    {
        unset(self::$objects[$alias]); //移除某个注册到树上的对象。
    }
}

因为该模式实际经常使用但常常不会留意，故作进一步解释：

// 业务代码初始前或进入业务时设置请求内容到类中
$request = $_REQUEST; 

$register = new Register();
$register->set('request', $request);

// 将$register传递往下层传递或置为全局变量
// ..... 业务代码 N+ 1行

// 获取到上层注册内容
$register->get('request'); 

```
#### 适配器模式

适配器模式也可以说是统一出口模式，将各种截然不同的函数接口封装成统一的API。
PHP中的数据库操作有MySQL,MySQLi,PDO三种，可以用适配器模式统一成一致，使不同的数据库操作，
统一成一样的API。类似的场景还有cache适配器，可以将memcache,redis,file,apc等不同的缓存函数，统一成一致。
首先定义一个接口(有几个方法，以及相应的参数)。然后，有几种不同的情况，就写几个类实现该接口。
将完成相似功能的函数，统一成一致的方法。

```
<?php
namespace IMooc;
interface IDatabase
{
    function connect($host, $user, $passwd, $dbname);
    function query($sql);
    function close();
}

MySQL
<?php
namespace IMooc\Database;
use IMooc\IDatabase;
class MySQL implements IDatabase
{
    protected $conn;
    function connect($host, $user, $passwd, $dbname)
    {
        $conn = mysql_connect($host, $user, $passwd);
        mysql_select_db($dbname, $conn);
        $this->conn = $conn;
    }

    function query($sql)
    {
        $res = mysql_query($sql, $this->conn);
        return $res;
    }

    function close()
    {
        mysql_close($this->conn);
    }
}

MySQLi
<?php
namespace IMooc\Database;
use IMooc\IDatabase;
class MySQLi implements IDatabase
{
    protected $conn;

    function connect($host, $user, $passwd, $dbname)
    {
        $conn = mysqli_connect($host, $user, $passwd, $dbname);
        $this->conn = $conn;
    }

    function query($sql)
    {
        return mysqli_query($this->conn, $sql);
    }

    function close()
    {
        mysqli_close($this->conn);
    }
}
PDO
<?php
namespace IMooc\Database;
use IMooc\IDatabase;
class PDO implements IDatabase
{
    protected $conn;
    function connect($host, $user, $passwd, $dbname)
    {
        $conn = new \PDO("mysql:host=$host;dbname=$dbname", $user, $passwd);
        $this->conn = $conn;
    }
    function query($sql)
    {
        return $this->conn->query($sql);
    }

    function close()
    {
        unset($this->conn);
    }
}

```

以上即是将三个数据库连接方式统一了内部出口的固有api，配合工厂模式或策略模式可以继续封装一层统一出口，
达到根据参数即可切换不同的连接引擎，而无需去更换底层代码。
通过以上案例，PHP与MySQL的数据库交互有三套API，在不同的场景下可能使用不同的API，那么开发好的代码，换一个环境，可能就要改变它的数据库API，
那么就要改写所有的代码，使用适配器模式之后，就可以使用统一的API去屏蔽底层的API差异带来的环境改变之后需要改写代码的问题。

#### 策略模式

