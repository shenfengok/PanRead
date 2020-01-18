

<?php 
 	
 

include './funcs.php';

	$conn = OpenCon();
	$sql ="insert into t_user(name, pwd) VALUES ('".$_GET['name']."','".$_GET['pwd']."');";
	mysqli_query($conn,$sql);
	CloseCon($conn);
 ?>
