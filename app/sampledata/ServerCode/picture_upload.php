<?php
header('Content-type: application/json;charset=utf-8');
include_once("connection.php");
if(empty($_FILES)) die('{"status":0, "msg": "wrong upload."}');
$dirPath = 'path';
if(!is_dir($dirPath)){
    @mkdir($dirPath);
    echo "no such directory.";
}
$count = count($_FILES);
if($count < 1){
    die('{"status":0, "msg": "no file upload."}');
}
$success = $failure = 0;
foreach ($_FILES as $key => $value) {
    $tmp = $value['name'];
    $tmpName = $value['tmp_name'];
    // the file uploaded will be put in a php temp directory, we call
    // function to move it to the target directory
//    echo $tmp;
    $tar_path = $dirPath.date('YmdHis').'_'.$tmp;
    echo $tar_path;
    echo $tmp; 
    if(move_uploaded_file($tmpName, $tar_path)){
	if(isset($_POST['u_id']) && isset($_POST['c_name'])){
	    $u_id = $_POST['u_id'];
	    $c_name = $_POST['c_name'];
	    $query = "select c_id from characters where cha_name = '" . $c_name . "'";
	    $result = $con->query($query);
	    $con->query("update characters set version = version + 1 where cha_name = '" . $c_name . "'");
	}
	foreach ($result as $row){
    	    $c_id = $row['c_id'];
            echo $row;
	}
	echo $c_id;
        $query = "insert into pictures(pic, c_id, u_id) values('$tar_path', $c_id, $u_id)";
        $result = $con->query($query);
        $success++;
    }else{
        $failure++;
    }
    $arr['status'] = 1; 
    $arr['msg'] = 'upload success';
    $arr['success'] = $success;
    $arr['failure'] = $failure;
    echo json_encode($arr);
}
?>
