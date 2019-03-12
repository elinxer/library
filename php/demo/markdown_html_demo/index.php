<?php

if ($_POST) {
	print_r($_POST);die();
}

?>

<!DOCTYPE html>
<html>
<head>
	<title></title>
	<link rel="stylesheet" href="editormd/css/editormd.css"/>
	<script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/1.6.4/jquery.min.js"></script>
	<script src="editormd/editormd.js"></script>  
</head>
<body>
<button id="get-md-btn">Get Markdown</button>
<button id="get-html-btn">Get HTML</button>

<form action="" method="post">
	<!--编辑器-->
	<div id="test-editormd">
	    <textarea style="display:none;" id="ts" name="content"></textarea>
	</div>
	<input type="submit" name="sub">
</form>

<script type="text/javascript">
//    调用编辑器
var testEditor;
$(function() {
    testEditor = editormd("test-editormd", {
        width   : "1000px",
        height  : 640,
        syncScrolling : "single",
        path    : "editormd/lib/",

        delay 	: 300, /* 延迟显示预览 */
        // emoji:true,
        saveHTMLToTextarea : true, /* 开启md到html转换 */
        editorTheme:"neo",
        previewTheme:"default",
        placeholder:"", /* 默认显示文字 */

        tex:true, /* 采用数学公式 */

        toolbarIcons : function() {
            // Or return editormd.toolbarModes[name]; // full, simple, mini
            // Using "||" set icons align right.
            return [
            	"undo", "redo", "|", 
            	"bold", "del", "italic", "quote", "uppercase", "lowercase", "link", "image", "|", 
            	"h1", "h2", "h3", "h4", "h5", "h6", "|", 
            	"list-ul", "list-ol", "hr", "||",
            	"watch", "preview", "fullscreen", "|",
            ]
        },
    });

    testEditor.setToolbarAutoFixed(true); /* 固定工具栏 */

    $("#get-md-btn").bind('click', function(){
        alert(testEditor.getMarkdown()); /* 获取markdown原始数据 */
    });
    
    $("#get-html-btn").bind('click', function() {
        alert(testEditor.getHTML()); /*获取转换的html */
    }); 

});
</script>



</body>
</html>
