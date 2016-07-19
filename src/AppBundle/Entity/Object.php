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
 * @ORM\Entity(repositoryClass="AppBundle\Repository\ObjectRepository")
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
     * @ORM\Column(type="string", length=50, name="name", nullable=false, unique=true)
     */
    private $name;

    /**
     * @ORM\Column(type="integer", name="quantity", nullable=false)
     */
    private $quantity;

    /**
     * @ORM\OneToOne(targetEntity="Image")
     * @ORM\JoinColumn(name="image_id")
     */
    private $image;

    /**
     * @ORM\ManyToOne(targetEntity="Trainer", inversedBy="object")
     * @ORM\JoinColumn(name="trainer_id", referencedColumnName="id")
     */
    private $trainers;

    /**
     * @ORM\ManyToOne(targetEntity="ObjectType", inversedBy="objects")
     * @ORM\JoinColumn(name="objectType_id", referencedColumnName="id", nullable=false)
     */
    private $objectType;

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
     * Set trainers
     *
     * @param \AppBundle\Entity\Trainer $trainers
     *
     * @return Object
     */
    public function setTrainers(\AppBundle\Entity\Trainer $trainers = null)
    {
        $this->trainers = $trainers;

        return $this;
    }

    /**
     * Get trainers
     *
     * @return \AppBundle\Entity\Trainer
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
    public function setObjectType(\AppBundle\Entity\ObjectType $objectType)
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
