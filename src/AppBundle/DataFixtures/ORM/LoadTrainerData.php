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
use AppBundle\Entity\Trainer;

class LoadTrainerData extends AbstractFixture implements OrderedFixtureInterface
{

    /**
     * Load data fixtures with the passed EntityManager
     *
     * @param ObjectManager $manager
     */
    public function load(ObjectManager $manager)
    {
        $trainer = new Trainer();
        $trainer->setName("Sasha531");
        $trainer->setLogin("sacha531@gmail.com");
        $trainer->setPassword(hash('sha256', 'sacha531'));
        $trainer->setIsMaster(false);
        $trainer->setPosition($this->getReference('position-ville'));
        $trainer->addBadge($this->getReference('badge-roche'));

        $trainer1 = new Trainer();
        $trainer1->setName("Luc54");
        $trainer1->setLogin("luc54@gmail.com");
        $trainer1->setPassword(hash('sha256', 'luc54'));
        $trainer1->setIsMaster(false);
        $trainer1->setPosition($this->getReference('position-ville'));

        $manager->persist($trainer);
        $manager->persist($trainer1);
        $manager->flush();

        $this->addReference('trainer', $trainer);
        $this->addReference('trainer1', $trainer1);
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