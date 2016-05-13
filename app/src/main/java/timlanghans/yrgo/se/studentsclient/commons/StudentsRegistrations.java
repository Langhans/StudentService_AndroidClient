package timlanghans.yrgo.se.studentsclient.commons;

import java.util.List;

/**
 * Java POJO representation of a registration, showing which courses a student
 * is enrolled in.
 */

public class StudentsRegistrations {

  private Student student;
  
  private List<Course> courses;
  
  
  public StudentsRegistrations(Student student , List<Course> courses){
    this.student = student;
    this.courses = courses;
  }

  public Student getStudent() {
    return student;
  }


  public List<Course> getCourses() {
    return courses;
  }

}
