<?php
	require_once 'config.php';
	header('Content-Type: application/json');
	
	
	if($_GET['client'] == "client"){
		//connect to database
		try{
			$db = new PDO('mysql:host=' . DB_HOST .';dbname=' . DB_NAME_FF . ';charset=utf8', DB_USER, DB_PASS);
			$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	           	$db->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
		} 
		catch (Exception $e) {
	    		exit('Could not connect to database server or access database');
		}
		
	 	$stmt = $db->prepare('SELECT * FROM emergencies');
	 	$stmt->execute();
		$result = $stmt->fetchAll();
		
		// check for empty result
		//if (mysql_num_rows($result) > 0) {
		if (sizeof($result) != 0) {
		    // success
		    for($i = 0; $i < sizeof($result); $i++){
			    $response[$i]["emergencyId"] = $result[$i]["emergencyId"];
			    $response[$i]["userId"] = $result[$i]["userId"];
			    $response[$i]["latitude"] = $result[$i]["latitude"];
			    $response[$i]["longitude"] = $result[$i]["longitude"];
			    $response[$i]["location"] = $result[$i]["location"];
			    $response[$i]["numVictims"] = $result[$i]["numVictims"];
			    $response[$i]["numWounded"] = $result[$i]["numWounded"];
			    $response[$i]["type"] = $result[$i]["type"];
			    $response[$i]["description"] = $result[$i]["description"];
			    $response[$i]["datetime"] = $result[$i]["datetime"];
		    }
		    // echoing JSON response
		    echo json_encode($response);
		    
		} else {
		    // no emergencies found
		    $response["success"] = 0;
		    $response["message"] = "No emergencies found";
		 
		    // echo no emergencies JSON
		    echo json_encode($response);
		}
	}
	else{
		echo("client:" . $_GET['client']);
		//echo"failed";
	}
	
?>