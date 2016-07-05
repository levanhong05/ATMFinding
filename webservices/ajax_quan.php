<?php
      ob_start();
      include 'config.php';
      @mysql_connect("{$db_host}", "{$db_user}", "{$db_pass}"); 
      @mysql_select_db("{$db_name}"); 

      $matinh = $_GET['id_tinh'];
      $sql2 = "select maquan, tenquan from quanhuyen where matinh like '$matinh'";
      $kq2 = mysql_query($sql2);
?>
      <select name="maquan" id="maquan">
         <?      while($row2 = mysql_fetch_array($kq2))
                    {  ?>
                       <option value="<? echo $row2['maquan']; ?>"><? echo $row2['tenquan'];?></option>;
                 <? }
         ?>
      </select>
