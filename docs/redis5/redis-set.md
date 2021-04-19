#### set 命令

redis set命令支持原子操作加锁和设置有效期

[Redis-set](https://redis.io/commands/set)

**使用**

```
SET test_nx "1" EX 60 NX
TTL test_nx
# 再次设置会返回null
SET test_nx "1" EX 60 NX
```

#### PHP-Redis驱动使用该方式

setnx意义：  SETNX 是『SET if Not eXists』(如果不存在，则 SET)的简写

设置成功，返回 1 ，设置失败，返回 0 。

```
# Redis设置nx锁并且设置有效期
$this->redis()->set($cacheNxKey = 'test_nx', 1, ['NX', 'EX' => 1]))
```

