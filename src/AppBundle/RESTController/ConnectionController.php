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
        $login = '';
        $password = '';
        $view = null;
        $trainer = null;
        $em = $this->getDoctrine()->getManager();

        if ($request != null) {

            if($request->request->get("login") != null) {
                $login = $request->request->get("login");
            }

            if($request->request->get("password") != null) {
                $password = $request->request->get("password");
            }

            $trainer = $em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $login));

            if ($trainer != null) {
                if ($trainer->getLogin() == $login && $trainer->getPassword() == hash('sha256', $password)) {
                    $view = $this->view(true, 200)->setFormat('json');
                }
            } else {
                $view = $this->view(false, 204)->setFormat('json');
            }
        }

        return $view;
    }

}