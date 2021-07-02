<?php 
	
	//adding dboperation file 
	require_once '../DbOperation.php';
	
	//response array 
   $response = array(); 
	if (empty($_POST['nombre']) && empty($_POST['edad']) && empty($_POST['sexo']) && empty($_POST['direccion']) && empty($_POST['municipio']) && empty($_POST['estado']) && empty($_POST['correo']) && empty($_POST['password']) && empty($_POST['imagen'])) {
      $response['error'] = true; 
      $response['message'] = 'Campos Vacios al enviar los datos';

   } else {
      if (isset($_POST['nombre']) && isset($_POST['edad']) && isset($_POST['sexo']) && isset($_POST['direccion']) && isset($_POST['municipio']) && isset($_POST['estado']) && isset($_POST['correo']) && isset($_POST['password']) && isset($_POST['imagen'])) {
         $db = new DbOperation(); 
         if($db->insertUser($_GET['nombre'], $_GET['edad'],$_GET['sexo'],$_GET['direccion'],$_GET['municipio'],$_GET['estado'],$_GET['correo'],$_GET['password'], $_GET['imagen'])){
            $response['error'] = false;
            $response['message'] = 'User added successfully';
         }else{
            $response['error'] = true;
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

   } 
	//displaying the data in json 
   echo json_encode($response);
?>