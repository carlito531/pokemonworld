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
use AppBundle\Entity\Position;
use AppBundle\Entity\Zone;


class LoadPositionData extends AbstractFixture implements OrderedFixtureInterface
{

    /**
     * Load data fixtures with the passed EntityManager
     *
     * @param ObjectManager $manager
     */
    public function load(ObjectManager $manager)
    {
        $position = new Position();
        $position->setLatitude('48.815456');
        $position->setLongitude('-3.4449429999999666');
        $position->setZones($this->getReference('zone-ville'));

        $startPosition = new Position();
        $startPosition->setLatitude('48.7453968');
        $startPosition->setLongitude('-3.5397994');
        $startPosition->setZones($this->getReference('depart'));

        $manager->persist($position);
        $manager->persist($startPosition);
        $manager->flush();

        $this->addReference('position-ville', $position);
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