<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 30/06/2016
 * Time: 23:17
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="position")
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
     * @ORM\Column(type="float", name="latitude")
     */
    private $latitude;

    /**
     * @ORM\Column(type="float", name="longitude")
     */
    private $longitude;

    /**
     * @ORM\OneToOne(targetEntity="Arena")
     * @ORM\JoinColumn(name="arena_id")
     */
    private $arena;

    /**
     * @ORM\ManyToOne(targetEntity="Zone", inversedBy="position")
     * @ORM\JoinColumn(name="zone_id", referencedColumnName="id")
     */
    private $zones;


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
    public function setZones(\AppBundle\Entity\Zone $zones = null)
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
}
