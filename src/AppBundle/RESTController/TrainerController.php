<?php

namespace AppBundle\RESTController;

use FOS\RestBundle\Controller\FOSRestController;
use AppBundle\Entity\Trainer;
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


class TrainerController extends FOSRestController
{
    /**
     * @Route("/")
     *
     * @Method("GET")
     *
     * @return View
     */
    public function getTrainers()
    {
        $em = $this->getDoctrine()->getManager();

        $trainers = $em->getRepository('AppBundle:Trainer')->findAll();

        if ($trainers != null) {
            $view = $this->view($trainers, 200)->setFormat('json');
        } else {
            $view = $this->view(false, 500);
        }

        return $view;
    }

    /**
     * @Route("/{name}")
     *
     * @Method("GET")
     *
     * @return View
     */
    public function getTrainer($name)
    {
        $em = $this->getDoctrine()->getManager();

        $trainer = $em->getRepository('AppBundle:Trainer')->findOneBy(array('name' => $name));

        if ($trainer != null) {
            $view = $this->view($trainer, 200)->setFormat('json');
        } else {
            $view = $this->view(false, 204);
        }
        return $view;
    }

}