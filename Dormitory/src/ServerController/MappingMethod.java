package ServerController;
import Models.Room;
import Models.Student;
import Models.StudentIn;
import Models.WaitingStudent;
import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import DAOs.*;
import HandlingMethods.StringHandling;
public class MappingMethod {
    public String Mapping(String json) {
        String res="";
        try {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
            System.out.println(json.toString());
            String function = jsonObject.get("function").toString();
            //System.out.println(function);
            switch (new StringHandling().StringFromJSON(function)) {
                case "ClientLoginDao_checkLogin": {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String user = new StringHandling().StringFromJSON(data.get("user").toString());
                    String pwd = new StringHandling().StringFromJSON(data.get("pwd").toString());
                    res = new ClientLoginDao().checkLogin(user, pwd) ? "true" : "false";
                    break;
                }
                case "AdminLoginDao_checkLogin": {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String user = new StringHandling().StringFromJSON(data.get("user").toString());
                    String pwd = new StringHandling().StringFromJSON(data.get("pwd").toString());
                    res = new AdminLoginDao().checkLogin(user, pwd) ? "true" : "false";
                    break;
                }
                case "AdminDAO_StudentsNum": {
                    String data = jsonObject.get("data").toString();
                    //System.out.println(data);
                    String school= new StringHandling().StringFromJSON(data);
                    res=""+new AdminDAO().StudentsNum(school)+"";
                    break;
                }
                case "AdminDAO_RoomList": {
                    String data = jsonObject.get("data").toString();
                    ArrayList<Room> rooms = new AdminDAO().RoomList(new StringHandling().StringFromJSON(data));
                    res = new Gson().toJson(rooms);
                    System.out.println(res);
                    break;
                }
                case "AdminDAO_RoommateList": {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String BuildingName = new StringHandling().StringFromJSON(data.get("BuildingName").toString());
                    String RoomNumber = new StringHandling().StringFromJSON(data.get("RoomNumber").toString());
                    ArrayList<StudentIn> students = new AdminDAO().RoommateList(RoomNumber,BuildingName);
                    res = new Gson().toJson(students);
                    break;
                }
                case "AdminDAO_del": {
                    String data = jsonObject.get("data").toString();
                    new AdminDAO().del(new StringHandling().StringFromJSON(data));
                    break;
                }
                case "AdminDAO_WaitingStudentsList": {
                    ArrayList<WaitingStudent> waitingStudents=new AdminDAO().WaitingStudentsList();
                    res = new Gson().toJson(waitingStudents);
                    break;
                }
                case "AdminDAO_Confirm": {
                    String data = jsonObject.get("data").toString();
                    new AdminDAO().Confirm(new StringHandling().StringFromJSON(data));
                    break;
                }
                case "ClientDAO_StudentInfo": {
                    String data = jsonObject.get("data").toString();
                    Student student = new ClientDAO().StudentInfo(new StringHandling().StringFromJSON(data));
                    res= new Gson().toJson(student);
                    break;
                }
                case "ClientDAO_StudentInInfo": {
                    String data = jsonObject.get("data").toString();
                    StudentIn student = new ClientDAO().StudentInInfo(new StringHandling().StringFromJSON(data));

                    res= new Gson().toJson(student);
                    break;
                }

                case "ClientDAO_isReg": {
                    String data = jsonObject.get("data").toString();
                    boolean student = new ClientDAO().isReg(new StringHandling().StringFromJSON(data));
                    res= new Gson().toJson(student);
                    break;
                }
                case "ClientDAO_RoommateList": {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    String BuildingName = new StringHandling().StringFromJSON(data.get("BuildingName").toString());
                    int RoomNumber = Integer.parseInt(new StringHandling().StringFromJSON(data.get("RoomNumber").toString()));
                    ArrayList<StudentIn> students = new ClientDAO().RoommateList(RoomNumber,BuildingName);
                    res = new Gson().toJson(students);
                    break;
                }
                case "ClientDAO_del": {
                    String data = jsonObject.get("data").toString();
                    new ClientDAO().del(new StringHandling().StringFromJSON(data));
                    break;
                }

                case "ClientDAO_RoomList": {
                    String data = jsonObject.get("data").toString();
                    ArrayList<Room> rooms = new ClientDAO().RoomList(new StringHandling().StringFromJSON(data));
                    res = new Gson().toJson(rooms);
                    System.out.println(res);
                    break;
                }

                case "ClientDAO_Register": {
                    JsonObject data = jsonObject.getAsJsonObject("data");
                    Student st = new Gson().fromJson(data.getAsJsonObject("student"),Student.class);
                    //System.out.println(st.getName());
                    String Building = new StringHandling().StringFromJSON(data.get("BuildingID").toString());
                    //System.out.println(Building);
                    int room = Integer.parseInt(data.get("RoomNumber").toString());
                    //System.out.println(room);
                    String dateBegin= new StringHandling().StringFromJSON(data.get("DateBegin").toString());
                    //System.out.println(dateBegin);
                    String dateEnd= new StringHandling().StringFromJSON(data.get("DateEnd").toString());
                    //System.out.println(dateEnd);
                    res = new ClientDAO().Register(st,Building,room,dateBegin,dateEnd);
                    break;
                }
                case "AdminDAO_StudentTable": {
                    String data = jsonObject.get("data").toString();
                    ArrayList<Student> students = new AdminDAO().StudentTable(new StringHandling().StringFromJSON(data));
                    res = new Gson().toJson(students);
                    break;
                }

            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return res;
    }

}
