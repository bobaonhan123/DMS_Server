package ViewsDataHandling;
import Configurations.Host;
import HandlingMethods.StringHandling;
import Models.Student;
import Models.StudentIn;
import Models.Room;
import Tools.Fetcher;
import Views.Alert;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import HandlingMethods.DateHandling;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class Client {
    private String url;
    private int port;
    public Client() {
        url= Host.url;
        port=Host.port;
    }
    public Student StudentInfo(String email){
        String sentence_from_server;
        String sentence_to_server;
        Student res=new Student();
        try {

            sentence_to_server = "{'function':'ClientDAO_StudentInfo','data':'"+email+"'}";

            sentence_from_server = new Fetcher().Fetch(sentence_to_server,url,port);
            res=new Gson().fromJson(sentence_from_server,Student.class);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return res;
    }
    public StudentIn StudentInInfo(String email){
        String sentence_from_server;
        String sentence_to_server;
        StudentIn res=new StudentIn();
        try {

            sentence_to_server = "{'function':'ClientDAO_StudentInInfo','data':'"+email+"'}";

            sentence_from_server = new Fetcher().Fetch(sentence_to_server,url,port);
            res=new Gson().fromJson(sentence_from_server,StudentIn.class);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return res;
    }
    public boolean isReg(String email) {
        String sentence_from_server;
        String sentence_to_server;
        boolean res=false;
        try {

            sentence_to_server = "{'function':'ClientDAO_isReg','data':'"+email+"'}";

            sentence_from_server = new Fetcher().Fetch(sentence_to_server,url,port);
            res=new Gson().fromJson(sentence_from_server,boolean.class);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return res;
    }
    public ArrayList<StudentIn> RoommateList(int roomNumber, String BuildingName){
        String toServer = "{'function':'ClientDAO_RoommateList','data':{'BuildingName':'"+BuildingName+"','RoomNumber':'"+roomNumber+"'}}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
        Type type = new TypeToken<List<StudentIn>>(){}.getType();
        ArrayList<StudentIn> StudentsList=new Gson().fromJson(fromServer, type);
        return StudentsList;
    }

    public void del(String email){
        String toServer = "{'function':'ClientDAO_del','data':'"+email+"'}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
    }

    public DefaultTableModel RoomList(boolean B1, boolean B2, boolean B3, boolean N4, boolean N6, boolean dv, String Number,Student student) {
        ArrayList<Boolean> BArr=new ArrayList<Boolean>();
        BArr.add(B1);
        BArr.add(B2);
        BArr.add(B3);
        BArr.add(N4);
        BArr.add(N6);
        BArr.add(dv);
        ArrayList<Room> res=new ArrayList<Room>();
        String condition="";
        String[] st={"OR Name='Dãy 1' ","OR Name='Dãy 2' ","OR(Name='Dãy 3' AND("," Type=4 "," Type=6 ",
                " OR Name='Dãy dịch vụ'"};
        for(int i=0;i<6;i++) {
            if(i==4) {
                if(N4 && N6) {
                    condition=condition+" OR ";
                }
            }
            if(i==5)
                if(B3) {
                    condition = condition + ")";
                }
            if(BArr.get(i)){
                    condition+=st[i];
                }
            if(i==4 && B3) {
                if(N4 || N6)
                    condition=condition+")";
                else
                    condition=condition+"0 )";
            }
        }

        condition=condition+")";


        if(!Number.equals("")) {
            condition= condition+" AND RoomNumber="+Number+"";
        }
        int gender=student.getGender() ? 1 : 0;
        condition= condition+" AND Gender="+gender+"";

        String toServer = "{'function':'ClientDAO_RoomList','data':\""+condition+"\"}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
        Type type = new TypeToken<ArrayList<Room>>(){}.getType();
        ArrayList<Room> RList= new Gson().fromJson(fromServer, type);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Dãy");
        model.addColumn("Số phòng");
        model.addColumn("Giới tính");
        model.addColumn("Số người");
        model.addColumn("Số chỗ trống");
        for(int i=0;i<RList.size();i++) {
            model.addRow(RList.get(i).toObject());
        }
        return model;
    }

    public void register(Student student, String BuildingID, int RoomNumber, Date DateBegin, Date DateEnd) {

        try {
            if(DateBegin.compareTo(DateEnd)>-1 || DateEnd.compareTo(DateHandling.DateNow())<1) {
                new Alert("Lỗi","Ngày không hợp lệ");
            }
            else {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateBegin=formatter.format(DateBegin);
                String dateEnd=formatter.format(DateEnd);
                String toServer = "{'function':'ClientDAO_Register','data':{" +
                        "'student':" +new Gson().toJson(student)+","+
                        "'BuildingID':'"+BuildingID+"',"+
                        "'RoomNumber':"+RoomNumber+","+
                        "'DateBegin':'"+dateBegin+"',"+
                        "'DateEnd':'"+dateEnd+"'"+
                        "}}";
                String fromServer =new Fetcher().Fetch(toServer,url,port);
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(fromServer, JsonObject.class);
                new Alert(new StringHandling().StringFromJSON(jsonObject.get("type").toString()),
                        new StringHandling().StringFromJSON(jsonObject.get("message").toString()));
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }

}
