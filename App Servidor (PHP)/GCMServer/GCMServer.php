<?php 
	$apiKey = $_POST['apiKey'];

	if($apiKey == '') {
		echo '<script>alert("Google Api Key inválida");</script>';
		echo '<script>history.go(-1);</script>';
		return;
	}
	
	$idDispositivo = $_POST['idDispositivo'];

	if($idDispositivo == '') {
		echo '<script>alert("ID del dispositivo inválido");</script>';
		echo '<script>history.go(-1);</script>';
		return;
	}
	
	$mensaje = $_POST['message'];
	
	if($mensaje == '') {
		echo '<script>alert("Mensaje Inválido");</script>';
		echo '<script>history.go(-1);</script>';
		return;
	}
	
	sendGoogleCloudMessage($apiKey,
						   $idDispositivo,
						   $mensaje);
	
	//Función que envía el mensaje al dispositivo
	function sendGoogleCloudMessage($apiKey, 
									$idDispositivo,
									$mensaje)
	{
		// Define URL to GCM endpoint
		$url = 'https://android.googleapis.com/gcm/send';

		$data = array( 'message' => $mensaje );
		
		$post = array('registration_ids'  => array($idDispositivo),
					  'data'              => $data,);

		$headers = array( 
							'Authorization: key=' . $apiKey,
							'Content-Type: application/json'
						);

		$ch = curl_init();

		//------------------------------
		// Set URL to GCM endpoint
		//------------------------------

		curl_setopt( $ch, CURLOPT_URL, $url );

		//------------------------------
		// Set request method to POST
		//------------------------------

		curl_setopt( $ch, CURLOPT_POST, true );

		//------------------------------
		// Set our custom headers
		//------------------------------

		curl_setopt( $ch, CURLOPT_HTTPHEADER, $headers );

		//------------------------------
		// Get the response back as 
		// string instead of printing it
		//------------------------------

		curl_setopt( $ch, CURLOPT_RETURNTRANSFER, true );

		//------------------------------
		// Set post data as JSON
		//------------------------------

		curl_setopt( $ch, CURLOPT_POSTFIELDS, json_encode( $post ) );

		//------------------------------
		// Actually send the push!
		//------------------------------

		curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
		
		$result = curl_exec( $ch );

		if ( curl_errno( $ch ) )
		{
			echo 'GCM error: ' . curl_error( $ch );
		}

		//------------------------------
		// Close curl handle
		//------------------------------

		curl_close( $ch );

		//------------------------------
		// Debug GCM response
		//------------------------------

		echo $result;
	}
?>