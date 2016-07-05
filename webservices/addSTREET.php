<?php
	require 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");
?>
<?php

	function cvd($inputString) { 
        $trans = array ( 
                        'á' => 'a', 'à' => 'a', 'ả' => 'a', 'ã' => 'a', 'ạ' => 'a',  
                        'Á' => 'A', 'À' => 'A', 'Ả' => 'A', 'Ã' => 'A', 'Ạ' => 'A', 
                         
            'â' => 'a', 'ấ' => 'a', 'ầ' => 'a', 'ẩ' => 'a', 'ẫ' => 'a', 'ậ' => 'a', 
            'Â' => 'A', 'Ấ' => 'A', 'À' => 'A', 'Ẩ' => 'A', 'Ẫ' => 'A', 'Ậ' => 'A', 
             
            'ă' => 'a', 'ắ' => 'a', 'ằ' => 'a', 'ẳ' => 'a', 'ẵ' => 'a', 'ặ' => 'a', 
            'Ă' => 'A', 'Ắ' => 'A', 'Ằ' => 'A', 'Ẳ' => 'A', 'Ẵ' => 'A', 'Ặ' => 'A', 
             
                        'é' => 'e', 'è' => 'e', 'ẻ' => 'e', 'ẽ' => 'e', 'ẹ' => 'e', 
                        'É' => 'E', 'È' => 'E', 'Ẻ' => 'E', 'Ẽ' => 'E', 'Ẹ' => 'E', 
                         
            'ê' => 'e', 'ế' => 'e', 'ề' => 'e', 'ể' => 'e', 'ễ' => 'e', 'ệ' => 'e',  
            'Ê' => 'E', 'Ế' => 'E', 'Ề' => 'E', 'Ể' => 'E', 'Ễ' => 'E', 'Ệ' => 'E', 
             
                        'í' => 'i', 'ì' => 'i', 'ỉ' => 'i', 'ĩ' => 'i', 'ị' => 'i',  
                        'Í' => 'I', 'Ì' => 'I', 'Ỉ' => 'I', 'Ĩ' => 'I', 'Ị' => 'I', 
                                     
                        'ó' => 'o', 'ò' => 'o', 'ỏ' => 'o', 'õ' => 'o', 'ọ' => 'o',  
                        'Ó' => 'O', 'Ò' => 'O', 'Ỏ' => 'O', 'Õ' => 'O', 'Ọ' => 'O', 
                         
            'ơ' => 'o', 'ớ' => 'o', 'ờ' => 'o', 'ở' => 'o', 'ỡ' => 'o', 'ợ' => 'o', 
            'Ơ' => 'O', 'Ớ' => 'O', 'Ờ' => 'O', 'Ở' => 'O', 'Ỡ' => 'O', 'Ợ' => 'O', 
             
            'ô' => 'o', 'ố' => 'o', 'ồ' => 'o', 'ổ' => 'o', 'ỗ' => 'o', 'ộ' => 'o',  
            'Ô' => 'O', 'Ố' => 'O', 'Ồ' => 'O', 'Ổ' => 'O', 'Ỗ' => 'O', 'Ộ' => 'O', 
             
                        'ú' => 'u', 'ù' => 'u', 'ủ' => 'u', 'ũ' => 'u', 'ụ' => 'u',  
                        'Ú' => 'U', 'Ù' => 'U', 'Ủ' => 'U', 'Ũ' => 'U', 'Ụ' => 'U', 
                         
            'ư' => 'u', 'ứ' => 'u', 'ừ' => 'u', 'ử' => 'u', 'ữ' => 'u', 'ự' => 'u', 
            'Ư' => 'U', 'Ứ' => 'U', 'Ừ' => 'U', 'Ử' => 'U', 'Ữ' => 'U', 'Ự' => 'U', 
                         
                        'ý' => 'y', 'ỳ' => 'y', 'ỷ' => 'y', 'ỹ' => 'y', 'ỵ' => 'y',  
                        'Ý' => 'Y', 'Ỳ' => 'Y', 'Ỷ' => 'Y', 'Ỹ' => 'Y', 'Ỵ' => 'Y', 
                         
            'đ' => 'd', 
            'Đ' => 'D', 
            
            ' ' => '-'  
            ); 
   
        return strtr ( $inputString, $trans ); 
    }  
	
	if($_REQUEST['status']=='OK')
		{	$tenduong = $_POST['tenduong'];
			$kodau = cvd($tenduong);
			$sql = "insert into dsduong values('$tenduong','$kodau')";
			mysql_query($sql);
			echo "<meta http-equiv='refresh' content='0;url=index.php?p=addSTREET'>";
		}
?>
<div id="page_left">
	<form method="post" action="index.php?p=addSTREET&status=OK">
		<table align="center">
			<tr>
				<td>Tên đường</td>
				<td><input type="text" size="30" name="tenduong"/>
			<tr align="center">
				<td colspan="2"><input type="submit" value="Thêm"/><input type="reset" value="Nhập lại"/>
		</table>
	</form>
</div>
<div id="page_right">
	<pre style="overflow: scroll;height:600px">
		<table border="1" align="center" width="100%">
			<tr align="center" bgcolor="#177F05" style="font-weight:bold;font-size:14px;color:#FFFFFF">
				<td height="30px">Tên đường có dấu</td>
				<td>Tên đường không dấu</td>
				<td>Action</td>
			<?php
				$sql = "select * from dsduong order by khongdau";
				$kqua = mysql_query($sql);
				while($row = mysql_fetch_array($kqua))
					{	?>
						<tr>
							<td><? echo $row['codau']; ?>
							<td><? echo $row['khongdau']; ?>
							<td align="center"><img src="images/edit.gif"></img>&nbsp;<img src="images/delete.gif"></img>
				<?	}
			?>
		</table>
	</pre>
</div>

