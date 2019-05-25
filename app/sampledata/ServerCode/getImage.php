<?PHP
error_reporting(E_WARNING);
include_once("connection.php");
$userId = $_GET['eid'];
$query = "SELECT img FROM characters WHERE id=$userId";
$result = $con->query($query);
foreach ($result as $row){
    $photo = $row;
}
header('Content-Type:image/jpeg');
echo $photo['img'];
?>
