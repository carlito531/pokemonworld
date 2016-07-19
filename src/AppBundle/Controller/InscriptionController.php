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
            "inscription.html.twig"
        );
    }

    /**
     * @Route("/inscription/startInscriptionOrConnection", name="inscription/startInscriptionOrConnection")
     */
    public function startInscriptionOrConnection(Request $request)
    {
        $login = "";
        $password = "";

        $request->isXmlHttpRequest();

        if ($request != null) {

            if($request->request->get("login") != null) {
                $login = $request->request->get("login");
            }

            if($request->request->get("password") != null) {
                $password = $request->request->get("password");
            }
        }

        if ($login == "" or $password == "") {
            return new Response("Login/password vide");
        }

        return new Response("login: " . $login . " mot de passe: " . $password);
    }
}