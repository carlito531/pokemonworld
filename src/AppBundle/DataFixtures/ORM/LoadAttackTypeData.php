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
        $attackType = new AttackType();
        $attackType->setType('Electrique');

        $attackType1 = new AttackType();
        $attackType1->setType('Normal');

        $manager->persist($attackType);
        $manager->persist($attackType1);
        $manager->flush();

        $this->addReference('attaquetype-electrique', $attackType);
        $this->addReference('attaquetype-normal', $attackType1);
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