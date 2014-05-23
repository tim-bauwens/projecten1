<?php
	require_once 'config.php';
	header('Content-Type: application/json');
	
	//Retrieve the user data from the app
	$usernameSent = $_GET['username'];
	$passwordSent = $_GET['password'];
	$response["loggedIn"] = false;
	
	//connect to database
	try{
		$db = new PDO('mysql:host=' . DB_HOST .';dbname=' . DB_NAME_FF . ';charset=utf8', DB_USER, DB_PASS);
		$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
           	$db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
	} 
	catch (Exception $e) {
    		exit('Could not connect to database server or access database');
	}
	
	// get all reports from emergencies table
	if($usernameSent != null || $passwordSent != null){
		$stmt = $db->prepare('SELECT username, password FROM users WHERE username = ?');
		$stmt->execute(array($usernameSent));		
		$collection = $stmt->fetch(PDO::FETCH_ASSOC);
		if($usernameSent == $collection['username'] && $collection['password'] == $passwordSent){
			$response["loggedIn"] = true;	
		}
	}
	echo json_encode($response);
?>