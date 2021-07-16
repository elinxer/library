<?php

namespace Common\Special;

class Framework
{
    protected $root;

    protected $routerFile;

    /**
     * @param $prefix
     * @param $class
     * @param $findPath
     */
    function _autoload_class_find($prefix, $class, $findPath) {
        // 项目的命名空间前缀
        //$prefix = 'Special\\Common\\';
        // 目录前缀对应的根目录
        $base_dir = $findPath . '/';
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
    }

    public function autoload()
    {
        /**
         * Register Namespace Path
         */
        spl_autoload_register(function ($class) {
            $prefix = 'Special\\Common\\';
            $this->_autoload_class_find($prefix, $class, $findPath=$this->root);
        });
    }

    public function setRoot($path='')
    {
        $this->root = $path;
        return $this;
    }

    /**
     * @param $routerFile
     * @return $this
     */
    public function setRouterFile($routerFile)
    {
        $this->routerFile = $routerFile;
        return $this;
    }

    public function bootstrap()
    {
        $this->autoload();

        require_once $this->routerFile ? $this->routerFile : $this->root . '/routes.php';

        // Set Current Path
        \Common\Special\Router::setGateway('/special/common/index.php');
        \Common\Special\Router::setProject('\Special\Common\Controller\\');
        \Common\Special\Router::dispatch();
    }

}