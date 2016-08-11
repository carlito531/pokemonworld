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
use AppBundle\Entity\Position;
use AppBundle\Entity\Zone;
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

                        $view = $this->view("User connected", 200)->setFormat('json');
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
                $trainer->setPosition($this->setStartPosition());
                $trainer->addPokemon($this->setFirstPokemon());

                // save the new user in database
                $em->persist($trainer);
                $em->flush();

                // put the new user in session
                session_start();
                $_SESSION['login'] = $trainer->getLogin();

                $view = $this->view("User registred", 201)->setFormat('json');
            } else {
                $view = $this->view("Identifiants manquants", 500)->setFormat('json');
            }

        } catch (\Exception $e) {
            $view = $this->view($e->getMessage(), 500);
        }

        return $view;
    }


    /**
     * Set trainer starting position
     * @return null
     */
    private function setStartPosition() {

        $position = null;

        try {
            $em = $this->getDoctrine()->getManager();

            $zone = $em->getRepository('AppBundle:Zone')->findOneBy(array('name' => 'Depart'));
            $position = $zone->getPosition()[0];

        } catch (Exception $e) {
            var_dump($e->getMessage());
        }

        return $position;
    }


    /**
     * set first pokemon attributed to a trainer after his inscription
     * @return null|object
     */
    private function setFirstPokemon()
    {
        $pokemon = null;

        try {
            $em = $this->getDoctrine()->getManager();
            $pokemon = $em->getRepository('AppBundle:Pokemon')->findOneBy(array('name' => 'Pikachu'));

        } catch (Exception $e) {
            var_dump($e->getMessage());
        }

        return $pokemon;
    }
}