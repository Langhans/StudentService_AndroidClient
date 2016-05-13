package timlanghans.yrgo.se.studentsclient.commons;

/**
 *
 * Java POJO representation of the main student database courses table
 *
 * Created by timlanghans on 16-04-19 / 16.
 */
public class Course {

    private int id;
    private String code;

    public Course(int id ,String code ){
        this.id = id;
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return code;
    }
}
