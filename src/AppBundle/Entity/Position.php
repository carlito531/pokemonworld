<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 30/06/2016
 * Time: 23:17
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use JMS\Serializer\Annotation\ExclusionPolicy;
use JMS\Serializer\Annotation\Expose;

/**
 * @ORM\Entity(repositoryClass="AppBundle\Repository\PositionRepository")
 * @ORM\Table(name="position")
 * @ExclusionPolicy("All")
 */
class Position
{
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="float", name="latitude", nullable=false)
     * @Expose
     */
    private $latitude;

    /**
     * @ORM\Column(type="float", name="longitude", nullable=false)
     * @Expose
     */
    private $longitude;

    /**
     * @ORM\OneToOne(targetEntity="Arena", cascade={"remove"})
     * @ORM\JoinColumn(name="arena_id")
     * @Expose
     */
    private $arena;

    /**
     * @ORM\ManyToOne(targetEntity="Zone", inversedBy="position")
     * @ORM\JoinColumn(name="zone_id", referencedColumnName="id")
     * @Expose
     */
    private $zones;

    /**
     * @ORM\OneToMany(targetEntity="Pokemon", mappedBy="position")
     */
    private $pokemons;

    /**
     * @ORM\OneToMany(targetEntity="Npc", mappedBy="position")
     */
    private $npcs;

    /**
     * @ORM\OneToMany(targetEntity="Trainer", mappedBy="position")
     */
    private $trainers;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->pokemons = new \Doctrine\Common\Collections\ArrayCollection();
        $this->npcs = new \Doctrine\Common\Collections\ArrayCollection();
        $this->trainers = new \Doctrine\Common\Collections\ArrayCollection();
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
     * Set latitude
     *
     * @param float $latitude
     *
     * @return Position
     */
    public function setLatitude($latitude)
    {
        $this->latitude = $latitude;

        return $this;
    }

    /**
     * Get latitude
     *
     * @return float
     */
    public function getLatitude()
    {
        return $this->latitude;
    }

    /**
     * Set longitude
     *
     * @param float $longitude
     *
     * @return Position
     */
    public function setLongitude($longitude)
    {
        $this->longitude = $longitude;

        return $this;
    }

    /**
     * Get longitude
     *
     * @return float
     */
    public function getLongitude()
    {
        return $this->longitude;
    }

    /**
     * Set arena
     *
     * @param \AppBundle\Entity\Arena $arena
     *
     * @return Position
     */
    public function setArena(\AppBundle\Entity\Arena $arena = null)
    {
        $this->arena = $arena;

        return $this;
    }

    /**
     * Get arena
     *
     * @return \AppBundle\Entity\Arena
     */
    public function getArena()
    {
        return $this->arena;
    }

    /**
     * Set zones
     *
     * @param \AppBundle\Entity\Zone $zones
     *
     * @return Position
     */
    public function setZones(\AppBundle\Entity\Zone $zones)
    {
        $this->zones = $zones;

        return $this;
    }

    /**
     * Get zones
     *
     * @return \AppBundle\Entity\Zone
     */
    public function getZones()
    {
        return $this->zones;
    }

    /**
     * Add pokemon
     *
     * @param \AppBundle\Entity\Pokemon $pokemon
     *
     * @return Position
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
     * Add npc
     *
     * @param \AppBundle\Entity\Npc $npc
     *
     * @return Position
     */
    public function addNpc(\AppBundle\Entity\Npc $npc)
    {
        $this->npcs[] = $npc;

        return $this;
    }

    /**
     * Remove npc
     *
     * @param \AppBundle\Entity\Npc $npc
     */
    public function removeNpc(\AppBundle\Entity\Npc $npc)
    {
        $this->npcs->removeElement($npc);
    }

    /**
     * Get npcs
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getNpcs()
    {
        return $this->npcs;
    }

    /**
     * Add trainer
     *
     * @param \AppBundle\Entity\Trainer $trainer
     *
     * @return Position
     */
    public function addTrainer(\AppBundle\Entity\Trainer $trainer)
    {
        $this->trainers[] = $trainer;

        return $this;
    }

    /**
     * Remove trainer
     *
     * @param \AppBundle\Entity\Trainer $trainer
     */
    public function removeTrainer(\AppBundle\Entity\Trainer $trainer)
    {
        $this->trainers->removeElement($trainer);
    }

    /**
     * Get trainers
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getTrainers()
    {
        return $this->trainers;
    }
}
