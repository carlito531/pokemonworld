<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 22/06/2016
 * Time: 21:29
 */

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Response;

class IndexController extends Controller
{

    /**
     * @Route("/index", name="index")
     */
    public function getIndex()
    {
        return $this->render(
            'index.html.twig'
        );
    }
}