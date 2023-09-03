package HandlingMethods;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringHandling {
    public String StringFromJSON(String jsonStr) {
        String res = "";
        for(int i=1;i<jsonStr.length()-1;i++) {
            res+=jsonStr.charAt(i);
        }
        return res;
    }
    public String toMD5(String input) {
        String res="";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            res = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return res;
    }
}
