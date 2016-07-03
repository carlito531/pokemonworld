<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 01/07/2016
 * Time: 00:16
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="attack")
 */
class Attack
{
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=50, name="name", nullable=false, unique=true)
     */
    private $name;

    /**
     * @ORM\Column(type="integer", name="damage", nullable=false)
     */
    private $damage;

    /**
     * @ORM\Column(type="float", length=50, name="accuracy", nullable=false)
     */
    private $accuracy;

    /**
     * @ORM\ManyToOne(targetEntity="AttackType", inversedBy="attacks")
     * @ORM\JoinColumn(name="attackType_id", referencedColumnName="id", nullable=false)
     */
    private $attackType;

    /**
     * @ORM\OneToMany(targetEntity="Pokemon", mappedBy="attack1")
     */
    private $pokemons1;

    /**
     * @ORM\OneToMany(targetEntity="Pokemon", mappedBy="attack2")
     */
    private $pokemons2;

    /**
     * @ORM\OneToMany(targetEntity="Pokemon", mappedBy="attack3")
     */
    private $pokemons3;

    /**
     * @ORM\OneToMany(targetEntity="Pokemon", mappedBy="attack4")
     */
    private $pokemons4;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->pokemons1 = new \Doctrine\Common\Collections\ArrayCollection();
        $this->pokemons2 = new \Doctrine\Common\Collections\ArrayCollection();
        $this->pokemons3 = new \Doctrine\Common\Collections\ArrayCollection();
        $this->pokemons4 = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Get id
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set name
     *
     * @param string $name
     *
     * @return Attack
     */
    public function setName($name)
    {
        $this->name = $name;

        return $this;
    }

    /**
     * Get name
     *
     * @return string
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * Set damage
     *
     * @param integer $damage
     *
     * @return Attack
     */
    public function setDamage($damage)
    {
        $this->damage = $damage;

        return $this;
    }

    /**
     * Get damage
     *
     * @return integer
     */
    public function getDamage()
    {
        return $this->damage;
    }

    /**
     * Set accuracy
     *
     * @param float $accuracy
     *
     * @return Attack
     */
    public function setAccuracy($accuracy)
    {
        $this->accuracy = $accuracy;

        return $this;
    }

    /**
     * Get accuracy
     *
     * @return float
     */
    public function getAccuracy()
    {
        return $this->accuracy;
    }

    /**
     * Set attackType
     *
     * @param \AppBundle\Entity\AttackType $attackType
     *
     * @return Attack
     */
    public function setAttackType(\AppBundle\Entity\AttackType $attackType = null)
    {
        $this->attackType = $attackType;

        return $this;
    }

    /**
     * Get attackType
     *
     * @return \AppBundle\Entity\AttackType
     */
    public function getAttackType()
    {
        return $this->attackType;
    }

    /**
     * Add pokemons1
     *
     * @param \AppBundle\Entity\Pokemon $pokemons1
     *
     * @return Attack
     */
    public function addPokemons1(\AppBundle\Entity\Pokemon $pokemons1)
    {
        $this->pokemons1[] = $pokemons1;

        return $this;
    }

    /**
     * Remove pokemons1
     *
     * @param \AppBundle\Entity\Pokemon $pokemons1
     */
    public function removePokemons1(\AppBundle\Entity\Pokemon $pokemons1)
    {
        $this->pokemons1->removeElement($pokemons1);
    }

    /**
     * Get pokemons1
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getPokemons1()
    {
        return $this->pokemons1;
    }

    /**
     * Add pokemons2
     *
     * @param \AppBundle\Entity\Pokemon $pokemons2
     *
     * @return Attack
     */
    public function addPokemons2(\AppBundle\Entity\Pokemon $pokemons2)
    {
        $this->pokemons2[] = $pokemons2;

        return $this;
    }

    /**
     * Remove pokemons2
     *
     * @param \AppBundle\Entity\Pokemon $pokemons2
     */
    public function removePokemons2(\AppBundle\Entity\Pokemon $pokemons2)
    {
        $this->pokemons2->removeElement($pokemons2);
    }

    /**
     * Get pokemons2
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getPokemons2()
    {
        return $this->pokemons2;
    }

    /**
     * Add pokemons3
     *
     * @param \AppBundle\Entity\Pokemon $pokemons3
     *
     * @return Attack
     */
    public function addPokemons3(\AppBundle\Entity\Pokemon $pokemons3)
    {
        $this->pokemons3[] = $pokemons3;

        return $this;
    }

    /**
     * Remove pokemons3
     *
     * @param \AppBundle\Entity\Pokemon $pokemons3
     */
    public function removePokemons3(\AppBundle\Entity\Pokemon $pokemons3)
    {
        $this->pokemons3->removeElement($pokemons3);
    }

    /**
     * Get pokemons3
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getPokemons3()
    {
        return $this->pokemons3;
    }

    /**
     * Add pokemons4
     *
     * @param \AppBundle\Entity\Pokemon $pokemons4
     *
     * @return Attack
     */
    public function addPokemons4(\AppBundle\Entity\Pokemon $pokemons4)
    {
        $this->pokemons4[] = $pokemons4;

        return $this;
    }

    /**
     * Remove pokemons4
     *
     * @param \AppBundle\Entity\Pokemon $pokemons4
     */
    public function removePokemons4(\AppBundle\Entity\Pokemon $pokemons4)
    {
        $this->pokemons4->removeElement($pokemons4);
    }

    /**
     * Get pokemons4
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getPokemons4()
    {
        return $this->pokemons4;
    }
}
