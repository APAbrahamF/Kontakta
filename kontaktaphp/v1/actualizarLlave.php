<?php 
  
  //adding dboperation file 
  require_once '../DbOperation.php';
  
  //response array 
   $response = array();
    if (isset($_POST['IDUsuario']) && isset($_POST['IDServicio_FK'])){
    $db = new DbOperation(); 
    //if($db->actualizePass($_POST['correo'], $_POST['password'])){
    if($db->actualizeLlave($_POST['IDUsuario'], $_POST['IDServicio_FK'])){
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