<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");
	if($_REQUEST['REMOVE']=='OK')
		{	$cdate = $_REQUEST['DATE'];
			$sql = "delete from lienhe where c_date like '".$cdate."'";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=viewCONTACT'>";
		}

?>
                <pre style="overflow: scroll;height:600px">
		<table align="center" border="1" width=80%>
			<tr align=center height=30px style="font-weight:bold;color:white" bgcolor=green>
				<td width=150px>NGÀY LIÊN HỆ</td>
				<td width=150px>ĐỊA CHỈ MAC</td>
				<td width=135px>TÊN MÁY</td>
				<td>TIÊU ĐỀ</td>
				<td>NỘI DUNG</td>
				<td></td>
			<?php
				$sql = "select * from lienhe";
				$kq = mysql_query($sql);
				while($row = mysql_fetch_array($kq))
			{	?>
                        
			<tr style="border-bottom:1px">
				<td align=center><? echo $row['c_date']; ?></td>				
				<td align=center><? echo $row['c_mac']; ?></td>
				<td align=center><? echo $row['c_name']; ?></td>
				<td align="left"><? echo substr($row['c_tieude'],0,30); ?></td>
			<td align="left"><a href="javascript:popupMES('<? echo 'Gửi lúc: <b>'.$row['c_date'].'</b><br>Tên/OS: <b>'.$row['c_name'].'</b><br>Nội dung: <b>'.$row['c_noidung'].'</b>'; ?>')"><? echo substr($row['c_noidung'],0,50); ?></a></td>
<td><a href="javascript:popup('Có chắc chắn xóa góp ý <? echo '<b>'.$row['c_date'].'</b>' ?> ?','gopy','<? echo $row['c_date']; ?>')"><img src="images/delete.gif"></img></a>

			<?	}
			?>
		</table>
                </pre>

