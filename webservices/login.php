<?php
function login()
		{	$user = $_POST['fuser'];
			$pass = md5(md5($_POST['fpasswd']));
			$sql = "select * from user where username = '$user'";
			$kq = mysql_query($sql);
			$row = mysql_fetch_array($kq);
			if (@mysql_num_rows($kq) == 0 )
				{	echo  "<div align='center'>Username không tồn tại</div>";
					return false;
				}
			if ($row['password'] != $pass)
				{	echo  "<div align='center'>Sai mật khẩu</div>";
					return false;
				}
			session_start();
			$_SESSION['login'] = "yes";		
			$_SESSION['user'] = $row['username'];
			$expire = time() + 3600;
			setcookie("username",$row['username'],$expire);
			setcookie("password",$row['password'],$expire);
			echo "<meta http-equiv='refresh' content='0;url=index.php'>";

			return false;
		} 
	
	function logout()
		{	setcookie("username","",time()-3600);
			setcookie("password","",time()-3600);
			session_unregister('login');
			session_unregister('user'); 
			echo "<meta http-equiv='refresh' content='0;url=index.php'>";
			return false;
		}

	
	function check_cookie($name)
		{	if(isset($_COOKIE[$name]))
				{	return $_COOKIE[$name];
				}
			else
				{	return false;
				}
		}
	
	$cookie = array();
	$cookie['username'] = check_cookie('username');
	$cookie['password'] = check_cookie('password');
	
	if ($cookie['username'] != "" and $cookie['password'] != "")
		{	$sql = "select * from user where username = '{$cookie['username']}'";
			$kq = mysql_query($sql);
			$row = mysql_fetch_array($kq);
			if ($row['password'] == $cookie['password'])
				{	$_SESSION['login'] = "yes";
					$_SESSION['user'] = $row['username'];
				}
		}
	
	if ($_SESSION['login'] == "yes"){
   include 'add_item.php';
}else

	
		{	?>
			<div align="center"><img src="images/server.jpg"></img></div>
			<form action="index.php?action=login" name="login" method="POST">
			<table align="center">
		    	<tr><td><input type="text" size="31" name="fuser" /></td>
		       	<tr><td><input type="password" size="31" name="fpasswd" /></td>
		        <tr><td  align="center"><input type="submit" value="Đăng nhập" /></td>
    			</table>
			</form>
	<?php	}
		


	if	(isset($_REQUEST['action']))
		{	$action=$_REQUEST['action'];
			if ($action == "login") 
				{ 	login(); 
				} 
			else if ($action == "logout") 
				{ 	logout(); 
				} 
		}
?>
