package ViewsDataHandling;
import Models.Room;
import Models.Student;
import Models.StudentIn;
import Models.WaitingStudent;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import HandlingMethods.DateHandling;
import Tools.Fetcher;
import com.google.gson.Gson;
import Configurations.Host;
import com.google.gson.reflect.TypeToken;

public class Admin {
    private String url;
    private int port;
    public Admin() {
        url=Host.url;
        port=Host.port;
    }

    public int StudentsCnt(String School){
        String sentence_from_server;
        String sentence_to_server;
        int res=0;
        try {
            sentence_to_server = "{'function':'AdminDAO_StudentsNum','data':'"+School+"'}";

            sentence_from_server = new Fetcher().Fetch(sentence_to_server,url,port);
            res=Integer.parseInt(sentence_from_server);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return res;
    }

    public DefaultTableModel RoomList(boolean B1, boolean B2, boolean B3, boolean N4, boolean N6, boolean dv, String Number) {

        ArrayList<Boolean> BArr=new ArrayList<Boolean>();
        BArr.add(B1);
        BArr.add(B2);
        BArr.add(B3);
        BArr.add(N4);
        BArr.add(N6);
        BArr.add(dv);
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
        String toServer = "{'function':'AdminDAO_RoomList','data':\""+condition+"\"}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
        Type type = new TypeToken<ArrayList<Room>>(){}.getType();
        List<Room> RList= new Gson().fromJson(fromServer, type);
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

    public DefaultTableModel RoomateTable(String BuildingName,String RoomNumber) {
        String toServer = "{'function':'AdminDAO_RoommateList','data':{'BuildingName':'"+BuildingName+"','RoomNumber':'"+RoomNumber+"'}}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
        Type type = new TypeToken<List<StudentIn>>(){}.getType();
        List<StudentIn> StudentsList=new Gson().fromJson(fromServer, type);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Email");
        model.addColumn("Tên");
        model.addColumn("Giới tính");
        model.addColumn("Trường");
        for(int i=0;i<StudentsList.size();i++) {
            model.addRow(StudentsList.get(i).toObject());
        }
        return model;
    }

    public void del(String email){
        String toServer = "{'function':'AdminDAO_del','data':\"StudentEmail='"+email+"'\"}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
    }

    public void del() {
        String toServer = "{'function':'AdminDAO_del','data':'1'}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
    }

    public DefaultTableModel WaitingStudentsTable() {
        String toServer = "{'function':'AdminDAO_WaitingStudentsList','data':''}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
        Type type = new TypeToken<List<WaitingStudent>>(){}.getType();

        List<WaitingStudent> StudentsList=new Gson().fromJson(fromServer, type);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Email");
        model.addColumn("Tên");
        model.addColumn("Giới tính");
        model.addColumn("Trường");
        model.addColumn("Số phòng");
        model.addColumn("Dãy phòng");
        for(int i=0;i<StudentsList.size();i++) {
            model.addRow(StudentsList.get(i).toObject());
        }
        return model;
    }

    public void RegConfirm(String email) {
        String toServer = "{'function':'AdminDAO_Confirm','data':\"StudentEmail='"+email+"'\"}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
    }
    public void RegConfirm() {
        String toServer = "{'function':'AdminDAO_Confirm','data':'1'}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
    }

    public void WaitingStudentsCSV() {
        try {
            String path="DanhSachCho"+DateHandling.dateToString(DateHandling.DateNow())+".csv";
            File myObj = new File(path);
            int num=1;
            while (!myObj.createNewFile()) {
                path="DanhSachCho_"+(num)+"_"+DateHandling.dateToString(DateHandling.DateNow())+".csv";
                myObj=new File(path);
                num++;
            }
            String toServer = "{'function':'AdminDAO_WaitingStudentsList','data':''}";
            String fromServer =new Fetcher().Fetch(toServer,url,port);
            Type type = new TypeToken<List<WaitingStudent>>(){}.getType();

            List<WaitingStudent> StudentsList=new Gson().fromJson(fromServer, type);
                FileWriter writer = new FileWriter(path, true);
                writer.write("Email,Họ và tên,Giới tính,Trường,Số phòng,Dãy");
                writer.write("\r\n");
                for (int i = 0; i < StudentsList.size(); i++) {
                    writer.write(StudentsList.get(i).toCSVLine());
                    writer.write("\r\n");

                }
                writer.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DefaultTableModel StudentTable(String filter) {
        String toServer = "{'function':'AdminDAO_StudentTable','data':'"+filter+"'}";
        String fromServer =new Fetcher().Fetch(toServer,url,port);
        Type type = new TypeToken<List<Student>>(){}.getType();
        List<Student> StudentsList=new Gson().fromJson(fromServer, type);
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Email");
        model.addColumn("Tên");
        model.addColumn("Giới tính");
        model.addColumn("Đang ở KTX");
        model.addColumn("Trường");
        for(int i=0;i<StudentsList.size();i++) {
            model.addRow(StudentsList.get(i).toObject());
        }
        return model;
    }
}
