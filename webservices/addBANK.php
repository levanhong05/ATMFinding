<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");
?>
<?php
	if($_REQUEST['status']=='OK')
		{	$mabank = $_POST['mabank'];
			$tenbank = $_POST['tenbank'];
			$sql = "insert into nganhang values('$mabank','$tenbank')";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addBANK'>";
		}
	if($_REQUEST['REMOVE']=='OK')
		{	$mabank = $_REQUEST['MABANK'];
			$sql = "delete from nganhang where manganhang like '".$mabank."'";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addBANK'>";
		}
	if($_REQUEST['updateBANK']=='OK')
		{	$mabank = $_POST['mabank'];
			$tenbank = $_POST['tenbank'];
			$sql = "update nganhang set tennganhang = '$tenbank' where manganhang like '$mabank'";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addBANK'>";
		}
	
?>
<div id="page_left">
	<form method="post" action="index.php?p=addBANK&status=OK">
		<table align="center">
			<tr>
				<td>Mã Ngân Hàng</td>
				<td><input type="text" size="30" name="mabank"/>
			<tr>
				<td>Tên Ngân Hàng</td>
				<td><input type="text" size="30" name="tenbank"/>
			<tr align="center">
				<td colspan="2"><input type="submit" value="Thêm"/><input type="reset" value="Nhập lại"/>
		</table>
	</form>
	<div id="edit_tab">
		<?php
			if($_REQUEST['edit']=='BANK')
				{
					$mabank = $_REQUEST['maBANK'];
					$kq = mysql_query("select * from nganhang where manganhang = '$mabank'");
					$row = mysql_fetch_array($kq);
					?></br></br><form method="post" action="index.php?p=addBANK&updateBANK=OK">
						<table align="center">
						<tr>
							<td>Mã Ngân Hàng</td>
					<td><input type="text" size="30" readonly name="mabank" value="<? echo $mabank; ?>"/>
						<tr>
							<td>Tên Ngân Hàng</td>
							<td><input type="text" size="30" name="tenbank" value="<? echo $row['tennganhang']; ?>"/>
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
				<td width="100px" height="30px">Mã Ngân Hàng</td>
				<td>Tên Ngân Hàng</td>
				<td>Action</td>
			<?php
				$sql = "select * from nganhang order by tennganhang";
				$kqua = mysql_query($sql);
				while($row = mysql_fetch_array($kqua))
					{	?>
						<tr>
							<td align="center"><? echo $row['manganhang']; ?>
							<td><? echo $row['tennganhang']; ?>
							<td align="center"><a href="index.php?p=addBANK&edit=BANK&maBANK=<? echo $row['manganhang']; ?>"><img src="images/edit.gif"></img></a>&nbsp;<a href="javascript:popup('Có chắc chắn xóa <? echo '<b>'.$row['tennganhang'].'</b>' ?> ?','nganhang','<? echo $row['manganhang']; ?>')"><img src="images/delete.gif"></img></a>
				<?	}
			?>
		</table>
	</pre>
</div>

