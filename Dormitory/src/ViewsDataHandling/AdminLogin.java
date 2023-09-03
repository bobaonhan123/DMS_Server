package ViewsDataHandling;
import HandlingMethods.StringHandling;
import Tools.Fetcher;
import com.google.gson.Gson;

import Configurations.Host;

public class AdminLogin {
    private String url=Host.url;
    private int port = Host.port;
    public boolean checkLogin(String user, String passWord) {
        String sentence_from_server;
        String sentence_to_server;
        boolean res=false;

        try {
            sentence_to_server = "{'function':'AdminLoginDao_checkLogin','data':{'user':'" + user + "','pwd':'" + new StringHandling().toMD5(passWord) + "'}}";
            sentence_from_server = new Fetcher().Fetch(sentence_to_server,url,port);
            res=new Gson().fromJson(sentence_from_server, boolean.class);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return res;
    }

}
