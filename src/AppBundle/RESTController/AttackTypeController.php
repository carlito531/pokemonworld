<?php

namespace AppBundle\RESTController;

use FOS\RestBundle\Controller\Annotations as Rest;
use FOS\RestBundle\Controller\FOSRestController;
use AppBundle\Entity\AttackType;
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


class AttackTypeController extends FOSRestController
{

    /**
     * @Route("/")
     *
     * @Method("GET")
     *
     * @return View
     */
    public function getAttacktypesList()
    {
        $em = $this->getDoctrine()->getManager();

        $attacktypes = $em->getRepository('AppBundle:AttackType')->findAll();

        //var_dump($attacktypes);

        $view = $this->view($attacktypes, 200)->setFormat('json');

        return $view;
    }

}