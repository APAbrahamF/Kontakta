<?php 
	
	//Aqui lo que hago es ver si ya me pasaron el correo y luego hago la sentencia, paso los datos a un array y lo regreso 
	require_once '../DbConnect.php';
   $db = new DbConnect();
   $con = $db->connect();

   $array = array();	
   if(isset($_POST['IDUserFK'])){
      $IDUserFK = $_POST['IDUserFK'];
      $sql = "SELECT * FROM usuario WHERE IDUsuario = '$IDUserFK'";
   if($resultset = mysqli_query($con, $sql)){
	while ($row = mysqli_fetch_assoc($resultset)){
      $e = array();
      $e['nombre'] = $row['nombre'];
      $e['imagen'] = $row['imagen'];
      $e['message'] = 'Buena';
      array_push($array,$e);
	}
   }
   }
   else{
      $array['message'] = "No se puede conectar";
   }
   echo json_encode($array);

?>