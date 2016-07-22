<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 22/06/2016
 * Time: 21:29
 */

namespace AppBundle\Controller;

use AppBundle\RESTController\AttackTypeController;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Response;

class IndexController extends Controller
{

    /**
     * @Route("/index", name="index")
     */

    /*
    public function getIndex()
    {
        $em = $this->getDoctrine()->getManager();

        $entitie = $em->getRepository('AppBundle:AttackType')->find(1);

        return new Response($entitie->getType());
    }
    */
}