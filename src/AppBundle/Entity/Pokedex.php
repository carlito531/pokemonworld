<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 01/07/2016
 * Time: 00:39
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity(repositoryClass="AppBundle\Repository\PokedexRepository")
 * @ORM\Table(name="pokedex")
 */
class Pokedex
{
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @ORM\OneToMany(targetEntity="Pokemon", mappedBy="pokedex")
     */
    private $pokedexs;
    /**
     * Constructor
     */
    public function __construct()
    {
        $this->pokedexs = new \Doctrine\Common\Collections\ArrayCollection();
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
     * Add pokedex
     *
     * @param \AppBundle\Entity\Pokemon $pokedex
     *
     * @return Pokedex
     */
    public function addPokedex(\AppBundle\Entity\Pokemon $pokedex)
    {
        $this->pokedexs[] = $pokedex;

        return $this;
    }

    /**
     * Remove pokedex
     *
     * @param \AppBundle\Entity\Pokemon $pokedex
     */
    public function removePokedex(\AppBundle\Entity\Pokemon $pokedex)
    {
        $this->pokedexs->removeElement($pokedex);
    }

    /**
     * Get pokedexs
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getPokedexs()
    {
        return $this->pokedexs;
    }
}
