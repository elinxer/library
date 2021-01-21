<?php

namespace Common;

use Exception;

/**
 * Class DataFactory
 * @package Common\Web
 */
class DataFactory implements DataInterface
{
    /**
     * @var array
     */
    protected static $availableModes=[];

    /**
     * @var array
     */
    protected $configs = [];

    /**
     * @var DataInterface
     */
    private $mode = null;

    /**
     * @return mixed
     */
    public function getMode()
    {
        return $this->mode;
    }

    /**
     * @param mixed ...$args
     * @return mixed
     */
    public function getData(...$args)
    {
        return $this->mode->getData(...$args);
    }

    /**
     * @return array
     */
    public static function modeClass()
    {
        return [];
    }

    /**
     * Register builtin fields.
     *
     * @return void
     */
    public static function registerBuilt()
    {
        $map = static::modeClass();
        foreach ($map as $abstract => $class) {
            static::$availableModes[$abstract] = $class;
        }
    }

    /**
     * Find field class.
     *
     * @param string $method
     *
     * @return bool|mixed
     */
    public static function findModeClass($method)
    {
        $class = static::$availableModes[$method] ?: '';
        if (class_exists($class)) {
            return $class;
        }
        return false;
    }

    /**
     * Generate a Mode object
     *
     * @param $method
     * @param $arguments
     * @return mixed
     * @throws Exception
     */
    public function __call($method, $arguments)
    {
        static::registerBuilt();

        if ($className = static::findModeClass($method)) {
            if ($arguments)  {
                // At __construct Use func_get_args() to get params
                $dataMode = new $className($arguments);
            } else {
                $dataMode = new $className();
            }
            return $dataMode;
        }

         throw new Exception("Class mode [$method] does not exist.");
    }

}
