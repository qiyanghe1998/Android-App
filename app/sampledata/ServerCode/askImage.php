<?php
include("connection.php");

$sqla = "SELECT pic FROM pictures where c_id = " . $_GET['c_id'];
$result = $con->query($sqla);
$dataarr = array();
foreach($result as $row){
    $row['pic'] = 'http://127.0.0.1/showImage.php?url=' . $row['pic'];
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

