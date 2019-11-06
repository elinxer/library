
// php将2个日期之间的日期存到数组里面 ,日期范围 数组
// print_r(date_rang('2014-11-10', '2014-11-25'));
function date_rang($start_date, $end_date){
    return array_map(function($n) {
        return date('Y-m-d', $n);
        }, range(strtotime($start_date), strtotime($end_date), 24 * 3600)
    );
}

