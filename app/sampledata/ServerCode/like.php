<?php
error_reporting(E_WARNING);
include_once("connection.php");
if(isset($_GET['pic_id']) &&
isset($_GET['u_id'])){
    $query = "insert into likes(u_id, pic_id) values(" .$_GET['u_id']. ", " .$_GET['pic_id']. ")";
    echo $query;
    $result = $con->query($query);
}
?>
