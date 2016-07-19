<?php

namespace AppBundle\RESTController;

use FOS\RestBundle\Controller\FOSRestController;
use AppBundle\Entity\Attack;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\HttpFoundation\Response;
use FOS\RestBundle\View\View;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;

/**
 * Created by PhpStorm.
 * User: charly
 * Date: 04/07/2016
 * Time: 23:56
 */


class AttackController extends FOSRestController
{

    /**
     * @Route("/")
     *
     * @Method("GET")
     *
     * @return View
     */
    public function getAttackList()
    {
        $em = $this->getDoctrine()->getManager();

        $attack = $em->getRepository('AppBundle:Attack')->findAll();

        //var_dump($attacktypes);

        $view = $this->view($attack, 200)->setFormat('json');

        return $view;
    }

}