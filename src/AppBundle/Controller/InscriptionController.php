<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 23/06/2016
 * Time: 10:11
 */

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use AppBundle\Entity\Trainer;
use AppBundle\Entity\Position;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;

class InscriptionController extends Controller
{

    /**
     * @Route("/inscription", name="inscription")
     * @Method("GET")
     */
    public function showAction()
    {
        return $this->render(
            "inscription.html.twig"
        );
    }

    /**
     * @Route("inscription/getConnection")
     * @Method("POST")
     */
    public function getConnection(Request $request)
    {
        $name = "";
        $login = "";
        $password = "";
        $trainer = null;
        $em = $this->getDoctrine()->getManager();
        $response = null;

        // check credentials
        if ($request != null) {

            if($request->request->get("loginText") != null) {
                $login = $request->request->get("loginText");
            }

            if($request->request->get("passwordText") != null) {
                $password = $request->request->get("passwordText");
            }

            // check if it is inscription, if true go to inscription function
            if($request->request->get("chkInscription")) {
                $name = $request->request->get("nameText");
                $response = $this->getInscription($name, $login, $password);

            } else {
                // check if user exist in database
                $trainer = $em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $login));

                // if user exist, put his login in session
                if ($trainer != null) {

                    if ($trainer->getLogin() == $login && $trainer->getPassword() == hash('sha256', $password)) {
                        session_start();
                        $_SESSION['login'] = $trainer->getLogin();
                        $response = new Response("User connected", 200);
                    }
                } else {
                    $response = new Response("Login or password doesn't match", 500);
                }
            }
        }
        var_dump($name);
        var_dump($login);
        var_dump($password);

        return $response;
    }

    /**
     * inscription method for a new user
     * @param $name
     * @param $login
     * @param $password
     * @return null|Response
     */
    private function getInscription($name, $login, $password)
    {
        $trainer = new Trainer();
        $em = $this->getDoctrine()->getManager();
        $response = null;

        try {
            // inscription of the new user
            if ($name != "" && $login != "" && $password != "") {
                $trainer->setName($name);
                $trainer->setLogin($login);
                $trainer->setPassword($password);

                // set the no nullable columns
                $trainer->setIsMaster(false);
                $trainer->setPosition($em->getRepository('AppBundle:Position')->find(3));

                // save the new user in database
                $em->persist($trainer);
                $em->flush();

                $response = new Response("User registred", 200);
            }
        } catch (\Exception $e) {
            print_r($e->getMessage());
        }

        return $response;
    }
}