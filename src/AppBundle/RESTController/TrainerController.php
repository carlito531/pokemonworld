<?php

namespace AppBundle\RESTController;

use AppBundle\Entity\Position;
use FOS\RestBundle\Controller\FOSRestController;
use AppBundle\Entity\Trainer;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\Config\Definition\Exception\Exception;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
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

        try {
            $trainer = $em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $name));

            if ($trainer != null) {
                $view = $this->view($trainer, 200)->setFormat('json');
            } else {
                $view = $this->view(false, 204);
            }

        } catch (Exception $e) {
            $view = $this->view($e->getMessage());
            var_dump($e->getMessage());
        }
        return $view;
    }

    /**
     * @Route("/{name}/move")
     * @Method("PUT")
     *
     * @param $name
     * @param Request $request
     * @return View
     */
    public function moveTrainer($name, Request $request) {

        $em = $this->getDoctrine()->getManager();
        $latitude = null;
        $longitude = null;
        $trainer = null;
        $position = null;
        $view = null;

        try {
            $trainer = $em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $name));

            if ($trainer != null) {
                if ($request != null) {

                    if($request->request->get("latitude") != null) {
                        $latitude = $request->request->get("latitude");
                    }

                    if($request->request->get("longitude") != null) {
                        $longitude = $request->request->get("longitude");
                    }

                    $position = new Position();
                    $position->setLatitude($latitude);
                    $position->setLongitude($longitude);

                    $trainer->setPosition($position);

                    $em->persist($trainer);
                    $em->flush();

                    $view = $this->view("Position mise à jour", 200)->setFormat('json');
                }
            }

        } catch (Exception $e) {
            $view = $this->view($e->getMessage())->setFormat('json');
            var_dump($e->getMessage());
        }

        return $view;
    }
}