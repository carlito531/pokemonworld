<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 28/06/2016
 * Time: 20:13
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use JMS\Serializer\Annotation\ExclusionPolicy;
use JMS\Serializer\Annotation\Expose;

/**
 * @ORM\Entity(repositoryClass="AppBundle\Repository\NpcTypeRepository")
 * @ORM\Table(name="npcType")
 * @ExclusionPolicy("All")
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
     * @ORM\Column(type="string", length=50, name="type", nullable=false, unique=true)
     * @Expose
     */
    private $type;

    /**
     * @ORM\OneToMany(targetEntity="Npc", mappedBy="npcType")
     */
    private $npcs;
    /**
     * Constructor
     */
    public function __construct()
    {
        $this->npcs = new \Doctrine\Common\Collections\ArrayCollection();
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
