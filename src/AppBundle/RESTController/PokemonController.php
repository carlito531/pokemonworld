<?php

namespace AppBundle\RESTController;

use FOS\RestBundle\Controller\FOSRestController;
use AppBundle\Entity\Pokemon;
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


class PokemonController extends FOSRestController
{
    /**
     * @Route("/")
     *
     * @Method("GET")
     *
     * @return View
     */
    public function getPokemons($name)
    {
        $em = $this->getDoctrine()->getManager();

        $pokemons = $em->getRepository('AppBundle:Pokemon')->findAll();

        $view = $this->view($pokemons, 200)->setFormat('json');

        return $view;
    }

    /**
     * @Route("/{name}")
     *
     * @Method("GET")
     *
     * @return View
     */
    public function getPokemon($name)
    {
        $em = $this->getDoctrine()->getManager();

        $pokemon = $em->getRepository('AppBundle:Pokemon')->findOneBy(array('name' => $name));

        $view = $this->view($pokemon, 200)->setFormat('json');

        return $view;
    }

}