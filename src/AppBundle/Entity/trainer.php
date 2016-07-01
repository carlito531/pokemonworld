<?php

/**
 * Created by PhpStorm.
 * User: charly
 * Date: 27/06/2016
 * Time: 20:44
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="trainer")
 */
class Trainer
{

    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=50, name="name")
     */
    private $name;

    /**
     * @ORM\Column(type="string", length=50, name="login")
     */
    private $login;

    /**
     * @ORM\Column(type="string", length=20, name="password")
     */
    private $password;

    /**
     * @ORM\Column(type="boolean", name="isMaster")
     */
    private $isMaster;

    /**
     * @ORM\OneToOne(targetEntity="Npc")
     * @ORM\JoinColumn(name="npc_id")
     */
    private $npc;

    /**
     * @ORM\OneToOne(targetEntity="Image")
     */
    private $image;

    /**
     * @ORM\ManyToMany(targetEntity="Badge")
     */
    private $badges;

    /**
     * @ORM\ManyToOne(targetEntity="Object", inversedBy="trainers")
     * @ORM\JoinColumn(name="trainer_id", referencedColumnName="id")
     */
    private $object;

    /**
     * @ORM\OneToMany(targetEntity="Fight", mappedBy="trainer1")
     */
    private $player;

    /**
     * @ORM\OneToMany(targetEntity="Fight", mappedBy="trainer2")
     */
    private $opponent;

    /**
     * @ORM\OneToMany(targetEntity="Pokemon", mappedBy="trainer")
     */
    private $pokemons;


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
     * @return Trainer
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
     * Set login
     *
     * @param string $login
     *
     * @return Trainer
     */
    public function setLogin($login)
    {
        $this->login = $login;

        return $this;
    }

    /**
     * Get login
     *
     * @return string
     */
    public function getLogin()
    {
        return $this->login;
    }

    /**
     * Set password
     *
     * @param string $password
     *
     * @return Trainer
     */
    public function setPassword($password)
    {
        $this->password = $password;

        return $this;
    }

    /**
     * Get password
     *
     * @return string
     */
    public function getPassword()
    {
        return $this->password;
    }

    /**
     * Set isMaster
     *
     * @param boolean $isMaster
     *
     * @return Trainer
     */
    public function setIsMaster($isMaster)
    {
        $this->isMaster = $isMaster;

        return $this;
    }

    /**
     * Get isMaster
     *
     * @return boolean
     */
    public function getIsMaster()
    {
        return $this->isMaster;
    }

    /**
     * Set npc
     *
     * @param \AppBundle\Entity\Npc $npc
     *
     * @return Trainer
     */
    public function setNpc(\AppBundle\Entity\Npc $npc = null)
    {
        $this->npc = $npc;

        return $this;
    }

    /**
     * Get npc
     *
     * @return \AppBundle\Entity\Npc
     */
    public function getNpc()
    {
        return $this->npc;
    }

    /**
     * Set image
     *
     * @param \AppBundle\Entity\Image $image
     *
     * @return Trainer
     */
    public function setImage(\AppBundle\Entity\Image $image = null)
    {
        $this->image = $image;

        return $this;
    }

    /**
     * Get image
     *
     * @return \AppBundle\Entity\Image
     */
    public function getImage()
    {
        return $this->image;
    }
    /**
     * Constructor
     */
    public function __construct()
    {
        $this->badges = new \Doctrine\Common\Collections\ArrayCollection();
        $this->player = new \Doctrine\Common\Collections\ArrayCollection();
        $this->opponent = new \Doctrine\Common\Collections\ArrayCollection();
        $this->pokemons = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add badge
     *
     * @param \AppBundle\Entity\Badge $badge
     *
     * @return Trainer
     */
    public function addBadge(\AppBundle\Entity\Badge $badge)
    {
        $this->badges[] = $badge;

        return $this;
    }

    /**
     * Remove badge
     *
     * @param \AppBundle\Entity\Badge $badge
     */
    public function removeBadge(\AppBundle\Entity\Badge $badge)
    {
        $this->badges->removeElement($badge);
    }

    /**
     * Get badges
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getBadges()
    {
        return $this->badges;
    }

    /**
     * Set object
     *
     * @param \AppBundle\Entity\Object $object
     *
     * @return Trainer
     */
    public function setObject(\AppBundle\Entity\Object $object = null)
    {
        $this->object = $object;

        return $this;
    }

    /**
     * Get object
     *
     * @return \AppBundle\Entity\Object
     */
    public function getObject()
    {
        return $this->object;
    }

    /**
     * Add player
     *
     * @param \AppBundle\Entity\Fight $player
     *
     * @return Trainer
     */
    public function addPlayer(\AppBundle\Entity\Fight $player)
    {
        $this->player[] = $player;

        return $this;
    }

    /**
     * Remove player
     *
     * @param \AppBundle\Entity\Fight $player
     */
    public function removePlayer(\AppBundle\Entity\Fight $player)
    {
        $this->player->removeElement($player);
    }

    /**
     * Get player
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getPlayer()
    {
        return $this->player;
    }

    /**
     * Add opponent
     *
     * @param \AppBundle\Entity\Fight $opponent
     *
     * @return Trainer
     */
    public function addOpponent(\AppBundle\Entity\Fight $opponent)
    {
        $this->opponent[] = $opponent;

        return $this;
    }

    /**
     * Remove opponent
     *
     * @param \AppBundle\Entity\Fight $opponent
     */
    public function removeOpponent(\AppBundle\Entity\Fight $opponent)
    {
        $this->opponent->removeElement($opponent);
    }

    /**
     * Get opponent
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getOpponent()
    {
        return $this->opponent;
    }

    /**
     * Add pokemon
     *
     * @param \AppBundle\Entity\Pokemon $pokemon
     *
     * @return Trainer
     */
    public function addPokemon(\AppBundle\Entity\Pokemon $pokemon)
    {
        $this->pokemons[] = $pokemon;

        return $this;
    }

    /**
     * Remove pokemon
     *
     * @param \AppBundle\Entity\Pokemon $pokemon
     */
    public function removePokemon(\AppBundle\Entity\Pokemon $pokemon)
    {
        $this->pokemons->removeElement($pokemon);
    }

    /**
     * Get pokemons
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getPokemons()
    {
        return $this->pokemons;
    }
}
