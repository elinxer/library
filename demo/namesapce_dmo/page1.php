<?php
namespace foo;


$a = new \foo\Animal();
spl_autoload_register(function ($class) {
    if ($class) {
        $file = str_replace('\\', '/', $class);
        $file .= '.php';
        if (file_exists($file)) {
            include $file;
        }
    }
});



class Cat {
	public function name() {
		echo "i am a cat";
	}
}













