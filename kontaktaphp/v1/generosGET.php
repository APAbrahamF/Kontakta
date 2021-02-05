<?php 
	
	//Aqui lo que hago es ver si ya me pasaron el correo y luego hago la sentencia, paso los datos a un array y lo regreso 
	require_once '../DbConnect.php';
   $db = new DbConnect();   
   $con = $db->connect();
   
   $array = array();	
   if(isset($_POST['genero'])){
      	$genero = $_POST['genero'];   		
     	$sql = "SELECT * FROM prestadorservicios WHERE	genero= '$genero'";
   if($resultset = mysqli_query($con, $sql)){
	while ($row = mysqli_fetch_assoc($resultset)){
		$e = array();
		$e['IDServicio'] = $row['IDServicio'];
		$e['nombreServicio'] = $row['nombreServicio'];
		$e['imagen'] = $row['imagen'];	
      array_push($array,$e);
	}
   }
   }
   else{
      $array['message'] = "No se puede conectar";
   }
   echo json_encode($array);

?>

