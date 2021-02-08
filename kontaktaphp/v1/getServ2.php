<?php 
	
	//Aqui lo que hago es ver si ya me pasaron el correo y luego hago la sentencia, paso los datos a un array y lo regreso 
	require_once '../DbConnect.php';
   $db = new DbConnect();   
   $con = $db->connect();
   
   $array = array();	
   if(isset($_POST['IDServ'])){
      	$IDServ = $_POST['IDServ'];   		
     	$sql = "SELECT * FROM prestadorservicios WHERE 	IDUsuario_FK= '$IDServ'";
   if($resultset = mysqli_query($con, $sql)){
	while ($row = mysqli_fetch_assoc($resultset)){
		$e = array();
		$e['IDServicio'] = $row['IDServicio'];
		$e['nombreServicio'] = $row['nombreServicio'];
		$e['imagen'] = $row['imagen'];
		$e['integrantes'] = $row['integrantes'];
		$e['descripcion'] = $row['descripcion'];
		$e['genero'] = $row['genero'];
		$e['youtube'] = $row['youtube'];
		$e['instagram'] = $row['instagram'];
		$e['facebook'] = $row['facebook'];
		$e['twitter'] = $row['twitter']; 		
      	$e['message'] = 'Sirve y muestra';
      array_push($array,$e);
	}
   }
   }
   else{
      $array['message'] = "No se puede conectar";
   }
   echo json_encode($array);

?>

