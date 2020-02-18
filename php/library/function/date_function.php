<?php
// php将2个日期之间的日期存到数组里面 ,日期范围 数组
// print_r(date_rang('2014-11-10', '2014-11-25'));
function date_rang($start_date, $end_date){
    // array_map用法：第一个参数为处理方法，第二个为需要处理的传入数组
    // 该处：使用range获取时间戳数组列表，传入后转为指定日期格式
    // range方法说明：range 开始，结束，然后第三个参数是步长，步长用于开始到结束计算每个步的长度，达到顺序生成
    return array_map(function($n) {
        return date('Y-m-d', $n);
        }, range(strtotime($start_date), strtotime($end_date), 24 * 3600)
    );
}
