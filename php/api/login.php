<?php

include './func.php';

header('Content-Type:application/json; charset=utf-8');



$conn = OpenCon();
    $arr = array();
    $content = file_get_contents('php://input');
    $post    = (array)json_decode($content, true);
    # 接收用户的登录信息
    $username = trim($post['username']);
    $pwd = trim($post['password']);
    // 判断提交的登录信息
    if (($username == '') || ($pwd == '')) {
        $arr["code"] = 11;
        $arr["msg"] = '用户名密码不能为空';
    }

    $sql = "select * from t_user where name ='".$username."' and pwd ='".$pwd."' limit 1;";
    $result =mysqli_query($conn,$sql);

    if (mysqli_num_rows($result) <= 0) {
        $arr["code"] = 12;
        $arr["msg"] = '登录失败';
        exit;
    } else {
        $user = mysqli_fetch_array($result);

        $arr["code"] = 0;
        $arr["msg"] = '登录成功';
        $arr["token"] = $user['id'];
    }
	CloseCon($conn);

	exit(json_encode($arr));
?>
