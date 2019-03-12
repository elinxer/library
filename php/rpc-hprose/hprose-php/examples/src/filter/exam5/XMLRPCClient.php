<?php
require_once "../../../vendor/autoload.php";
require_once '../LogFilter.php';

use Hprose\Client;
use Hprose\Filter\XMLRPC;

$client = Client::create('tcp://127.0.0.1:1143/', false);
$client->addFilter(new XMLRPC\ClientFilter());
$client->addFilter(new LogFilter());

var_dump($client->hello("world"));
