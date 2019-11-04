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

$read_sql = "select i.id, i.name,i.subject_id,r.update_time from (select * from t_read rr where rr.uid=".$uid." and rr.sid =".$sid." limit 1) r   inner join   t_item i  on i.id = r.iid;";


//上次阅读
$read_rs = mysqli_query($conn,$read_sql);

if (mysqli_num_rows($read_rs) > 0) {//阅读记录
	$row_rs = mysqli_fetch_array($read_rs);
    echo "上次阅读：";
    echo "<a target=_blank href='read.php?sid=". $row_rs['subject_id']."&iid=".$row_rs['id']. "'>".$row_rs['name']."</a><br>";
    
}





$result = mysqli_query($conn,"select * from t_subject where id = '".$sid."';");
if($row1 = mysqli_fetch_array($result)){
	echo $row1['name'];
	echo "<table>";
	$item_result = mysqli_query($conn,"select * from t_item where subject_id = '".$sid."';");
	while($row = mysqli_fetch_array($item_result))
	{
	  echo "<tr>";
	  echo "<td width='25%'><a target=_blank href='read.php?sid=". $row['subject_id']."&iid=".$row['id']. "'>".$row['name']."</a></td>";
	  echo "<td width='25%'>".$row['audio']."</td>";
	  echo "</tr>";
	}
	echo "</table>";
}





CloseCon($conn);
?>