<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 23/06/2016
 * Time: 10:11
 */

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;

class InscriptionController extends Controller
{

    /**
     * @Route("/inscription", name="inscription")
     */
    public function getInscription()
    {
        return $this->render(
            'inscription.html.twig'
        );
    }

    /**
     * @Route("/inscription/startInscriptionOrConnection", name="inscription/startInscriptionOrConnection")
     */
    public function startInscriptionOrConnection(Request $request)
    {
        $login = '';
        $password = '';
        $test = '';

        $request->isXmlHttpRequest();

        if ($request != null) {

            if($request->query->get('login') != null) {
                $login = $request->query->get('loginText');
            }

            if($request->query->get('password') != null) {
                $password = $request->query->get('passwordText');
            }

            if ($request->attributes->get('login') != null) {
                $test = $request->attributes->get('login');
            }
        }

        if ($login == '' or $password == '') {
            return new Response("Login/password vide");
        }

        return new Response("login: " + $login + " Mot de passe: " + $password);
    }
}