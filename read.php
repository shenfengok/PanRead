<?php 
ini_set('display_errors',1);            //错误信息
ini_set('display_startup_errors',1);    //php启动错误信息
error_reporting(-1);                    //打印出所有的 错误信息


echo "<a href='index.php'>首页</a>";
echo "<a href='his.php'>历史</a>";

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
if($row1 = mysqli_fetch_array($result1)){
	echo "当前阅读:<a  href='dtl.php?sid=".$row0['id']."'>".$row0['name']."</a> >> ". $row1['name']."<br>" ;

	$path = "zhanlan-wanjie/".$row0['name']."/";

	if (strpos($row1['name'], '♀') !== false) {
	    $pieces = explode("♀",$row1['name']);
	    $path = $path.$pieces[0].'/'.$pieces[1];
	}else {
		$path = $path.$row1['name'];
	}
	$html = $path.".html";
	$audio = $path.$row1['audio'];
}
echo "<audio controls preload='none' style='width:480px;'>" ;

echo "<source src='".$audio."' type='audio/mp4' /></audio><br>" ;
echo "<div style='height:100%;width:100%'><iframe style='float:left' src='".$html."' width='75%' height='100%'> </iframe><div style='float:right;width:20%'>" ;
if($row2 = mysqli_fetch_array($result2)){
	echo "上一篇：<a  href='read.php?sid=".$row0['id']."&iid=".$row2['id']."'>".$row2['name']."</a><br>" ;
}

$result3 = mysqli_query($conn,"select * from t_item where id > '".$iid."' && subject_id ='".$sid."' order by id asc limit 1;");
if($row3 = mysqli_fetch_array($result3)){
	echo "下一篇：<a  href='read.php?sid=".$row0['id']."&iid=".$row3['id']."'>".$row3['name']."</a>" ;
}
echo "</div></div>";
CloseCon($conn);
?>