<?php 
ini_set('display_errors',1);            //错误信息
ini_set('display_startup_errors',1);    //php启动错误信息
error_reporting(-1);                    //打印出所有的 错误信息

echo "首页";

echo "<a href='his.php'>历史</a>";


include './funcs.php';
$conn = OpenCon();


$start = get_start();


$result = mysqli_query($conn,"select * from t_subject LIMIT ".$start.", ".$perpage.";");

echo "<table>";
while($row = mysqli_fetch_array($result))
{
  echo "<tr>";
  echo "<td width='25%'><a target=_blank href='dtl.php?sid=".$row['id']."'>". $row['name'] . "</a></td>";
  $read_sql = "select i.id,i.name,i.subject_id,r.update_time from (select * from t_read rr where rr.uid=".$uid." and rr.sid =".$row['id']." limit 1) r   inner join   t_item i  on i.id = r.iid;";


  // echo $read_sql;
  $read_rs = mysqli_query($conn,$read_sql);
  
  if (mysqli_num_rows($read_rs) > 0) {//阅读记录
  		$row_rs = mysqli_fetch_array($read_rs);
	    
	    echo "<td width='25%'><a target=_blank href='read.php?sid=". $row_rs['subject_id']."&iid=".$row_rs['id']. "'>".$row_rs['name']."</a></td>";
	    echo "<td width='25%'>".$row_rs['update_time']."</td>";
	}
  else{
   		$one_sql = "select * from t_item where subject_id=".$row['id']."  LIMIT 1;";
   		// echo $one_sql;
   		$read_ri = mysqli_query($conn,$one_sql);
   		 if (mysqli_num_rows($read_ri) > 0) {
	    	$row_i = mysqli_fetch_array($read_ri);
	    	echo "<td width='25%'>开始阅读：<a target=_blank href='read.php?sid=". $row['id']."&iid=".$row_i['id']. "'>".$row_i['name']."</a></td>";
	    	echo "<td width='25%'></td>";
	    }
   		
   }
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