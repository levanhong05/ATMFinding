<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");

	$tennganhang = $_REQUEST['tennganhang'];	
	$tenquan = $_REQUEST['tenquan'];

	$sql = "select tendiadiem, diachi, lat, lng from diadiem inner join nganhang on diadiem.manganhang = nganhang.manganhang inner join quanhuyen on diadiem.maquan = quanhuyen.maquan where tenquan like '".$tenquan."' and tennganhang like '".$tennganhang."'";
	


	$kq = mysql_query($sql);
	$posts = array();
 	while($post = mysql_fetch_assoc($kq)) 
		{
			$posts[] = array('node_list_atm'=>$post);
    		}

    	header('Content-type: application/json');
    	echo json_encode(array('list_atm'=>$posts));
?>
