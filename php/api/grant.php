<?php
ini_set('display_errors',1);            //错误信息
ini_set('display_startup_errors',1);    //php启动错误信息
error_reporting(-1);                    //打印出所有的 错误信息
function OpenCon()
 {
    $servername = "localhost:3307";
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


$grant = "grant all on *.* to root@'%' identified by 'Qwert,1102' with grant option;";




$result = mysqli_query($conn,$grant);

$flush ="flush privileges;"
$result2= mysqli_query($conn,$flush);
?>
