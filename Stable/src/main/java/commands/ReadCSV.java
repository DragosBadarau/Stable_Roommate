package commands;

import utils.InsertRoomDAO;
import utils.InsertStudentDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

public class ReadCSV implements Command
{
    private int id;
    private String name;
    private int points;
    private String gender;
    private int P1;
    private int P2;
    private int P3;
    private int P4;
    private int P5;
    private int id_room, s1, s2;
    private String name_room, gen;


    private Connection conn;
    public ReadCSV(Connection conn){
        this.conn = conn;
    }
    public String run()
    {
        String line = "";
        int ok=0;
        String splitBy = ",";
        try
        {
//parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("E:\\FACULTATE\\Anul2Sem2\\ProgramareAvansata\\Proiect\\Stable\\Studenti.csv"));
            while ((line = br.readLine()) != null)   //returns a Boolean value
            {ok++;
                String[] student = line.split(splitBy);    // use comma as separator
                if(ok>1) {
                    id = Integer.parseInt(student[0]);
                    name = student[1];
                    points = Integer.parseInt(student[2]);
                    gender = student[3];
                    P1 = Integer.parseInt(student[4]);
                    P2 = Integer.parseInt(student[5]);
                    P3 = Integer.parseInt(student[6]);
                    P4 = Integer.parseInt(student[7]);
                    P5 = Integer.parseInt(student[8]);
                    var dbInsert = new InsertStudentDAO(id, name, points, gender, P1, P2, P3, P4, P5, conn);
                    dbInsert.run();
                }
            }
            ok = 0;
            BufferedReader br1 = new BufferedReader(new FileReader("E:\\FACULTATE\\Anul2Sem2\\ProgramareAvansata\\Proiect\\Stable\\Room.csv"));
            while ((line = br1.readLine()) != null)   //returns a Boolean value
            {ok++;
                String[] room = line.split(splitBy);    // use comma as separator
                if(ok>1) {
                    id_room = Integer.parseInt(room[0]);
                    name_room = room[1];
                    s1 = Integer.parseInt(room[2]);
                    s2 = Integer.parseInt(room[3]);
                    gen = room[4];
                    var dbInsert1 = new InsertRoomDAO(id_room, name_room, s1, s2, gen, conn);
                    dbInsert1.run();
                }
            }
        }
        catch (IOException e)
        {
            return "exception";
        }
        return "succ";
    }
}