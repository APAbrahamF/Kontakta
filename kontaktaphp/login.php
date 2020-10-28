<?php 
	
	//adding dboperation file 
	require_once '../DbOperation.php';
	
	//response array 
    $response = array();
    if (isset($_POST['correo']) && isset($_POST['password']) ) {
        $db = new DbOperation(); 
        if($db->login($_POST['correo'], $_POST['password'])){
           $response['error'] = true;
           $response['message'] = 'Bienvenido';
        }else{
           $response['error'] = false;
           $response['message'] = 'Los datos de ingreso son incorrectos';
        }
        // }else{
        // 	$response['error'] = true; 
        // 	$response['message'] = 'Required Parameters are missing';
        // }	
     }else{
        $response['error'] = true; 
        $response['message'] = 'Invalid Request';
     }
     
   //displaying the data in json 
   echo json_encode($response);
?>