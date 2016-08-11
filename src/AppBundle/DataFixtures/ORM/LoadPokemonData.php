<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 01/07/2016
 * Time: 21:54
 */

namespace AppBundle\DataFixtures\ORM;

use Doctrine\Common\DataFixtures\AbstractFixture;
use Doctrine\Common\DataFixtures\OrderedFixtureInterface;
use Doctrine\Common\Persistence\ObjectManager;
use AppBundle\Entity\Pokemon;

class LoadPokemonData extends AbstractFixture implements OrderedFixtureInterface
{

    /**
     * Load data fixtures with the passed EntityManager
     *
     * @param ObjectManager $manager
     */
    public function load(ObjectManager $manager)
    {
        $pokemon = new Pokemon();
        $pokemon->setName('Raichu');
        $pokemon->setExperience(50);
        $pokemon->setHp(100);
        $pokemon->setLevel(1);
        $pokemon->setPokemonType($this->getReference('pokemontype-electrique'));
        $pokemon->setPosition($this->getReference('position-ville'));
        $pokemon->setAttack1($this->getReference('attaque-eclair'));
        $pokemon->setAttack2($this->getReference('attaque-tonnerre'));
        $pokemon->setAttack3($this->getReference('attaque-viveattaque'));
        $pokemon->setAttack4($this->getReference('attaque-mimiqueue'));
        $pokemon->setPokedex($this->getReference('pokedex'));

        $pokemon1 = new Pokemon();
        $pokemon1->setName('Pikachu');
        $pokemon1->setExperience(80);
        $pokemon1->setHp(100);
        $pokemon1->setLevel(1);
        $pokemon1->setPokemonType($this->getReference('pokemontype-electrique'));
        $pokemon1->setPosition($this->getReference('position-ville'));
        $pokemon1->setAttack1($this->getReference('attaque-eclair'));
        $pokemon1->setAttack2($this->getReference('attaque-tonnerre'));
        $pokemon1->setAttack3($this->getReference('attaque-viveattaque'));
        $pokemon1->setAttack4($this->getReference('attaque-mimiqueue'));
        $pokemon1->setPokedex($this->getReference('pokedex'));

        $manager->persist($pokemon);
        $manager->persist($pokemon1);
        $manager->flush();

        $this->addReference('pokemon-raichu', $pokemon);
        $this->addReference('pokemon-pikachu', $pokemon1);
    }

    /**
     * Get the order of this fixture
     *
     * @return integer
     */
    public function getOrder()
    {
        return 2;
    }
}