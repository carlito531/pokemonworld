<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 30/06/2016
 * Time: 22:55
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="fight")
 */
class Fight
{
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="string", name="date", nullable=false)
     */
    private $date;

    /**
     * @ORM\ManyToOne(targetEntity="Trainer", inversedBy="player")
     * @ORM\JoinColumn(name="trainer_id1", referencedColumnName="id", nullable=false)
     */
    private $trainer1;

    /**
     * @ORM\ManyToOne(targetEntity="Trainer", inversedBy="opponent")
     * @ORM\JoinColumn(name="trainer_id2", referencedColumnName="id", nullable=false)
     */
    private $trainer2;

    /**
     * @ORM\ManyToOne(targetEntity="Arena", inversedBy="fights")
     * @ORM\JoinColumn(name="arena_id", referencedColumnName="id")
     */
    private $arena;

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
     * Set date
     *
     * @param \DateTime $date
     *
     * @return Fight
     */
    public function setDate($date)
    {
        $this->date = $date;

        return $this;
    }

    /**
     * Get date
     *
     * @return \DateTime
     */
    public function getDate()
    {
        return $this->date;
    }

    /**
     * Set trainer1
     *
     * @param \AppBundle\Entity\Trainer $trainer1
     *
     * @return Fight
     */
    public function setTrainer1(\AppBundle\Entity\Trainer $trainer1 = null)
    {
        $this->trainer1 = $trainer1;

        return $this;
    }

    /**
     * Get trainer1
     *
     * @return \AppBundle\Entity\Trainer
     */
    public function getTrainer1()
    {
        return $this->trainer1;
    }

    /**
     * Set trainer2
     *
     * @param \AppBundle\Entity\Trainer $trainer2
     *
     * @return Fight
     */
    public function setTrainer2(\AppBundle\Entity\Trainer $trainer2 = null)
    {
        $this->trainer2 = $trainer2;

        return $this;
    }

    /**
     * Get trainer2
     *
     * @return \AppBundle\Entity\Trainer
     */
    public function getTrainer2()
    {
        return $this->trainer2;
    }

    /**
     * Set arena
     *
     * @param \AppBundle\Entity\Arena $arena
     *
     * @return Fight
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
}
