### Redis cluster在laravel5以上应用

https://learnku.com/docs/laravel/7.x/redis/7498

https://www.jb51.net/article/123702.htm

在配置： `app/config/database.php`

```
'redis' => [

    'client' => 'predis',

    'default' => [
        'host' => env('REDIS_HOST', '127.0.0.1'),
        'password' => env('REDIS_PASSWORD', null),
        'port' => env('REDIS_PORT', 6379),
        'database' => env('REDIS_DB', 0),
    ],

    'options' => [
        'cluster' => 'redis',
    ],

    'clusters' => [
        'cluster1' => [
            [
                'host' => '127.0.0.1',
                'password' => '123456',
                'port' => 7100,
                'database' => 0,
            ],
            [
                'host' => '127.0.0.1',
                'password' => '123456',
                'port' => 7101,
                'database' => 0,
            ],
            [
                'host' => '127.0.0.1',
                'password' => '123456',
                'port' => 7102,
                'database' => 0,
            ],
            [
                'host' => '127.0.0.1',
                'password' => '123456',
                'port' => 7103,
                'database' => 0,
            ],
            [
                'host' => '127.0.0.1',
                'password' => '123456',
                'port' => 7104,
                'database' => 0,
            ],
            [
                'host' => '127.0.0.1',
                'password' => '123456',
                'port' => 7105,
                'database' => 0,
            ],
        ],
    ],

    'cache' => [
        'host' => env('REDIS_HOST', '127.0.0.1'),
        'password' => env('REDIS_PASSWORD', null),
        'port' => env('REDIS_PORT', 6379),
        'database' => env('REDIS_CACHE_DB', 1),
    ],

],

```


```

// 代码和配置完成通过laravel5.7，真实可用，如出现报错，咋要考虑是否是无法外部访问到集群

$redis = \Redis::connection('cluster1');
$redis->set('test','1');
echo $redis->get('test');
```

大概原理是这样，如执行以下redis命令

get test

会将test作crc32运算得到一个hash值

所有服务器按一定算法放到一个长度默认为128的数组中，每个服务器在其中占几项，由以下决定：

权重/总权重*总的服务器数量*128，可参考Predis\Cluster\Distribution\HashRing::addNodeToRing方法

每一项的hash值是按服务器ip:端口的格式，作crc32计算的

```

protected function addNodeToRing(&$ring, $node, $totalNodes, $replicas, $weightRatio)
{
    $nodeObject = $node['object'];
    $nodeHash = $this->getNodeHash($nodeObject);
    $replicas = (int) round($weightRatio * $totalNodes * $replicas);
    for ($i = 0; $i < $replicas; $i++) {
      $key = crc32("$nodeHash:$i");
      $ring[$key] = $nodeObject;
    }
}
```

key的hash值也有了，服务器环也计算好了，剩下的就是查找了，二分法能较快的查找相应的服务器节点
