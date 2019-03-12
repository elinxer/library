<?php

/**
 +-----------------------------------------------------------------------------
 * 检查代理ip信息有效性
 +-----------------------------------------------------------------------------
 * @param string $proxy_ip [117.95.100.126:8998]
 * @param int $times 执行检查次数
 * @return array
 * @author elinx <654753115@qq.com> 2016-07-29
 +-----------------------------------------------------------------------------
 */
function check_proxy_ip_info($proxy_ip=false, $times=10) {
    $header = array(
        // "GET / HTTP/1.1",
        // "HOST: www.baidu.com",
        "accept: application/json",
        "accept-encoding: gzip, deflate",
        "accept-language: en-US,en;q=0.8",
        "content-type: application/json",
        "user-agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36",
    );
    $url = 'http://www.baidu.com/';
    $result['succeed_times'] = 0; //成功次数
    $result['defeat_times']  = 0; //失败次数
    $result['total_spen']    = 0; //总用时
    for ($i=0; $i < $times; $i++) { 
        $s = microtime();
        $curl = curl_init();
        curl_setopt($curl, CURLOPT_URL, $url); //设置传输的url
        curl_setopt($curl, CURLOPT_HTTPHEADER, $header); //发送http报头
        curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
        curl_setopt($curl, CURLOPT_ENCODING, 'gzip,deflate'); // 解码压缩文件
        curl_setopt($curl, CURLOPT_SSL_VERIFYPEER, false); //不验证证SSL书
        curl_setopt($curl, CURLOPT_SSL_VERIFYHOST, false); //不验证SSL证书

        if (@$proxy_ip != false) { //使用代理ip
            curl_setopt($curl, CURLOPT_HTTPHEADER, array (
                'Client_Ip: '.mt_rand(0, 255).'.'.mt_rand(0, 255).'.'.mt_rand(0, 255).'.'.mt_rand(0, 255),
            ));
            curl_setopt($curl, CURLOPT_HTTPHEADER, array (
                'X-Forwarded-For: '.mt_rand(0, 255).'.'.mt_rand(0, 255).'.'.mt_rand(0, 255).'.'.mt_rand(0, 255),
            ));
            curl_setopt($curl, CURLOPT_PROXYTYPE, CURLPROXY_HTTP);
            curl_setopt($curl, CURLOPT_PROXY, $proxy_ip);
        }

        curl_setopt($curl, CURLOPT_COOKIEFILE, dirname(__FILE__).'/cookie.txt');
        curl_setopt($curl, CURLOPT_COOKIEJAR, dirname(__FILE__).'/cookie.txt');
        curl_setopt($curl, CURLOPT_TIMEOUT, 30); // 设置超时限制防止死循环
        // $response_header = curl_getinfo($curl); // 获取返回response报头
        $content = curl_exec($curl);
        if (strstr($content, '百度一下，你就知道')) {
            $result['list'][$i]['status'] = 1;
            $result['succeed_times'] += 1;
        } else {
            $result['list'][$i]['status'] = 0;
            $result['defeat_times']  += 1;
        }
        $e = microtime();
        $result['total_spen']          += abs($e-$s);
        $result['list'][$i]['spen']    =  abs($e-$s);
        $result['list'][$i]['content'] =  json_encode($content, true);
        // $result['list'][$i]['response_header'] =  $response_header;
    }
    $result['precent'] = (number_format($result['succeed_times']/$times, 4)*100).'%';
    $result['average_spen'] = number_format($result['total_spen']/$times, 4);
    return $result;
}

/**
 +-----------------------------------------------------------------------------
 * 保存指定路径的图片
 +-----------------------------------------------------------------------------
 * @param string $url 完整图片地址
 * @param string $filename 另存为的图片名字
 * @param string $save_dir 保存指定路径
 * @return mixed
 * @author elinx <654753115@qq.com> 2016-06-11
 * save_image("http://www.w3school.com.cn/ui/bg.gif");
 +-----------------------------------------------------------------------------
 */
function save_image($url, $filename="", $save_dir="./images/"){ 
    if($url == ""){return false;}
    $pic_ext = strrchr($url, ".");/*得到图片的扩展名*/
    $ext     = array(".gif", ".png", ".jpg", ".bmp"); /* 支持格式 */
    if(!in_array($pic_ext, $ext)){echo "格式不支持！";return false;} 
    if($filename == ""){$filename = time().$pic_ext;} /*间戳另起名*/
    if (!is_dir($save_dir)) { echo "保存 {$save_dir} 目录不存在！";return false; }
    /*开始捕捉图片流*/
    ob_start(); /*图片数据开启缓存*/
    readfile($url);
    $img  = ob_get_contents();
    ob_end_clean();/*图片数据缓存清除*/
    $size = strlen($img);
    $fp   = fopen($save_dir.$filename , "a");/*打开路径*/
    fwrite($fp, $img);/*写入图片数据流*/
    fclose($fp);
    return $filename;
}

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
function generate_csv($data, $delimiter = ',', $enclosure = '"') {
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
