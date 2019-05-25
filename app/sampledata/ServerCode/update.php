<?PHP
error_reporting(E_WARNING);
include_once("connection.php");

if(	isset($_POST['txtUsername']) &&
    isset($_POST['txtPassword']) &&
    isset($_POST['txtEmail'])
)
{
    $result = $con->query("SELECT count(*) FROM UserManager ".
        " WHERE email = '". $_POST['txtEmail'] . "'");
    if($result->fetchColumn() > 0){
        $result = $con->exec("UPDATE UserManager" .
            " set name='" . $_POST['txtUsername']
            . "', password='" . $_POST['txtPassword'] . "' where email='" .
            $_POST['txtEmail'] . "'");
        echo "update_success";
    }else{
        echo "update_failed";
    }
}
?>