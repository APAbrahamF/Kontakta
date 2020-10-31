<?php
include('funcionesGET.php');
//$tipo = $_GET['txtTi'];
$array = array();	
if($resultset=getSQLResultSet("SELECT * FROM usuario")){
	
	while ($row = $resultset->fetch_array(MYSQLI_NUM)){
		$e = array();
                $e['IDUsuario'] = $row[0];
                $e['nombre'] = $row[1];
                $e['edad'] = $row[2];
                $e['sexo'] = $row[3];
                $e['direccion'] = $row[4];
                array_push($array,$e);
	}
        echo json_encode($array);
}
?>
