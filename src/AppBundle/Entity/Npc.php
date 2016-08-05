<?php

/**
 * Created by PhpStorm.
 * User: charly
 * Date: 27/06/2016
 * Time: 22:08
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use JMS\Serializer\Annotation\ExclusionPolicy;
use JMS\Serializer\Annotation\Expose;

/**
 * @ORM\Entity(repositoryClass="AppBundle\Repository\NpcRepository")
 * @ORM\Table(name="npc")
 * @ExclusionPolicy("All")
 */
class Npc
{
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=50, name="name", nullable=false)
     * @Expose
     */
    private $name;

    /**
     * @ORM\ManyToOne(targetEntity="NpcType", inversedBy="npcs")
     * @ORM\JoinColumn(name="npctype_id", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $npcType;

    /**
     * @ORM\OneToOne(targetEntity="Trainer")
     * @ORM\JoinColumn(name="trainer_id", referencedColumnName="id")
     */
    private $trainer;

    /**
     * @ORM\ManyToOne(targetEntity="Position", inversedBy="npcs")
     * @ORM\JoinColumn(name="position_id", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $position;

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
     * @return Npc
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
     * Set npcType
     *
     * @param \AppBundle\Entity\NpcType $npcType
     *
     * @return Npc
     */
    public function setNpcType(\AppBundle\Entity\NpcType $npcType)
    {
        $this->npcType = $npcType;

        return $this;
    }

    /**
     * Get npcType
     *
     * @return \AppBundle\Entity\NpcType
     */
    public function getNpcType()
    {
        return $this->npcType;
    }

    /**
     * Set trainer
     *
     * @param \AppBundle\Entity\Trainer $trainer
     *
     * @return Npc
     */
    public function setTrainer(\AppBundle\Entity\Trainer $trainer = null)
    {
        $this->trainer = $trainer;

        return $this;
    }

    /**
     * Get trainer
     *
     * @return \AppBundle\Entity\Trainer
     */
    public function getTrainer()
    {
        return $this->trainer;
    }

    /**
     * Set position
     *
     * @param \AppBundle\Entity\Position $position
     *
     * @return Npc
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
