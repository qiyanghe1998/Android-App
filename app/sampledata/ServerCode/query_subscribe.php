<?php
error_reporting(E_WARNING);
include_once('connection.php');
$result = $con->query("select * from characters join (
    select u_id, c_id from user_character
) x on characters.c_id = x.c_id
where x.u_id = " . $_GET['u_id']
);
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

