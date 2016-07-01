<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 30/06/2016
 * Time: 23:59
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * @ORM\Entity
 * @ORM\Table(name="pokemon")
 */
class Pokemon
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
     * @ORM\Column(type="integer", name="level")
     */
    private $level;

    /**
     * @ORM\Column(type="integer", name="experience")
     */
    private $experience;

    /**
     * @ORM\Column(type="integer", name="hp")
     */
    private $hp;

    /**
     * @ORM\OneToOne(targetEntity="Pokemon")
     * @ORM\JoinColumn(name="pokemonEvolution_id", referencedColumnName="id")
     */
    private $evolution;

    /**
     * @ORM\OneToOne(targetEntity="Npc")
     * @ORM\JoinColumn(name="npc_id")
     */
    private $npc;

    /**
     * @ORM\ManyToOne(targetEntity="Trainer", inversedBy="pokemons")
     * @ORM\JoinColumn(name="trainer_id", referencedColumnName="id")
     */
    private $trainer;

    /**
     * @ORM\ManyToOne(targetEntity="PokemonType", inversedBy="pokemons")
     * @ORM\JoinColumn(name="pokemonType_id", referencedColumnName="id")
     */
    private $pokemonType;

    /**
     * @ORM\ManyToOne(targetEntity="Attack", inversedBy="attacks1")
     * @ORM\JoinColumn(name="attack_id1", referencedColumnName="id")
     */
    private $attack1;

    /**
     * @ORM\ManyToOne(targetEntity="Attack", inversedBy="attacks2")
     * @ORM\JoinColumn(name="attack_id2", referencedColumnName="id")
     */
    private $attack2;

    /**
     * @ORM\ManyToOne(targetEntity="Attack", inversedBy="attacks3")
     * @ORM\JoinColumn(name="attack_id3", referencedColumnName="id")
     */
    private $attack3;

    /**
     * @ORM\ManyToOne(targetEntity="Attack", inversedBy="attacks4")
     * @ORM\JoinColumn(name="attack_id4", referencedColumnName="id")
     */
    private $attack4;

    /**
     * @ORM\ManyToOne(targetEntity="Pokedex", inversedBy="pokedexs")
     * @ORM\JoinColumn(name="pokedex_id", referencedColumnName="id")
     */
    private $pokedex;

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
     * @return Pokemon
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
     * Set level
     *
     * @param integer $level
     *
     * @return Pokemon
     */
    public function setLevel($level)
    {
        $this->level = $level;

        return $this;
    }

    /**
     * Get level
     *
     * @return integer
     */
    public function getLevel()
    {
        return $this->level;
    }

    /**
     * Set experience
     *
     * @param integer $experience
     *
     * @return Pokemon
     */
    public function setExperience($experience)
    {
        $this->experience = $experience;

        return $this;
    }

    /**
     * Get experience
     *
     * @return integer
     */
    public function getExperience()
    {
        return $this->experience;
    }

    /**
     * Set hp
     *
     * @param integer $hp
     *
     * @return Pokemon
     */
    public function setHp($hp)
    {
        $this->hp = $hp;

        return $this;
    }

    /**
     * Get hp
     *
     * @return integer
     */
    public function getHp()
    {
        return $this->hp;
    }

    /**
     * Set evolution
     *
     * @param \AppBundle\Entity\Pokemon $evolution
     *
     * @return Pokemon
     */
    public function setEvolution(\AppBundle\Entity\Pokemon $evolution = null)
    {
        $this->evolution = $evolution;

        return $this;
    }

    /**
     * Get evolution
     *
     * @return \AppBundle\Entity\Pokemon
     */
    public function getEvolution()
    {
        return $this->evolution;
    }

    /**
     * Set npc
     *
     * @param \AppBundle\Entity\Npc $npc
     *
     * @return Pokemon
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
     * Set trainer
     *
     * @param \AppBundle\Entity\Trainer $trainer
     *
     * @return Pokemon
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
     * Set pokemonType
     *
     * @param \AppBundle\Entity\PokemonType $pokemonType
     *
     * @return Pokemon
     */
    public function setPokemonType(\AppBundle\Entity\PokemonType $pokemonType = null)
    {
        $this->pokemonType = $pokemonType;

        return $this;
    }

    /**
     * Get pokemonType
     *
     * @return \AppBundle\Entity\PokemonType
     */
    public function getPokemonType()
    {
        return $this->pokemonType;
    }

    /**
     * Set attack1
     *
     * @param \AppBundle\Entity\Attack $attack1
     *
     * @return Pokemon
     */
    public function setAttack1(\AppBundle\Entity\Attack $attack1 = null)
    {
        $this->attack1 = $attack1;

        return $this;
    }

    /**
     * Get attack1
     *
     * @return \AppBundle\Entity\Attack
     */
    public function getAttack1()
    {
        return $this->attack1;
    }

    /**
     * Set attack2
     *
     * @param \AppBundle\Entity\Attack $attack2
     *
     * @return Pokemon
     */
    public function setAttack2(\AppBundle\Entity\Attack $attack2 = null)
    {
        $this->attack2 = $attack2;

        return $this;
    }

    /**
     * Get attack2
     *
     * @return \AppBundle\Entity\Attack
     */
    public function getAttack2()
    {
        return $this->attack2;
    }

    /**
     * Set attack3
     *
     * @param \AppBundle\Entity\Attack $attack3
     *
     * @return Pokemon
     */
    public function setAttack3(\AppBundle\Entity\Attack $attack3 = null)
    {
        $this->attack3 = $attack3;

        return $this;
    }

    /**
     * Get attack3
     *
     * @return \AppBundle\Entity\Attack
     */
    public function getAttack3()
    {
        return $this->attack3;
    }

    /**
     * Set attack4
     *
     * @param \AppBundle\Entity\Attack $attack4
     *
     * @return Pokemon
     */
    public function setAttack4(\AppBundle\Entity\Attack $attack4 = null)
    {
        $this->attack4 = $attack4;

        return $this;
    }

    /**
     * Get attack4
     *
     * @return \AppBundle\Entity\Attack
     */
    public function getAttack4()
    {
        return $this->attack4;
    }

    /**
     * Set pokedex
     *
     * @param \AppBundle\Entity\Pokedex $pokedex
     *
     * @return Pokemon
     */
    public function setPokedex(\AppBundle\Entity\Pokedex $pokedex = null)
    {
        $this->pokedex = $pokedex;

        return $this;
    }

    /**
     * Get pokedex
     *
     * @return \AppBundle\Entity\Pokedex
     */
    public function getPokedex()
    {
        return $this->pokedex;
    }
}
