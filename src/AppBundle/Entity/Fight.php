<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 30/06/2016
 * Time: 22:55
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use JMS\Serializer\Annotation\ExclusionPolicy;
use JMS\Serializer\Annotation\Expose;

/**
 * @ORM\Entity(repositoryClass="AppBundle\Repository\FightRepository")
 * @ORM\Table(name="fight")
 * @ExclusionPolicy("All")
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
     * @Expose
     */
    private $date;

    /**
     * @ORM\OneToOne(targetEntity="FightState")
     * @Expose
     */
    private $fightState;

    /**
     * @ORM\ManyToOne(targetEntity="Trainer", inversedBy="player")
     * @ORM\JoinColumn(name="trainer_id1", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $trainer1;

    /**
     * @ORM\ManyToOne(targetEntity="Trainer", inversedBy="opponent")
     * @ORM\JoinColumn(name="trainer_id2", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $trainer2;

    /**
     * @ORM\ManyToOne(targetEntity="Arena", inversedBy="fights")
     * @ORM\JoinColumn(name="arena_id", referencedColumnName="id")
     * @Expose
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
     * @param string $date
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
     * @return string
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
    public function setTrainer1(\AppBundle\Entity\Trainer $trainer1)
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
    public function setTrainer2(\AppBundle\Entity\Trainer $trainer2)
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

    /**
     * Set fightState
     *
     * @param \AppBundle\Entity\FightState $fightState
     *
     * @return Fight
     */
    public function setFightState(\AppBundle\Entity\FightState $fightState = null)
    {
        $this->fightState = $fightState;

        return $this;
    }

    /**
     * Get fightState
     *
     * @return \AppBundle\Entity\FightState
     */
    public function getFightState()
    {
        return $this->fightState;
    }
}
