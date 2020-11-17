<?php 
	
	//Aqui lo que hago es ver si ya me pasaron el correo y luego hago la sentencia, paso los datos a un array y lo regreso 
	require_once '../DbConnect.php';
   $db = new DbConnect();
   $con = $db->connect();

   $array = array();	
   if(isset($_POST['correo'])){
      $correo = $_POST['correo'];
      $sql = "SELECT * FROM usuario WHERE correo = '$correo'";
   if($resultset = mysqli_query($con, $sql)){
	while ($row = mysqli_fetch_assoc($resultset)){
		$e = array();
      $e['nombre'] = $row['nombre'];
      $e['edad'] = $row['edad'];
      $e['sexo'] = $row['sexo'];
      $e['password'] = $row['password'];
      $e['direccion'] = $row['direccion'];
      $e['estado'] = $row['estado'];
      $e['municipio'] = $row['municipio'];
      $e['imagen'] = $row['imagen'];
      $e['IDServicio_FK'] = $row['IDServicio_FK'];
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