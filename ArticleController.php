<?php

namespace App\Controller;

use Sensio\Bundle\FrameworkExtraBundle\Configuration\Method;
use Symfony\Component\Serializer\Encoder\JsonEncoder;
use Symfony\Component\Serializer\Normalizer\NormalizerInterface ;
use Symfony\Component\Serializer\Serializer;
use App\Entity\Article;
use App\Form\ArticleType;
use App\Repository\ArticleRepository;
use Dompdf\Dompdf;
use Dompdf\Options;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\JsonResponse;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Serializer\Normalizer\ObjectNormalizer;
use Symfony\Component\Validator\Constraints\Json;

/**
 * @Route("/article")
 */
class ArticleController extends AbstractController
{
    /**
     * @Route("/", name="article_index", methods={"GET"})
     */
    public function index(ArticleRepository $articleRepository): Response
    {
        return $this->render('article/index.html.twig', [
            'articles' => $articleRepository->findAll(),
        ]);
    }

    /**
     * @Route("/new", name="article_new", methods={"GET","POST"})
     */
    public function new(Request $request): Response
    {
        $article = new Article();
        $form = $this->createForm(ArticleType::class, $article);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($article);
            $entityManager->flush();

            return $this->redirectToRoute('article_index');
        }

        return $this->render('article/new.html.twig', [
            'article' => $article,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/show/{id}", name="article_show", methods={"GET"})
     */
    public function show(Article $article): Response
    {
        return $this->render('article/show.html.twig', [
            'article' => $article,
        ]);
    }

    /**
     * @Route("/{id}/edit", name="article_edit", methods={"GET","POST"})
     */
    public function edit(Request $request, Article $article): Response
    {
        $form = $this->createForm(ArticleType::class, $article);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $this->getDoctrine()->getManager()->flush();

            return $this->redirectToRoute('article_index');
        }

        return $this->render('article/edit.html.twig', [
            'article' => $article,
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/delete/{id}", name="article_delete", methods={"DELETE"})
     */
    public function delete(Request $request, Article $article): Response
    {
        if ($this->isCsrfTokenValid('delete'.$article->getId(), $request->request->get('_token'))) {
            $entityManager = $this->getDoctrine()->getManager();
            $entityManager->remove($article);
            $entityManager->flush();
        }

        return $this->redirectToRoute('article_index');
    }

    /**
     * @Route("/searchOffreajax ", name="ajaxsearch-article")
     */
    public function searchOffreajax(Request $request,ArticleRepository $repository)
    {
        $repository = $this->getDoctrine()->getRepository(Article::class);
        $requestString=$request->get('searchValue');
        $articles = $repository->findArticlebyname($requestString);

        return $this->render('article/articleajax.html.twig', [
            "articles"=>$articles
        ]);
    }

    /**
     * @Route("/triecroissant ", name="trie_croissant")
     */

    public function trie_croissant(ArticleRepository $repository)
    {
        $repository = $this->getDoctrine()->getRepository(Article::class);
        $articles = $repository->triecroissant();
        return $this->render('article/index.html.twig', [
            "articles"=>$articles
        ]);
    }

    /**
     * @Route("/triedecroissant ", name="trie_decroissant")
     */

    public function trie_decroissant(ArticleRepository $repository)
    {
        $repository = $this->getDoctrine()->getRepository(Article::class);
        $articles = $repository->triedecroissant();
        return $this->render('article/index.html.twig', [
            "articles"=>$articles
        ]);
    }

    /**
     * @Route("/pdf", name="pdf",methods={"GET"})
     */
    public function pdf(ArticleRepository $repository)
    {
        $pdfOptions = new Options();
        $pdfOptions->set('defaultFont', 'Arial');

        // Instantiate Dompdf with our options
        $dompdf = new Dompdf($pdfOptions);

        $articles=$repository->findAll();

        // Retrieve the HTML generated in our twig file

        $html = $this->renderView('article/pdf.html.twig', [
            "articles"=>$articles
        ]);


        // Load HTML to Dompdf
        $dompdf->loadHtml($html);

        // (Optional) Setup the paper size and orientation 'portrait' or 'portrait'
        $dompdf->setPaper('A4', 'portrait');

        // Render the HTML as PDF
        $dompdf->render();

        // Output the generated PDF to Browser (inline view)
        $dompdf->stream("mypdf.pdf",array("Attachment"=>0));
    }

    /*************** Affichage des articles *************/

    /**
     *@Route("/displayArticles",name="display_article")
     */
    public function AllArtAction()
    {
        $article = $this->GetDoctrine()->getManager()->getRepository(Article::class)->findAll();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($article);

        return new JsonResponse($formatted);
    }

    /******** Supprimer Article ******/
    /**
     *@Route("/deleteArticle", name="delete_article")
     */

    function deleteArticleAction(Request $request)
    {
        $id = $request->get("id");

        $em=$this->getDoctrine()->getManager();
        $article = $em->getRepository(Article::class)->find($id);
        if($article!=null){
            $em->remove($article);
            $em->flush();

            $serialize = new Serializer([new ObjectNormalizer()]);
            $formatted = $serialize->normalize("Article supprimé avec succées.");
            return new JsonResponse($formatted);
        }
        return  new JsonResponse("L'id de l'article est invalide");
    }

    /******** Ajouter reclamation ******/
    /**
     *@Route("/addArticle", name="add_article")
     * @Method("POST")
     */

    public function ajouterArticleAction(Request $request)
    {
        $article = new Article();
        $id = $request->query->get("id");
        $titre = $request->query->get("titre");
        $auteur = $request->query->get("auteur");
        $corpsTexte = $request->query->get("corpsTexte");
        $em = $this->getDoctrine()->getManager();

        $article->setId($id);
        $article->setTitre($titre);
        $article->setAuteur($auteur);
        $article->setCorpsTexte($corpsTexte);

        $em->persist($article);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($article);
        return new JsonResponse($formatted);

    }

    /******** Modifier Article ******/
    /**
     *@Route("/updateArticle", name="update_article")
     * @Method("PUT")
     */
    public function modifierArticleAction(Request $request)
    {
        $em = $this->getDoctrine()->getManager();
        $article = $this->getDoctrine()->getManager()
                    ->getRepository(Article::class)
            ->find($request->get("id"));

        $article->setTitre($request->get("titre"));
        $article->setAuteur($request->get("auteur"));
        $article->setCorpsTexte($request->get("corpsTexte"));

        $em->persist($article);
        $em->flush();
        $serializer = new Serializer([new ObjectNormalizer()]);
        $formatted = $serializer->normalize($article);
        return new JsonResponse("Article mdoifié avec succes");

    }
    /******************Detail Reclamation*****************************************/

    /**
     * @Route("/detailArticle", name="detail_article")
     * @Method("GET")
     */

    //Detail Reclamation
    public function detailArticle(Request $request)
    {
        $id = $request->get("id");

        $em = $this->getDoctrine()->getManager();
        $article = $this->getDoctrine()->getManager()->getRepository(Article::class)->find($id);
        $encoder = new JsonEncoder();
        $normalizer = new ObjectNormalizer();
        $normalizer->setCircularReferenceHandler(function ($object) {
            return $object->getDescription();
        });
        $serializer = new Serializer([$normalizer], [$encoder]);
        $formatted = $serializer->normalize($article);
        return new JsonResponse($formatted);
    }

}
