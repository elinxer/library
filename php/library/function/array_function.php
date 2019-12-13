<?php

/**
 * 经典排序，一维数组按指定键的顺序进行排序
 *
 * @param $data
 * @param array $columnKeys
 * @return array
 */
function array_key_sort($data, $columnKeys=[]) {

    //$columnKeys = array ( 'id' => 'ID', 'updated_at' => '最后更新时间', 'created_at' => '提交时间', 'username' => '用户名', );
    //$array      = array ( 'id' => 22, 'created_at' => '2019-12-12 17:41:07', 'updated_at' => '2019-12-12 19:00:24', 'username' => '', );
    // 一维数组按指定键的顺序进行排序
    $data = array_merge(array_flip(array_keys($columnKeys)), $data);

    return $data;
}
