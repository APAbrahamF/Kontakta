<?php 
	
	//adding dboperation file 
	require_once '../DbOperation.php';
	
	//response array 
   $response = array();
    if (isset($_POST['IDServicio']) && isset($_POST['nombreServicio']) && isset($_POST['imagen']) && isset($_POST['descripcion']) && isset($_POST['genero']) && isset($_POST['integrantes']) && isset($_POST['facebook']) && isset($_POST['youtube']) && isset($_POST['instagram']) && isset($_POST['twitter'])){
    $db = new DbOperation(); 
    if($db->actualizeServ($_POST['IDServicio'],$_POST['nombreServicio'],$_POST['imagen'],$_POST['descripcion'],$_POST['genero'],$_POST['integrantes'],$_POST['facebook'],$_POST['youtube'],$_POST['instagram'],$_POST['twitter'])){
       $response['error'] = true;
       $response['message'] = 'Se actualizaron los datos correctamente';
    }else{
       $response['error'] = false;
       $response['message'] = 'Could not add User';
    }    	
 }else{
    $response['error'] = false; 
    $response['message'] = 'Invalid Request';
 }
   //displaying the data in json
   echo json_encode($response);
?>