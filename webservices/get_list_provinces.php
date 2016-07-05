<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");
	
	$sql = "select * from tinhthanh";
	$kq = mysql_query($sql);
	
  	$posts = array();
 	while($post = mysql_fetch_assoc($kq)) 
	{
		$posts[] = array('node_list_provinces'=>$post);
    }

    header('Content-type: application/json');
    echo json_encode(array('list_provinces'=>$posts));
?>
