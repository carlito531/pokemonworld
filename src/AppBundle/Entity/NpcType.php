<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 28/06/2016
 * Time: 20:13
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="npcType")
 */
class NpcType
{

    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=50, name="type")
     */
    private $type;

    /**
     * @ORM\OneToMany(targetEntity="Npc", mappedBy="npctype")
     */
    private $npcs;


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
     * Set type
     *
     * @param string $type
     *
     * @return NpcType
     */
    public function setType($type)
    {
        $this->type = $type;

        return $this;
    }

    /**
     * Get type
     *
     * @return string
     */
    public function getType()
    {
        return $this->type;
    }
    /**
     * Constructor
     */
    public function __construct()
    {
        $this->npcs = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add npc
     *
     * @param \AppBundle\Entity\Npc $npc
     *
     * @return NpcType
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
}
