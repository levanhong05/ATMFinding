<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");

	$sql1 = "select * from nganhang";
	$kq1 = mysql_query($sql1);
?>
<?php
	if($_REQUEST['status']=='OK')
		{	$tendiadiem = $_POST['tendiadiem'];
			$diachi = $_POST['diachi'];
			$maquan = $_POST['maquan'];
			$manganhang = $_POST['manganhang'];
			
			$sql = "select max(madiadiem) as diadiem_id from diadiem";
			$kq = mysql_query($sql);
			$row = mysql_fetch_array($kq);
			$madiadiem = $row['diadiem_id'] + 1;

			$sql = "insert into diadiem values('$madiadiem','$tendiadiem','$diachi','$maquan','$manganhang','','')";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addLOCATION'>";
		}
	if($_REQUEST['REMOVE']=='OK')
		{	$madiadiem = $_REQUEST['MADIADIEM'];
			$sql = "delete from diadiem where madiadiem like '".$madiadiem."'";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addLOCATION'>";
		}
?>
<div id="page_left">
	<form method="post" action="index.php?p=addLOCATION&status=OK">
		<table align="center">
			<tr>
				<td>Ngân hàng</td>
				<td><select name="manganhang" style="width:220px" id="tinh">
					<? while($row1 = mysql_fetch_array($kq1))
						{	?>
							<option value="<? echo $row1['manganhang']; ?>"><? echo $row1['tennganhang'];?></option> <?
					   	}
					?>
				</select></td>
                        <tr>
                                <td>Tỉnh/Thành</td>
                                <td><select name="matinh" id="matinh">
					<? 	
						$sql3 = "select matinh, tentinh from tinhthanh";
						$kq3 = mysql_query($sql3);
						while($row3 = mysql_fetch_array($kq3))
						{	?>
							<option value="<? echo $row3['matinh']; ?>"><? echo $row3['tentinh'];?></option> 
					<?   	}
					?>
                                </select></td>
			<tr>
				<td>Quận/Huyện</td>
				<td><span id="shuyen"></span></td>
			<tr>
				<td>Tên Địa điểm</td>
				<td><input type="text" size="30" name="tendiadiem"/>
			<tr>
				<td>Địa chỉ</td>
				<td><input type="text" size="30" name="diachi"/>
			<tr align="center">
				<td colspan="2"><input type="submit" value="Thêm"/><input type="reset" value="Nhập lại"/>
		</table>
	</form>
</div>
<div id="page_right">
	<pre style="overflow: scroll;height:600px">
		<table border="1" align="center">
			<tr align="center" bgcolor="#177F05" style="font-weight:bold;font-size:14px;color:#FFFFFF">
				<td width="100px" height="30px">Mã Địa điểm</td>
				<td>Tên Địa điểm</td>
				<td>Địa chỉ</td>
				<td>Ngân hàng</td>
				<td>Tỉnh/Thành</td>
				<td>Action</td>
			<?php
				$sql = "select * from diadiem order by madiadiem";
				$kqua = mysql_query($sql);
				while($row = mysql_fetch_array($kqua))
					{	?>
						<tr>
							<td align="center"><? echo $row['madiadiem']; ?>
							<td><? echo $row['tendiadiem']; ?>
							<td><? echo $row['diachi']; ?>
							<td>
								<?
									$sql3 = "select tennganhang from nganhang where manganhang like '".$row['manganhang']."'"; 
									$kq3 = mysql_query($sql3);
									$row3 = mysql_fetch_array($kq3);
									echo $row3['tennganhang'];
								?>
							</td>
							<td>
								<?
									$sql4 = "select tentinh from tinhthanh inner join quanhuyen where maquan like '".$row['maquan']."'"; 
									$kq4 = mysql_query($sql4);
									$row4 = mysql_fetch_array($kq4);
									echo $row4['tentinh'];
								?>
							</td>							
							<td align="center"><img src="images/edit.gif"></img>&nbsp;<a href="javascript:popup('Có chắc chắn xóa địa điểm <? echo '<b>'.$row['tendiadiem'].'</b>' ?> ?','diadiem','<? echo $row['madiadiem']; ?>')"><img src="images/delete.gif"></img></a>
				<?	}
			?>
		</table>
	</pre>
</div>

