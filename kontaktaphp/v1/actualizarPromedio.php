<?php 
  
  //adding dboperation file 
  require_once '../DbOperation.php';
  
  //response array 
   $response = array();
    if (isset($_POST['IDServicio']) && isset($_POST['promedio'])){
    $db = new DbOperation(); 
    //if($db->actualizePass($_POST['correo'], $_POST['password'])){
    if($db->actualizePromedio($_POST['IDServicio'], $_POST['promedio'])){
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