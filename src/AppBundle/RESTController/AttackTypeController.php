<?php

namespace AppBundle\RESTController;

use FOS\RestBundle\Controller\FOSRestController;
use AppBundle\Entity\AttackType;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\Config\Definition\Exception\Exception;
use Symfony\Component\HttpFoundation\Request;
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

        $view = $this->view($attacktypes, 200)->setFormat('json');

        return $view;
    }


    /**
     * @Route("/add")
     *
     * @Method("POST")
     *
     * @return View
     */
    public function getAttackType(Request $request)
    {
        $type = new AttackType();
        $view = null;
        $em = $this->getDoctrine()->getManager();

        if ($request != null) {

            if($request->request->get("type") != null) {
                $type->setType($request->request->get("type"));
            }

            try {
                $em->persist($type);
                $em->flush();

                $view = $this->view(true, 200)->setFormat('json');

            } catch(Exception $e) {
                $view = $this->view($e->getMessage(), 204)->setFormat('json');
            }
        }
        return $view;
    }
}