<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");

	$sql1 = "select * from tinhthanh";
	$kq1 = mysql_query($sql1);
?>
<?php
	if($_REQUEST['status']=='OK')
		{	$matinh = $_POST['matinh'];
			$maquan = $_POST['maquan'];
			$tenquan = $_POST['tenquan'];
			$sql = "insert into quanhuyen values('$maquan','$tenquan','$matinh')";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addDISTRICT'>";
		}
	if($_REQUEST['REMOVE']=='OK')
		{	$maquan = $_REQUEST['MAQUAN'];
			$sql = "delete from quanhuyen where maquan like '".$maquan."'";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addDISTRICT'>";
		}
	if($_REQUEST['updateDISTRICT']=='OK')
		{	$matinh = $_POST['matinh'];
			$tenquan = $_POST['tenquan'];
			$maquan = $_POST['maquan'];
			$sql = "update quanhuyen set tenquan = '$tenquan', matinh = '$matinh' where maquan like '$maquan'";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addDISTRICT'>";
		}
?>
<div id="page_left">
	<form method="post" action="index.php?p=addDISTRICT&status=OK">
		<table align="center">
			<tr>
				<td>Tỉnh/Thành</td>
				<td>
				<select name="matinh">
					<? while($row = mysql_fetch_array($kq1))
						{	?>
							<option value="<? echo $row['matinh']; ?>"><? echo $row['tentinh'];?></option> <?
					   	}
					?>
				</select></td>
			<tr>
				<td>Mã Quận/Huyện</td>
				<td><input type="text" size="30" name="maquan"/>
			<tr>
				<td>Tên Quận/Huyện</td>
				<td><input type="text" size="30" name="tenquan"/>
			<tr align="center">
				<td colspan="2"><input type="submit" value="Thêm"/><input type="reset" value="Nhập lại"/>
		</table>
	</form>
	<div id="edit_tab">
		<?php
			if($_REQUEST['edit']=='DISTRICT')
				{
					$maquan = $_REQUEST['maDISTRICT'];
					$kq = mysql_query("select * from quanhuyen where maquan = '$maquan'");
					$row = mysql_fetch_array($kq);
					?></br></br><form method="post" action="index.php?p=addDISTRICT&updateDISTRICT=OK">
						<table align="center">
						<tr>
							<td>Mã Quận/Huyện</td>
					<td><input type="text" size="30" readonly name="maquan" value="<? echo $maquan; ?>"/>
						<tr>
							<td>Tỉnh/Thành</td>
							<td><select name="matinh">
								<? 	
									$kq1 = mysql_query("select * from tinhthanh");
									while($row1 = mysql_fetch_array($kq1))
									{
						if($row1['matinh']==$_REQUEST['maPROVINCE'])
							{?> 
						<option value="<? echo $row1['matinh']; ?>" selected="selected"><? echo $row1['tentinh']; ?></option>;
							<?}
						else
							{?> 
						<option value="<? echo $row1['matinh']; ?>"><? echo $row1['tentinh']; ?></option>;
							<?}	
								  	}
								?>
							</select></td>
						<tr>
							<td>Tên Quận/Huyện</td>
							<td><input type="text" size="30" name="tenquan" value="<? echo $row['tenquan']; ?>"/>
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
				<td width="120px" height="30px">Mã Quận/Huyện</td>
				<td>Tên Quận/Huyện</td>
				<td>Tên Tỉnh/Thành</td>
				<td>Action</td>
			<?php
				$sql = "select * from quanhuyen order by tenquan";
				$kqua = mysql_query($sql);
				while($row = mysql_fetch_array($kqua))
					{	?>
						<tr>
							<td align="center"><? echo $row['maquan']; ?>
							<td><? echo $row['tenquan']; ?>
							<td>
								<?
									$sql1 = "select tentinh from tinhthanh where matinh like '".$row['matinh']."'"; 
									$kq1 = mysql_query($sql1);
									$row1 = mysql_fetch_array($kq1);
									echo $row1['tentinh'];
								?>
							</td>
							<td align="center"><a href="index.php?p=addDISTRICT&edit=DISTRICT&maDISTRICT=<? echo $row['maquan']; ?>&maPROVINCE=<? echo $row['matinh']; ?>"><img src="images/edit.gif"></img></a>&nbsp;<a href="javascript:popup('Có chắc chắn xóa <? echo '<b>'.$row['tenquan'].'</b>' ?> ?','quanhuyen','<? echo $row['maquan']; ?>')"><img src="images/delete.gif"></img></a>
				<?	}
			?>
		</table>
	</pre>
</div>

