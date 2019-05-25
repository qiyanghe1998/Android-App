<?php
error_reporting(E_WARNING);
include_once("connection.php");
$dataarr = array();
if(isset($_GET['c_id'])){
    $c_id = $_GET['c_id'];
    $outer_result = $con->query("select * from pictures where c_id = $c_id");
    foreach($outer_result as $outer_row){
        $pic_id = $outer_row['p_id'];
        $query = "select count(*) as total from likes where pic_id = $pic_id";
        $result = $con->query($query);
        foreach($result as $row){
            $ret = $row['total'];
        }
        $item = array(
            "pic_id"=>$pic_id,
            "like_num"=>$ret
        );
        $dataarr[] = $item;
    }
}
echo json_encode($dataarr);
?>
