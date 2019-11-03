<?php

$perpage = 20;// number of elements perpage

function OpenCon()
 {
    $servername = "localhost:3307";
    $username = "root";
    $password = "ppwwdd";
    $db = "pan_db";
    // Create connection
    $conn = mysqli_connect($servername, $username, $password,$db);
    // Check connection
    if (!$conn) {
       die("Connection failed: " . mysqli_connect_error());
    }
             
     return $conn;
}
     
function CloseCon($conn)
{
     $conn -> close();
}

function is_exists($conn,$sql){
    $res=mysqli_query($conn,$sql);
    if (mysqli_num_rows($res) > 0) {
         return true;
    }
    return false;
}

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


function read_item($key,$conn){
    echo 'read sub path '.'zhanlan-wanjie/'.$key.'<br>';
    $items = read_path_item('zhanlan-wanjie/'.$key,true);
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


       
?>
