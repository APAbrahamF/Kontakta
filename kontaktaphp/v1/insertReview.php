<?php 
  
  //adding dboperation file 
  require_once '../DbOperation.php';
  
  //response array 
    $response = array(); 
    if (isset($_POST['comentario']) && isset($_POST['valoracion'])) {
        $db = new DbOperation(); 
        if($db->insertReview($_POST['comentario'], $_POST['valoracion'])){
           $response['error'] = false;
           $response['message'] = 'Agregado correctamente';
        }else{
           $response['error'] = true;
           $response['message'] = 'Revise que su correo no este registrado o que los datos esten correctos';
        }
        // }else{
        //  $response['error'] = true; 
        //  $response['message'] = 'Required Parameters are missing';
        // }  
     }else{
        $response['error'] = false; 
        $response['message'] = 'Invalid Request';
     }
  //displaying the data in json 
   echo json_encode($response);
?>