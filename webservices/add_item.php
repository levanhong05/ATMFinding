		<div id="menu" >
			<ul>
				<li><a href="index.php?p=HOME">HOME</a></li>
				<li><a href="index.php?p=addBANK">Thêm Ngân Hàng</a></li>
				<li><a href="index.php?p=addSTREET">Thêm Tên Đường</a></li>
				<li><a href="index.php?p=addPROVINCE">Thêm Tỉnh Thành</a></li>
				<li><a href="index.php?p=addDISTRICT">Thêm Quận Huyện</a></li>
				<li><a href="index.php?p=addLOCATION">Thêm Địa Điểm</a></li>
				<li><a href="index.php?p=viewCONTACT">Xem Liên Hệ</a></li>
				<li><a href="index.php?action=logout"><img src="images/out1.png"></img></a></li>
			</ul>
		</div>
		<hr>
		<div id="page">
			<?php
				if(!isset($_REQUEST['p']))
					include 'hello.php';
				if(isset($_REQUEST['p']))
					{
						if($_REQUEST['p']=='HOME')
							include 'hello.php';
						if($_REQUEST['p']=='addBANK')
							include 'addBANK.php';
						if($_REQUEST['p']=='addPROVINCE')
							include 'addPROVINCE.php';
						if($_REQUEST['p']=='addDISTRICT')
							include 'addDISTRICT.php';
						if($_REQUEST['p']=='addLOCATION')
							include 'addLOCATION.php';
						if($_REQUEST['p']=='viewCONTACT')
							include 'viewCONTACT.php';
						if($_REQUEST['p']=='addSTREET')
							include 'addSTREET.php';
					}
			?>
		</div>	


