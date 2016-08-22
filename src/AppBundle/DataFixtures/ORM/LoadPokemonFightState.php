<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 16/08/2016
 * Time: 19:55
 */

namespace AppBundle\DataFixtures\ORM;

use Doctrine\Common\DataFixtures\AbstractFixture;
use Doctrine\Common\DataFixtures\OrderedFixtureInterface;
use Doctrine\Common\Persistence\ObjectManager;
use AppBundle\Entity\PokemonFightState;

class LoadPokemonFightState extends AbstractFixture implements OrderedFixtureInterface
{

    /**
     * Load data fixtures with the passed EntityManager
     *
     * @param ObjectManager $manager
     */
    public function load(ObjectManager $manager)
    {
        $inFightList = new PokemonFightState();
        $inFightList->setName("IN_FIGHT_LIST");

        $fighting = new PokemonFightState();
        $fighting->setName("IN_FIGHT");

        $isKo = new PokemonFightState();
        $isKo->setName("KO");

        $manager->persist($inFightList);
        $manager->persist($fighting);
        $manager->persist($isKo);

        $manager->flush();
    }

    /**
     * Get the order of this fixture
     *
     * @return integer
     */
    public function getOrder()
    {
       return 0;
    }
}