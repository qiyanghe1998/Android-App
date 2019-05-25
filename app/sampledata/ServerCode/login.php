<?PHP
error_reporting(E_WARNING);
include_once("connection.php");

if(	isset($_POST['txtEmail']) &&
	isset($_POST['txtPassword']) 
	)
{
	$result = $con->query("SELECT count(*) FROM UserManager ".
			" WHERE email = '". $_POST['txtEmail'] ."' AND " .
			" password = '" . $_POST['txtPassword']. "'");

	if($result->fetchColumn() > 0)
	{
		$result = $con->query("SELECT id, name FROM UserManager ".
			" WHERE email = '". $_POST['txtEmail'] . "'");
		echo "login_success;";
		foreach ($result as $row) {
			echo $row['name'] .';'. $row['id'];
		}
	}
	else{
		echo "login_falied";
	}	
}				
?>

