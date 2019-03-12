<?php  
/** 
 * 在我们的MVC框架中，处理视图的功能 
 */  
class View_Model  
{  
    /** 
     * 保存赋给视图模板的变量 
     */  
    private $data = array();  
  
    /** 
     * 保存视图渲染状态 
     */  
    private $render = FALSE;  
  
    /** 
     * 加载一个视图模板 
     */  
    public function __construct($template)  
    {  
        //构成完整文件路径  
        $file = SERVER_ROOT . '/views/' . strtolower($template) . '.php';  
        
        if (file_exists($file))  
        {  
            /** 
             * 当模型对象销毁时才能渲染视图 
             * 如果现在就渲染视图，那么我们就不能给视图模板赋予变量 
             * 所以此处先保存要渲染的视图文件路径 
             */  
            $this->render = $file;  
        }        
    }  
  
    /** 
     * 接受从控制器赋予的变量，并保存在data数组中 
     *  
     * @param $variable 
     * @param $value 
     */
    public function assign($variable , $value)
    {
        $this->data[$variable] = $value;
    }
    
    public function __destruct()
    {  
        //把类中的data数组变为该函数的局部变量，以方便在视图模板中使用  
        $data = $this->data;
        
        //渲染视图
        include($this->render);
    }
}  