package timlanghans.yrgo.se.studentsclient.commons;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by timlanghans on 16-05-13 / 19.
 */
public class StudentSorterByName implements Comparator<Student> {

  @Override
  public int compare(Student lhs, Student rhs) {
    Collator col = Collator.getInstance();
    col.setStrength(Collator.PRIMARY);
    return col.compare(lhs.getName(),rhs.getName());
  }
}
