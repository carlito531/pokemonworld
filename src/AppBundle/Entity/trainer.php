<?php

/**
 * Created by PhpStorm.
 * User: charly
 * Date: 27/06/2016
 * Time: 20:44
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use JMS\Serializer\Annotation\ExclusionPolicy;
use JMS\Serializer\Annotation\Expose;

/**
 * @ORM\Entity(repositoryClass="AppBundle\Repository\TrainerRepository")
 * @ORM\Table(name="trainer")
 * @ExclusionPolicy("All")
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
     * @ORM\Column(type="string", length=50, name="name", nullable=false, unique=true)
     * @Expose
     */
    private $name;

    /**
     * @ORM\Column(type="string", length=50, name="login", nullable=false, unique=true)
     * @Expose
     */
    private $login;

    /**
     * @ORM\Column(type="string", length=64, name="password", nullable=false)
     */
    private $password;

    /**
     * @ORM\Column(type="boolean", name="isMaster")
     * @Expose
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
     * @Expose
     */
    private $badges;

    /**
     * @ORM\OneToMany(targetEntity="Object", mappedBy="trainers")
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
     * @ORM\ManyToOne(targetEntity="Position", inversedBy="trainers")
     * @ORM\JoinColumn(name="position_id", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $position;
    /**
     * Constructor
     */
    public function __construct()
    {
        $this->badges = new \Doctrine\Common\Collections\ArrayCollection();
        $this->object = new \Doctrine\Common\Collections\ArrayCollection();
        $this->player = new \Doctrine\Common\Collections\ArrayCollection();
        $this->opponent = new \Doctrine\Common\Collections\ArrayCollection();
        $this->pokemons = new \Doctrine\Common\Collections\ArrayCollection();
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
     * Add object
     *
     * @param \AppBundle\Entity\Object $object
     *
     * @return Trainer
     */
    public function addObject(\AppBundle\Entity\Object $object)
    {
        $this->object[] = $object;

        return $this;
    }

    /**
     * Remove object
     *
     * @param \AppBundle\Entity\Object $object
     */
    public function removeObject(\AppBundle\Entity\Object $object)
    {
        $this->object->removeElement($object);
    }

    /**
     * Get object
     *
     * @return \Doctrine\Common\Collections\Collection
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

    /**
     * Set position
     *
     * @param \AppBundle\Entity\Position $position
     *
     * @return Trainer
     */
    public function setPosition(\AppBundle\Entity\Position $position)
    {
        $this->position = $position;

        return $this;
    }

    /**
     * Get position
     *
     * @return \AppBundle\Entity\Position
     */
    public function getPosition()
    {
        return $this->position;
    }
}
