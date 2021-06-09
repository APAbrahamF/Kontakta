<?php 
  
  //adding dboperation file 
  require_once '../DbOperation.php';
  
  //response array 
    $response = array(); 
    if (isset($_POST['nombrePrestador']) && isset($_POST['imagenPrestador']) && isset($_POST['estadoUser']) && isset($_POST['municipioUser']) && isset($_POST['sexoUser']) && isset($_POST['edadUser']) && isset($_POST['IDServicio_FK']) && isset($_POST['IDUsuario_FK'])) {
        $db = new DbOperation(); 
        if($db->insertHistorial($_POST['nombrePrestador'], $_POST['imagenPrestador'], $_POST['estadoUser'], $_POST['municipioUser'], $_POST['sexoUser'], $_POST['edadUser'], $_POST['IDServicio_FK'], $_POST['IDUsuario_FK'])){
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