<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");
?>
<?php
	if($_REQUEST['status']=='OK')
		{	$matinh = $_POST['matinh'];
			$tentinh = $_POST['tentinh'];
			$sql = "insert into tinhthanh values('$matinh','$tentinh')";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addPROVINCE'>";
		}
	if($_REQUEST['REMOVE']=='OK')
		{	$matinh = $_REQUEST['MATINH'];
			$sql = "delete from tinhthanh where matinh like '".$matinh."'";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addPROVINCE'>";
		}
	if($_REQUEST['updatePROVINCE']=='OK')
		{	$matinh = $_POST['matinh'];
			$tentinh = $_POST['tentinh'];
			$sql = "update tinhthanh set tentinh = '$tentinh' where matinh like '$matinh'";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addPROVINCE'>";
		}
?>
<div id="page_left">
	<form method="post" action="index.php?p=addPROVINCE&status=OK">
		<table align="center">
			<tr>
				<td>Mã Tỉnh/Thành</td>
				<td><input type="text" size="30" name="matinh"/>
			<tr>
				<td>Tên Tỉnh/Thành</td>
				<td><input type="text" size="30" name="tentinh"/>
			<tr align="center">
				<td colspan="2"><input type="submit" value="Thêm"/><input type="reset" value="Nhập lại"/>
		</table>
	</form>
	<div id="edit_tab">
		<?php
			if($_REQUEST['edit']=='PROVINCE')
				{
					$matinh = $_REQUEST['maPROVINCE'];
					$kq = mysql_query("select * from tinhthanh where matinh = '$matinh'");
					$row = mysql_fetch_array($kq);
					?></br></br><form method="post" action="index.php?p=addPROVINCE&updatePROVINCE=OK">
						<table align="center">
						<tr>
							<td>Mã Tỉnh/Thành</td>
					<td><input type="text" size="30" readonly name="matinh" value="<? echo $matinh; ?>"/>
						<tr>
							<td>Tên Tỉnh/Thành</td>
							<td><input type="text" size="30" name="tentinh" value="<? echo $row['tentinh']; ?>"/>
						<tr align="center">
							<td colspan="2"><input type="submit" value="Sửa"/>
						</table>
					</form><?
				}			
		?>
	</div>
</div>
<div id="page_right">
	<pre style="overflow: scroll;height:600px">
		<table border="1" align="center" width="100%">
			<tr align="center" bgcolor="#177F05" style="font-weight:bold;font-size:14px;color:#FFFFFF">
				<td width="120px" height="30px">Mã Tỉnh/Thành</td>
				<td>Tên Tỉnh/Thành</td>
				<td>Action</td>
			<?php
				$sql = "select * from tinhthanh order by tentinh";
				$kqua = mysql_query($sql);
				while($row = mysql_fetch_array($kqua))
					{	?>
						<tr>
							<td align="center"><? echo $row['matinh']; ?>
							<td><? echo $row['tentinh']; ?>
							<td align="center"><a href="index.php?p=addPROVINCE&edit=PROVINCE&maPROVINCE=<? echo $row['matinh']; ?>"><img src="images/edit.gif"></img></a>&nbsp;<a href="javascript:popup('Có chắc chắn xóa <? echo '<b>'.$row['tentinh'].'</b>' ?> ?','tinhthanh','<? echo $row['matinh']; ?>')"><img src="images/delete.gif"></img></a>
				<?	}
			?>
		</table>
	</pre>
</div>

