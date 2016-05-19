<?php
/**
* 新闻模型为新闻控制器做复杂的后台操作
*/
class News_Model
{
	/* 文章数组. key为文章标题, 值为相应的文章。*/
	private $articles = array
	(
		//文章1
		'new' => array
		(
			'title' => 'New Website' ,
			'content' => 'Welcome to the site! We are glad to have you here.'
		)
		,
		//2
		'mvc' => array
		(
			'title' => 'PHP MVC Frameworks are Awesome!' ,
			'content' => 'It really is very easy. Take it from us!'
		)
		,
		//3
		'test' => array
		(
			'title' => 'Testing' ,
			'content' => 'This is just a measly test article.'
		)
	);
	public function __construct()
	{
	}
	
	/**
	* 根据标题获取文章
	*
	* @param string $articleName
	*
	* @return array $article
	*/
	public function get_article($articleName)
	{
		//从数组中获取文章
		$article = $this->articles[$articleName];
		return $article;
	}

}


?>