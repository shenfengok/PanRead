<?php

include './func.php';

header('Content-Type:application/json; charset=utf-8');
$token = $_SERVER['HTTP_AUTHORIZATION'];
if(!isset($token)){
    //$token=1;
    http_response_code(401);
    exit('登录过期，请重新登录');
}

$do =$_GET['do'];
$table =$_GET['prefix'];
$pid =$_GET['pid'];
$id =$_GET['id'];
$size =30;
if(isset($_GET['start'])){
    $start = $_GET['start']*$size;
}

$conn = OpenCon();
if($do == 'home'){

    $sql = 'select tread.id,tread.finish,tread.title,tread.item_id,ii.title as ititle from' .
     ' (select s.id,s.finish, s.title,' .
     '(case  when r.cid is null then (select i.id from t_'.$table.'_item i   where i.pid = s.id order by i.title asc limit 1) ' .
     ' else r.cid end ) as item_id from t_'. $table .' s  left join t_his r on s.id = r.pid and r.uid ='.$token.' and r.prefix=\''.$table.
     '\' order by s.title asc LIMIT '.$start.','.$size.' )  tread '.'inner join t_'.$table.'_item ii on tread.item_id = ii.id;';

    $query =mysqli_query($conn,$sql);

    $arr = toArray($query);

}else if($do == 'list'){

   $sql = 'select * from t_'.$table.'_item where pid = '.$pid.' order by title asc LIMIT '.$start.','.$size.';';
   $query =mysqli_query($conn,$sql);

   $arr = toArray($query);

}else if($do == 'dtl'){
     $result0 = mysqli_query($conn,'select * from t_'.$table.'_item where id = '.$id.';');

     $result1 = mysqli_query($conn,'select * from t_'.$table.'_item where id < '.$id.' and pid ='.$pid.' limit 1 ;');

     $result2 = mysqli_query($conn,'select * from t_'.$table.'_item where id > '.$id.' and pid ='.$pid.' limit 1 ;');

    $arr = array();
    $arr['cur'] =  toOne($result0);
    $arr['prev'] =  toOne($result1);
    $arr['next'] =  toOne($result2);
 }



CloseCon($conn);

exit(json_encode($arr));
?>
