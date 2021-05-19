/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PICodeName.gui;

import PICodeName.entities.formation;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.SwipeableContainer;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import java.util.ArrayList;
import java.util.Date;
import services.Servicefrm;

/**
 *
 * @author Omar Ben Salem
 */

public class searchFrm  extends Form {
        Form current;
     Image icon = FontImage.createMaterial(FontImage.MATERIAL_UPDATE, "Button", 3.0f);

    public searchFrm(Form previous) {
     Container list = new Container(BoxLayout.y());

        list.setScrollableY(true);
        list.setDropTarget(true);
        TextField trech = new TextField("", "Recherchez iÃ§i..");
        
        Button brech = new Button("Rechercher");
        list.add(trech);
        list.add(brech);

            brech.addActionListener((s) -> {
             ArrayList<formation> ev = new ArrayList<formation>();
        ev = Servicefrm.getInstance().SearchFormation(trech.getText());                
         for (formation f : ev) {

            list.add(createRankWidget(f.getDate(),f.getTitle(),f, f.getLocalisation(),f.getDescription(),Integer.toString(f.getId())));
             refreshTheme();
        }
            });

        addAll(list);
         getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.show());



    }
      public SwipeableContainer createRankWidget(Date d,String meet,formation s, String desc,String de,String id) {
         MultiButton button = new MultiButton(meet);
        button.setIcon(icon);
        current = this;

        //button.setTextLine1(id);
        button.setPressedIcon(icon);
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
        return new SwipeableContainer(null,
                button);
    }

}
