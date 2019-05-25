<?php
error_reporting(E_WARNING);
include_once('connection.php');
$result = $con->query("select * from pictures where pictures.c_id = " . $_GET['c_id']);
$dataarr = array();
foreach($result as $row){
    $row['img'] = 'http://hostname/showImage.php?url=' . $row['pic'];
    $dataarr[]=$row;
}

$result=array(
    'code_1'=>1,
    'message'=>'数据返回成功',
    'data'=>$dataarr
);
//输出json
echo json_encode($result);
?>
