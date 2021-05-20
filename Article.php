<?php

namespace App\Entity;

use App\Repository\ArticleRepository;
use Doctrine\ORM\Mapping as ORM;
use phpDocumentor\Reflection\Types\Integer;
use Symfony\Component\Serializer\Annotation\Groups;

/**
 * @ORM\Entity(repositoryClass=ArticleRepository::class)
 */
class Article
{
    /**
     * @ORM\Id
     * @ORM\GeneratedValue
     * @ORM\Column(type="integer")
     */
    private $id;

    /**
     * @ORM\Column(type="string", length=100)
     * @Groups ("post:read")
     */
    private $titre;

    /**
     * @ORM\Column(type="string", length=50)
     * @Groups ("post:read")
     */
    private $auteur;

    /**
     * @ORM\Column(type="string", length=1000)
     * @Groups ("post:read")
     */
    private $corpsTexte;

    /**
     * @return mixed
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param mixed $id
     */
    public function setId($id): void
    {
        $this->id = $id;
    }

    /**
     * @return mixed
     */
    public function getTitre()
    {
        return $this->titre;
    }

    /**
     * @param mixed $titre
     */
    public function setTitre($titre): void
    {
        $this->titre = $titre;
    }

    /**
     * @return mixed
     */
    public function getAuteur()
    {
        return $this->auteur;
    }

    /**
     * @param mixed $auteur
     */
    public function setAuteur($auteur): void
    {
        $this->auteur = $auteur;
    }

    /**
     * @return mixed
     */
    public function getCorpsTexte()
    {
        return $this->corpsTexte;
    }

    /**
     * @param mixed $corpsTexte
     */
    public function setCorpsTexte($corpsTexte): void
    {
        $this->corpsTexte = $corpsTexte;
    }
    /**
     * @ORM\Column(type="string", length=100)
     * @Groups ("post:read")
     */


}
