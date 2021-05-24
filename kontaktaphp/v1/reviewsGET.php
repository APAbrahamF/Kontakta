<?php
include('funcionesGET.php');
//$tipo = $_GET['txtTi'];
$array = array();	
if($resultset=getSQLResultSet("SELECT * FROM review")){
	
	while ($row = $resultset->fetch_array(MYSQLI_NUM)){
		$e = array();
                $e['IDReview'] = $row[0];
                $e['comentario'] = $row[1];
                $e['valoracion'] = $row[2];
                array_push($array,$e);
	}
        echo json_encode($array);
}
?>
