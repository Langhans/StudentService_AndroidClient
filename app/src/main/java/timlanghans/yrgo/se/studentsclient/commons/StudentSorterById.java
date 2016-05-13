package timlanghans.yrgo.se.studentsclient.commons;

import java.util.Comparator;

/**
 * Created by timlanghans on 16-05-13 / 19.
 */
public class StudentSorterById implements Comparator<Student>{


  @Override
  public int compare(Student lhs, Student rhs) {
    if (lhs.getId() > rhs.getId()){
      return 1;
    } else if (lhs.getId() < rhs.getId()){
      return -1;
    } else{
      return 0;
    }
  }
}
