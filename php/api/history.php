<?php

include './func.php';

header('Content-Type:application/json; charset=utf-8');
$uid = $_SERVER['HTTP_AUTHORIZATION'];
if(!isset($uid)){
    //$token=1;
    http_response_code(401);
    exit('登录过期，请重新登录');
}
$size =30;
if(isset($_GET['start'])){
    $start = $_GET['start']*$size;
}

$conn = OpenCon();
$sql = "select * from t_his where  uid =$uid order by up_time desc  LIMIT $start,$size;";
$query =mysqli_query($conn,$sql);

$arr = toArray($query);
CloseCon($conn);

exit(json_encode($arr));
?>
