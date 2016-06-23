<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 23/06/2016
 * Time: 10:11
 */

namespace AppBundle\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Route;
use Symfony\Bundle\FrameworkBundle\Controller\Controller;
use Symfony\Component\HttpFoundation\Response;

class InscriptionController extends Controller
{

    /**
     * @Route("/inscription", name="inscription")
     */
    public function getInscription()
    {
        return $this->render(
            'inscription.html.twig'
        );
    }
}