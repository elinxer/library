<?php

/**
 * 使用SPL组册这个自动加载函数后，遇到下述代码时这个函数会尝试   从/path/to/project/src/Baz/Qux.php文件中加载\Foo\Bar\Baz\Qux类：
 *  new \Foo\Bar\Baz\Qux;
 * @param string $class 完全限定的类名。
 * @return void
 **/
spl_autoload_register(function ($class) {
    // 项目的命名空间前缀
    $prefix = 'Foo\\Bar\\';

    // 目录前缀对应的根目录
    $base_dir = __DIR__ . '/src/';

    // 判断传入的类是否使用了这个命名空间前缀
    $len = strlen($prefix);
    if (strncmp($prefix, $class, $len) !== 0) {
        // 没有使用，交给注册的下一个自动加载器处理
        return;
    }

    // 获取去掉前缀后的类名
    $relative_class = substr($class, $len);

    // 把命名空间前缀替换成根目录，
    // 在去掉前缀的类名中，把命名空间分隔符替换成目录分隔符，
    // 然后在后面加上.php
    $file = $base_dir . str_replace('\\', '/', $relative_class) . '.php';

    // 如果该文件存在，就将其导入
    if (file_exists($file)) {
        require $file;
    }
});

