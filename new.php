

<?php 
 	
 
function OpenCon()
 {
    $servername = "localhost:3307";
    $username = "root";
    $password = "ipqmtd123";
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

	$conn = OpenCon();
	$sql ="insert into t_user(name, pwd) VALUES ('".$_GET['name']."','".$_GET['pwd']."');";
	mysqli_query($conn,$sql);
	CloseCon($conn);
 ?>
