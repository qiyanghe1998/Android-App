<?PHP
error_reporting(E_WARNING);
include_once("connection.php");

if(	isset($_POST['txtUsername']) &&
    isset($_POST['txtPassword']) &&
    isset($_POST['txtEmail'])
)
{
    try {
        $result = $con->exec("INSERT INTO UserManager" .
            " (name, email, password) values ('" . $_POST['txtUsername'] .
            "','" . $_POST['txtEmail'] . "','" . $_POST['txtPassword'] . "')");
        echo "register_success";
    } catch (PDOException $e) {
        echo "register_failed";
    }

}
?>