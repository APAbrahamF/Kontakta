<?php
require_once '../DbConnect.php';
   $db = new DbConnect();
   $con = $db->connect();
   $array = array();
   if(isset($_POST['entrada'])){
    $entrada = $_POST['entrada'];
    $sql = "SELECT * FROM `prestadorservicios` JOIN `usuario` ON prestadorservicios.IDUsuario_FK = usuario.IDUsuario WHERE `estado` = '$entrada'";
    if($resultset = mysqli_query($con, $sql)){
	
	        while ($row = $resultset->fetch_array(MYSQLI_NUM)){
		    $e = array();
                $e['IDServicio'] = $row[0];
                $e['nombreServicio'] = $row[1];
                $e['imagen'] = $row[2];
                $e['integrantes'] = $row[3];
                $e['descripcion'] = $row[4];
		        $e['genero'] = $row[5];
                array_push($array,$e);
	        }
        echo json_encode($array);
}
}
?>
