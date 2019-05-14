<?php

/**
 * 导出csv
 *
 * @param array $data
 * @param array $header
 * @param string $fileName
 */
function exportCsv($data = [], $header = [], $fileName = '')
{
    header('Content-Type: application/vnd.ms-excel');
    header('Content-Disposition: attachment;filename=' . $fileName);
    header('Cache-Control: max-age=0');
    $fp = fopen('php://output', 'a');
    if (!empty($header)) {
        foreach ($header as $key => $value) {
            $header[$key] = iconv('utf-8', 'gbk', $value);
        }
        fputcsv($fp, $header);
    }
    $num = 0;
    //每隔$limit行，刷新一下输出buffer，不要太大，也不要太小
    $limit = 100000;
    //逐行取出数据，不浪费内存
    $count = count($data);
    if ($count > 0) {
        for ($i = 0; $i < $count; $i++) {
            $num++;
            //刷新一下输出buffer，防止由于数据过多造成问题
            if ($limit == $num) {
                ob_flush();
                flush();
                $num = 0;
            }
            $row = $data[$i];
            foreach ($row as $key => $value) {
                mb_convert_variables('utf-8', 'gbk', $row[$key]);
            }

            fputcsv($fp, $row);
        }
    }

    fclose($fp);
}

/**
 * 读取CSV文件
 *
 * @param string $csvFile csv文件路径
 * @param int $lines 读取行数
 * @param int $offset 起始行数
 * @return array|bool
 */
function importCsv($csvFile = '', $lines = 0, $offset = 0)
{
    if (!$fp = fopen($csvFile, 'r')) {
        return false;
    }
    $i = $j = 0;
    while (false !== ($line = fgets($fp))) {
        if ($i++ < $offset) {
            continue;
        }
        break;
    }
    $data = array();
    while (($j++ < $lines) && !feof($fp)) {
        $data[] = fgetcsv($fp);
    }
    fclose($fp);

    foreach ($data as &$v) {
        $str = collect($v)->implode(',');
        if (!mb_check_encoding($str,'utf-8')) {
            mb_convert_variables('utf-8', 'gbk', $v);
        }
    }

    return $data;
}