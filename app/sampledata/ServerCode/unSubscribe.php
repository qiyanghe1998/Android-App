<?php
error_reporting(E_WARNING);
include_once("connection.php");
if(isset($_GET['c_name']) &&
    isset($_GET['u_id'])){
    $result = $con->query( "select * from characters where cha_name = '" . $_GET['c_name'] . "'");
    foreach($result as $row){
        $c_id = $row['c_id'];
        echo $c_id;
    }
    $query = "delete from user_character where u_id = " .$_GET['u_id']. " and c_id = " .$c_id;
    echo $query;
    $result = $con->query($query);
}
?>

