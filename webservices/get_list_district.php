<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");

	$tentinh = $_REQUEST['tentinh'];	
	$sql = "select tenquan from quanhuyen inner join tinhthanh on quanhuyen.matinh = tinhthanh.matinh where tentinh like '".$tentinh."'";
//$sql = "select * from quanhuyen inner join tinhthanh on quanhuyen.matinh = tinhthanh.matinh where tentinh like 'Hà Nội'";
	$kq = mysql_query($sql);
	$posts = array();
 	while($post = mysql_fetch_assoc($kq)) 
		{
			$posts[] = array('node_list_district'=>$post);
    		}

    	header('Content-type: application/json');
    	echo json_encode(array('list_district'=>$posts));
?>
