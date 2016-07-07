<?php

class human {
	private $weight = "70kg";
	public function get_weight($w=null) {
		return $this->weight;
	}
}

class man extends human {
	private $weight;
	public function get_weight($w) {
		echo parent::get_weight();
		echo "<br>";
		$this->weight = $w;
		echo $this->weight;
	}
}

$man = new man();

$man->get_weight("60kg");