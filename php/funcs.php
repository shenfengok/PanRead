<?php

$perpage = 30;// number of elements perpage

  
if(!isset($_COOKIE['uid'])|| !isset($_COOKIE['username'])){
   header('location:login.html',true,301);
    exit();
} else{
    echo $_COOKIE['username'];
    $uid = $_COOKIE['uid'];
    echo "<a href='logout.php'>注销</a>";
}

 

include './db_conn.php';

function is_exists($conn,$sql){
    $res=mysqli_query($conn,$sql);
    if (mysqli_num_rows($res) > 0) {
         return true;
    }
    return false;
}

// function is_valid_user($conn,$username,$pwd){
//     $sql = "select * from user where name ='".$username."' and pwd ='".$pwd."' limit 1;"
//     return is_exists($conn,$sql);
// }


function get_start(){
    $start = 0 ;
    if(isset($_GET['start'])){
         $start =  (int)$_GET['start']; // grab the page number
    }
    return $start;
}

function get_sid(){
    $sid = 0 ;
    if(isset($_GET['sid'])){
         $sid =  (int)$_GET['sid']; // grab the page number
    }
    return $sid;
}

function get_iid(){
    $iid = 0 ;
    if(isset($_GET['iid'])){
         $iid =  (int)$_GET['iid']; // grab the page number
    }
    return $iid;
}

function read_path_item($path,$read_children){
    $ddirs = array();
    // directory handle
    $dir = dir($path);

    while (false !== ($entry = $dir->read())) {
        if ($entry != '.' && $entry != '..') {
         

           if ($read_children) { //读取item

                if (is_dir($path . '/' .$entry)) {
                    $children  = read_path_item($path . '/' .$entry,false);
                    for ($j=0;$j < count($children);$j++){
                        $ddirs[] = $entry."♀".$children[$j]; 
                    }
                }else{
                    $ddirs[] = $entry; 
                }
           }else{//读取subject
             $ddirs[] = $entry; 
           }
        }
    }
    sort($ddirs);
    return $ddirs;
}

function endWith($haystack, $needle) {   

    $length = strlen($needle);  
    if($length == 0)
    {    
      return true;  
    }  
    return (substr($haystack, -$length) === $needle);
}


function read_item($path,$key,$conn){
    echo 'read sub path '.$path.'/'.$key.'<br>';
    $items = read_path_item($path.'/'.$key,true);
    // print_r($items);
    for ($ii= 0;$ii< count($items); $ii++){
        if(endWith($items[$ii],'.html')){
            $it = rtrim($items[$ii],'.html');
            $audio = '.mp3';
            if(in_array($it.'.m4a', $items)){
                $audio = '.m4a';
            }

            $subject = mysqli_query($conn,"select * from t_subject where name ='".$key."' limit 1");

            $row = mysqli_fetch_array($subject);
            $subject_id = $row['id'];

            $insert_item_sql = "INSERT INTO t_item(name,subject_id, audio) VALUES('".$it."', '".$subject_id."', '".$audio."') ON DUPLICATE KEY UPDATE subject_id='".$subject_id."', audio='".$audio."';";
            if (mysqli_query($conn, $insert_item_sql)) {
                 echo "-----------". $it."---新记录插入成功";
            } else {
                 echo "-----------Error: " . $sql . "<br>" . mysqli_error($conn);
            }
        }
        echo "<br>";
    }
}


function read_file($file_path){
    if(file_exists($file_path)){
        $fp = fopen($file_path,"r");
        $str = fread($fp,filesize($file_path));//指定读取大小，这里把整个文件内容读取出来
        return $str;
    }
    return "not exist";
}

       
?>
