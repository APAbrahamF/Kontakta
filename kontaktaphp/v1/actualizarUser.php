<?php 
	
	//adding dboperation file 
	require_once '../DbOperation.php';
	
	//response array 
   $response = array();
    if (isset($_POST['nombre']) && isset($_POST['edad']) && isset($_POST['sexo']) && isset($_POST['direccion']) && isset($_POST['municipio']) && isset($_POST['estado']) && isset($_POST['correo']) && isset($_POST['imagen'])){
    $db = new DbOperation(); 
    if($db->actualizeUser($_POST['nombre'],$_POST['edad'],$_POST['sexo'],$_POST['direccion'],$_POST['municipio'],$_POST['estado'],$_POST['correo'], $_POST['imagen'])){
       $response['error'] = true;
       $response['message'] = 'Se actualizaron los datos correctamente';
    }else{
       $response['error'] = false;
       $response['message'] = 'Could not add User';
    }
    // }else{
    // 	$response['error'] = true; 
    // 	$response['message'] = 'Required Parameters are missing';
    // }	
 }else{
    $response['error'] = false; 
    $response['message'] = 'Invalid Request';
 }
   //displaying the data in json
   echo json_encode($response);
?>