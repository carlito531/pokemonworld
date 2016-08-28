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
use AppBundle\Entity\FightState;

class LoadFightState extends AbstractFixture implements OrderedFixtureInterface
{

    /**
     * Load data fixtures with the passed EntityManager
     *
     * @param ObjectManager $manager
     */
    public function load(ObjectManager $manager)
    {
        $fightRequestState = new FightState();
        $fightRequestState->setName("FIGHT_REQUEST_SENT");

        $fightRequestAccepted = new FightState();
        $fightRequestAccepted->setName("FIGHT_REQUEST_ACCEPTED");

        $trainer1PokemonsReady = new FightState();
        $trainer1PokemonsReady->setName("TRAINER1_POKEMONS_READY");

        $trainer2PokemonsReady = new FightState();
        $trainer2PokemonsReady->setName("TRAINER2_POKEMONS_READY");

        $allPokemonsReady = new FightState();
        $allPokemonsReady->setName("ALL_POKEMONS_READY");

        $fightCanStart = new FightState();
        $fightCanStart->setName("FIGHT_CAN_START");

        $trainer1AttackTurn = new FightState();
        $trainer1AttackTurn->setName("TRAINER1_ATTACK_TURN");

        $trainer2AttackTurn = new FightState();
        $trainer2AttackTurn->setName("TRAINER2_ATTACK_TURN");

        $manager->persist($fightRequestState);
        $manager->persist($fightRequestAccepted);
        $manager->persist($trainer1PokemonsReady);
        $manager->persist($trainer2PokemonsReady);
        $manager->persist($allPokemonsReady);
        $manager->persist($fightCanStart);
        $manager->persist($trainer1AttackTurn);
        $manager->persist($trainer2AttackTurn);

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