package main

import (
	"fmt"
	"io/ioutil"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {

	var controllerName string
	var tpl string
	for idx, args := range os.Args {
		if strings.HasSuffix(args, "Controller") {
			controllerName = args
		}
		if args == "-tpl" {
			tpl = args
		}
		fmt.Println("参数"+strconv.Itoa(idx)+":", args)
	}

	if controllerName == "" || !strings.HasSuffix(controllerName, "Controller") {
		log.Println("Controller must like: TestController.")
		os.Exit(0)
	}

	makePath := "application/controllers"
	os.MkdirAll(makePath, os.ModePerm)
	file := makePath + "/" + strings.Replace(controllerName, "Controller", "", -1) + ".php"

	fileCon, err := os.OpenFile(file, os.O_RDWR|os.O_CREATE, 0766)
	if err != nil {
		log.Println(err)
		log.Printf("error")
	}
	controllerContent := `
<?php

use Common\Schema\Form;
use Common\Schema\Grid\Filter;
use Common\Schema\Grid;

class FormDemoController extends FormBaseController
{
	use \Common\Form\BaseTrait;

	/**
		* 页面标题
		* @var string
		*/
	public $_title = '表单模板';

	/**
		* 使用模型，继承 BaseModel
		* @var LotteryPrizeCardModel
		*/
	public $model;

	/**
		* @throws Exception
		*/
	public function init()
	{
		$noAuth = [
			"{$this->getController()}/_query",
			"{$this->getController()}/edit",
			"{$this->getController()}/_export",
			"{$this->getController()}/_form",
			"{$this->getController()}/_delete",
		];
		array_push($this->_lca, ...$noAuth);
		parent::init();
		$this->model = new LotteryPrizeCardModel();
	}

	/**
		* @param null $view
		* @return mixed
		* @throws Exception
		*/
	public function grid($view = null)
	{
		$grid = new Grid($this->model, $this->requests, $view);

		$grid->column('code', '礼包码')->width(150);
		$grid->column('username', '领取账号')->width(140);

		$grid->filter(function (Filter $filter) {
			if ($filter instanceof Filter) {}
			$filter->equal('code', '礼包码');
			$filter->equal('username', '领取账号');
			$filter->hidden('tid', $_REQUEST['tid']);
		});

		$grid->actions(function(Grid\Displayers\Actions $action) {
			//$action->disableEdit();
			$action->disableView();
			$action->disableDelete();
			$action->setWidth(200);
		});

		//$grid->disableActions();
		//$grid->disableCreateButton();
		//$grid->disableDeleteButton();
		$grid->disableExport();

		$grid->model()->orderBy([['id', 'desc']]);

		return $grid->build($view ? false : true);
	}

	/**
		* @param null $view
		* @return array|Form
		* @throws Exception
		*/
	public function form($view = null)
	{
		$form = new Form($this->model, $view);
		$form->hidden('tid', 'TID')->default($_REQUEST['tid']);
		$form->text('pid', '奖品ID');
		$form->text('code', '礼包码')->required();
		$form->text('username', '领取账号');
		return $form->build($view, $render=false);
	}

	/**
		* @throws Exception
		*/
	public function indexAction()
	{
		$grid = $this->grid($this->smarty);
		$form = $this->form($this->smarty);

		$this->assign("grid", $grid);
		$this->assign("form", $form);

		return $this->view();
	}

	/**
		* @throws Exception
		*/
	public function editAction()
	{
		$rowData = $this->model->fetchOne(['id' => $this->requests['id']]);
		$form = $this->form($this->smarty);
		$this->assign("info", $this->transferJsonColumn($rowData));
		$this->assign("form", $form);
		
		return $this->render($tpl=$this->_controller . '/edit.htm');
	}

	/**
		* 查询列表接口
		* @throws Exception
		*/
	public function _queryAction()
	{
		$this->setResult();
		try {

			$grid = $this->grid();
			list($data, $total) = array_values($grid['rows'] ?: []);
			$this->setResult(['rows' => $data, 'total' => $total]);

		} catch (Exception $exception) {
			$this->exception($exception, $this);
		}

		return $this->result();
	}

	/**
		* 表单数据处理
		* @throws Exception
		*/
	public function _formAction()
	{
		if ($this->isPost()) {
			$data = $this->combineJsonColumn($this->requests);
			if (count($data) > 0 && $result = $this->model->saveData($data)) {
				$this->_result['data'] = $data;
				$this->_result['state'] = $this->_resCode = 1;
			}
		}
		return $this->result();
	}

	public function _deleteAction()
	{
		$id = $this->getPost('id');
		$id = is_array($id) ? $id : [$id];
		if ($id && $this->model->deleteData([['id', $id, 'in', 'and']])) {
			$this->_result['state'] = $this->_resCode = 1;
		}
		return $this->result();
	}

	public function _exportAction()
	{
		// 表头字段
		$columnKeys = ['id' => 'ID', 'created_at' => '提交时间', 'updated_at' => '最后更新时间', ];

		$this->requests['_export_']      = 'all';
		$this->requests['tableTitles']   = $columnKeys;
		$this->requests['tableFileName'] = 'demo' . date('Y-m-d');

		$this->grid($view = null);exit;
	}

}

	`

	controllerContent = strings.Replace(controllerContent, "FormDemoController", controllerName, -1)
	//判断文件
	bool, err := isFileExist(file)
	if bool {
		log.Println("文件已存在！")
	} else {
		//以字符串写入
		fileCon.WriteString(controllerContent)
	}
	fileCon.Close()

	if tpl == "-tpl" {

		tplPath := "./application/views/formdemo"
		indexCon := getFileContent(tplPath + "/index.htm")
		editCon := getFileContent(tplPath + "/edit.htm")

		pathName := strings.Replace(controllerName, "Controller", "", -1)

		pathName = strings.ToLower(pathName)

		makeNewPath := "application/views/" + pathName
		os.MkdirAll(makeNewPath, os.ModePerm)

		fileIndex := makeNewPath + "/index.htm"
		fileIndexCon, err2 := os.OpenFile(fileIndex, os.O_RDWR|os.O_CREATE, 0766)
		if err2 != nil {
			log.Println(err2)
		}
		//判断文件
		bool1, _ := isFileExist(fileIndex)
		if bool1 {
			log.Println("index.htm文件已存在！")
		} else {
			//以字符串写入
			fileIndexCon.WriteString(indexCon)
		}
		fileIndexCon.Close()

		fileEdit := makeNewPath + "/edit.htm"
		fileEditCon, err1 := os.OpenFile(fileEdit, os.O_RDWR|os.O_CREATE, 0766)
		if err1 != nil {
			log.Println(err1)
		}
		//判断文件
		bool2, _ := isFileExist(fileEdit)
		if bool2 {
			log.Println("edit.htm文件已存在！")
		} else {
			//以字符串写入
			fileEditCon.WriteString(editCon)
		}
		fileEditCon.Close()
	}
	log.Println("创建完成！")
}

//判断文件文件夹是否存在
func isFileExist(path string) (bool, error) {
	fileInfo, err := os.Stat(path)

	if os.IsNotExist(err) {
		return false, nil
	}
	//我这里判断了如果是0也算不存在
	if fileInfo.Size() == 0 {
		return false, nil
	}
	if err == nil {
		return true, nil
	}
	return false, err
}

func getFileContent(path string) string {
	content, err := ioutil.ReadFile(path)
	if err != nil {
		log.Println(err)
	}
	string1 := string(content)
	return string1
}
