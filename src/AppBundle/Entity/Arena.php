<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 30/06/2016
 * Time: 23:11
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="arena")
 */
class Arena
{
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=50 , name="name")
     */
    private $name;

    /**
     * @ORM\OneToMany(targetEntity="Fight", mappedBy="arena")
     */
    private $fights;

    /**
     * @ORM\OneToOne(targetEntity="Position")
     * @ORM\JoinColumn(name="position_id")
     */
    private $position;

    /**
     * Constructor
     */
    public function __construct()
    {
        $this->fights = new \Doctrine\Common\Collections\ArrayCollection();
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
     * @return Arena
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
     * Add fight
     *
     * @param \AppBundle\Entity\Fight $fight
     *
     * @return Arena
     */
    public function addFight(\AppBundle\Entity\Fight $fight)
    {
        $this->fights[] = $fight;

        return $this;
    }

    /**
     * Remove fight
     *
     * @param \AppBundle\Entity\Fight $fight
     */
    public function removeFight(\AppBundle\Entity\Fight $fight)
    {
        $this->fights->removeElement($fight);
    }

    /**
     * Get fights
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getFights()
    {
        return $this->fights;
    }

    /**
     * Set position
     *
     * @param \AppBundle\Entity\Position $position
     *
     * @return Arena
     */
    public function setPosition(\AppBundle\Entity\Position $position = null)
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
