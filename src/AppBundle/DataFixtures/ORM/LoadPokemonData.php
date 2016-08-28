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
        $sacha531 = $this->getReference('trainer-sacha531');
        $luc54 = $this->getReference('trainer-luc54');
        $bob33 = $this->getReference('trainer-bob33');
        $paul22 = $this->getReference('trainer-paul22');

        $trainers = array($sacha531, $luc54, $bob33, $paul22);

        for ($i = 0; $i < count($trainers); $i++) {

            $raichu = new Pokemon();
            $raichu->setName('Raichu');
            $raichu->setExperience(0);
            $raichu->setHp(100);
            $raichu->setLevel(1);
            $raichu->setPokemonType($this->getReference('pokemontype-electrique'));
            $raichu->setPosition($this->getReference('position-ville'));
            $raichu->setAttack1($this->getReference('attaque-eclair'));
            $raichu->setAttack2($this->getReference('attaque-tonnerre'));
            $raichu->setAttack3($this->getReference('attaque-viveattaque'));
            $raichu->setAttack4($this->getReference('attaque-mimiqueue'));
            $raichu->setTrainer($trainers[$i]);

            $pikachu = new Pokemon();
            $pikachu->setName('Pikachu');
            $pikachu->setExperience(0);
            $pikachu->setHp(100);
            $pikachu->setLevel(1);
            $pikachu->setPokemonType($this->getReference('pokemontype-electrique'));
            $pikachu->setPosition($this->getReference('position-ville'));
            $pikachu->setAttack1($this->getReference('attaque-eclair'));
            $pikachu->setAttack2($this->getReference('attaque-tonnerre'));
            $pikachu->setAttack3($this->getReference('attaque-viveattaque'));
            $pikachu->setAttack4($this->getReference('attaque-mimiqueue'));
            $pikachu->setTrainer($trainers[$i]);

            $evoli = new Pokemon();
            $evoli->setName('Evoli');
            $evoli->setExperience(0);
            $evoli->setHp(100);
            $evoli->setLevel(1);
            $evoli->setPokemonType($this->getReference('pokemontype-normal'));
            $evoli->setPosition($this->getReference('position-ville'));
            $evoli->setAttack1($this->getReference('attaque-morsure'));
            $evoli->setAttack2($this->getReference('attaque-belier'));
            $evoli->setAttack3($this->getReference('attaque-viveattaque'));
            $evoli->setAttack4($this->getReference('attaque-mimiqueue'));
            $evoli->setTrainer($trainers[$i]);

            $racaillou = new Pokemon();
            $racaillou->setName('Racaillou');
            $racaillou->setExperience(0);
            $racaillou->setHp(100);
            $racaillou->setLevel(1);
            $racaillou->setPokemonType($this->getReference('pokemontype-roche'));
            $racaillou->setPosition($this->getReference('position-ville'));
            $racaillou->setAttack1($this->getReference('attaque-roulade'));
            $racaillou->setAttack2($this->getReference('attaque-viveattaque'));
            $racaillou->setAttack3($this->getReference('attaque-bouleroc'));
            $racaillou->setAttack4($this->getReference('attaque-destruction'));
            $racaillou->setTrainer($trainers[$i]);

            $abra = new Pokemon();
            $abra->setName('Abra');
            $abra->setExperience(0);
            $abra->setHp(100);
            $abra->setLevel(1);
            $abra->setPokemonType($this->getReference('pokemontype-psi'));
            $abra->setPosition($this->getReference('position-ville'));
            $abra->setAttack1($this->getReference('attaque-psyko'));
            $abra->setAttack2($this->getReference('attaque-devoreve'));
            $abra->setAttack3($this->getReference('attaque-distorsion'));
            $abra->setAttack4($this->getReference('attaque-interversion'));
            $abra->setTrainer($trainers[$i]);

            $manager->persist($raichu);
            $manager->persist($pikachu);
            $manager->persist($evoli);
            $manager->persist($racaillou);
            $manager->persist($abra);

            $manager->flush();
        }
    }

    /**
     * Get the order of this fixture
     *
     * @return integer
     */
    public function getOrder()
    {
        return 3;
    }
}