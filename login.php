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
	header('Content-type:text/html; charset=utf-8');
	// // 开启Session
	// session_start();

	// 处理用户登录信息
	if (isset($_POST['login'])) {
		# 接收用户的登录信息
		$username = trim($_POST['username']);
		$pwd = trim($_POST['password']);
		// 判断提交的登录信息
		if (($username == '') || ($pwd == '')) {
			// 若为空,视为未填写,提示错误,并3秒后返回登录界面
			header('refresh:3; url=login.html');
			echo "用户名或密码不能为空,系统将在3秒后跳转到登录界面,请重新填写登录信息!";
			exit;
		}

		$sql = "select * from t_user where name ='".$username."' and pwd ='".$pwd."' limit 1;";
		$result =mysqli_query($conn,$sql);

		if (mysqli_num_rows($result) <= 0) {
			# 用户名或密码错误,同空的处理方式
			header('refresh:6; url=login.html');
			// echo $sql;
			echo "用户名或密码错误,系统将在3秒后跳转到登录界面,请重新填写登录信息!";
			CloseCon($conn);
			exit;
		} else {
			$user = mysqli_fetch_array($result);
			// 若勾选7天内自动登录,则将其保存到Cookie并设置保留7天
			// } 
			
			// if ($_POST['remember'] == "yes") {
			setcookie('username', $user['name'], time()+7*24*60*60);
			setcookie('uid', $user['id'], time()+7*24*60*60);
				// setcookie('code', md5($username.md5($password)), time()+7*24*60*60);
			// 处理完附加项后跳转到登录成功的首页
			header('location:index.php');
		}
	}
	CloseCon($conn);
 ?>
