<?php
/**
 * 这个文件处理文章的查询，并提供文章
 */
class News_Controller
{
	public $template = 'news'; //视图文件名称

	/**
	 * 此方法为route.php默认调用
	 * @access public
	 * @param array $getVars 传入到index.php的GET变量数组
	 */
	public function main(Array $getVars)
	{
		$newsModel = new News_Model;
		//获取一篇文章
   		$article = $newsModel->get_article($getVars['article']);
   		$view = new View_Model($this->template); // 渲染模板
		//把文章数据赋给视图模板
		$view->assign('title', $article['title']);
		$view->assign('content', $article['content']);
		
	}
}

?>