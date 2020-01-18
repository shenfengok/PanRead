<?php
function OpenCon()
 {
    $servername = "192.168.33.102:3306";
    $username = "root";
    $password = "Qwert,1102";
    $db = "jing";
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

function toArray($result){

    $arr = array();
     if ($result){
            while($row = mysqli_fetch_array($result)) {

                    $count=count($row);

                    for($i=0;$i<$count;$i++){

                        unset($row[$i]);//删除冗余数据

                    }

                    array_push($arr,$row);

                }
        }

     return $arr;
 }

function toOne($result){

    $arr = toArray($result);
    if(count($arr)){
        return array_pop($arr);
    }
    return (object)null;
 }
?>