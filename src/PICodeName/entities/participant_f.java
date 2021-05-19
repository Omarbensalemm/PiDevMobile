/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PICodeName.entities;

/**
 *
 * @author Omar Ben Salem
 */
public class participant_f {
    
    private int id;
    private String nom;
    private String mail;
    

    public participant_f(String nom, String mail) {
        this.nom = nom;
        this.mail = mail;
    }
     public participant_f(String nom, String mail,int id) {
        
         this.nom = nom;
        this.mail = mail;
         this.id = id;
    }
     public participant_f(int id,String nom, String mail) {
         this.id = id;
         this.nom = nom;
        this.mail = mail;
    }

    public participant_f(String mail) {
        this.mail = mail;
    }

    public participant_f() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getNom() {
        return nom;
    }
      public int getId() {
        return id;
    }
       public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "participant_f{" + "nom=" + nom + ", mail=" + mail + '}';
    }
    
}

