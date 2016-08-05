<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 19/07/2016
 * Time: 11:02
 */

namespace AppBundle\RESTController;

use FOS\RestBundle\Controller\FOSRestController;
use AppBundle\Entity\Trainer;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\View\View;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\HttpKernel\Exception\NotFoundHttpException;

class ConnectionController extends FOSRestController
{

    /**
     * @Route("/")
     *
     * @Method("POST")
     *
     * @return View
     */
    public function getConnection(Request $request)
    {
        $pseudo = '';
        $login = '';
        $password = '';
        $view = null;
        $trainer = null;
        $em = $this->getDoctrine()->getManager();

        // get values from request
        if ($request != null) {

            if($request->request->get("login") != null) {
                $login = $request->request->get("login");
            }

            if($request->request->get("password") != null) {
                $password = $request->request->get("password");
            }

            // if it is an inscription request, go to inscription function
            if($request->request->get("pseudo") != null) {
                $pseudo = $request->request->get("pseudo");
                $view = $this->getInscription($pseudo, $login, $password);

            } else {
                // else, check if user login exist in database
                $trainer = $em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $login));

                // if true, check if password match and put user login in session
                if ($trainer != null) {
                    if ($trainer->getLogin() == $login && $trainer->getPassword() == $password) {
                        session_start();
                        $_SESSION['login'] = $trainer->getLogin();

                        $view = $this->view("User connected", 200);
                    }
                } else {
                    $view = $this->view(false, 500)->setFormat('json');
                }
            }
        }

        return $view;
    }


    /**
     * Inscription function
     *
     * @param $pseudo
     * @param $login
     * @param $password
     * @return mixed
     */
    private function getInscription($pseudo, $login, $password) {

        $trainer = new Trainer();
        $em = $this->getDoctrine()->getManager();
        $view = null;

        try {
            // inscription of the new user
            if ($pseudo != "" && $login != "" && $password != "") {
                $trainer->setName($pseudo);
                $trainer->setLogin($login);
                $trainer->setPassword($password);

                // set the no nullable columns
                $trainer->setIsMaster(false);
                $trainer->setPosition($em->getRepository('AppBundle:Position')->find(3));

                // save the new user in database
                $em->persist($trainer);
                $em->flush();

                $view = $this->view("User registred", 201);
            } else {
                $view = $this->view(false, 500)->setFormat('json');
            }

        } catch (\Exception $e) {
            print_r($e->getMessage());
        }

        return $view;
    }

}