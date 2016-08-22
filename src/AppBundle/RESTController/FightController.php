<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 15/08/2016
 * Time: 17:52
 */

namespace AppBundle\RESTController;

use AppBundle\Entity\Fight;
use FOS\RestBundle\Controller\FOSRestController;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
use Symfony\Component\Config\Definition\Exception\Exception;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\HttpFoundation\Request;
use FOS\RestBundle\View\View;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;


class FightController extends FOSRestController
{

    /**
     * Set a new fight in database
     *
     * @Route("/new")
     * @Method("POST")
     * @return View
     */
    public function setNewFight(Request $request) {
        $view = null;
        $em = $this->getDoctrine()->getManager();

        $date = null;
        $fightState = null;
        $trainer1 = null;
        $trainer2 = null;

        // Get attributes from request
        if($request->request->get("date") != null) {
            $date = $request->request->get("date");
        }

        if($request->request->get("fightState") != null) {
            $fightState = $request->request->get("fightState");
        }

        if($request->request->get("trainer1") != null) {
            $trainer1 = $request->request->get("trainer1");
        }

        if($request->request->get("trainer2") != null) {
            $trainer2 = $request->request->get("trainer2");
        }

        if ($date != null && $fightState != null && $trainer1 != null && $trainer2 != null) {
            try {
                $fight = new Fight();
                $fight->setDate($date);
                $fight->setFightState($em->getRepository('AppBundle:FightState')->findOneBy(array('name' => $fightState)));
                $fight->setTrainer1($em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $trainer1)));
                $fight->setTrainer2($em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $trainer2)));

                $em->persist($fight);
                $em->flush();
                $fight->getId();

                $view = $this->view("Nouveau combat enregistré !", 200)->setFormat('json');

            } catch (Exception $e) {
                var_dump($e->getMessage());
            }
        }

        return $view;
    }


    /**
     * Get trainer fight
     *
     * @Route("/{trainer}")
     * @Method("GET")
     * @return View
     */
    public function getFight($trainer) {
        $view = null;
        $em = $this->getDoctrine()->getManager();

        try {
            $user = $em->getRepository('AppBundle:Trainer')->findOneBy(array('login' => $trainer));
            $fightTrainer1 = $em->getRepository('AppBundle:Fight')->findOneBy(array('trainer1' => $user));
            $fightTrainer2 = $em->getRepository('AppBundle:Fight')->findOneBy(array('trainer2' => $user));

            if ($fightTrainer1 != null) {
                $fight = $fightTrainer1;

            } else if ($fightTrainer2 != null) {
                $fight = $fightTrainer2;
            }

            if ($fight != null) {
                $view = $this->view($fight, 200)->setFormat('json');
            } else {
                $view = $this->view(false, 204);
            }
        } catch (Exception $e) {
            var_dump($e->getMessage());
        }

        return $view;
    }


    /**
     * @Route("/{id}/update")
     * @Method("PUT")
     *
     * @param $id
     * @param Request $request
     * @return View
     */
    public function updateFightState($id, Request $request) {

        $em = $this->getDoctrine()->getManager();
        $fightState = null;
        $view = null;

        try {
            $fight = $em->getRepository('AppBundle:Fight')->findOneBy(array('id' => $id));

            if ($fight != null) {
                if ($request != null) {

                    if($request->request->get("fightState") != null) {
                        $fightState = $request->request->get("fightState");
                    }

                    $newfightState = $em->getRepository('AppBundle:FightState')->findOneBy(array('name' => $fightState));

                    if ($newfightState != null) {
                        $fight->setFightState($newfightState);
                    }

                    $em->persist($fight);
                    $em->flush();

                    $view = $this->view("Combat mis à jour", 200)->setFormat('json');
                }
            }

        } catch (Exception $e) {
            $view = $this->view($e->getMessage())->setFormat('json');
        }

        return $view;
    }

}