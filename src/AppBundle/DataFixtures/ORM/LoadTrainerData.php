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
        $sacha531 = new Trainer();
        $sacha531->setName("Sasha531");
        $sacha531->setLogin("sacha531@gmail.com");
        $sacha531->setDeviceId("66077d2362694b9fc208ac900abec521");
        $sacha531->setPassword(hash('sha256', 'sacha531'));
        $sacha531->setIsMaster(false);
        $sacha531->setPosition($this->getReference('position-depart'));
        $sacha531->addBadge($this->getReference('badge-roche'));

        $luc54 = new Trainer();
        $luc54->setName("Luc54");
        $luc54->setLogin("luc54@gmail.com");
        $luc54->setDeviceId("1e2e8190f8a7e870cd4df2fe9173abbc");
        $luc54->setPassword(hash('sha256', 'luc54'));
        $luc54->setIsMaster(false);
        $luc54->setPosition($this->getReference('position-aleatoire'));

        $bob33 = new Trainer();
        $bob33->setName("Bob33");
        $bob33->setLogin("bob33@gmail.com");
        $bob33->setDeviceId("1e2e8190f8a7e870cd4df2fe9173abbc");
        $bob33->setPassword(hash('sha256', 'bob33'));
        $bob33->setIsMaster(false);
        $bob33->setPosition($this->getReference('position-aleatoire1'));

        $paul22 = new Trainer();
        $paul22->setName("Paul22");
        $paul22->setLogin("paul22@gmail.com");
        $paul22->setDeviceId("1e2e8190f8a7e870cd4df2fe9173abbc");
        $paul22->setPassword(hash('sha256', 'paul22'));
        $paul22->setIsMaster(false);
        $paul22->setPosition($this->getReference('position-ville'));

        $manager->persist($sacha531);
        $manager->persist($luc54);
        $manager->persist($bob33);
        $manager->persist($paul22);

        $manager->flush();

        $this->addReference('trainer-sacha531', $sacha531);
        $this->addReference('trainer-luc54', $luc54);
        $this->addReference('trainer-bob33', $bob33);
        $this->addReference('trainer-paul22', $paul22);
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