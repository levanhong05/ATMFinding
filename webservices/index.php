<?php
ob_start();
	include 'config.php';
	@mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
	@mysql_select_db("{$db_name}");

?>

<html>
	<head>
		<title>PHP WEBSERVICES - Lê Văn Hồng 2013</title>
	    	<meta http-equiv="content-type" content="text/html; charset=utf-8" />
        	<link rel="stylesheet" type="text/css" href="index.css" />

                <script language="javascript" src="select.js"></script>
                <script language="javascript">
                        $(document).ready(function() 
                            {     $('#matinh').change(function() 
                                      {    giatri = this.value;
                                           $('#shuyen').load('ajax_quan.php?id_tinh='+ giatri);
                                      });
                            });
                       	function abc() 
                            {     
                                   $('#shuyen').load('ajax_quan.php?id_tinh='+ dn);
                            };

	
                </script>
		<script type="text/javascript">
			function popupMES(message){
				var maskHeight = $(document).height();  
				var maskWidth = $(window).width();
				var dialogTop =  (maskHeight/3) - ($('#dialog-box1').height());  
				var dialogLeft = (maskWidth/2) - ($('#dialog-box1').width()/2); 
	
				$('#dialog-overlay').css({height:maskHeight, width:maskWidth}).show();
				$('#dialog-box1').css({top:dialogTop, left:dialogLeft}).show();
				$('#dialog-message1').html(message);
				$(document).ready(function () 
					{
						$('#dialog-button').click(function () 
							{		
								$('#dialog-overlay, #dialog-box1').hide();		
								return false;
							});
					});

			}
			function popup(message,loai,ma) {
				var maskHeight = $(document).height();  
				var maskWidth = $(window).width();
				var dialogTop =  (maskHeight/3) - ($('#dialog-box').height());  
				var dialogLeft = (maskWidth/2) - ($('#dialog-box').width()/2); 
	
				$('#dialog-overlay').css({height:maskHeight, width:maskWidth}).show();
				$('#dialog-box').css({top:dialogTop, left:dialogLeft}).show();
				$('#dialog-message').html(message);

				$(document).ready(function () 
					{
						$('#button-cancel').click(function () 
							{		
								$('#dialog-overlay, #dialog-box').hide();		
								return false;
							});
						$('#button-ok').click(function () 
							{		
							if(loai == 'nganhang')
								{    window.location.href='index.php?p=addBANK&REMOVE=OK&MABANK='+''+ma;	}
							if(loai == 'quanhuyen')
								{    window.location.href='index.php?p=addDISTRICT&REMOVE=OK&MAQUAN='+''+ma;	}
							if(loai == 'tinhthanh')
								{   window.location.href='index.php?p=addPROVINCE&REMOVE=OK&MATINH='+''+ma;	}
							if(loai == 'diadiem')
								{   window.location.href='index.php?p=addLOCATION&REMOVE=OK&MADIADIEM='+''+ma;	}
							if(loai == 'gopy')
								{   window.location.href='index.php?p=viewCONTACT&REMOVE=OK&DATE='+''+ma;	}	
						});	
					});
			}

		</script>
	</head>
	<body onLoad="abc()">
		<?php include'login.php'; ?>
		<div id="dialog-overlay"></div>
		<div id="dialog-box">
			<div class="dialog-content">
				<div id="dialog-message"></div>
				<div class="dialog-button">
					<a href="#" id="button-ok" class="button">Đồng Ý</a>
					<a href="#" id="button-cancel" class="button">Bỏ Qua</a>
				</div>
			</div>
		</div>

		<div id="dialog-box1">
			<div class="dialog-content">
				<div id="dialog-message1"></div>
				<a href="#" id="dialog-button" class="button1">Close</a>
			</div>
		</div>	


	
	</body>

</html>
<? ob_flush(); ?>
