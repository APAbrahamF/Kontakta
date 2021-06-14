<?php 
	
	require_once '../DbConnect.php';
   $db = new DbConnect();   
   $con = $db->connect();
   
   $array = array();	
   if(isset($_POST['IDUsuario_FK'])){
      	$IDUsuario_FK = $_POST['IDUsuario_FK'];   		
     	$sql = "SELECT * FROM historialcontacto WHERE	IDUsuario_FK= '$IDUsuario_FK' ORDER BY IDHistCont DESC";
   if($resultset = mysqli_query($con, $sql)){
	while ($row = mysqli_fetch_assoc($resultset)){
		$e = array();
		$e['IDHistCont'] = $row['IDHistCont'];
		$e['nombrePrestador'] = $row['nombrePrestador'];
		$e['imagenPrestador'] = $row['imagenPrestador'];	
		$e['IDServicio_FK'] = $row['IDServicio_FK'];
      array_push($array,$e);
	}
   }
   }
   else{
      $array['message'] = "No se puede conectar";
   }
   echo json_encode($array);

?>

