<?php 
ini_set('display_errors',1);            //错误信息
ini_set('display_startup_errors',1);    //php启动错误信息
error_reporting(-1);                    //打印出所有的 错误信息

print "hello";


include './funcs.php';
$conn = OpenCon();
       



 // echo "<pre>"; print_r($dirs); exit;
echo "<br>";
$dirs = read_path_item('zhuanlan-gengxin/专栏-更新',false);
for ($i= 0;$i< count($dirs); $i++){
	$key = $dirs[$i];
	$qql = "select * from t_subject where name ='".$key."';";
	
	$sql = "INSERT INTO t_subject (name,updating) VALUES ('".$key."',1);";
	//先查询是否存在
	if (!is_exists($conn,$qql)) {
		//不存在则插入
	    if (mysqli_query($conn, $sql)) {
	         echo $key."---新记录插入成功";
	    } else {
	        echo "Error: " . $sql . "<br>" . mysqli_error($conn);
	    }
	    
	} else {
	    echo  $key."---已经存在";
	}
	echo "<br>";
	//插入item
	read_item('zhuanlan-gengxin/专栏-更新',$key,$conn);
}
 

CloseCon($conn);
?>