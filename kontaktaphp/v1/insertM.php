<?php 
	
	//adding dboperation file 
	require_once '../DbOperation.php';
	
	//response array 
    $response = array(); 
    if (isset($_POST['nombre']) && isset($_POST['imagen']) && isset($_POST['integrantes']) && isset($_POST['descripcion']) && isset($_POST['genero']) && isset($_POST['youtube']) && isset($_POST['instagram']) && isset($_POST['facebook']) && isset($_POST['twitter'])) {
        $db = new DbOperation(); 
        if($db->insertServ($_POST['nombre'], $_POST['imagen'], $_POST['integrantes'],$_POST['descripcion'],$_POST['genero'],$_POST['youtube'],$_POST['instagram'],$_POST['facebook'],$_POST['twitter'])){
           $response['error'] = false;
           $response['message'] = 'Agregado correctamente';
        }else{
           $response['error'] = true;
           $response['message'] = 'Revise que su correo no este registrado o que los datos esten correctos';
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