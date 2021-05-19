/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import PICodeName.entities.formation;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.ComboBox;
import com.codename1.ui.events.ActionListener;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author wassim
 */
public class Servicefrm {
    public ArrayList<formation> events;
    
    public static Servicefrm instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private Servicefrm() {
        req = new ConnectionRequest();
    }

    public static Servicefrm getInstance() {
        if (instance == null) {
            instance = new Servicefrm();
        }
        return instance;
    }

    public boolean addrdv(formation e) {
      String to = "omar.bensalem.1@esprit.tn";
        String host = "smtp.gmail.com";
        final String mail = "handclasp1@gmail.com";
        final String password = "handclasp11223344";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mail, password);
            }
        });

        try {
            MimeMessage m = new MimeMessage(session);
            m.setFrom(mail);
            m.addRecipients(javax.mail.Message.RecipientType.TO, to);
            m.setSubject("MEETING");
            m.setText("Formation ajoutee :");
            Transport.send(m);

        } catch (MessagingException ex) {
        }
        JSONObject json = new JSONObject();
        try {
            ConnectionRequest post = new ConnectionRequest() {
                @Override
                protected void buildRequestBody(OutputStream os) throws IOException {
                    os.write(json.toString().getBytes("UTF-8"));
                }

                @Override
                protected void readResponse(InputStream input) throws IOException {
                }

                @Override
                protected void postResponse() {
                }
            };
            /*json.put("title", e.getTitle());
            json.put("type", e.getType());
            json.put("description", e.getDescription());
            json.put("localitation", e.getLocalitation());
            json.put("id_societe", 1);*/

            json.put("localisation", e.getLocalisation());
            json.put("date", e.getDate());
            json.put("description", e.getDescription());
            json.put("title", e.getTitle());
           

            post.setUrl("http://127.0.0.1:8000/webservicesaddrdv");
            post.setPost(true);
            post.setContentType("application/json");
            post.addArgument("body", json.toString());
            String bodyToString = json.toString();
            NetworkManager.getInstance().addToQueueAndWait(post);
            Map<String, Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(post.getResponseData()), "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;

    }
     public boolean updaterdv(formation e,int id) {
        JSONObject json = new JSONObject();
        try {
            ConnectionRequest post = new ConnectionRequest() {
                @Override
                protected void buildRequestBody(OutputStream os) throws IOException {
                    os.write(json.toString().getBytes("UTF-8"));
                }

                @Override
                protected void readResponse(InputStream input) throws IOException {
                }

                @Override
                protected void postResponse() {
                }
            };

            json.put("localisation", e.getLocalisation());
            json.put("date", e.getDate());
            json.put("description", e.getDescription());
            json.put("title", e.getTitle());

            post.setUrl("http://127.0.0.1:8000/webservicesupdaterdv/"+id);
            post.setPost(true);
            post.setContentType("application/json");
            post.addArgument("body", json.toString());
            String bodyToString = json.toString();
            NetworkManager.getInstance().addToQueueAndWait(post);
            Map<String, Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(post.getResponseData()), "UTF-8"));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());;
        }
        return true;

    }

    public ArrayList<formation> parserdv(String jsonText) throws Exception  {
        try {
            events = new ArrayList<>();
            JSONParser j = new JSONParser();

            Map<String, Object> EventsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) EventsListJson.get("root");

            for (Map<String, Object> obj : list) {
                formation e = new formation();
                float id = Float.parseFloat(obj.get("id").toString());
                e.setId((int) id);
                e.setTitle(obj.get("title").toString());
                e.setLocalisation(obj.get("localisation").toString());
                String sDate1=obj.get("date").toString();  
                Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+00:00").parse(sDate1);
    
                e.setDate(date);
                // e.setStatus(((int)Float.parseFloat(obj.get("status").toString())));
                e.setDescription(obj.get("description").toString());
                events.add(e);
            }

        } catch (IOException ex) {
            System.out.println("services.Servicerdv.parserdv()");

        }
        return events;
    }
    

    public ArrayList<formation> getAllrdvs() {
        String url = "http://127.0.0.1:8000/webservicesrdvs";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    events = parserdv(new String(req.getResponseData()));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());;
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(events.toString());
        return events;
    }
    public boolean deleterdv(int id) {
        String url = "http://127.0.0.1:8000/webservicesdeleterdv/" + id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(events.toString());
        return true;
    }
    public ArrayList<formation> SearchFormation(String search) {
        String url = "http://127.0.0.1:8000/webserviceSearch?title="+search;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    events = parserdv(new String(req.getResponseData()));
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());;
                }
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        System.out.println(events.toString());
        return events;
    }
   
}