<?php
$url = $_GET['url'];
$img = file_get_contents($url,true);
header("Content-Type: image/jpeg;text/html; charset=utf-8");
echo $img;
exit;
?>