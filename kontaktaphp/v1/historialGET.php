<?php 
	
	/*Hay algo que explicar aqui y es que esta mierda al parecer tiene una memoria que se puede saturar si le metes muchos valores
	Hasta el  momento no se de cuanto es una memoria pero segun AS es de 4mb, asi que en el limit van a tener que cambiar el valor
	para que funcione no se por que alv*/
	require_once '../DbConnect.php';
   $db = new DbConnect();   
   $con = $db->connect();
   
   $array = array();	 		
    $sql = "SELECT * FROM historialcontacto Limit 4000";
   if($resultset = mysqli_query($con, $sql)){
	while ($row = mysqli_fetch_assoc($resultset)){
		$e = array();
		$e['IDHistCont'] = $row['IDHistCont'];
		$e['nombrePrestador'] = $row['nombrePrestador'];
		$e['imagenPrestador'] = $row['imagenPrestador'];
		$e['estadoUser'] = $row['estadoUser'];
		$e['municipioUser'] = $row['municipioUser'];
		$e['sexoUser'] = $row['sexoUser'];
		$e['edadUser'] = $row['edadUser'];
		$e['IDServicio_FK'] = $row['IDServicio_FK'];
		$e['IDUsuario_FK'] = $row['IDUsuario_FK']; 		
      	$e['message'] = 'Sirve y muestra';
      array_push($array,$e);
	}
   }
   else{
      $array['message'] = "No se puede conectar";
   }
   echo json_encode($array);

?>
