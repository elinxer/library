<?php

class mysql
{

    /**
     * 拼接批量插入SQL
     *
     * @param string $table 表明,可以带上数据库
     * @param array $dataValues 数据集
     * @param array $columns 数据字段集，用于匹配数据集单个数组
     * @return bool|string
     */
    public function insetIgnoreBat($table, $dataValues, $columns = [])
    {
        $sql = 'insert ignore into ' . $table . ' (' . implode(',', $columns) . ') values ';
        foreach ($dataValues as $columnArr) {
            $sql .= "(";

            $arr = [];
            foreach ($columns as $key => $column) {
                $arr[$key] = "'{$columnArr[$column]}'";
            }

            $sql .= implode(',', $arr);

            $sql .= "),";
        }

        $sql = substr($sql, 0, strlen($sql) - 1);

        return $sql;
    }

}
