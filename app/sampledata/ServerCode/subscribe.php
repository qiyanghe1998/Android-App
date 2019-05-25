<?php
error_reporting(E_WARNING);
include_once("connection.php");
if(isset($_GET['c_name']) &&
    isset($_GET['u_id'])){
    $result = $con->query( "select * from characters where cha_name = '" . $_GET['c_name'] . "'");
    foreach($result as $row){
	$c_id = $row['c_id'];
	echo $c_id;
	echo $c_id;
    }
    $query = "insert into user_character(u_id, c_id) values(" .$_GET['u_id']. ", " .$c_id. ")";
    echo $query;
    $result = $con->query($query);
}
?>
