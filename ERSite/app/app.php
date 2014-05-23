<?php

// Bootstrap
require __DIR__ . DIRECTORY_SEPARATOR . 'bootstrap.php';


$app->error(function (\Exception $e, $code) {
	if ($code == 404) {
		return '404 - Not Found! // ' . $e->getMessage();
	} else {
		return 'Something went wrong // ' . $e->getMessage();
	}
});

//$app->mount('/register/', new ERSite\Provider\Controller\Register());

//static pages
$pages = array(
        '/' => 'home'
        );
    foreach ($pages as $route => $view) {
        $app->get($route, function () use ($app, $view) {
            return $app['twig']->render('static/' . $view . '.twig');
        })->bind($view);
    }

// Mount our Controllers
$app->mount('/register/', new ERSite\provider\controller\registercontroller());
