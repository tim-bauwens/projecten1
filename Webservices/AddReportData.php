<?php
	require_once 'config.php';
	date_default_timezone_set('Europe/Brussels');
	
	//connect to database
	try{
		$db = new PDO('mysql:host=' . DB_HOST .';dbname=' . DB_NAME_FF . ';charset=utf8', DB_USER, DB_PASS);
		$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
           	$db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
	} 
	catch (Exception $e) {
    		exit('Could not connect to database server or access database');
	}
	
	//check username valid
	$username = $_POST['username'];
	$password = $_POST['password'];
	if($username != null || $password != null){
		//get user data
		$stmt = $db->prepare('SELECT usersId, username, password FROM users WHERE username = ?');
		$stmt->execute(array($username));		
		$collection = $stmt->fetch(PDO::FETCH_ASSOC);
		if($username == $collection['username'] && $collection['password'] == $password){
			//user data
			$userId = $collection['usersId'];
			//Retrieve the data from report
			$latitude = $_POST['latitude'];
			$longitude = $_POST['longitude'];
			$location = $_POST['location'];
			$numVictims = $_POST['numVictims'];
			$numWounded = $_POST['numWounded'];
			$type = $_POST['type'];
			$description = $_POST['description'];
			//get current datetime
			//get currrent time and date
			$date = date('Y-m-d H:i:s');
		
			//prepare the inser statement
			$stmt2 = $db->prepare('INSERT INTO emergencies(userId, latitude, longitude, location, numVictims, numWounded, type, description, datetime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)');
					
			//execute the insert statement
			$stmt2->execute(array($userId, $latitude, $longitude, $location, $numVictims, $numWounded, $type, $description, $date));
		}
		else{
	 		echo("failed1");
		}
	}
	else{
	 echo("failed2");
	}
?>