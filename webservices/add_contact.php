<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");
	$ctdate = date('Y-m-d h:i:s');
	$sql = "insert into lienhe values('".$ctdate."','".$_REQUEST['mac']."','".$_REQUEST['name']."','".$_REQUEST['tieude']."','".$_REQUEST['noidung']."')";
	@mysql_query($sql);
?>
