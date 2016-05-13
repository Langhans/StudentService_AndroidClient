package timlanghans.yrgo.se.studentsclient.commons;

/**
 *
 * Java POJO representation of the main databases students table
 *
 * Created by timlanghans on 16-04-19 / 16.
 */
public class Student {


    private int id;
    private String name;


    public Student(int id , String name){
        this.id = id ;
        this.name = name;

    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }


    @Override
    public String toString(){
        return id + "  " + name;
    }

}
