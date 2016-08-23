<?php
/**
 * Created by PhpStorm.
 * User: charly
 * Date: 30/06/2016
 * Time: 23:59
 */

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use JMS\Serializer\Annotation\ExclusionPolicy;
use JMS\Serializer\Annotation\Expose;
use JMS\Serializer\Annotation\XmlRoot;

/**
 * @ORM\Entity(repositoryClass="AppBundle\Repository\PokemonRepository")
 * @ORM\Table(name="pokemon")
 * @ExclusionPolicy("All")
 * @XmlRoot("pokemon")
 */
class Pokemon
{
    /**
     * @ORM\Column(type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     * @Expose
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=50, name="name", nullable=false)
     * @Expose
     */
    private $name;

    /**
     * @ORM\Column(type="integer", name="level", nullable=false)
     * @Expose
     */
    private $level;

    /**
     * @ORM\Column(type="integer", name="experience", nullable=false)
     * @Expose
     */
    private $experience;

    /**
     * @ORM\Column(type="integer", name="hp", nullable=false)
     * @Expose
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
     * @ORM\ManyToOne(targetEntity="PokemonFightState")
     * @ORM\JoinColumn(referencedColumnName="id")
     * @Expose
     */
    private $pokemonFightState;

    /**
     * @ORM\ManyToOne(targetEntity="PokemonType", inversedBy="pokemons")
     * @ORM\JoinColumn(name="pokemonType_id", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $pokemonType;

    /**
     * @ORM\ManyToOne(targetEntity="Attack", inversedBy="pokemons1")
     * @ORM\JoinColumn(name="attack_id1", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $attack1;

    /**
     * @ORM\ManyToOne(targetEntity="Attack", inversedBy="pokemons2")
     * @ORM\JoinColumn(name="attack_id2", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $attack2;

    /**
     * @ORM\ManyToOne(targetEntity="Attack", inversedBy="pokemons3")
     * @ORM\JoinColumn(name="attack_id3", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $attack3;

    /**
     * @ORM\ManyToOne(targetEntity="Attack", inversedBy="pokemons4")
     * @ORM\JoinColumn(name="attack_id4", referencedColumnName="id", nullable=false)
     * @Expose
     */
    private $attack4;

    /**
     * @ORM\ManyToOne(targetEntity="Position", inversedBy="pokemons")
     * @ORM\JoinColumn(name="position_id", referencedColumnName="id")
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
    public function setPokemonType(\AppBundle\Entity\PokemonType $pokemonType)
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
    public function setAttack1(\AppBundle\Entity\Attack $attack1)
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
    public function setAttack2(\AppBundle\Entity\Attack $attack2)
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
    public function setAttack3(\AppBundle\Entity\Attack $attack3)
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
    public function setAttack4(\AppBundle\Entity\Attack $attack4)
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
     * Set position
     *
     * @param \AppBundle\Entity\Position $position
     *
     * @return Pokemon
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


    /**
     * Set pokemonFightState
     *
     * @param \AppBundle\Entity\PokemonFightState $pokemonFightState
     *
     * @return Pokemon
     */
    public function setPokemonFightState(\AppBundle\Entity\PokemonFightState $pokemonFightState)
    {
        $this->pokemonFightState = $pokemonFightState;

        return $this;
    }

    /**
     * Get pokemonFightState
     *
     * @return \AppBundle\Entity\PokemonFightState
     */
    public function getPokemonFightState()
    {
        return $this->pokemonFightState;
    }
}
