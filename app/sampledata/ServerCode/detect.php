<?php
error_reporting(E_WARNING);
include_once('connection.php');
$result = $con->query("select * from user_character where u_id = " . $_GET['u_id']);
$flag = 0;
$u_id = $_GET['u_id'];
foreach($result as $row){
    $ver = $row['version'];
    $c_id = $row['c_id'];
    $sub_result = $con->query("select * from characters where c_id = $c_id"); 
    foreach($sub_result as $sub_row){
        $sub_ver = $sub_row['version'];
    }
    if($sub_ver > $ver){
	$con->query("update user_character set version = $sub_ver where u_id = $u_id and c_id = $c_id");
	
        $flag = 1;
    }
}
if($flag == 0){
    echo "updated";
}else{
    echo "not_updated";
}
?>

