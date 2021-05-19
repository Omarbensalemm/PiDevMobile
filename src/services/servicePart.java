/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import PICodeName.entities.formation;
import PICodeName.entities.participant_f;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import org.json.JSONObject;

/**
 *
 * @author Omar Ben Salem
 */
public class servicePart {
    public static servicePart instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private servicePart() {
        req = new ConnectionRequest();
    }

    public static servicePart getInstance() {
        if (instance == null) {
            instance = new servicePart();
        }
        return instance;
    }

    

    public void addPart(String ma,String nom)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl( "http://127.0.0.1:8000/webservicePart");
        con.addArgument("mail", ma);
        con.addArgument("idF",nom);
        NetworkManager.getInstance().addToQueueAndWait(con);
        con.getResponseData();
    }
     public void cancelPart(String ma,String nom)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl( "http://127.0.0.1:8000/webserviceAnnulerPart");
        con.addArgument("mail", ma);
        con.addArgument("idF",nom);
        NetworkManager.getInstance().addToQueueAndWait(con);
        con.getResponseData();
    }

    public boolean checkParticipationFormation(String mail,String nom)
    {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://127.0.0.1:8000/webserviceCheckPart");
                System.out.println(nom); System.out.println(mail);

        con.addArgument("mail", mail);
        con.addArgument("idF", nom);
        NetworkManager.getInstance().addToQueueAndWait(con);
        String response = new String(con.getResponseData());
        System.out.println(response);
        if(response.equals("\"OK\"")) return true;
        return false;
    }

  

}
