<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");

	$tenduong = $_REQUEST['tenduong'];	
	
	$sql = "select codau from dsduong where khongdau like '%".$tenduong."%' limit 0,1";
	
	$kq = mysql_query($sql);
	$posts = array();
 	while($post = mysql_fetch_assoc($kq)) 
		{
			$posts[] = array('node_duong'=>$post);
    		}

    	header('Content-type: application/json');
    	echo json_encode(array('list_duong'=>$posts));
?>
