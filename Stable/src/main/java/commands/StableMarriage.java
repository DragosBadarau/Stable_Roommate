package commands;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;

public class StableMarriage {
    private int Boys[][];
    private int Girls[][];
    private int boysRooms[][];
    private int GirlsRooms[][];
    private static int nrBoys=30;
    private static int sortByPointsBoys[];
    private static int nrRoomsBoys=12;
    private static int nrGirls=20;
    private static int sortByPointsGirls[];
    private static int nrRoomsGirls=8;
    private Connection con;

    public Boolean topBoy(int Student)
    {
        for(int i=1;i<=nrRoomsBoys*2;i++)
            if(sortByPointsBoys[i]==Student)
                return true;
        return false;
    }
    public Boolean topGirl(int Student)
    {
        for(int i=1;i<=nrRoomsGirls*2;i++)
            if(sortByPointsGirls[i]==Student)
                return true;
        return false;
    }
    public void StableRoommateBoys(){
        int preferenceBoys[][]=new int[nrBoys+2][nrBoys+2]; //matricea preferintelor doar a primilor nrRoomBoys*2 studenti
        int i, j,k,pref;
        int kthPreferred;
        int roomsAvailable=nrRoomsBoys;
        for(i=1;i<=nrBoys;i++)
            for(j=1;j<=nrBoys;j++)
            preferenceBoys[i][j]=0;
        for(i=1;i<=nrBoys;i++)
            {
                if(topBoy(i))//daca studentul cu id-ul i va fi primit la camin
                {
                for (k = 0; k < 5; k++)
                    {
                    kthPreferred = Boys[i][k];// studentul cu id ul kthprefered are preferinta 5-k
                        if(topBoy(kthPreferred)) {//daca si studentul preferat va fi primit la camin
                            preferenceBoys[i][kthPreferred] += 5 - k;
                            for (pref = 1; pref <= nrBoys; pref++)//verificam si daca acel student cu id-ul kthprefered il prefera pe studentul cu id ul i
                                if (preferenceBoys[kthPreferred][pref] == i)
                                    preferenceBoys[i][kthPreferred] += 5 - pref;//se adauga cata preferinta invers
                        }
                    }
                }
            }
            int maxPreferrence=0;
        int boy1, boy2;
        int evidentaBoys[]=new int[nrBoys+2];
        for(i=1;i<=nrBoys;i++)
            evidentaBoys[i]=0;
        while(roomsAvailable>0)
        {
           while(maxPreferrence>0)
           {i=1;j=i+1;
               boy1=-1; boy2=-1;
               while(i<nrBoys) {
                   while (j <= nrBoys) {
                       if (preferenceBoys[i][j] > maxPreferrence) {
                           maxPreferrence = preferenceBoys[i][j];
                           boy1 = i;
                           boy2 = j;
                           preferenceBoys[i][j]=0;
                       }
                       j++;
                   }
                   i++;
                   j = i+1;
               }
               //am gasit pereche
               if(boy1!=-1) {
                   roomsAvailable--;
                   evidentaBoys[boy1] = 1;
                   evidentaBoys[boy2] = 1;
                   boysRooms[boy1][boy2] = 1;
               }
               else//nu mai exista perechi
                   maxPreferrence=0;
           }
            boy1=-1;
            boy2=-1;
           //nu mai sunt perechi compatibile
            //asignam automat studentii care intra la camin
            for(i=1;i<=nrRoomsBoys*2;i++)
            {
                if(evidentaBoys[sortByPointsBoys[i]]==0)
                {
                    if(boy1<0)
                        boy1=sortByPointsBoys[i];
                    else {//creez o pereche
                        boy2=sortByPointsBoys[i];
                        evidentaBoys[boy1]=1;
                        evidentaBoys[boy2]=1;
                        boysRooms[boy1][boy2] = 1;
                        boy1=-1;boy2=-1;
                        roomsAvailable--;
                    }
                }
            }
        }
    }
    public void StableRoommateGirls(){
        int preferenceGirls[][]=new int[nrGirls+2][nrGirls+2]; //matricea preferintelor doar a primilor nrRoomGirls*2 studenti
        int i, j,k,pref;
        int kthPreferred;
        int roomsAvailable=nrRoomsGirls;
        for(i=1;i<=nrGirls;i++)
            for(j=1;j<=nrGirls;j++)
                preferenceGirls[i][j]=0;
        for(i=1;i<=nrGirls;i++)
        {
            if(topGirl(i))//daca studentul cu id-ul i va fi primit la camin
            {
                for (k = 0; k < 5; k++)
                {
                    kthPreferred =Girls[i][k];// studentul cu id ul kthprefered are preferinta 5-k
                    if(topGirl(kthPreferred)) {//daca si studentul preferat va fi primit la camin
                        preferenceGirls[i][kthPreferred] += 5 - k;
                        for (pref = 1; pref <= nrGirls; pref++)//verificam si daca acel student cu id-ul kthprefered il prefera pe studentul cu id ul i
                            if (preferenceGirls[kthPreferred][pref] == i)
                                preferenceGirls[i][kthPreferred] += 5 - pref;//se adauga cata preferinta invers
                    }
                }
            }
        }
        int maxPreferrence=0;
        int Girl1, Girl2;
        int evidentaGirls[]=new int[nrGirls+2];
        for(i=1;i<=nrGirls;i++)
            evidentaGirls[i]=0;
        while(roomsAvailable>0)
        {
            while(maxPreferrence>0)
            {i=1;j=i+1;
                Girl1=-1; Girl2=-1;
                while(i<nrGirls) {
                    while (j <= nrGirls) {
                        if (preferenceGirls[i][j] > maxPreferrence) {
                            maxPreferrence = preferenceGirls[i][j];
                            Girl1 = i;
                            Girl2 = j;
                            preferenceGirls[i][j]=0;
                        }
                        j++;
                    }
                    i++;
                    j = i+1;
                }
                //am gasit pereche
                if(Girl1!=-1) {
                    roomsAvailable--;
                    evidentaGirls[Girl1] = 1;
                    evidentaGirls[Girl2] = 1;
                    GirlsRooms[Girl1][Girl2] = 1;
                }
                else//nu mai exista perechi
                    maxPreferrence=0;
            }
            Girl1=-1;
            Girl2=-1;

            //nu mai sunt perechi compatibile
            //asignam automat studentii care intra la camin
            for(i=1;i<=nrRoomsGirls*2;i++)
            {
                if(evidentaGirls[sortByPointsGirls[i]]==0)
                {
                    if(Girl1<0)
                        Girl1=sortByPointsGirls[i];
                    else {//creez o pereche
                        Girl2=sortByPointsGirls[i];
                        evidentaGirls[Girl1]=1;
                        evidentaGirls[Girl2]=1;
                        GirlsRooms[Girl1][Girl2] = 1;
                        Girl1=-1;Girl2=-1;
                        roomsAvailable--;
                    }
                }
            }
        }
    }
    public StableMarriage(Connection con) {
        this.con = con;
        NumberFormat cf = NumberFormat.getCurrencyInstance();
        int count=1;
        ResultSet boys = getDataBoys(con);
        ResultSet girls = getDataGirls(con);
        try {
            while (boys.next()) {
                int col=0;
                Boys m = getBoy(boys);
                Boys[m.id][col++]=m.p1;//preferintele studentului cu id-ul m.id
                Boys[m.id][col++]=m.p2;
                Boys[m.id][col++]=m.p3;
                Boys[m.id][col++]=m.p4;
                Boys[m.id][col++]=m.p5;
                sortByPointsBoys[count++]=m.id;//retin ordinea descrescatoare a punctajelor baieti
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        count=1;
        try {
            while (girls.next()) {
                int col=0;
                Girls f = getGirl(girls);
                Girls[f.id][col++]=f.p1;
                Girls[f.id][col++]=f.p2;
                Girls[f.id][col++]=f.p3;
                Girls[f.id][col++]=f.p4;
                Girls[f.id][col++]=f.p5;
                sortByPointsGirls[count++]=f.id;//retin ordinea descrescatoare a punctajelor fete
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ResultSet getDataBoys(Connection con) {
        try {
            Statement s = con.createStatement();
            String select = "Select ID,P1,P2,P3,P4,P5 " + "from STUDENTS order by PUNCTAJ desc where GEN = 'M'";
            ResultSet rows;
            rows = s.executeQuery(select);
            return rows;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ResultSet getDataGirls(Connection con) {
        try {
            Statement s = con.createStatement();
            String select = "Select ID,P1,P2,P3,P4,P5 " + "from STUDENTS order by PUNCTAJ desc where GEN = 'F'";
            ResultSet rows;
            rows = s.executeQuery(select);
            return rows;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static Boys getBoy(ResultSet student) {
        try {int id,p1,p2,p3,p4,p5;
             id = student.getInt("ID");
             p1=student.getInt("P1");
             p2=student.getInt("P2");
             p3=student.getInt("P3");
             p4=student.getInt("P4");
             p5=student.getInt("P5");
            return new Boys(id,p1,p2,p3,p4,p5);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    private static Girls getGirl(ResultSet student) {
        try {int id,p1,p2,p3,p4,p5;
            id = student.getInt("ID");
            p1=student.getInt("P1");
            p2=student.getInt("P2");
            p3=student.getInt("P3");
            p4=student.getInt("P4");
            p5=student.getInt("P5");
            return new Girls(id,p1,p2,p3,p4,p5);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    static class Boys {
        public int id;
        public int p1;
        public int p2;
        public int p3;
        public int p4;
        public int p5;

        public Boys(int id, int p1, int p2, int p3, int p4, int p5) {
            this.id = id;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p4 = p4;
            this.p5 = p5;
        }
    }
    static class Girls {
        public int id;
        public int p1;
        public int p2;
        public int p3;
        public int p4;
        public int p5;


        public Girls(int id, int p1, int p2, int p3, int p4, int p5) {
            this.id = id;
            this.p1 = p1;
            this.p2 = p2;
            this.p3 = p3;
            this.p4 = p4;
            this.p5 = p5;
        }
    }

}
