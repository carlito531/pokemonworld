<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 01/07/2016
 * Time: 21:54
 */

namespace AppBundle\DataFixtures\ORM;

use AppBundle\Entity\Pokemon;
use Doctrine\Common\DataFixtures\AbstractFixture;
use Doctrine\Common\DataFixtures\OrderedFixtureInterface;
use Doctrine\Common\Persistence\ObjectManager;
use AppBundle\Entity\PokemonType;

class LoadPokemonTypeData extends AbstractFixture implements OrderedFixtureInterface
{

    /**
     * Load data fixtures with the passed EntityManager
     *
     * @param ObjectManager $manager
     */
    public function load(ObjectManager $manager)
    {
        $electrique = new PokemonType();
        $electrique->setName('Electrique');

        $normal = new PokemonType();
        $normal->setName('Normal');

        $psi = new PokemonType();
        $psi->setName('Psi');

        $roche = new PokemonType();
        $roche->setName('Roche');

        $manager->persist($electrique);
        $manager->persist($normal);
        $manager->persist($psi);
        $manager->persist($roche);

        $manager->flush();

        $this->addReference('pokemontype-electrique', $electrique);
        $this->addReference('pokemontype-normal', $normal);
        $this->addReference('pokemontype-psi', $psi);
        $this->addReference('pokemontype-roche', $roche);
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