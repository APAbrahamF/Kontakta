<?php
 
class DbOperation
{
    private $con;
 
    function __construct()
    {
        require_once dirname(__FILE__) . '/DbConnect.php';
        $db = new DbConnect();
        $this->con = $db->connect();
    }
 
	//adding a record to database 
	public function insertUser($nombre, $edad, $sexo, $direccion, $municipio, $estado, $correo, $password){
		$sql = "SELECT * FROM usuario WHERE correo = '$correo'";
		$result = mysqli_query($this->con, $sql);
		if($result->num_rows == 0)
		{
		$stmt = $this->con->prepare("INSERT INTO usuario(nombre, edad, sexo, direccion, municipio, estado, correo, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		$stmt->bind_param("ssssssss", $nombre, $edad, $sexo, $direccion, $municipio, $estado, $correo, $password);
		if($stmt->execute())
			return true; 
		return false; 
		}
		else
		{
			return false;
		}
	}
	public function insertServ($nombre, $integrantes, $descripcion, $genero, $youtube, $instagram, $facebook, $twitter, $correo){
		$sql = "SELECT * FROM usuario WHERE correo = '$correo'";
		$result = mysqli_query($this->con, $sql);
		$row = mysqli_fetch_assoc($result);
		$id = $row['IDUsuario'];
		$stmt = $this->con->prepare("INSERT INTO prestadorservicios(nombreServicio, integrantes, descripcion, genero, youtube, instagram, facebook, twitter, IDUsuario_FK) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		$stmt->bind_param("sssssssss", $nombre, $integrantes, $descripcion, $genero, $youtube, $instagram, $facebook, $twitter, $id);
		if($stmt->execute())
			return true; 
		return false; 
	}
	public function login($correo, $password)
	{
		$sql = "SELECT * FROM usuario WHERE correo = '$correo'";
		$result = mysqli_query($this->con, $sql);
		$row = mysqli_fetch_assoc($result);
		$passtemp = $row['password'];
		if($password == $passtemp)
			return true;
		else
			return false;

	}
	//fetching all records from the database 
	public function getUser(){
		$stmt = $this->con->prepare("SELECT Id, Nombres, Correo, Start FROM comment");
		$stmt->execute();
		$stmt->bind_result($Id, $Nombres, $Correo, $Start);
		$artists = array();
		
		while($stmt->fetch()){
			$temp = array(); 
			$temp['Id'] = $Id; 
			$temp['name'] = $Nombres; 
			$temp['comment'] = $Correo; 
			$temp['start'] = $Start; 
			array_push($artists, $temp);
		}
		return $artists; 
	}
}