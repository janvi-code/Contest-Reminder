package android.example.network;

public class item {
    private String status;
    private String dateStart;
    private String timeStart;
    private String dateEnd;
    private String timeEnd;
    private String time1;
    private  String time2;
    private String name;

    public String getName() {
        return name;
    }

    public item(String status, String dS, String tS, String dE, String tE, String time1, String time2, String name){
        this.status = status;
        dateStart = dS;
        timeStart = tS;
        dateEnd = dE;
        timeEnd = tE;
        this.time1 = time1;
        this.time2 = time2;
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public String getDateStart() {
        return dateStart;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public String getTime1() {
        return time1;
    }

    public String getTime2() {
        return time2;
    }

    public String getTimeEnd() {
        return timeEnd;
    }
}
