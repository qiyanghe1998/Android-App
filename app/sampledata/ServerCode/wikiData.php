<?php
include("connection.php");

$sqla = "SELECT * FROM characters";
$result = $con->query($sqla);
$dataarr = array();
foreach($result as $row){
    $row['img'] = 'http://hostname/showImage.php?url=' . $row['img'];
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

