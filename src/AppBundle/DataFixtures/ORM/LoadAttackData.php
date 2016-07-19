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
use AppBundle\Entity\Attack;

class LoadAttackData extends AbstractFixture implements OrderedFixtureInterface
{

    /**
     * Load data fixtures with the passed EntityManager
     *
     * @param ObjectManager $manager
     */
    public function load(ObjectManager $manager)
    {
        $attack = new Attack();
        $attack->setName('Eclair');
        $attack->setAccuracy(85.50);
        $attack->setDamage(6);
        $attack->setAttackType($this->getReference('attaquetype-electrique'));

        $attack1 = new Attack();
        $attack1->setName('Tonnerre');
        $attack1->setAccuracy(85.50);
        $attack1->setDamage(12);
        $attack1->setAttackType($this->getReference('attaquetype-electrique'));

        $attack2 = new Attack();
        $attack2->setName('Vive-attaque');
        $attack2->setAccuracy(80.50);
        $attack2->setDamage(8);
        $attack2->setAttackType($this->getReference('attaquetype-normal'));

        $attack3 = new Attack();
        $attack3->setName('Mimi-queue');
        $attack3->setAccuracy(85.50);
        $attack3->setDamage(0);
        $attack3->setAttackType($this->getReference('attaquetype-normal'));

        $manager->persist($attack);
        $manager->persist($attack1);
        $manager->persist($attack2);
        $manager->persist($attack3);
        $manager->flush();

        $this->addReference('attaque-eclair', $attack);
        $this->addReference('attaque-tonnerre', $attack1);
        $this->addReference('attaque-viveattaque', $attack2);
        $this->addReference('attaque-mimiqueue', $attack3);
    }

    /**
     * Get the order of this fixture
     *
     * @return integer
     */
    public function getOrder()
    {
        return 1;
    }
}