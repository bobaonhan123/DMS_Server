package ViewsDataHandling;
import Configurations.Host;
import HandlingMethods.StringHandling;
import Tools.Fetcher;

import com.google.gson.Gson;


public class ClientLogin {
    String sentence_to_server;
    String sentence_from_server;
    boolean res=false;
    private String host=Host.url;
    private int port=Host.port;
    public boolean checkLogin(String user, String passWord) {
        try {
            sentence_to_server = "{'function':'ClientLoginDao_checkLogin','data':{'user':'" + user + "','pwd':'" + new StringHandling().toMD5(passWord) + "'}}";
            sentence_from_server= new Fetcher().Fetch(sentence_to_server,host,port);
            res=new Gson().fromJson(sentence_from_server, boolean.class);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return res;
    }

}
