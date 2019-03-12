 在PHP网站开发中，为了满足网站的需要，时常需要对PHP环境变量进行设置和应用，在虚拟主机环境下，有时我们更需要通过PHP环境变量操作函数来对PHP环境变量值进行设置。为此我们有必要对PHP环境变量先有所熟悉。今天和大家分享PHP环境变量$_SERVER和PHP系统常量的部分详细说明。
　　PHP环境变量主要有$GLOBALS[]、$_SERVER[]、$_GET[]、$_POST[]、$_COOKIE[]、$_FILES[]、$_ENV[]、$_REQUEST[]、$_SESSION[]。$_GET和$_POST主要针对FORM表单提交的数据，$_COOKIE和$_SESSION主要针对客户端游览器和服务器端会话数据。$_FILES主要针对文件上传时提交的数据，$_REQUEST主要针对提交表单中所有请求数组，包括$_GET、$_POST、$_COOKIE中的所有内容，你可以通过print_r函数分别输出$_REQUEST或者$_COOKIE等进行比较。PHP环境如何搭建？

PHP环境变量$_SERVER

　　是一个包含服务器端相关信息的PHP全局环境变量，在PHP4.1.0之前的版本使用$HTTP_SERVER_VARS。更多信息可以参考这里。

　　$_SERVER['PHP_SELF'] 当前正在执行脚本的文件名，与 document root相关。在FORM表单中，如执行文件是本身，你可以在ACTION中使用$_SERVER['PHP_SELF']，好处是当执行文件名有变动时可以不去频繁替换ACTION中的文件名。

　　$_SERVER['SERVER_NAME'] 当前运行的PHP程序所在服务器主机的名称。

　　$_SERVER['REQUEST_METHOD'] 访问页面时的请求方法，即GET、HEAD、POST、PUT。

　　$_SERVER['DOCUMENT_ROOT'] 当前运行的PHP程序所在的文档根目录。也就是PHP.INI文件中的定义。

　　$_SERVER['HTTP_REFERER'] 链接到当前页面的前一页面的URL地址。在页面跳转功能中非常有用。

　　$_SERVER['REMOTE_ADDR'] 正在浏览当前页面访问者的IP地址。

　　$_SERVER['REMOTE_HOST'] 正在浏览当前页面用户的主机名。

　　$_SERVER['REMOTE_PORT'] 正在游览的用户连接到服务器时所使用的端口。

　　$_SERVER['SCRIPT_FILENAME'] 当前执行脚本的绝对路径名。

　　$_SERVER['SERVER_PORT'] 服务器所使用的端口

　　$_SERVER['SCRIPT_NAME'] 包含当前脚本的路径。这在页面需要指向自己时非常有用。

　　$_SERVER['REQUEST_URI'] 访问此页面所需的URI。如“/index.html”。

　　$_SERVER['PHP_AUTH_USER'] 应用在HTTP用户登录认证功能中，这个变量是用户输入的用户名。

　　$_SERVER['PHP_AUTH_PW'] 应用在HTTP用户登录认证功能中，这个变量便是用户输入的密码。

　　$_SERVER['AUTH_TYPE'] 应用在HTTP用户登录认证功能中，这个变量便是认证的类型。

　　注：上述提到的这些PHP全局环境变量，在php.ini中的register_globals设置为on时，这些变量在所有PHP程序脚本中都可用，也就是$_SERVER数组被分离了。当然为了安全考虑，还是不要将register_globals打开为好。

PHP系统常量

　　__FILE__ 当前PHP程序脚本的绝对路径及文件名称

　　__LINE__ 存储该常量所在的行号

　　__FUNCTION__ 存储该常量所在的函数名称

　　__CLASS__ 存储该常量所在的类的名称

　　PHP_VERSION 存储当前PHP的版本号，也可以通过PHPVERSION()函数获取。

　　PHP_OS 存储当前服务器的操作系统

　　PHP环境变量$_SERVER的更多信息请参考PHP帮助手册，文章开头提到在虚拟主机环境下我们需要通过PHP环境变量操作函数来对PHP环境变量值进行设置，主要用到ini_set和ini_get，其实还有更多此类函数，比如PHP中的错误报告设置等，其实都涉及到PHP.INI中的相关内容，有机会下次分享。