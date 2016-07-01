<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 30/06/2016
 * Time: 22:10
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="object")
 */
class Object
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
     * @ORM\Column(type="integer", name="quantity")
     */
    private $quantity;

    /**
     * @ORM\OneToOne(targetEntity="Image")
     * @ORM\JoinColumn(name="image_id")
     */
    private $image;

    /**
     * @ORM\OneToMany(targetEntity="Trainer", mappedBy="object")
     */
    private $trainers;

    /**
     * @ORM\ManyToOne(targetEntity="ObjectType", inversedBy="objects")
     * @ORM\JoinColumn(name="objectType_id", referencedColumnName="id")
     */
    private $objectType;

    /**
     * Constructor
     */
    public function __construct()
    {
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
     * Set name
     *
     * @param string $name
     *
     * @return Object
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
     * Set quantity
     *
     * @param integer $quantity
     *
     * @return Object
     */
    public function setQuantity($quantity)
    {
        $this->quantity = $quantity;

        return $this;
    }

    /**
     * Get quantity
     *
     * @return integer
     */
    public function getQuantity()
    {
        return $this->quantity;
    }

    /**
     * Set image
     *
     * @param \AppBundle\Entity\Image $image
     *
     * @return Object
     */
    public function setImage(\AppBundle\Entity\Image $image = null)
    {
        $this->image = $image;

        return $this;
    }

    /**
     * Get image
     *
     * @return \AppBundle\Entity\Image
     */
    public function getImage()
    {
        return $this->image;
    }

    /**
     * Add trainer
     *
     * @param \AppBundle\Entity\Trainer $trainer
     *
     * @return Object
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

    /**
     * Set objectType
     *
     * @param \AppBundle\Entity\ObjectType $objectType
     *
     * @return Object
     */
    public function setObjectType(\AppBundle\Entity\ObjectType $objectType = null)
    {
        $this->objectType = $objectType;

        return $this;
    }

    /**
     * Get objectType
     *
     * @return \AppBundle\Entity\ObjectType
     */
    public function getObjectType()
    {
        return $this->objectType;
    }
}
