<?PHP
$server_name = "mysql:host=127.0.0.1;dbname=animation";
//animation
$user_name = "name";
$password = "password";
$con = new PDO($server_name, $user_name, $password);
$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
$con->query("SET NAMES 'utf8'");
?>
