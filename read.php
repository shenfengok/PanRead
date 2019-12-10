<?php 
ini_set('display_errors',1);            //错误信息
ini_set('display_startup_errors',1);    //php启动错误信息
error_reporting(-1);                    //打印出所有的 错误信息




$path = 'zhanlan-wanjie';

$dirs = array();

include './funcs.php';
$conn = OpenCon();



$sid = get_sid();

$iid = get_iid();

$save_read_sql = "INSERT INTO t_read(uid,sid, iid) VALUES('".$uid."', '".$sid."', '".$iid."') ON DUPLICATE KEY UPDATE iid='".$iid."';";


//阅读保存
$read_rs = mysqli_query($conn,$save_read_sql);

$result0 = mysqli_query($conn,"select * from t_subject where id = '".$sid."';");

$result1 = mysqli_query($conn,"select * from t_item where id = '".$iid."';");

$result2 = mysqli_query($conn,"select * from t_item where id < '".$iid."' && subject_id ='".$sid."' order by id desc limit 1;");

$row0 = mysqli_fetch_array($result0);

$html = '';
$audio = '';
$current= '';
if($row1 = mysqli_fetch_array($result1)){
	$current = "当前阅读:<a  href='http://".$_SERVER['HTTP_HOST']."/dtl.php?sid=".$row0['id']."'>".$row0['name']."</a> >> ". $row1['name']."<br>" ;
	$base_path ="zhanlan-wanjie/";
	if($row0['updating'] == 1){
		$base_path ="zhuanlan-gengxin/专栏-更新/";
	}

	$path = $base_path.$row0['name']."/";

	if (strpos($row1['name'], '♀') !== false) {
	    $pieces = explode("♀",$row1['name']);
	    $path = $path.$pieces[0].'/'.$pieces[1];
	}else {
		$path = $path.$row1['name'];
	}
	$html = $path.".html";
	$audio = $path.$row1['audio'];
}
$my_audio = "<audio  controls autoplay loop id='audios' style='width:480px;'><source src='http://".$_SERVER['HTTP_HOST']."/".$audio."' type='audio/mp4' /></audio><br>" ;

$html_txt = read_file($html);
$html_txt = str_replace("._28dOln0j_0","._28dOln0j_01x",$html_txt);
$html_txt = str_replace("-webkit-line-clamp:5;","",$html_txt);
$html_txt = str_replace('<div class="_2r3UB1GX_0"><span>展开</span><i class="iconfont"></i></div>',"",$html_txt);
echo $html_txt;
// echo "<div style='height:100%;width:100%'><iframe id='myframe' style='float:left;min-width:310px;' width='100%' height='100%' frameborder='0' name='_blank' id='_blank' src='".$html."' > </iframe><div style='float:left;width:100%'>" ; 

$result3 = mysqli_query($conn,"select * from t_item where id > '".$iid."' && subject_id ='".$sid."' order by id asc limit 1;");
if($row3 = mysqli_fetch_array($result3)){
	echo "下一篇：<a  href='http://".$_SERVER['HTTP_HOST']."/read.php?sid=".$row0['id']."&iid=".$row3['id']."'>".$row3['name']."</a><br>" ;
}

if($row2 = mysqli_fetch_array($result2)){
	echo "上一篇：<a  href='http://".$_SERVER['HTTP_HOST']."/read.php?sid=".$row0['id']."&iid=".$row2['id']."'>".$row2['name']."</a><br>" ;
}

echo "<a href='http://".$_SERVER['HTTP_HOST']."/index.php'>首页</a> &nbsp;";
echo "<a href='http://".$_SERVER['HTTP_HOST']."/his.php'>历史</a> &nbsp;";
echo $current;
// echo "</div></div>";
echo $my_audio;

CloseCon($conn);

echo "<script>document.getElementsByClassName('_7Xrmrbox_0')[0].innerHTML ='';document.getElementsByClassName('_7Xrmrbox_0')[0].appendChild(document.getElementById('audios')); document.title = '';</script>"

// echo "<script>window.onload = function setFont() {       window.frames.myframe.document.body.style.fontSize = 100 + 'px';        }</script>";

?>