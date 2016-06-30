<?php

/**
 * Created by PhpStorm.
 * User: charly
 * Date: 27/06/2016
 * Time: 20:44
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="trainer")
 */
class Trainer
{

    /**
     * @ORM\Column(type="integer", name="id_trainer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=50, name="name")
     */
    private $name;

    /**
     * @ORM\Column(type="string", length=50, name="login")
     */
    private $login;

    /**
     * @ORM\Column(type="string", length=20, name="password")
     */
    private $password;

    /**
     * @ORM\Column(type="boolean", name="isMaster")
     */
    private $isMaster;

    /**
     * @ORM\OneToOne(targetEntity="AppBundle\Entity\Npc")
     * @ORM\JoinColumn(name="id_npc")
     */
    private $npc;

    /**
     * @ORM\OneToOne(targetEntity="AppBundle\Entity\Image", cascade={"persist"})
     */
    private $image;


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
     * @return Trainer
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
     * Set login
     *
     * @param string $login
     *
     * @return Trainer
     */
    public function setLogin($login)
    {
        $this->login = $login;

        return $this;
    }

    /**
     * Get login
     *
     * @return string
     */
    public function getLogin()
    {
        return $this->login;
    }

    /**
     * Set password
     *
     * @param string $password
     *
     * @return Trainer
     */
    public function setPassword($password)
    {
        $this->password = $password;

        return $this;
    }

    /**
     * Get password
     *
     * @return string
     */
    public function getPassword()
    {
        return $this->password;
    }

    /**
     * Set isMaster
     *
     * @param boolean $isMaster
     *
     * @return Trainer
     */
    public function setIsMaster($isMaster)
    {
        $this->isMaster = $isMaster;

        return $this;
    }

    /**
     * Get isMaster
     *
     * @return boolean
     */
    public function getIsMaster()
    {
        return $this->isMaster;
    }

    /**
     * Set npc
     *
     * @param \AppBundle\Entity\Npc $npc
     *
     * @return Trainer
     */
    public function setNpc(\AppBundle\Entity\Npc $npc = null)
    {
        $this->npc = $npc;

        return $this;
    }

    /**
     * Get npc
     *
     * @return \AppBundle\Entity\Npc
     */
    public function getNpc()
    {
        return $this->npc;
    }

    /**
     * Set image
     *
     * @param \AppBundle\Entity\Image $image
     *
     * @return Trainer
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
}
