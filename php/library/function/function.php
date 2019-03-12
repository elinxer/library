<?php

/**
 * 5行代码生成树
 * 此方法由@Tonton 提供
 * http://my.oschina.net/u/918697
 *
 * @date 2012-12-12
 * @param $items
 * @return array
 */
function genTree5($items) { 
    // $items = array(
    //     1 => array('id' => 1, 'pid' => 0, 'name' => '江西省'),
    //     2 => array('id' => 2, 'pid' => 0, 'name' => '黑龙江省'),
    //     3 => array('id' => 3, 'pid' => 1, 'name' => '南昌市'),
    //     4 => array('id' => 4, 'pid' => 2, 'name' => '哈尔滨市'),
    //     5 => array('id' => 5, 'pid' => 2, 'name' => '鸡西市'),
    //     6 => array('id' => 6, 'pid' => 4, 'name' => '香坊区'),
    // );
    foreach ($items as $item) 
        $items[$item['pid']]['son'][$item['id']] = &$items[$item['id']]; 
    return isset($items[0]['son']) ? $items[0]['son'] : array(); 
}

/**
 * 把指定时间段切份 - N份
 * -----------------------------------
 * @param string $start 开始时间 2016-07-18 00:00:00
 * @param string $end 结束时间 2016-07-18 23:59:59
 * @param boolean $format 是否格式化返回日期
 * @param int $nums 切分数目
 * @return array 时间段数组
 */
function cut_up_time_part($start, $end="", $nums = 7, $format=true) {
    $start = strtotime($start);
    $end   = strtotime($end);
    $parts = ($end - $start)/$nums;
    $last  = ($end - $start)%$nums;
    if ( $last > 0) {
        $parts = ($end - $start - $last)/$nums;
    }
    for ($i=1; $i <= $nums; $i++) { 
        $_end  = $start + $parts * $i;
        $arr[] = array($start + $parts * ($i-1), $_end);
    }
    $len = count($arr)-1;
    $arr[$len][1] = $arr[$len][1] + $last;
    if ($format) {
        foreach ($arr as $key => $value) {
            $arr[$key][0] = date("Y-m-d H:i:s", $value[0]);
            $arr[$key][1] = date("Y-m-d H:i:s", $value[1]);
        }
    }
    return $arr;
}

/**
 * 邮箱验证
 * @param string $email 需要验证的邮箱
 * @return boolean
 */
function verify_email($email="") {
    $pattern = "/^[_.0-9a-z-a-z-]+@([0-9a-z][0-9a-z-]+.)+[a-z]{2,4}$/";
    if (!preg_match($pattern, $email)){
       return false;
    } else {
        return true;
    }
}

/**
 * php对url解码和编码
 *
 * $url="ftp://ud03:password@s.jb51.net/中文/中文.rar";
 * echo url_encode_decode($url);
 *
 * @param string $url
 * @return mixed|string
 */
function url_encode_decode($url="")
{
    $url = rawurlencode(mb_convert_encoding($url, 'gb2312', 'utf-8')); //当前编码为utf-8强制转为gb2312
    $a = array("%3A", "%2F", "%40");
    $b = array(":", "/", "@");
    $url = str_replace($a, $b, $url);
    return $url;
}

/**
 * 获取目录下所有文件，包括子目录
 * @param string $dir 目录地址
 * @return array 文件名称数组
 */
function get_filenames_bydir($dir){
    $files =  array();
    get_all_files($dir,$files);
    return $files;
}
function get_all_files($path,&$files) {
    if(is_dir($path)){
        $dp = dir($path);
        while ($file = $dp ->read()){
            if($file !="." && $file !=".."){
                get_all_files($path."/".$file, $files);
            }
        }
        $dp ->close();
    }
    if(is_file($path)){
        $files[] =  $path;
    }
}

/**
 * 获取当前页面完整URL地址
 */
 function get_url() {
    $sys_protocal   = isset($_SERVER['SERVER_PORT']) && $_SERVER['SERVER_PORT'] == '443' ? 'https://' : 'http://';
    $php_self       = $_SERVER['PHP_SELF'] ? $_SERVER['PHP_SELF'] : $_SERVER['SCRIPT_NAME'];
    $path_info      = isset($_SERVER['PATH_INFO']) ? $_SERVER['PATH_INFO'] : '';
    $relate_url     = isset($_SERVER['REQUEST_URI']) ? $_SERVER['REQUEST_URI'] : $php_self.(isset($_SERVER['QUERY_STRING']) ? '?'.$_SERVER['QUERY_STRING'] : $path_info);
    return $sys_protocal.(isset($_SERVER['HTTP_HOST']) ? $_SERVER['HTTP_HOST'] : '').$relate_url;
}

/**
 * 返回时间段星期数组
 * @param string $start 开始时间 2016-01-10
 * @param string $end 结束时间 2016-01-28
 * @param string $m_or_w 模式 week / month
 * @return array
 */
function get_time_array($start, $end, $m_or_w = 'week') {
    $array = array();
    $start = strtotime($start." 00:00:00");
    $end   = strtotime($end." 23:59:59");
    if($m_or_w == 'week') {
        $s_w = date('w', $start);
        $f_w = 7 - $s_w;
    } else {
        $allday = date('t',$start);
        $today  = date('d',$start);
        $f_w    = $allday - $today + 1;
    }
    if($f_w) {
        $f_end = $start + 86400 * $f_w - 1;
    } else {
        $f_end = $start + 86400 - 1;
    }
    $new_end = $f_end;
    if($end < $new_end) {
        $array[] = array($start, $end);
        return $array;
    }
    while ($end > $new_end) {
        $array[] = array($start, $new_end);
        $start = $new_end + 1;
        if($m_or_w == 'week') {
            $day = 7;
        } else {
            $day = date('t', $new_end + 10);
        }
        $new_end = $new_end + $day * 86400;
    }
    if($m_or_w == 'week') {
        $fullday = 7;
    } else {
        $fullday = date('t', $new_end);
    }
    $arrayr[] = array($new_end - $fullday * 86400 + 1, $end);
    return $array;
}

/**
 * 生成从开始月份到结束月份的月份数组
 *
 * @param int $start 开始时间戳
 * @param int $end 结束时间戳
 * @param int $strtotime 时间格式
 * @return array|string
 */
function get_month_list_bytime($start, $end, $strtotime=0){
    if(!is_numeric($start)||!is_numeric($end)||($end<=$start)) return '';
    $start  = date('Y-m', $start);
    $end    = date('Y-m', $end);
    $start  = strtotime($start.'-01');
    $end    = strtotime($end.'-01');
    $i      = 0;
    $d      = array();
    while($start <= $end){
        //这里累加每个月的的总秒数 计算公式：上一月1号的时间戳秒数减去当前月的时间戳秒数
        if ($strtotime == 1) {
            $d[$i] = $start;
        } else {
            $d[$i] = trim(date('Y-m', $start),' ');
        }
        $start += strtotime('+1 month', $start)-$start;
        $i++;
    }
    return $d;
}

/**
 * 删除文件目录
 *
 * @param string $dir 删除目录路径
 * @return boolean
 */
function del_dir($dir) {
    //先删除目录下的文件：
    $dh=opendir($dir);
    while ($file=readdir($dh)) {
        if($file!="." && $file!="..") {
            $fullpath=$dir."/".$file;
            if(!is_dir($fullpath)) {
                @unlink($fullpath);
            } else {
                deldir($fullpath);
            }
        }
    }
    closedir($dh);
    //删除当前文件夹：
    if(rmdir($dir)) {
        return true;
    } else {
        return false;
    }
}

/**
 * 取文件最后$n行，此方法高效
 *
 * @param string $filename 文件路径
 * @param int $n 最后几行
 * @return mixed false表示有错误，成功则返回数组
 */
function fileLast_lines($filename,$n){
    if(!$fp = fopen($filename,'r')){
        echo "打开文件失败，请检查文件路径是否正确，路径和文件名不要包含中文";
        return false;
    }
    $pos = -2;
    $eof = "";
    $str = array();
    while($n > 0){
        while($eof != "\n"){
            if(!fseek($fp, $pos, SEEK_END)){
                $eof = fgetc($fp);
                $pos--;
            }else{
                break;
            }
        }
        $str[] .= fgets($fp);
        $eof = "";
        $n--;
    }
    return $str;
}

/**
 * 二维数组按键值升降排序
 *
 * @param array $arr 排序二维数组
 * @param string $keys 排序键名
 * @param string $type 升降asc/desc
 * @return array 数组
 */
function array_sort($arr, $keys, $type = 'asc'){
    $keysvalue = $new_array = array();
    foreach($arr as $k => $v){
        $keysvalue[$k] = $v[$keys];
    }
    if($type == 'asc'){
        asort($keysvalue);
    }else{
        arsort($keysvalue);
    }
    reset($keysvalue);
    foreach($keysvalue as $k => $v){
        $new_array[$k] = $arr[$k];
    }
    return $new_array;
}

/**
 * 存储单位转换，字节转KB和MB和GB
 *
 * @param int $unit 字节单位
 * @return string
 */
function storage_unit($unit) {
    $ext = '';
    if ($unit < 1024) {
        $result = $unit;
        $ext = '字节';
    } else if (($unit/1024)  < 1024) {
        $result = ($unit / 1024);
        $ext = ' KB';
    } else if (($unit / (1024*1024)) < 1024) {

        $result = ($unit /(1024*1024));
        $ext = 'MB';
    } else if ($unit >= (1024*1024*1024)) {

        $result = ($unit / (1024*1024*1024));
        $ext = 'GB';
    } else {
        $result = 0;
    }
    return number_format($result,3).$ext;
}

/**
 * 数据加密 / 解密
 *
 * $str = '3412eafrmlcjS8g75DgHXRlehynzjp0Yw40fPYCl';
 * $key = 'walle';
 * $act = 'DECODE';
 * echo authcode($str,$act,$key,100);
 *
 * @param string $string  明文 或 密文
 * @param string $operation DECODE表示解密,其它表示加密
 * @param string $key  密匙
 * @param int $expiry 密文有效期
 * @return string 返回密文
 */
function authcode($string, $operation = 'DECODE', $key = '', $expiry = 0) {
    // 动态密匙长度，相同的明文会生成不同密文就是依靠动态密匙
    $ckey_length = 4;
    // 密匙
    $key = md5($key ? $key : "");
    // 密匙a会参与加解密
    $keya = md5(substr($key, 0, 16));
    // 密匙b会用来做数据完整性验证
    $keyb = md5(substr($key, 16, 16));
    // 密匙c用于变化生成的密文
    $keyc = $ckey_length ? ($operation == 'DECODE' ? substr($string, 0, $ckey_length): substr(md5(microtime()), -$ckey_length)) : '';
    // 参与运算的密匙
    $cryptkey = $keya.md5($keya.$keyc);
    $key_length = strlen($cryptkey);
    // 明文，前10位用来保存时间戳，解密时验证数据有效性，10到26位用来保存$keyb(密匙b)，解密时会通过这个密匙验证数据完整性
    // 如果是解码的话，会从第$ckey_length位开始，因为密文前$ckey_length位保存 动态密匙，以保证解密正确
    $string = $operation == 'DECODE' ? base64_decode(substr($string, $ckey_length)) : sprintf('%010d', $expiry ? $expiry + time() : 0).substr(md5($string.$keyb), 0, 16).$string;
    $string_length = strlen($string);
    $result = '';
    $box = range(0, 255);
    $rndkey = array();
    // 产生密匙簿
    for($i = 0; $i <= 255; $i++) {
        $rndkey[$i] = ord($cryptkey[$i % $key_length]);
    }
    // 用固定的算法，打乱密匙簿，增加随机性，好像很复杂，实际上对并不会增加密文的强度
    for($j = $i = 0; $i < 256; $i++) {
        $j = ($j + $box[$i] + $rndkey[$i]) % 256;
        $tmp = $box[$i];
        $box[$i] = $box[$j];
        $box[$j] = $tmp;
    }
    // 核心加解密部分
    for($a = $j = $i = 0; $i < $string_length; $i++) {
        $a = ($a + 1) % 256;
        $j = ($j + $box[$a]) % 256;
        $tmp = $box[$a];
        $box[$a] = $box[$j];
        $box[$j] = $tmp;
        // 从密匙簿得出密匙进行异或，再转成字符
        $result .= chr(ord($string[$i]) ^ ($box[($box[$a] + $box[$j]) % 256]));
    }
    if($operation == 'DECODE') {
        // substr($result, 0, 10) == 0 验证数据有效性
        // substr($result, 0, 10) - time() > 0 验证数据有效性
        // substr($result, 10, 16) == substr(md5(substr($result, 26).$keyb), 0, 16) 验证数据完整性
        // 验证数据有效性，请看未加密明文的格式
        if((substr($result, 0, 10) == 0 || substr($result, 0, 10) - time() > 0) && substr($result, 10, 16) == substr(md5(substr($result, 26).$keyb), 0, 16)) {
            return substr($result, 26);
        } else {
            return '';
        }
    } else {
        // 把动态密匙保存在密文里，这也是为什么同样的明文，生产不同密文后能解密的原因
        // 因为加密后的密文可能是一些特殊字符，复制过程可能会丢失，所以用base64编码
        return $keyc.str_replace('=', '', base64_encode($result));
    }
}

/**
 * 判断是否为手机访问
 * @return  boolean
 */
function tea_is_mobile() {
	static $sp_is_mobile;
	if ( isset($sp_is_mobile) )
		return $sp_is_mobile;
	if ( empty($_SERVER['HTTP_USER_AGENT']) ) {
		$sp_is_mobile = false;
	} elseif ( strpos($_SERVER['HTTP_USER_AGENT'], 'Mobile') !== false // many mobile devices (all iPhone, iPad, etc.)
			|| strpos($_SERVER['HTTP_USER_AGENT'], 'Android') !== false
			|| strpos($_SERVER['HTTP_USER_AGENT'], 'Silk/') !== false
			|| strpos($_SERVER['HTTP_USER_AGENT'], 'Kindle') !== false
			|| strpos($_SERVER['HTTP_USER_AGENT'], 'BlackBerry') !== false
			|| strpos($_SERVER['HTTP_USER_AGENT'], 'Opera Mini') !== false
			|| strpos($_SERVER['HTTP_USER_AGENT'], 'Opera Mobi') !== false ) {
		$sp_is_mobile = true;
	} else {
		$sp_is_mobile = false;
	}
	return $sp_is_mobile;
}

/**
 * 检查字符串是否是UTF8编码
 *
 * @param string $string 字符串
 * @return bool
 */
function is_utf8($string) {
    return preg_match('%^(?:
         [\x09\x0A\x0D\x20-\x7E]            # ASCII
       | [\xC2-\xDF][\x80-\xBF]             # non-overlong 2-byte
       |  \xE0[\xA0-\xBF][\x80-\xBF]        # excluding overlongs
       | [\xE1-\xEC\xEE\xEF][\x80-\xBF]{2}  # straight 3-byte
       |  \xED[\x80-\x9F][\x80-\xBF]        # excluding surrogates
       |  \xF0[\x90-\xBF][\x80-\xBF]{2}     # planes 1-3
       | [\xF1-\xF3][\x80-\xBF]{3}          # planes 4-15
       |  \xF4[\x80-\x8F][\x80-\xBF]{2}     # plane 16
    )*$%xs', $string);
}

/**
 * 产生随机字串， 默认长度6位 字母和数字混合
 *
 * @param int $len 长度
 * @param string $type 字串类型 0 字母 1 数字 其它 混合
 * @param string $addChars 额外字符
 * @return string
 */
function rand_string($len=6, $type='', $addChars='') {
    $str ='';
    switch($type) {
        case 0:
            $chars='ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.$addChars;
            break;
        case 1:
            $chars= str_repeat('0123456789',3);
            break;
        case 2:
            $chars='ABCDEFGHIJKLMNOPQRSTUVWXYZ'.$addChars;
            break;
        case 3:
            $chars='abcdefghijklmnopqrstuvwxyz'.$addChars;
            break;
        case 4:
            $chars = "们以我到他会作时要动国产的一是工就年阶义发成部民可出能方进在了不和有大这主中人上为来分生对于学下级地个用同行面说种过命度革而多子后自社加小机也经力线本电高量长党得实家定深法表着水理化争现所二起政三好十战无农使性前等反体合斗路图把结第里正新开论之物从当两些还天资事队批点育重其思与间内去因件日利相由压员气业代全组数果期导平各基或月毛然如应形想制心样干都向变关问比展那它最及外没看治提五解系林者米群头意只明四道马认次文通但条较克又公孔领军流入接席位情运器并飞原油放立题质指建区验活众很教决特此常石强极土少已根共直团统式转别造切九你取西持总料连任志观调七么山程百报更见必真保热委手改管处己将修支识病象几先老光专什六型具示复安带每东增则完风回南广劳轮科北打积车计给节做务被整联步类集号列温装即毫知轴研单色坚据速防史拉世设达尔场织历花受求传口断况采精金界品判参层止边清至万确究书术状厂须离再目海交权且儿青才证低越际八试规斯近注办布门铁需走议县兵固除般引齿千胜细影济白格效置推空配刀叶率述今选养德话查差半敌始片施响收华觉备名红续均药标记难存测士身紧液派准斤角降维板许破述技消底床田势端感往神便贺村构照容非搞亚磨族火段算适讲按值美态黄易彪服早班麦削信排台声该击素张密害侯草何树肥继右属市严径螺检左页抗苏显苦英快称坏移约巴材省黑武培著河帝仅针怎植京助升王眼她抓含苗副杂普谈围食射源例致酸旧却充足短划剂宣环落首尺波承粉践府鱼随考刻靠够满夫失包住促枝局菌杆周护岩师举曲春元超负砂封换太模贫减阳扬江析亩木言球朝医校古呢稻宋听唯输滑站另卫字鼓刚写刘微略范供阿块某功套友限项余倒卷创律雨让骨远帮初皮播优占死毒圈伟季训控激找叫云互跟裂粮粒母练塞钢顶策双留误础吸阻故寸盾晚丝女散焊功株亲院冷彻弹错散商视艺灭版烈零室轻血倍缺厘泵察绝富城冲喷壤简否柱李望盘磁雄似困巩益洲脱投送奴侧润盖挥距触星松送获兴独官混纪依未突架宽冬章湿偏纹吃执阀矿寨责熟稳夺硬价努翻奇甲预职评读背协损棉侵灰虽矛厚罗泥辟告卵箱掌氧恩爱停曾溶营终纲孟钱待尽俄缩沙退陈讨奋械载胞幼哪剥迫旋征槽倒握担仍呀鲜吧卡粗介钻逐弱脚怕盐末阴丰雾冠丙街莱贝辐肠付吉渗瑞惊顿挤秒悬姆烂森糖圣凹陶词迟蚕亿矩康遵牧遭幅园腔订香肉弟屋敏恢忘编印蜂急拿扩伤飞露核缘游振操央伍域甚迅辉异序免纸夜乡久隶缸夹念兰映沟乙吗儒杀汽磷艰晶插埃燃欢铁补咱芽永瓦倾阵碳演威附牙芽永瓦斜灌欧献顺猪洋腐请透司危括脉宜笑若尾束壮暴企菜穗楚汉愈绿拖牛份染既秋遍锻玉夏疗尖殖井费州访吹荣铜沿替滚客召旱悟刺脑措贯藏敢令隙炉壳硫煤迎铸粘探临薄旬善福纵择礼愿伏残雷延烟句纯渐耕跑泽慢栽鲁赤繁境潮横掉锥希池败船假亮谓托伙哲怀割摆贡呈劲财仪沉炼麻罪祖息车穿货销齐鼠抽画饲龙库守筑房歌寒喜哥洗蚀废纳腹乎录镜妇恶脂庄擦险赞钟摇典柄辩竹谷卖乱虚桥奥伯赶垂途额壁网截野遗静谋弄挂课镇妄盛耐援扎虑键归符庆聚绕摩忙舞遇索顾胶羊湖钉仁音迹碎伸灯避泛亡答勇频皇柳哈揭甘诺概宪浓岛袭谁洪谢炮浇斑讯懂灵蛋闭孩释乳巨徒私银伊景坦累匀霉杜乐勒隔弯绩招绍胡呼痛峰零柴簧午跳居尚丁秦稍追梁折耗碱殊岗挖氏刃剧堆赫荷胸衡勤膜篇登驻案刊秧缓凸役剪川雪链渔啦脸户洛孢勃盟买杨宗焦赛旗滤硅炭股坐蒸凝竟陷枪黎救冒暗洞犯筒您宋弧爆谬涂味津臂障褐陆啊健尊豆拔莫抵桑坡缝警挑污冰柬嘴啥饭塑寄赵喊垫丹渡耳刨虎笔稀昆浪萨茶滴浅拥穴覆伦娘吨浸袖珠雌妈紫戏塔锤震岁貌洁剖牢锋疑霸闪埔猛诉刷狠忽灾闹乔唐漏闻沈熔氯荒茎男凡抢像浆旁玻亦忠唱蒙予纷捕锁尤乘乌智淡允叛畜俘摸锈扫毕璃宝芯爷鉴秘净蒋钙肩腾枯抛轨堂拌爸循诱祝励肯酒绳穷塘燥泡袋朗喂铝软渠颗惯贸粪综墙趋彼届墨碍启逆卸航衣孙龄岭骗休借".$addChars;
            break;
        default :
            // 默认去掉了容易混淆的字符oOLl和数字01，要添加请使用addChars参数
            $chars='ABCDEFGHIJKMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789'.$addChars;
            break;
    }
    if($len>10 ) {//位数过长重复字符串一定次数
        $chars= $type==1? str_repeat($chars,$len) : str_repeat($chars,5);
    }
    if($type!=4) {
        $chars   =   str_shuffle($chars);
        $str     =   substr($chars,0,$len);
    }else{
        // 中文随机字
        for($i=0;$i<$len;$i++){
          $str.= msubstr($chars, floor(mt_rand(0,mb_strlen($chars,'utf-8')-1)),1);
        }
    }
    return $str;
}

/**
 * 代码加亮
 *
 * @param String  $str 要高亮显示的字符串 或者 文件名
 * @param Boolean $show 是否输出
 * @return mixed
 */
function highlight_code($str, $show = false) {
    if(file_exists($str)) {
        $str = file_get_contents($str);
    }
    $str =  stripslashes(trim($str));
    // The highlight string function encodes and highlights
    // brackets so we need them to start raw
    $str = str_replace(array('&lt;', '&gt;'), array('<', '>'), $str);
    // Replace any existing PHP tags to temporary markers so they don't accidentally
    // break the string out of PHP, and thus, thwart the highlighting.
    $str = str_replace(array('&lt;?php', '?&gt;',  '\\'), array('phptagopen', 'phptagclose', 'backslashtmp'), $str);
    // The highlight_string function requires that the text be surrounded
    // by PHP tags.  Since we don't know if A) the submitted text has PHP tags,
    // or B) whether the PHP tags enclose the entire string, we will add our
    // own PHP tags around the string along with some markers to make replacement easier later
    $str = '<?php //tempstart'."\n".$str.'//tempend ?>'; // <?
    // All the magic happens here, baby!
    $str = highlight_string($str, TRUE);
    // Prior to PHP 5, the highlight function used icky font tags
    // so we'll replace them with span tags.
    if (abs(phpversion()) < 5) {
        $str = str_replace(array('<font ', '</font>'), array('<span ', '</span>'), $str);
        $str = preg_replace('#color="(.*?)"#', 'style="color: \\1"', $str);
    }
    // Remove our artificially added PHP
    $str = preg_replace("#\<code\>.+?//tempstart\<br />\</span\>#is", "<code>\n", $str);
    $str = preg_replace("#\<code\>.+?//tempstart\<br />#is", "<code>\n", $str);
    $str = preg_replace("#//tempend.+#is", "</span>\n</code>", $str);
    // Replace our markers back to PHP tags.
    $str = str_replace(array('phptagopen', 'phptagclose', 'backslashtmp'), array('&lt;?php', '?&gt;', '\\'), $str); //<?
    $line   =   explode("<br />", rtrim(ltrim($str,'<code>'),'</code>'));
    $result =   '<div class="code"><ol>';
    foreach($line as $key=>$val) {
        $result .=  '<li>'.$val.'</li>';
    }
    $result .=  '</ol></div>';
    $result = str_replace("\n", "", $result);
    if( $show !== false) {
        echo($result);
    }else {
        return $result;
    }
    return $result;
}

/**
 * 自动转换字符集 支持数组转换
 *
 * @param string|array $fContents 要转换的文本或数组
 * @param string $from 原编码
 * @param string $to 要转换为编码
 * @return mixed
 */
function auto_charset($fContents, $from = 'gbk', $to = 'utf-8') {
    $from = strtoupper($from) == 'UTF8' ? 'utf-8' : $from;
    $to = strtoupper($to) == 'UTF8' ? 'utf-8' : $to;
    if (strtoupper($from) === strtoupper($to) || empty($fContents) || (is_scalar($fContents) && !is_string($fContents))) {
        //如果编码相同或者非字符串标量则不转换
        return $fContents;
    }
    if (is_string($fContents)) {
        if (function_exists('mb_convert_encoding')) {
            return mb_convert_encoding($fContents, $to, $from);
        } elseif (function_exists('iconv')) {
            return iconv($from, $to, $fContents);
        } else {
            return $fContents;
        }
    } elseif (is_array($fContents)) {
        foreach ($fContents as $key => $val) {
            $_key = auto_charset($key, $from, $to);
            $fContents[$_key] = auto_charset($val, $from, $to);
            if ($key != $_key) unset($fContents[$key]);
        }
        return $fContents;
    }
    else {
        return $fContents;
    }
}

/**
 * 查询二维数值是否存在某个值
 *
 * @param $value
 * @param $array
 * @return mixed
 */
function search_in_array($value, $array)
{
    foreach ($array as $item) {
        if (!is_array($item)) {
            if ($item == $value) {
                return $item;
            } else {
                continue;
            }
        }
        if (in_array($value, $item)) {
            return $item;
        } else if (search_in_array($value, $item)) {
            return $item;
        }
    }
    return false;
}

/**
 * 二维数组去除重复元素- 基于一键名不重复
 *
 * @param array $arr 处理二维数组
 * @param string $key 基于键名
 * @return array
 */
function assoc_unique($arr, $key)
{
    $tmp_arr = array();
    foreach($arr as $k => $v)
    {
        if(in_array($v[$key], $tmp_arr))//搜索$v[$key]是否在$tmp_arr数组中存在，若存在返回true
        {
            unset($arr[$k]);
        }
        else {
            $tmp_arr[] = $v[$key];
        }
    }
    rsort($arr); //rsort函数对数组进行排序
    return $arr;
}

/**
 * 重新初始化键值，从0开始
 *
 * @param array $array 输入数组
 * @return array
 */
function array_rekey($array){
    $keys = array();
    for($i=0;$i<count($array);$i++){
        $keys[$i] = $i;
    }
    return array_combine($keys, $array);
}

/**
 * 检测字符串是否由纯英文，纯中文，中英文混合组成
 *
 * @param string $str
 * @return mixed 1:纯英文;2:纯中文;3:中英文混合
 */
function check_str($str = ''){
    if(trim($str) == ''){
        return '';
    }
    $m = mb_strlen($str,'utf-8');
    $s = strlen($str);
    if($s == $m){
        return 1;
    }
    if($s % $m == 0 && $s % 3 == 0){
        return 2;
    }
    return 3;
}

/**
 * 匹配是否为URL
 *
 * @param string 输入字符串
 * @return boolean
 */
function is_url($s)
{
    return preg_match('/^http[s]?:\/\/'.
        '(([0-9]{1,3}\.){3}[0-9]{1,3}'. // IP形式的URL- 199.194.52.184
        '|'. // 允许IP和DOMAIN（域名）
        '([0-9a-z_!~*\'()-]+\.)*'. // 域名- www.
        '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\.'. // 二级域名
        '[a-z]{2,6})'.  // first level domain- .com or .museum
        '(:[0-9]{1,4})?'.  // 端口- :80
        '((\/\?)|'.  // a slash isn't required if there is no file name
        '(\/[0-9a-zA-Z_!~\'\(\)\[\]\.;\?:@&=\+\$,%#-\/^\*\|]*)?)$/',
        $s) == 1;
}

/**
 * 产生随机字符串，不长于32位
 *
 * @param int $length 生成字符串长度
 * @return string
 */
function create_noncestr( $length = 32 )
{
    $chars = "abcdefghijklmnopqrstuvwxyz0123456789";
    $str = "";
    for ( $i = 0; $i < $length; $i++ )  {
        $str.= substr($chars, mt_rand(0, strlen($chars)-1), 1);
    }
    return $str;
}

/**
 * GET 方法
 * PHP获取远程json返回数据，需要开启CURL
 *
 * @param string $url 远程路径地址
 * @return array 解释过的json数组
 */
function get_curl_json($url){
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER,true);
    $result = curl_exec($ch);
    if(curl_errno($ch)){
        print_r(curl_error($ch));
    }
    curl_close($ch);
    //返回有三个空字符，马蒂，坑死人了，如果返回没有的话可以注释这里
    $result = substr($result,3);
    return json_decode($result,TRUE);
}

/**
 * 邮件发送函数
 *
 * @param string $to 发给的人
 * @param string $title 邮件标题（主题）
 * @param $message 邮件内容
 * @return mixed
 */
function send_mail($to, $title, $message) {
//    // 导入Email类
//    Vendor('PHPMailer.PHPMailerAutoload');
//    $mail= new PHPMailer();
//    // 设置PHPMailer使用SMTP服务器发送Email
//    $mail->IsSMTP();
//    $mail->CharSet = C('TEA_MAIL_CHARSET'); // 设置邮件的字符编码，若不指定，则为'UTF-8'
//    $mail->AddAddress($to);                 // 添加收件人地址，可以多次使用来添加多个收件人
//    $mail->IsHTML(true);                    // 支持html格式内容
//    $mail->Body = $message;                 // 设置邮件正文
//    $mail->From = C('TEA_MAIL_ADDRESS');    // 设置邮件头的From字段。
//    $mail->FromName = C('TEA_MAIL_SENDER'); // 设置发件人名字
//    $mail->Subject=$title;                  // 设置邮件标题
//    $mail->Host = C('TEA_MAIL_SMTP');       // 设置SMTP服务器。
//    $mail->SMTPAuth = C('TEA_MAIL_SMTPAUTH'); //启用smtp认证
//    // 设置用户名和密码。
//    $mail->Username = C('TEA_MAIL_LOGINNAME');
//    $mail->Password = C('TEA_MAIL_PASSWORD');
//    // 发送邮件。
//    return($mail->Send());

    return 0;
}

/**
 * 发送短信
 *
 * @param string  手机号码
 * @param string 短信内容
 * @return string
 */
function send_sms($mobile, $content)
{
//    $mobile_code = GetRandStr ( 6 );
//    $_SESSION ['mobile_code'] = $mobile_code;
//    if(!isset($content) || empty($content)){
//        $content = "您的验证码是：".$mobile_code."。请不要把验证码泄露给其他人。";
//    }
//    import("Org.Util.SendSms");
//    $result=Sms::send($mobile, $content);
//    if($result=='发送成功'){
//        $_SESSION ['mobile_code'] = $mobile_code;
//        return '2';
//    }else{
//        return $result;
//    }
    return '';
}

/**
 * 随机生成数字字符串
 * @param int $len 生成的长度
 * @return string
 */
function get_rand_str($len)
{
    $chars = array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
    $charsLen = count($chars) - 1;
    shuffle($chars);
    $output = "";
    for ($i = 0; $i < $len; $i++) {
        $output .= $chars[mt_rand(0, $charsLen)];
    }
    return $output;
}

/**
 * 生成随机的订单号
 */
function create_order_sn()
{
    $firstNum = "1";
    $order_sn = $firstNum . date('Ymd') . str_pad(mt_rand(1, 99999), 5, '0', STR_PAD_LEFT) . get_rand_str(1);
    return $order_sn;
}

/**
 * 参数1：访问的URL，参数2：post数据(不填则为GET)，参数3：提交的$cookies,参数4：是否返回$cookies
 *
 * @param $url
 * @param string $post
 * @param string $cookie
 * @param int $returnCookie
 * @return bool|string
 */
function curl_request($url, $post = '', $cookie = '', $returnCookie = 0)
{
    $curl = curl_init();
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_USERAGENT, 'Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; Trident/6.0)');
    curl_setopt($curl, CURLOPT_FOLLOWLOCATION, 1);
    curl_setopt($curl, CURLOPT_AUTOREFERER, 1);
    curl_setopt($curl, CURLOPT_REFERER, "http://XXX");
    if ($post) {
        curl_setopt($curl, CURLOPT_POST, 1);
        curl_setopt($curl, CURLOPT_POSTFIELDS, http_build_query($post));
    }
    if ($cookie) {
        curl_setopt($curl, CURLOPT_COOKIE, $cookie);
    }
    curl_setopt($curl, CURLOPT_HEADER, $returnCookie);
    curl_setopt($curl, CURLOPT_TIMEOUT, 10);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
    $data = curl_exec($curl);
    if (curl_errno($curl)) {
        return curl_error($curl);
    }
    curl_close($curl);
    if ($returnCookie) {
        list($header, $body) = explode("\r\n\r\n", $data, 2);
        preg_match_all("/Set\-Cookie:([^;]*);/", $header, $matches);
        $info['cookie'] = substr($matches[1][0], 1);
        $info['content'] = $body;
        return $info;
    } else {
        return $data;
    }
}

/**
 * 获取应用当前模板下的模板列表
 * @return array
 */
function get_topic_tpl_file_list(){
//    $template_path="Application/Home/View/Temp/";
//    $files=sp_scan_dir($template_path."*");
//    $tpl_files=array();
//    foreach ($files as $f){
//        if($f!="." || $f!=".."){
//            if(is_file($template_path.$f)){
//                $suffix=".html";    //C("TMPL_TEMPLATE_SUFFIX")
//                $result=preg_match("/$suffix$/", $f);
//                if($result){
//                    $tpl=str_replace($suffix, "", $f);
//                    $tpl_files[$tpl]=$tpl;
//                }else if(preg_match("/\.php$/", $f)){
//                    $tpl=str_replace($suffix, "", $f);
//                    $tpl_files[$tpl]=$tpl;
//                }
//            }
//        }
//    }
//    return $tpl_files;

    return [];
}

/**
 * 单位转为千克的装法
 * @param int $many 多重
 * @param $type 1：克（g）2：千克（kg）3：盎司（oz）4：磅（lb）
 * @return mixed 返回以千克为单位的
 */
function change_to_kg($many, $type){
    if($type == 1){
        return $many / 1000;
    }else if($type == 2){
        return $many;
    }else if($type == 3){
        return $many*0.0283495;
    }else{
        return $many*0.4535924;
    }
}

/**
 * 字符串截取，支持中文和其他编码
 *
 * @param string $str 需要转换的字符串
 * @param string $start 开始位置
 * @param string $length 截取长度
 * @param string $charset 编码格式
 * @param string $suffix 截断显示字符
 * @return string
 */
function msubstr($str, $start=0, $length = 0, $charset="utf-8", $suffix=true) {
    if(function_exists("mb_substr"))
        $slice = mb_substr($str, $start, $length, $charset);
    elseif(function_exists('iconv_substr')) {
        $slice = iconv_substr($str,$start,$length,$charset);
        if(false === $slice) {
            $slice = '';
        }
    }else{
        $re['utf-8']  = "/[\x01-\x7f]|[\xc2-\xdf][\x80-\xbf]|[\xe0-\xef][\x80-\xbf]{2}|[\xf0-\xff][\x80-\xbf]{3}/";
        $re['gb2312'] = "/[\x01-\x7f]|[\xb0-\xf7][\xa0-\xfe]/";
        $re['gbk']    = "/[\x01-\x7f]|[\x81-\xfe][\x40-\xfe]/";
        $re['big5']   = "/[\x01-\x7f]|[\x81-\xfe]([\x40-\x7e]|\xa1-\xfe])/";
        preg_match_all($re[$charset], $str, $match);
        $slice = join("",array_slice($match[0], $start, $length));
    }
    return $suffix ? $slice.'...' : $slice;
}

/**
 * 计算两组经纬度坐标 之间的距离
 * @param  $lat1 纬度1
 * @param  $lng1 经度1
 * @param  $lat2 纬度2
 * @param  $lng2 经度2
 * @param  $len_type （1:m or 2:km)
 * @param  $decimal 保留的小数点
 * @return number
 */
function get_distance($lat1, $lng1, $lat2, $lng2, $len_type = 1, $decimal = 2)
{
    $EARTH_RADIUS=6378.137;
    $PI=3.1415926;
    $radLat1 = $lat1 * $PI / 180.0;
    $radLat2 = $lat2 * $PI / 180.0;
    $a = $radLat1 - $radLat2;
    $b = ($lng1 * $PI / 180.0) - ($lng2 * $PI / 180.0);
    $s = 2 * asin(sqrt(pow(sin($a/2),2) + cos($radLat1) * cos($radLat2) * pow(sin($b/2),2)));
    $s = $s * $EARTH_RADIUS; $s = round($s * 1000);
    if ($len_type > 1) { $s /= 1000; }
    return round($s,$decimal);
}

/**
 * 折扣计算
 * @param $original 原价
 * @param $current 现价
 * @param $point 小数点位数
 * @return string number
 */
function count_discount($original, $current, $point=0) {
    $amount = $original - $current; //节省金额
    //$discount折扣计算
    if ( $original > 0 ) {
        $discount = round(10 / ($original / $current), $point);
    } else {
        $discount = 0;
    }
    if ( $discount <= 0 ) { $discount = 1; }
    if ( $discount >= 10 ) { $discount = 0; }
    return $discount;
}

/**
 * 把返回的数据集转换成Tree
 *
 * $list = array(
 * array('id'=>1, 'pid'=>0, 'title'=>'菜单', 'sort'=>0),
 * array('id'=>2, 'pid'=>1, 'title'=>'菜单列表1', 'sort'=>0),
 * array('id'=>3, 'pid'=>1, 'title'=>'菜单列表2', 'sort'=>1),
 * );
 *
 * @param $list 要转换的数据集
 * @param string $pk
 * @param string $pid parent标记字段
 * @param string $child
 * @param int $root
 * @return array
 */
function list_to_tree($list, $pk = 'id', $pid = 'pid', $child = '_child', $root = 0)
{
    // 创建Tree
    $tree = array();
    if (is_array($list)) {
        // 创建基于主键的数组引用
        $refer = array();
        foreach ($list as $key => $data) {
            $refer[$data[$pk]] =& $list[$key];
        }
        foreach ($list as $key => $data) {
            // 判断是否存在parent
            $parentId = $data[$pid];
            if ($root == $parentId) {
                $tree[] =& $list[$key];
            } else {
                if (isset($refer[$parentId])) {
                    $parent =& $refer[$parentId];
                    $parent[$child][] =& $list[$key];
                }
            }
        }
    }
    return $tree;
}

/**
* 对查询结果集进行排序
* @access public
* @param array $list 查询结果
* @param string $field 排序的字段名
* @param string $sortby 排序类型
* asc正向排序 desc逆向排序 nat自然排序
* @return array|bool
*/
function list_sort_by($list, $field, $sortby='asc') {
    if(is_array($list)){
        $refer = $resultSet = array();
        foreach ($list as $i => $data) {
           $refer[$i] = &$data[$field];
        }
        switch ($sortby) {
            case 'asc': // 正向排序
                asort($refer);
                break;
            case 'desc':// 逆向排序
                arsort($refer);
                break;
            case 'nat': // 自然排序
                natcasesort($refer);
                break;
        }
        foreach ( $refer as $key=> $val) {
            $resultSet[] = &$list[$key];
        }
        return $resultSet;
    }
    return false;
}

/**
 * 在数据列表中搜索
 * @access public
 * @param array $list 数据列表
 * @param mixed $condition 查询条件
 * 支持 array('name'=>$value) 或者 name=$value
 * @return array
 */
function list_search($list,$condition) {
    if(is_string($condition)) parse_str($condition,$condition);
    // 返回的结果集合
    $resultSet = array();
    foreach ($list as $key => $data){
        $find = false;
        foreach ($condition as $field=>$value){
            if(isset($data[$field])) {
                if(0 === strpos($value,'/')) {
                    $find = preg_match($value,$data[$field]);
                }elseif($data[$field] == $value){
                    $find = true;
                }
            }
        }
        if($find) $resultSet[] = &$list[$key];
    }
    return $resultSet;
}

/**
 * 将list_to_tree的树还原成列表
 * @param  array $tree  原来的树
 * @param  string $child 孩子节点的键
 * @param  string $order 排序显示的键，一般是主键 升序排列
 * @param  array  $list  过渡用的中间数组，
 * @return array        返回排过序的列表数组
 */
function tree_to_list($tree, $child = '_child', $order='id', &$list = array()){
    if(is_array($tree)) {
        foreach ($tree as $key => $value) {
            $reffer = $value;
            if(isset($reffer[$child])){
                unset($reffer[$child]); // 删除子树键名
                tree_to_list($value[$child], $child, $order, $list);//递归删除
            }
            $list[] = $reffer; //把一个数组剩下的内容压入新数组
        }
        $list = list_sort_by($list, $order, $sortby='asc'); //对整理列表进行排序
    }
    return $list;
}

/**
 * 浏览器友好的变量输出
 * @param mixed $var 变量
 * @param boolean $echo 是否输出 默认为True 如果为false 则返回输出字符串
 * @param string $label 标签 默认为空
 * @param boolean $strict 是否严谨 默认为true
 * @return void|string
 */
function dump($var, $echo=true, $label=null, $strict=true) {
    $label = ($label === null) ? '' : rtrim($label) . ' ';
    if (!$strict) {
        if (ini_get('html_errors')) {
            $output = print_r($var, true);
            $output = '<pre>' . $label . htmlspecialchars($output, ENT_QUOTES) . '</pre>';
        } else {
            $output = $label . print_r($var, true);
        }
    } else {
        ob_start();
        var_dump($var);
        $output = ob_get_clean();
        if (!extension_loaded('xdebug')) {
            $output = preg_replace('/\]\=\>\n(\s+)/m', '] => ', $output);
            $output = '<pre>' . $label . htmlspecialchars($output, ENT_QUOTES) . '</pre>';
        }
    }
    if ($echo) {
        echo($output);
        return null;
    }else
        return $output;
}

/**
 * 获取所有编译执行的函数
 * @return array
 */
function reflex_function() {
    return get_defined_functions();
    // array get_declared_classes ( void ) //返回在当前脚本中声明的类的名称的数组。
}

/**
 * 判断是否SSL协议
 * @return boolean
 */
function is_ssl() {
    if(isset($_SERVER['HTTPS']) && ('1' == $_SERVER['HTTPS'] || 'on' == strtolower($_SERVER['HTTPS']))){
        return true;
    }elseif(isset($_SERVER['SERVER_PORT']) && ('443' == $_SERVER['SERVER_PORT'] )) {
        return true;
    }
    return false;
}

/**
 * URL重定向
 * @param string $url 重定向的URL地址
 * @param integer $time 重定向的等待时间（秒）
 * @param string $msg 重定向前的提示信息
 * @return void
 */
function redirect($url, $time=0, $msg='') {
    //多行URL地址支持
    $url = str_replace(array("\n", "\r"), '', $url);
    if (empty($msg))
        $msg    = "系统将在{$time}秒之后自动跳转到{$url}！";
    if (!headers_sent()) {
        // redirect
        if (0 === $time) {
            header('Location: ' . $url);
        } else {
            header("refresh:{$time};url={$url}");
            echo($msg);
        }
        exit();
    } else {
        $str    = "<meta http-equiv='Refresh' content='{$time};URL={$url}'>";
        if ($time != 0)
            $str .= $msg;
        exit($str);
    }
}

/**
 * 获取客户端IP地址
 * @param integer $type 返回类型 0 返回IP地址 1 返回IPV4地址数字
 * @param boolean $adv 是否进行高级模式获取（有可能被伪装）
 * @return mixed
 */
function get_client_ip($type = 0,$adv=false) {
    $type       =  $type ? 1 : 0;
    static $ip  =   NULL;
    if ($ip !== NULL) return $ip[$type];
    if($adv){
        if (isset($_SERVER['HTTP_X_FORWARDED_FOR'])) {
            $arr    =   explode(',', $_SERVER['HTTP_X_FORWARDED_FOR']);
            $pos    =   array_search('unknown',$arr);
            if(false !== $pos) unset($arr[$pos]);
            $ip     =   trim($arr[0]);
        }elseif (isset($_SERVER['HTTP_CLIENT_IP'])) {
            $ip     =   $_SERVER['HTTP_CLIENT_IP'];
        }elseif (isset($_SERVER['REMOTE_ADDR'])) {
            $ip     =   $_SERVER['REMOTE_ADDR'];
        }
    }elseif (isset($_SERVER['REMOTE_ADDR'])) {
        $ip     =   $_SERVER['REMOTE_ADDR'];
    }
    // IP地址合法验证
    $long = sprintf("%u",ip2long($ip));
    $ip   = $long ? array($ip, $long) : array('0.0.0.0', 0);
    return $ip[$type];
}

/**
 * 发送HTTP状态
 * @param integer $code 状态码
 * @return void
 */
function send_http_status($code) {
    static $_status = array(
            // Informational 1xx
            100 => 'Continue',
            101 => 'Switching Protocols',
            // Success 2xx
            200 => 'OK',
            201 => 'Created',
            202 => 'Accepted',
            203 => 'Non-Authoritative Information',
            204 => 'No Content',
            205 => 'Reset Content',
            206 => 'Partial Content',
            // Redirection 3xx
            300 => 'Multiple Choices',
            301 => 'Moved Permanently',
            302 => 'Moved Temporarily ',  // 1.1
            303 => 'See Other',
            304 => 'Not Modified',
            305 => 'Use Proxy',
            // 306 is deprecated but reserved
            307 => 'Temporary Redirect',
            // Client Error 4xx
            400 => 'Bad Request',
            401 => 'Unauthorized',
            402 => 'Payment Required',
            403 => 'Forbidden',
            404 => 'Not Found',
            405 => 'Method Not Allowed',
            406 => 'Not Acceptable',
            407 => 'Proxy Authentication Required',
            408 => 'Request Timeout',
            409 => 'Conflict',
            410 => 'Gone',
            411 => 'Length Required',
            412 => 'Precondition Failed',
            413 => 'Request Entity Too Large',
            414 => 'Request-URI Too Long',
            415 => 'Unsupported Media Type',
            416 => 'Requested Range Not Satisfiable',
            417 => 'Expectation Failed',
            // Server Error 5xx
            500 => 'Internal Server Error',
            501 => 'Not Implemented',
            502 => 'Bad Gateway',
            503 => 'Service Unavailable',
            504 => 'Gateway Timeout',
            505 => 'HTTP Version Not Supported',
            509 => 'Bandwidth Limit Exceeded'
    );
    if(isset($_status[$code])) {
        header('HTTP/1.1 '.$code.' '.$_status[$code]);
        // 确保FastCGI模式下正常
        header('Status:'.$code.' '.$_status[$code]);
    }
}

/**
 * 根据PHP各种类型变量生成唯一标识号
 * @param mixed $mix 变量
 * @return string
 */
function to_guid_string($mix) {
    if (is_object($mix)) {
        return spl_object_hash($mix);
    } elseif (is_resource($mix)) {
        $mix = get_resource_type($mix) . strval($mix);
    } else {
        $mix = serialize($mix);
    }
    return md5($mix);
}

/**
 * XML编码
 * @param mixed $data 数据
 * @param string $root 根节点名
 * @param string $item 数字索引的子节点名
 * @param string $attr 根节点属性
 * @param string $id   数字索引子节点key转换的属性名
 * @param string $encoding 数据编码
 * @return string
 */
function xml_encode($data, $root='think', $item='item', $attr='', $id='id', $encoding='utf-8') {
    if(is_array($attr)){
        $_attr = array();
        foreach ($attr as $key => $value) {
            $_attr[] = "{$key}=\"{$value}\"";
        }
        $attr = implode(' ', $_attr);
    }
    $attr   = trim($attr);
    $attr   = empty($attr) ? '' : " {$attr}";
    $xml    = "<?xml version=\"1.0\" encoding=\"{$encoding}\"?>";
    $xml   .= "<{$root}{$attr}>";
    $xml   .= data_to_xml($data, $item, $id);
    $xml   .= "</{$root}>";
    return $xml;
}

/**
 * 数据XML编码
 * @param mixed  $data 数据
 * @param string $item 数字索引时的节点名称
 * @param string $id   数字索引key转换为的属性名
 * @return string
 */
function data_to_xml($data, $item='item', $id='id') {
    $xml = $attr = '';
    foreach ($data as $key => $val) {
        if(is_numeric($key)){
            $id && $attr = " {$id}=\"{$key}\"";
            $key  = $item;
        }
        $xml    .=  "<{$key}{$attr}>";
        $xml    .=  (is_array($val) || is_object($val)) ? data_to_xml($val, $item, $id) : $val;
        $xml    .=  "</{$key}>";
    }
    return $xml;
}

/**
 * 对输入字符串作数据库安全过滤处理
 *
 * @param $str
 * @param int $type
 * @return array|int|mixed|string
 */
function filter_str($str, $type = 1)
{
    if (is_array($str)) {
        foreach ($str as $key => $val) {
            $str[$key] = filter_str($val, $type);
        }
        return $str;
    }
    $result = '';
    $str = trim($str);
    switch ($type) {
        //数字、字母、下划线、横线
        case 1:
            if (preg_match('#^(\\w|-)*$#i', $str)) {
                $result = $str;
            }
            break;
        //数字
        case 2:
            if (preg_match('#^(\\d)*$#i', $str)) {
                $result = $str;
            }
            $result = (int)$result;
            break;
        //过滤掉常用sql注入,适用于富文本基本过滤
        case 3:
            $result = str_ireplace(array('#', 'CONCAT', 'UNION', 'select'), '', addslashes($str));
            break;
        //过滤掉常用sql注入和xss注入,同时会过滤掉空格,最严格过滤
        case 4:
            $result = str_ireplace(array('#', ' ', 'CONCAT', 'UNION', 'select'), '', addslashes(htmlspecialchars(strip_tags($str), ENT_QUOTES)));
            break;
        //过滤掉常用sql注入和xss注入,适用于整个input值过滤,但input里面不能是json，否则解不了
        case 5:
            $result = str_ireplace(array('#', 'CONCAT', 'UNION', 'select'), '', addslashes(htmlspecialchars(strip_tags($str), ENT_QUOTES)));
            break;
    }
    return $result;
}

/**
 * 全角转半角
 *
 * @param $str
 * @return string
 */
function SBC2DBC($str) {
    $map = array('０' => '0', '１' => '1', '２' => '2', '３' => '3', '４' => '4',
        '５' => '5', '６' => '6', '７' => '7', '８' => '8', '９' => '9',
        'Ａ' => 'A', 'Ｂ' => 'B', 'Ｃ' => 'C', 'Ｄ' => 'D', 'Ｅ' => 'E',
        'Ｆ' => 'F', 'Ｇ' => 'G', 'Ｈ' => 'H', 'Ｉ' => 'I', 'Ｊ' => 'J',
        'Ｋ' => 'K', 'Ｌ' => 'L', 'Ｍ' => 'M', 'Ｎ' => 'N', 'Ｏ' => 'O',
        'Ｐ' => 'P', 'Ｑ' => 'Q', 'Ｒ' => 'R', 'Ｓ' => 'S', 'Ｔ' => 'T',
        'Ｕ' => 'U', 'Ｖ' => 'V', 'Ｗ' => 'W', 'Ｘ' => 'X', 'Ｙ' => 'Y',
        'Ｚ' => 'Z', 'ａ' => 'a', 'ｂ' => 'b', 'ｃ' => 'c', 'ｄ' => 'd',
        'ｅ' => 'e', 'ｆ' => 'f', 'ｇ' => 'g', 'ｈ' => 'h', 'ｉ' => 'i',
        'ｊ' => 'j', 'ｋ' => 'k', 'ｌ' => 'l', 'ｍ' => 'm', 'ｎ' => 'n',
        'ｏ' => 'o', 'ｐ' => 'p', 'ｑ' => 'q', 'ｒ' => 'r', 'ｓ' => 's',
        'ｔ' => 't', 'ｕ' => 'u', 'ｖ' => 'v', 'ｗ' => 'w', 'ｘ' => 'x',
        'ｙ' => 'y', 'ｚ' => 'z',
        '（' => '(', '）' => ')', '〔' => '[', '〕' => ']', '【' => '[',
        '】' => ']', '〖' => '[', '〗' => ']', '“' => '[', '”' => ']',
        '‘' => '[', '’' => ']', '｛' => '{', '｝' => '}', '《' => '<',
        '》' => '>',
        '％' => '%', '＋' => '+', '—' => '-', '－' => '-', '～' => '-',
        '：' => ':', '。' => '.', '、' => ',', '，' => '.', '、' => '.',
        '；' => ',', '？' => '?', '！' => '!', '…' => '-', '‖' => '|',
        '”' => '"', '’' => '`', '‘' => '`', '｜' => '|', '〃' => '"',
        ' ' => ' ','＄'=>'$','＠'=>'@','＃'=>'#','＾'=>'^','＆'=>'&','＊'=>'*',
        '＂'=>'"'
    );
    return strtr($str, $map);
}
