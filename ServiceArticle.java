/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Article;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



        
/**
 *
 * @author user16
 */
public class ServiceArticle {
    
    private static ServiceArticle instance = null;
    
    private static boolean resultOk = true;
    
    private ConnectionRequest req;
    
    public static ServiceArticle getInstance(){
        if(instance == null)
            instance = new ServiceArticle();
        return instance;
    }
    
    public ServiceArticle(){
        req = new ConnectionRequest();
    }
    
    //ajout
    public void ajoutArticle(Article article){
        
        String url=Statics.BASE_URL+"/article/addArticle?id="+article.getId()+"&titre="+article.getTitre()
                +"&auteur="+article.getAuteur()+"&corpsTexte="+article.getCorpsTexte();
        
        req.setUrl(url);
        req.addResponseListener( (e)-> {
            String str = new String(req.getResponseData());
            System.out.println("data == "+str);
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    //affichage
    public ArrayList<Article>affichageArticles(){
        ArrayList<Article> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/article/displayArticles";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt){
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String, Object>mapArticles = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    
                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapArticles.get("root");
                    
                    for (Map<String,Object> obj : listOfMaps) {
                    Article re = new Article();
                    
                    float id = Float.parseFloat(obj.get("id").toString());
                    
                    String titre = obj.get("titre").toString();
                    String auteur = obj.get("auteur").toString();
                    String corpsTexte = obj.get("corpsTexte").toString();
                        
                    re.setId((int) id);
                    re.setAuteur(auteur);
                    re.setTitre(titre);
                    re.setCorpsTexte(corpsTexte);
                    
                    result.add(re);
                    
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result ;
        
    }

    public Article DetailArticle(int id, Article article)
    {
        String url = Statics.BASE_URL+"/article/detailArticle?"+id;
        req.setUrl(url);
        
        String str = new String (req.getResponseData());
        req.addResponseListener(((evt) -> {
            
            JSONParser jsonp = new JSONParser();
            
            try{
                Map<String, Object> obj = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
            
                article.setId(Integer.parseInt(obj.get("id").toString()));
                article.setTitre(obj.get("titre").toString());
                article.setAuteur(obj.get("auteur").toString());
                article.setCorpsTexte(obj.get("corpsTexte").toString());
                
            }
            catch(IOException ex)
            {
                System.out.println("Error related to sql :( "+ ex.getMessage());
            }
            
            
            System.out.println("data === "+str);
            
        }));
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return article;
    }
    
    public boolean deleteArticle(int id){
    String url = Statics.BASE_URL+"/article/deleteArticle?id="+id;
    
    req.setUrl(url);
    
    req.addResponseListener(new ActionListener<NetworkEvent>(){
        @Override 
        public void actionPerformed(NetworkEvent evt){
        req.removeResponseCodeListener(this);
        }
    });
    
    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;
    }
    
    public boolean modifierArticle(Article article){
        String url = Statics.BASE_URL+"/article/updateArticle?id="+article.getId()
                +"&titre="+article.getTitre()+"&auteur="+article.getAuteur()+"&corpsTexte="+article.getCorpsTexte();
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener <NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200 ; 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;
    }
}

