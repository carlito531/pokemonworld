<?php

/**
 * Created by PhpStorm.
 * User: charly
 * Date: 27/06/2016
 * Time: 22:08
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="npc")
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
     * @ORM\Column(type="string", length=50, name="name")
     */
    private $name;

    /**
     * @ORM\ManyToOne(targetEntity="NpcType", inversedBy="npcs")
     * @ORM\JoinColumn(name="npctype_id", referencedColumnName="id")
     */
    private $npctype;

    /**
     * @ORM\OneToOne(targetEntity="Trainer")
     * @ORM\JoinColumn(name="trainer_id", referencedColumnName="id")
     */
    private $trainer;


    /**
     * Set npcType
     *
     * @param \AppBundle\Entity\NpcType $npcType
     *
     * @return Npc
     */
    public function setNpcType(\AppBundle\Entity\NpcType $npcType = null)
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
     * Get id
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set id
     *
     * @param integer $id
     *
     * @return Npc
     */
    public function setId($id)
    {
        $this->id = $id;

        return $this;
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
}
