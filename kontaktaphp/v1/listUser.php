<?php 
	
	//adding dboperation file 
	require_once '../DbOperation.php';
	
	//response array 
	$response = array(); 
   
   //if it is getUser that means we are fetching the records
	$db = new DbOperation();
   $ListComment = $db->getUser();
   if(count($ListComment)<=0){
      $response['error'] = true; 
      $response['message'] = 'No se encontró nada en la base de datos.';
   }else{
      $response['error'] = false; 
      $response['artists'] = $ListComment;
   }
	
	//displaying the data in json 
   echo json_encode($response);
?>