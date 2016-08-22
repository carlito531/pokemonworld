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
use AppBundle\Entity\AttackType;

class LoadAttackTypeData extends AbstractFixture implements OrderedFixtureInterface
{

    /**
     * Load data fixtures with the passed EntityManager
     *
     * @param ObjectManager $manager
     */
    public function load(ObjectManager $manager)
    {
        $electrique = new AttackType();
        $electrique->setType('Electrique');

        $normal = new AttackType();
        $normal->setType('Normal');

        $psi = new AttackType();
        $psi->setType('Psi');

        $roche = new AttackType();
        $roche->setType('Roche');

        $manager->persist($electrique);
        $manager->persist($normal);
        $manager->persist($psi);
        $manager->persist($roche);

        $manager->flush();

        $this->addReference('attaquetype-electrique', $electrique);
        $this->addReference('attaquetype-normal', $normal);
        $this->addReference('attaquetype-psi', $psi);
        $this->addReference('attaquetype-roche', $roche);
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