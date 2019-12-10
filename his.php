<?php 
ini_set('display_errors',1);            //错误信息
ini_set('display_startup_errors',1);    //php启动错误信息
error_reporting(-1);                    //打印出所有的 错误信息

echo "<a href='index.php'>首页</a>";

echo "<a href='his.php'>历史</a>";


include './funcs.php';
$conn = OpenCon();


$start = get_start();
$read_sql = "select i.id,i.name,i.subject_id,r.update_time from (select * from t_read rr where rr.uid=".$uid." order by rr.update_time desc LIMIT ".$start.", ".$perpage.") r   inner join   t_item i  on i.id = r.iid;";



$result = mysqli_query($conn,$read_sql);

echo "<table>";
while($row_rs = mysqli_fetch_array($result))
{
  echo "<tr>";
  
  // $read_sql = "select i.id,i.name,i.subject_id,r.update_time from (select * from t_read rr where rr.uid=".$uid." and rr.sid =".$row['id']." limit 1) r   inner join   t_item i  on i.id = r.iid;";

  $subject = mysqli_query($conn,"select * from t_subject where id = '".$row_rs['subject_id']."';");
  // echo $read_sql;
  $row = mysqli_fetch_array($subject);
  echo "<td width='25%'><a target=_blank href='dtl.php?sid=".$row['id']."'>". $row['name'] . "</a></td>";
  //阅读记录
	// $row = mysqli_fetch_array($read_rs);
  
  echo "<td width='25%'>继续阅读：<a target=_blank href='read.php?sid=". $row_rs['subject_id']."&iid=".$row_rs['id']. "'>".$row_rs['name']."</a></td>";
  echo "<td width='25%'>".$row_rs['update_time']."</td>";

  echo "</tr>";
}
echo "</table>";
echo "<a href='index.php'>首页</a>";
if($start >= $perpage){
	$prev = $start - $perpage;
	echo "<a href='?start=$prev' > 上一页</a>";
}
$next = $start + $perpage;
echo "<a href='?start=$next' > 下一页</a>";


CloseCon($conn);
?>