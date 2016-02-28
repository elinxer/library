<?php





/**
 +-----------------------------------------------------------------------------
 * 在PHP数组中生成.csv 文件 
 +-----------------------------------------------------------------------------
 * @param string $data
 * @param string $delimiter
 * @param mixed $enclosure
 * @return mixed
 +-----------------------------------------------------------------------------
 */
function generateCsv($data, $delimiter = ',', $enclosure = '"') {
    $handle = fopen('php://temp', 'r+');
    foreach ($data as $line) {
        fputcsv($handle, $line, $delimiter, $enclosure);
    }
    rewind($handle);
    while (!feof($handle)) {
       $contents .= fread($handle, 8192);
    }
    fclose($handle);
    return $contents;
}
/**
 +-----------------------------------------------------------------------------
 * 创建数据uri 
 +-----------------------------------------------------------------------------
 * @param string $file
 * @param string $mime
 * 通过使用此代码，你可以创建数据Uri，这对在HTML/CSS中嵌入图片非常有用，可帮助
 * 节省HTTP请求
 +-----------------------------------------------------------------------------
 */
function data_uri($file, $mime) {
  $contents=file_get_contents($file);
  $base64=base64_encode($contents);
  echo "data:$mime;base64,$base64";
}

/**
 +-----------------------------------------------------------------------------
 * 查看浏览器语言 
 +-----------------------------------------------------------------------------
 * @param string $availableLanguages
 * @param string $default
 * @return mixed
 * 检测浏览器使用的代码脚本语言。
 +-----------------------------------------------------------------------------
 */
function get_client_language($availableLanguages, $default='en'){
    if (isset($_SERVER['HTTP_ACCEPT_LANGUAGE'])) {
        $langs=explode(',',$_SERVER['HTTP_ACCEPT_LANGUAGE']);
        foreach ($langs as $value){
            $choice=substr($value,0,2);
            if(in_array($choice, $availableLanguages)){
                return $choice;
            }
        }
    } 
    return $default;
}

/**
 +-----------------------------------------------------------------------------
 * 通过IP检索出地理位置 
 +-----------------------------------------------------------------------------
 * @param string $ip ip地址
 * @return mixed
 * 这段代码帮助你查找特定的IP，只需在功能参数上输入IP，就可检测出位置。
 +-----------------------------------------------------------------------------
 */
function detect_city($ip) {
    $default = 'UNKNOWN';
    if (!is_string($ip) || strlen($ip) < 1 || $ip == '127.0.0.1' || $ip == 'localhost') $ip = '8.8.8.8';
        $curlopt_useragent = 'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2) Gecko/20100115 Firefox/3.6 (.NET CLR 3.5.30729)';
        $url = 'http://ipinfodb.com/ip_locator.php?ip=' . urlencode($ip);
        $ch = curl_init();
        $curl_opt = array(
        CURLOPT_FOLLOWLOCATION  => 1,
        CURLOPT_HEADER      => 0,
        CURLOPT_RETURNTRANSFER  => 1,
        CURLOPT_USERAGENT   => $curlopt_useragent,
        CURLOPT_URL       => $url,
        CURLOPT_TIMEOUT         => 1,
        CURLOPT_REFERER         => 'http://' . $_SERVER['HTTP_HOST'],
    );
    curl_setopt_array($ch, $curl_opt);
    $content = curl_exec($ch);
    if (!is_null($curl_info)) {
        $curl_info = curl_getinfo($ch);
    }
    curl_close($ch);
    if ( preg_match('{
        City : ([^<]*)}i', $content, $regs)){ 
        $city = $regs[1]; 
    }
    if ( preg_match('{
        State/Province : ([^<]*)}i', $content, $regs)){ 
        $state = $regs[1]; 
    } 
    if( $city!='' && $state!='' ){ 
        $location = $city . ',' . $state; 
        return $location; 
    }else{ 
        return $default; 
    } 
}

/**
 +-----------------------------------------------------------------------------
 * 利用PHP获取Whois请求 
 +-----------------------------------------------------------------------------
 * @param string $domain 域名
 * @return mixed
 * 在特定的域名里可获得whois信息,把域名名称作为参数，并显示所有域名的相关信息。
 +-----------------------------------------------------------------------------
 */
function whois_query($domain) {
    // fix the domain name:
    $domain = strtolower(trim($domain));
    $domain = preg_replace('/^http:\/\//i', '', $domain);
    $domain = preg_replace('/^www\./i', '', $domain);
    $domain = explode('/', $domain);
    $domain = trim($domain[0]);
    // split the TLD from domain name
    $_domain = explode('.', $domain);
    $lst = count($_domain)-1;
    $ext = $_domain[$lst];
    // You find resources and lists 
    // like these on wikipedia: 
    //
    // <a href="http://de.wikipedia.org/wiki/Whois">http://de.wikipedia.org/wiki/Whois</a>
    //
    $servers = array(
        "biz" => "whois.neulevel.biz",
        "com" => "whois.internic.net",
        "us" => "whois.nic.us",
        "coop" => "whois.nic.coop",
        "info" => "whois.nic.info",
        "name" => "whois.nic.name",
        "net" => "whois.internic.net",
        "gov" => "whois.nic.gov",
        "edu" => "whois.internic.net",
        "mil" => "rs.internic.net",
        "int" => "whois.iana.org",
        "ac" => "whois.nic.ac",
        "ae" => "whois.uaenic.ae",
        "at" => "whois.ripe.net",
        "au" => "whois.aunic.net",
        "be" => "whois.dns.be",
        "bg" => "whois.ripe.net",
        "br" => "whois.registro.br",
        "bz" => "whois.belizenic.bz",
        "ca" => "whois.cira.ca",
        "cc" => "whois.nic.cc",
        "ch" => "whois.nic.ch",
        "cl" => "whois.nic.cl",
        "cn" => "whois.cnnic.net.cn",
        "cz" => "whois.nic.cz",
        "de" => "whois.nic.de",
        "fr" => "whois.nic.fr",
        "hu" => "whois.nic.hu",
        "ie" => "whois.domainregistry.ie",
        "il" => "whois.isoc.org.il",
        "in" => "whois.ncst.ernet.in",
        "ir" => "whois.nic.ir",
        "mc" => "whois.ripe.net",
        "to" => "whois.tonic.to",
        "tv" => "whois.tv",
        "ru" => "whois.ripn.net",
        "org" => "whois.pir.org",
        "aero" => "whois.information.aero",
        "nl" => "whois.domain-registry.nl"
    );
    if (!isset($servers[$ext])){
        die('Error: No matching nic server found!');
    }
    $nic_server = $servers[$ext];
    $output = '';
    // connect to whois server:
    if ($conn = fsockopen ($nic_server, 43)) {
        fputs($conn, $domain."\r\n");
        while(!feof($conn)) {
            $output .= fgets($conn,128);
        }
        fclose($conn);
    }
    else { die('Error: Could not connect to ' . $nic_server . '!'); }
    return $output;
}

/**
 +-----------------------------------------------------------------------------
 * 生成验证码图片
 +-----------------------------------------------------------------------------
 * @param string $num 验证码个数
 * @param string $w 图片宽度
 * @param string $h 图片高度
 * 如若要使用务必开启session以便在需要处进行验证
 +-----------------------------------------------------------------------------
 */
function getCode($num=4, $w=60, $h=25) {
    // 去掉了 0 1 O l 等
    $str  = "0123456789abcdefghijkmnpqrstuvwxyz";
    $code = '';
    for ($i = 0; $i < $num; $i++) {
        $code .= $str[mt_rand(0, strlen($str)-1)];
    }
    //将生成的验证码写入session，备验证页面使用
    $_SESSION["char"] = $code;
    //创建图片，定义颜色值
    Header("Content-type: image/PNG");
    $im      = imagecreate($w, $h);
    $black   = imagecolorallocate($im, mt_rand(0, 200), mt_rand(0, 120), mt_rand(0, 120));
    $gray    = imagecolorallocate($im, 118, 151, 199);
    $bgcolor = imagecolorallocate($im, 235, 236, 237);
    //画背景
    imagefilledrectangle($im, 0, 0, $w, $h, $bgcolor);
    //画边框
    imagerectangle($im, 0, 0, $w-1, $h-1, $gray);
    imagefill($im, 0, 0, $bgcolor);/*图片边框*/
    //在画布上随机生成大量点，起干扰作用;
    for ($i = 0; $i < 80; $i++) {
        imagesetpixel($im, rand(0, $w), rand(0, $h), $black);
    }
    //将字符随机显示在画布上,字符的水平间距和位置都按一定波动范围随机生成
    $strx = rand(3, 8);
    for ($i = 0; $i < $num; $i++) {
        $strpos = rand(1, 6);
        imagestring($im, 5, $strx, $strpos, substr($code, $i, 1), $black);
        $strx += rand(8, 14);
    }
    imagepng($im);
    imagedestroy($im);
}