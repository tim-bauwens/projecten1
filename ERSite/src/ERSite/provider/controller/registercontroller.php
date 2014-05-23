<?php

namespace ERSite\provider\controller;

use SoapClient;
use Silex\Application;
use Silex\ControllerProviderInterface;
use Silex\ControllerCollection;
use Symfony\Component\Validator\Constraints as Assert;


class registercontroller implements ControllerProviderInterface {

	public function connect(Application $app) {

		//@note $app['controllers_factory'] is a factory that returns a new instance of ControllerCollection when used.
		//@see http://silex.sensiolabs.org/doc/organizing_controllers.html
		$controllers = $app['controllers_factory'];

		// Bind sub-routes
		$controllers->get('/', array($this, 'register'))
					->method('GET|POST')
					->bind('register');

        	$controllers->get('/id_lezen', array($this,'readEid'));
		return $controllers;

	}

	public function register(Application $app){
	
        $errors = array();
         
   	$eidguid = $app['request']->get('eidguid');
   	$data = array(
   		"firstname" => '',
   		"lastname" => ''
   		);
        if($eidguid != null && $eidguid != ''){
                try {
                        //turn off WSDL caching if not in a production environment
                        $ini = ini_set("soap.wsdl_cache_enabled", "0");
                        $strEIDServiceLicenceID = "1cefc59d-5079-444a-ba42-af9b863d382d";
                        $client = new SoapClient("https://www.eidtoolkit.be/services/EIDServiceWS.asmx?wsdl");
                        $params = array('strEIDguid' => $eidguid, 'strEIDServiceLicenceID' => $strEIDServiceLicenceID);
                        $eiddataXML = $client->GetXMLByEIDguid($params)->GetXMLByEIDguidResult;
                        $xml = simplexml_load_string($eiddataXML);
                        $data['firstname']=(string)$xml->FirstName;
                        $data['lastname']=(string)$xml->LastName;
                } catch (Exception $exception) {
                        echo($exception);
                }
        }
        
        $builder = $app['form.factory'];
        $registerform= $builder->createNamed('registerform', 'form',$data);
		$registerform->add('firstname', 'text', array(
                  	'constraints' => array(new Assert\NotBlank()),
                  	'attr' => array('readonly'=> true)
           		 ));
		$registerform->add('lastname', 'text', array(
                  	'constraints' => array(new Assert\NotBlank()),
                  	'attr' => array('readonly' => true)
            		));
        	$registerform->add('username', 'text', array(
                  	'constraints' => array(new Assert\NotBlank(), new Assert\Length(array('min' => 5))
            		)));
        	$registerform->add('emailaddress', 'email');
        	$registerform->add('password', 'password', array(
                  	'constraints' => array(new Assert\NotBlank(), new Assert\Length(array('min' => 8))
            		)));
        	$registerform->add('reenterpassword', 'password', array(
                  	'constraints' => array(new Assert\NotBlank(), new Assert\Length(array('min' => 8))
            		)));
            		
        if ('POST' == $app['request']->getMethod() && isset( $_POST['submit'] )) {
            $registerform->bind($app['request']);

	        if ($registerform->isValid()) {
			$data = $registerform->getData();
			if($data['reenterpassword'] == $data['password']){
				unset($data['reenterpassword']);
				$cryptedPass = openssl_digest($data['password'] , 'sha512');
				$data['password'] = $cryptedPass;
				$app['db.users']->insert($data);
				// redirect somewhere
				return $app->redirect($app['url_generator']->generate('home'));
			}
			else {
	        		return $app->redirect($app['url_generator']->generate('register'));
	       		 }
		}
	        else {
	        	return $app->redirect($app['url_generator']->generate('register'));
	        }
   	 }    		
	return $app['twig']->render('register/register.twig', array(
		'registerform' => $registerform->createView(),
    		'errors' => $errors
		));
		

	}

    public function readEid(Application $app){
        return $app['twig']->render('register/EIDServiceRequest.twig');  
    }

}