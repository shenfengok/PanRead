<?php

include './func.php';

header('Content-Type:application/json; charset=utf-8');
$uid = $_SERVER['HTTP_AUTHORIZATION'];
if(!isset($uid)){
    //$token=1;
    http_response_code(401);
    exit('登录过期，请重新登录');
}
$conn = OpenCon();
$arr = array();
$prefix =$_GET['prefix'];
$pid =$_GET['pid'];
$id =$_GET['id'];
$pname =$_GET['pname'];
$cname =$_GET['cname'];

$sql = "INSERT INTO t_his(pid, cid, prefix,uid,pname,cname) VALUES($pid, $id, '$prefix',$uid,'$pname','$cname')
ON DUPLICATE KEY UPDATE cid =$id,cname ='$cname'";
mysqli_query($conn,$sql);

$arr["code"] = 0;
$arr["msg"] = '成功'.$sql;
CloseCon($conn);

exit(json_encode($arr));
?>
