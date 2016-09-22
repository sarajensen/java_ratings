import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/ratings_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteCoursesQuery = "DELETE FROM courses *;";
      String deleteSchoolsQuery = "DELETE FROM schools *;";
      String deleteSchoolRatingsQuery = "DELETE FROM school_ratings *;";
      String deleteCoursesRatingsQuery = "DELETE FROM course_ratings *;";
      con.createQuery(deleteCoursesQuery).executeUpdate();
      con.createQuery(deleteSchoolsQuery).executeUpdate();
      con.createQuery(deleteSchoolRatingsQuery).executeUpdate();
      con.createQuery(deleteCoursesRatingsQuery).executeUpdate();
    }
  }

}
