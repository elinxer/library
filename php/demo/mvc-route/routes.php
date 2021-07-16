<?php

use \Common\Special\Router;

Router::get('/test', "Index@index");

Router::get('/test2', "Index@test");

