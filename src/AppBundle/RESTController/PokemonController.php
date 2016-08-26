<?php

namespace AppBundle\RESTController;

use FOS\RestBundle\Controller\FOSRestController;
use AppBundle\Entity\Pokemon;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\ParamConverter;
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


class PokemonController extends FOSRestController
{
    /**
     * @Route("/")
     *
     * @Method("GET")
     *
     * @return View
     */
    public function getPokemons()
    {
        $em = $this->getDoctrine()->getManager();

        $pokemons = $em->getRepository('AppBundle:Pokemon')->findAll();

        if ($pokemons != null) {
            $view = $this->view($pokemons, 200)->setFormat('json');
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
    public function getPokemon($name)
    {
        $em = $this->getDoctrine()->getManager();

        $pokemon = $em->getRepository('AppBundle:Pokemon')->findOneBy(array('name' => $name));

        if ($pokemon != null) {
            $view = $this->view($pokemon, 200)->setFormat('json');
        } else {
            $view = $this->view(false, 204);
        }
        return $view;
    }


    /**
     * @Route("/putInFightList")
     * @Method("PUT")
     *
     * @param $name
     * @param Request $request
     * @return View
     */
    public function putInFightList(Request $request) {

        $em = $this->getDoctrine()->getManager();
        $JsonIds = null;
        $ids = null;
        $view = null;

        try {
            if ($request != null) {

                if($request->request->get("pokemonIds") != null) {
                    $JsonIds = $request->request->get("pokemonIds");
                }

                $ids = json_decode($JsonIds, true);

                for ($i = 0; $i < count($ids); $i++) {
                    $pokemon = $em->getRepository('AppBundle:Pokemon')->find($ids[$i]);
                    $pokemon->setPokemonFightState($em->getRepository('AppBundle:PokemonFightState')->findOneBy(array('name' => 'IN_FIGHT_LIST')));
                    $em->persist($pokemon);
                    $em->flush();
                }

                $view = $this->view("Pokemons prêt à combattre !", 200)->setFormat('json');
            }

        } catch (Exception $e) {
            $view = $this->view($e->getMessage(), 500)->setFormat('json');
            var_dump($e->getMessage());
        }

        return $view;
    }


    /**
     * @Route("{id}/putInFight")
     * @Method("PUT")
     *
     * @param $id
     * @param Request $request
     * @return View
     */
    public function putInFight($id) {

        $em = $this->getDoctrine()->getManager();
        $view = null;

        try {
            if ($id != null) {
                $pokemon = $em->getRepository('AppBundle:Pokemon')->find($id);
                $pokemon->setPokemonFightState($em->getRepository('AppBundle:PokemonFightState')->findOneBy(array('name' => 'IN_FIGHT')));
                $em->persist($pokemon);
                $em->flush();

                $view = $this->view("Pokemons prêt à combattre !", 200)->setFormat('json');
            }

        } catch (Exception $e) {
            $view = $this->view($e->getMessage(), 500)->setFormat('json');
            var_dump($e->getMessage());
        }

        return $view;
    }

    /**
     * @Route("{id}/attack")
     * @Method("PUT")
     *
     * @param $id
     * @param Request $request
     * @return View
     */
    public function attackOpponent($id, Request $request) {

        $em = $this->getDoctrine()->getManager();
        $damage = null;
        $view = null;

        try {
            if ($request != null) {

                if($request->request->get("damage") != null) {
                    $damage = $request->request->get("damage");
                }

                $pokemon = $em->getRepository('AppBundle:Pokemon')->find($id);
                $pokemon->setHp(($pokemon->getHp()) - $damage);
                if ($pokemon->getHp() < 0) {
                    $pokemon->setHp(0);
                }

                $em->persist($pokemon);
                $em->flush();

                $view = $this->view("Pokemon adverse attaqué ", 200)->setFormat('json');
            }

        } catch (Exception $e) {
            $view = $this->view($e->getMessage(), 500)->setFormat('json');
            var_dump($e->getMessage());
        }

        return $view;
    }
}