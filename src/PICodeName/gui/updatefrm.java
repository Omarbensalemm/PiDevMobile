/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PICodeName.gui;

import PICodeName.entities.formation;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import services.Servicefrm;

/**
 *
 * @author wassim
 */
public class updatefrm extends Form{
Form current;
    public updatefrm(formation e ,String id,Form previous) {
        current = new Home();
        System.out.println(e.toString());
        
        setTitle("Update Formation");
        setLayout(BoxLayout.y());
        TextField tfTitle = new TextField(e.getTitle());
        TextField tfDescription = new TextField(e.getDescription());
        TextField tfLocalisation = new TextField(e.getLocalisation());
        Picker date = new Picker();
        date.setType(Display.PICKER_TYPE_DATE_AND_TIME);
        Button btnValider = new Button("Update Formation");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfTitle.getText().length() == 0 || tfDescription.getText().length() == 0|| tfLocalisation.getText().length() == 0) {
                    Dialog.show("Alert", "Please Fill All fields", new Command("OK"));
                } else {
                    
                         try {
                       formation e = new formation( tfTitle.getText(),tfDescription.getText(),date.getDate(),tfLocalisation.getText());
                        if (Servicefrm.getInstance().updaterdv(e,Integer.parseInt(id))) {
                            Dialog.show("Success", "Formation Updated", new Command("OK"));
                             new Listfrm(current).show();
                        } else {
                            Dialog.show("ERROR", "Server Error", new Command("OK"));
                        }
                    } catch (Exception ex) {
                        Dialog.show("ERROR", "ERROR", new Command("OK"));
                    }
                }
            }
        });
        addAll(tfTitle,tfDescription,date,tfLocalisation, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e1 -> previous.show());
    }

    
}