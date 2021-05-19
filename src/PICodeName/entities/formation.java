/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PICodeName.entities;

import java.util.Date;

/**
 *
 * @author Omar Ben Salem
 */
public class formation {
    
    private int id;
    private String description;
    private Date date;
    private String title;
    private String localisation;
    
    
    
    public formation(int id, String title ,Date date, String description,String localisation ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.localisation = localisation;
            
    }
     public formation(String description, Date date ,String title ,String localisation ,int id) {
        this.description = description;
        this.date = date;
        this.title = title;
        this.localisation = localisation;
        this.id = id;   
    }
      public formation(String title ,String description ,Date date, String localisation ) {
        this.description = description;
        this.date = date;
        this.title = title;
        this.localisation = localisation;
       
    }
    
    
    
    
    
    
    
    
   
     public formation(int id) {
        this.id = id;
    }
     public formation(Date date,String title, String description) {
        this.title = title;
        this.description = description;
        this.date = date;
    }

  public formation() {
    }

    public formation(String string, String string0, String string1, String string2, String string3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    @Override
    public String toString() {
        return "formation{" + "id=" + id + ", title=" + title + ", description=" + description + ", date=" + date + ", localisation=" + localisation + '}';
    }
    
}
