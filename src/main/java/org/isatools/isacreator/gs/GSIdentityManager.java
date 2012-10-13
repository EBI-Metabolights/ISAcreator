package org.isatools.isacreator.gs;

import org.apache.log4j.Logger;
import org.genomespace.client.GsSession;
import org.genomespace.client.User;
import org.genomespace.client.UserManagerClient;
import org.genomespace.client.exceptions.AuthorizationException;
import org.genomespace.client.exceptions.InternalServerException;
import org.genomespace.client.exceptions.ServerNotFoundException;
import org.isatools.isacreator.api.Authentication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by the ISATeam.
 * User: agbeltran
 * Date: 26/09/2012
 * Time: 14:41
 *
 * Identity Manager for Genome Space
 *
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class GSIdentityManager implements Authentication {

    //private Map<String, GsSession> userSessions = new HashMap<String, GsSession>();

    //maintaining a single session
   // private String username = null;
    private GsSession session = null;
    //private GSDataManager gsDataManager = null;

    //private String gsUser = null;
    private String gsToken = null;

    private String tokenSaveDir = ".gs";
    private String tokenSaveFileName = ".gstoken";
    private String usernameSaveFileName = ".gsusername";

    private static final Logger log = Logger.getLogger(GSIdentityManager.class);

    /**
     * Empty constructor
     */
    public GSIdentityManager(){

    }

    /**
     *
     * @param username
     * @param pass an array of characters with the user's password
     * @return
     */
    public boolean login(String username, char[] pass) {
        if (username==null || pass==null)
            return false;

        try{
            String password = new String(pass);

            //when creating a new session, .gs folder for SSO is created automatically
            session = new GsSession();
            User user = session.login(username, password);
            //userSessions.put(user.getUsername(),session);

            log.info("Logged into GenomeSpace as "+username);
            return true;
        }catch(AuthorizationException e){
            return false;
        }catch(InternalServerException isex){
            return false;
        }catch(ServerNotFoundException snfex){
            return false;
        }
    }

    /**
     *
     *
     * @param username
     * @return
     */
    public boolean logout(String username) {
        //GsSession session = userSessions.get(username);
        if (session==null)
            return false;
        session.logout();
        return true;
    }

    /**
     * The GSIdentityManager does not support single sign on, thus it returns false
     *
     * @return
     */
    public boolean login(){
        String token = getGSToken();
        if (token==null)
            return false;
        try{
            GsSession gsSession = new GsSession(token);
            //identityManager.addSession(gsSession);
            setSession(gsSession);
            return true;
        }catch(InternalServerException e){
            log.debug(e.getMessage());
            return false;
        }
    }

    /**
     *
     * @param username
     * @return
     */
    public boolean isLoggedIn(String username) {
       // GsSession session = userSessions.get(username);
        if (session==null)
            return false;
        return session.isLoggedIn();
    }

    /**
     *
     * @param username
     * @param password
     * @param emailAddress
     * @return
     */
    public boolean registerUser(String username, String password, String emailAddress) {

       // GsSession session = userSessions.get(username);
        if (session==null)
            return false;

        try {
            User newUser = session.registerUser(username,
                    password, emailAddress);
            return true;
        }catch(InternalServerException isex){
            return false;
        }
    }


    /**
     * Gets user session.
     *
     *
     * @return
     */
    //public GsSession getSession(String userName){
    public GsSession getSession(){
        //return userSessions.get(userName);
        return session;
    }

    public void setSession(GsSession gsSession){
        if (gsSession.isLoggedIn()){
            UserManagerClient userManagerClient = gsSession.getUserManagerClient();

            try{
                long time = userManagerClient.getRemainingTokenTime();
                if (time!=0){
                    String username = gsSession.getCachedUsernameForSSO();
                    //Collection<String> users = userManagerClient.getAllUsernames();
                    //for(String username: users){
                        //userSessions.put(username, gsSession);
                    session = gsSession;
                    //}
                }
            }catch(InternalServerException e){
                e.printStackTrace();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public GSDataManager getGsDataManager(){
        log.debug("session: is logged in when retrieving DM?"+session.isLoggedIn());
        return new GSDataManager(session);
    }

    private String getGSToken() {
        if (gsToken == null) {
            File file = getTokenFile();
            if (file!=null && file.exists()) {
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(file));
                    gsToken = br.readLine();
                } catch (IOException e) {
                    log.error("Error reading GS cookie", e);
                } finally {
                    if (br != null) try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return gsToken;
    }

    private File getTokenFile() {
        File gsDir = getTokenSaveDir();
        return (gsDir != null && gsDir.exists()) ? new File(gsDir, tokenSaveFileName) : null;
    }

    private File getTokenSaveDir() {
        String userDir = System.getProperty("user.home");
        File gsDir = new File(userDir, tokenSaveDir);
        if (!gsDir.exists()) {
            gsDir.mkdir();
        }
        return gsDir;
    }

}
