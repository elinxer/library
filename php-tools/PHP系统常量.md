__FILE__

这个默认常量是 PHP 程序文件名。若引用文件 (include 或 require)则在引用文件内的该常量为引用文件名，而不是引用它的文件名。

__LINE__

这个默认常量是 PHP 程序行数。若引用文件 (include 或 require)则在引用文件内的该常量为引用文件的行，而不是引用它的文件行。

PHP_VERSION

这个内建常量是 PHP 程序的版本，如 ‘3.0.8-dev’。

PHP_OS

这个内建常量指执行 PHP 解析器的操作系统名称，如 ‘Linux’。

TRUE

这个常量就是真值 (true)。

FALSE

这个常量就是伪值 (false)。

E_ERROR

这个常量指到最近的错误处。

E_WARNING

这个常量指到最近的警告处。

E_PARSE

本常式为解析语法有潜在问题处。

E_NOTICE

这个常式为发生不寻常但不一定是错误处。例如存取一个不存在的变量。

define() 的功能可以让我们自行定义所需要的常量。见下例

<?php

define(“COPYRIGHT”, “Copyright &copy; 2000, netleader.126.com”);

echo COPYRIGHT;

?>

2.

__LINE__
文件中的当前行号。

__FILE__
文件的完整路径和文件名。如果用在包含文件中，则返回包含文件名。自 PHP 4.0.2 起，

__FILE__

总是包含一个绝对路径，而在此之前的版本有时会包含一个相对路径。

__FUNCTION__
函数名称（PHP 4.3.0 新加）。自 PHP 5 起本常量返回该函数被定义时的名字（区分大小写）。在 PHP 4 中该值总是小写字母的。

__CLASS__
类的名称（PHP 4.3.0 新加）。自 PHP 5 起本常量返回该类被定义时的名字（区分大小写）。在 PHP 4 中该值总是小写字母的。

__METHOD__
类的方法名（PHP 5.0.0 新加）。返回该方法被定义时的名字（区分大小写）。

3.

1）dirname(__FILE___) 函数返回的是脚本所在在的路径。

     比如文件 b.php 包含如下内容：

     <?php

    $basedir = dirname(__FILE__);

    ?>

    如果b.php被其他目录里的a.php文件require 或者 include 去引用的话。

    变量$basedir 的内容还是b.php所在的那个文件夹的路径。

    而不是变成a.php文件所在的目录。

2）dirname(__FILE__) 一般会返回文件所的当前目录到系统根目录的一个目录结构。

    不会返回当前的文件名称。

    dirname(__FILE__) 也可能返回一个 . (当前目录)

    [原因是 b.php 文件在 http.conf 或者 PHP 配置开发环境的默认WEB目录下.

    比如 WEB_ROOT 为: "C:/root/www/".]

    b.php文件路径为: “C:/root/www/b.php”.

3）使用方法提示,

    如果重复一次可以把目录往上提升一个层次:

    比如：$d = dirname(dirname(__FILE__));

    其实就是把一个目录给dirname()做参数了．因为dirname()返回最后的目录不带＼＼或者是／

    所以重复使用的时候可以认为　dirname()　把最下层的目录当成文件名来处理了．照常返回

   当前目录的上级目录.这样重复就得到了它的上一级的目录.

4）包含得到上一级目录的文件

    include(dirname(__FILE__).’/../filename