<?php
// 表列名
$headArr = array('开始时间', '结束时间', '订单数量', '商品数量', '税额', '总计', '订单状态');
$array = array(
	array('2016-02-17', '2016-02-18', '444', '22222', '43232.3232', '3232323232.2', '1'),
	array('2016-02-17', '2016-02-18', '444', '22222', '43232.3232', '3232323232.2', '1'),
	array('2016-02-17', '2016-02-18', '444', '22222', '43232.3232', '3232323232.2', '1'),
	array('2016-02-17', '2016-02-18', '444', '22222', '43232.3232', '3232323232.2', '1'),
	array('2016-02-17', '2016-02-18', '444', '22222', '43232.3232', '3232323232.2', '1'),
	array('2016-02-17', '2016-02-18', '444', '22222', '43232.3232', '3232323232.2', '1'),
	array('2016-02-17', '2016-02-18', '444', '22222', '43232.3232', '3232323232.2', '1'),
	array('开始时间', '结束时间', '订单数量', '商品数量', '税额', '总计', '订单状态'),
);
// 导出表格
export_excel('filename', $headArr, $array);

//导出数据方法使用列子
function example_excel($save_file_name, $data_list=array())
{
	// 表格列名
	$headArr = array('开始时间', '结束时间', '订单数量', '商品数量', '税额', '总计', '订单状态');
    $data = array();
    // 重置传入数据的列名顺序，表列名不对应更改
    foreach ($data_list as $k=>$info){
        $data[$k]['date_start'] 	= $info['date_start'];
        $data[$k]['date_end'] 		= $info['date_end'];
        $data[$k]['order_number'] 	= $info['order_number'];
        $data[$k]['order_product_number'] = $info['order_product_number'];
        $data[$k]['tax']  			= $info['tax'];
        $data[$k]['order_total']  	= $info['order_total'];
        $data[$k]['order_status']  	= $info['order_status'];
    }
    $this->export_excel($save_file_name, $headArr,$data);
}
/**
 * 导入PHPExcel类驱动信息
 * @param $fileName 传入excel的完整路径名称
 * @param $headArr 列名称
 * @param $data 传入的数据数组
 */
function export_excel($fileName,$headArr,$data){
    //导入PHPExcel类库，因为PHPExcel没有使用用命名空间，只能inport导入
    require_once("PHPExcel.class.php");
    require_once("PHPExcel/Writer/Excel5.php");
    require_once("PHPExcel/IOFactory.php");
    $date = date("Y_m_d",time());
    $fileName .= "_{$date}.xls";
    //创建PHPExcel对象，注意，不能少了\
    $objPHPExcel = new \PHPExcel();
    $objProps = $objPHPExcel->getProperties();
    $key = ord("A"); //设置表开头
    foreach($headArr as $v){
        $colum = chr($key);
        $objPHPExcel->setActiveSheetIndex(0) ->setCellValue($colum.'1', $v);
        $objPHPExcel->setActiveSheetIndex(0) ->setCellValue($colum.'1', $v);
        $key += 1;
    }
    $column = 2;
    $objActSheet = $objPHPExcel->getActiveSheet();
    foreach($data as $key => $rows){ //行写入
        $span = ord("A");
        foreach($rows as $keyName=>$value){// 列写入
            $j = chr($span);
            $objActSheet->setCellValue($j.$column, $value);
            $span++;
        }
        $column++;
    }
    $fileName = iconv("utf-8", "gb2312", $fileName);
    $objPHPExcel->setActiveSheetIndex(0);
    ob_end_clean(); //清除缓冲区
    header("Content-type:application/vnd.ms-excel"); 
    header("Content-Disposition: attachment;filename=\"$fileName\"");
    header('Cache-Control: max-age=0');
    $objWriter = \PHPExcel_IOFactory::createWriter($objPHPExcel, 'Excel5');
    $objWriter->save('php://output'); //文件通过浏览器下载
    exit;
}


?>