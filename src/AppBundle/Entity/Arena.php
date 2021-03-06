<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 30/06/2016
 * Time: 23:11
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use JMS\Serializer\Annotation\ExclusionPolicy;
use JMS\Serializer\Annotation\Expose;

/**
 * @ORM\Entity(repositoryClass="AppBundle\Repository\ArenaRepository")
 * @ORM\Table(name="arena")
 * @ExclusionPolicy("All")
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
     * @ORM\Column(type="string", length=50 , name="name", nullable=false)
     * @Expose
     */
    private $name;

    /**
     * @ORM\OneToMany(targetEntity="Fight", mappedBy="arena")
     */
    private $fights;

    /**
     * @ORM\OneToOne(targetEntity="Position" , cascade={"remove"})
     * @ORM\JoinColumn(name="position_id", nullable=false)
     * @Expose
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
