/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PICodeName.gui;


import PICodeName.entities.formation;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Slider;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.TextField;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Date;

import services.Servicefrm;
import services.servicePart;

/**
 *
 * @author wassim
 */
public class Listfrm extends Form {

    private TextField tfTitle;
    private TextField tfLocalisation;
    private TextField tfDescription;
   
    
    private Resources theme;

    private Button clear;
    private Button submit;
    private Button but,but1;
    Form current;
     Image icon = FontImage.createMaterial(FontImage.MATERIAL_UPDATE, "Button", 3.0f);
    public SwipeableContainer createRankWidget(Date d,String meet,formation s, String desc,String de,String id) {
         MultiButton button = new MultiButton(meet);
         
        current = this;

        //button.setTextLine1(id);
        button.setPressedIcon(icon);
         but=new Button("Participer");
         but1=new Button("Annuler Participation");
        
                 Boolean test = servicePart.getInstance().checkParticipationFormation("omar.bensalem.1@esprit.tn",id);
                 System.out.println("test="+test);
                 
if(test==true){
 button.addComponent(BorderLayout.NORTH,but);
   button.addActionListener(e
                -> {Dialog.show("Confirmation","Participate to this formation?","ok","Annuler");
                    servicePart.getInstance().addPart("omar.bensalem.1@esprit.tn",id);
                    new Listfrm(current).show();
        
        });

}else {
 button.addComponent(BorderLayout.NORTH,but1);
   button.addActionListener(e
                -> {Dialog.show("Confirmation","Cancel Participation to this formation?","ok","Annuler");
                    servicePart.getInstance().cancelPart("omar.bensalem.1@esprit.tn",id);
                    new Listfrm(current).show();
        
        });
}

      
        button.addLongPressListener(e
                -> Servicefrm.getInstance().deleterdv(Integer.parseInt(id))
        );
        
        button.addLongPressListener(e
                -> {Dialog.show("Confirmation","Delete this formation?","ok","Annuler");});
        
        
        button.addLongPressListener(e
                -> new Listfrm(current).show()
        );
         button.addActionListener(e
                -> new updatefrm(s,id,current).show()
        );
        button.setName("Label_3_3");
        button.setUIID("SmallFontLabel");
        button.setTextLine2(desc);
        button.setTextLine3(d.toString());
        button.setTextLine4(de);
     
       
        return new SwipeableContainer(null
                ,button);
    }

   Button rechercher;

    public Listfrm(Form previous) {
        setTitle("List Formations");

        Container list = new Container(BoxLayout.y());
        list.setScrollableY(true);
        list.setDropTarget(true);


        ArrayList<formation> ev = new ArrayList<formation>();
        ev = Servicefrm.getInstance().getAllrdvs();

            rechercher = new Button("Rechercher une formation");
            list .add(rechercher);
            rechercher.addActionListener((s) -> {
                       current = new Home();
                             new searchFrm(current).show();

            });


        for (formation s : ev) {

            list.add(createRankWidget(s.getDate(),s.getTitle(),s, s.getLocalisation(),s.getDescription(),Integer.toString(s.getId())));
           
        }
        addAll(list);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.show());

    }

}
