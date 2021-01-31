<?php
include('funcionesGET.php');
//$tipo = $_GET['txtTi'];
$array = array();	
if($resultset=getSQLResultSet("SELECT * FROM prestadorservicios")){
	
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
?>
