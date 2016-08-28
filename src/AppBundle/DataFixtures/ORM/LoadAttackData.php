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
        $attack->setDamage(36);
        $attack->setAttackType($this->getReference('attaquetype-electrique'));

        $attack1 = new Attack();
        $attack1->setName('Tonnerre');
        $attack1->setAccuracy(85.50);
        $attack1->setDamage(32);
        $attack1->setAttackType($this->getReference('attaquetype-electrique'));

        $attack2 = new Attack();
        $attack2->setName('Vive-attaque');
        $attack2->setAccuracy(80.50);
        $attack2->setDamage(38);
        $attack2->setAttackType($this->getReference('attaquetype-normal'));

        $attack3 = new Attack();
        $attack3->setName('Mimi-queue');
        $attack3->setAccuracy(85.50);
        $attack3->setDamage(0);
        $attack3->setAttackType($this->getReference('attaquetype-normal'));

        $attack4 = new Attack();
        $attack4->setName('Morsure');
        $attack4->setAccuracy(85.50);
        $attack4->setDamage(35);
        $attack4->setAttackType($this->getReference('attaquetype-normal'));

        $attack5 = new Attack();
        $attack5->setName('Belier');
        $attack5->setAccuracy(85.50);
        $attack5->setDamage(37);
        $attack5->setAttackType($this->getReference('attaquetype-normal'));

        $attack6 = new Attack();
        $attack6->setName('Boule Roc');
        $attack6->setAccuracy(85.50);
        $attack6->setDamage(30);
        $attack6->setAttackType($this->getReference('attaquetype-roche'));

        $attack7 = new Attack();
        $attack7->setName('Roulade');
        $attack7->setAccuracy(85.50);
        $attack7->setDamage(37);
        $attack7->setAttackType($this->getReference('attaquetype-roche'));

        $attack8 = new Attack();
        $attack8->setName('Destruction');
        $attack8->setAccuracy(85.50);
        $attack8->setDamage(100);
        $attack8->setAttackType($this->getReference('attaquetype-normal'));

        $attack9 = new Attack();
        $attack9->setName('Psyko');
        $attack9->setAccuracy(85.50);
        $attack9->setDamage(35);
        $attack9->setAttackType($this->getReference('attaquetype-psi'));

        $attack10 = new Attack();
        $attack10->setName('Devoreve');
        $attack10->setAccuracy(85.50);
        $attack10->setDamage(39);
        $attack10->setAttackType($this->getReference('attaquetype-psi'));

        $attack11 = new Attack();
        $attack11->setName('Distorsion');
        $attack11->setAccuracy(85.50);
        $attack11->setDamage(32);
        $attack11->setAttackType($this->getReference('attaquetype-psi'));

        $attack12 = new Attack();
        $attack12->setName('Interversion');
        $attack12->setAccuracy(85.50);
        $attack12->setDamage(42);
        $attack12->setAttackType($this->getReference('attaquetype-psi'));


        $manager->persist($attack);
        $manager->persist($attack1);
        $manager->persist($attack2);
        $manager->persist($attack3);
        $manager->persist($attack4);
        $manager->persist($attack5);
        $manager->persist($attack6);
        $manager->persist($attack7);
        $manager->persist($attack8);
        $manager->persist($attack9);
        $manager->persist($attack10);
        $manager->persist($attack11);
        $manager->persist($attack12);
        $manager->flush();

        $this->addReference('attaque-eclair', $attack);
        $this->addReference('attaque-tonnerre', $attack1);
        $this->addReference('attaque-viveattaque', $attack2);
        $this->addReference('attaque-mimiqueue', $attack3);
        $this->addReference('attaque-morsure', $attack4);
        $this->addReference('attaque-belier', $attack5);
        $this->addReference('attaque-bouleroc', $attack6);
        $this->addReference('attaque-roulade', $attack7);
        $this->addReference('attaque-destruction', $attack8);
        $this->addReference('attaque-psyko', $attack9);
        $this->addReference('attaque-devoreve', $attack10);
        $this->addReference('attaque-distorsion', $attack11);
        $this->addReference('attaque-interversion', $attack12);
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