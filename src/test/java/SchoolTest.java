import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.sql2o.*;

public class SchoolTest{
  School school;
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    school = new School("Codecademy", false, true, false, "http://www.codecademy.com");
  }

  @Test
  public void school_instantiatesCorrectly_School() {
    assertTrue(school instanceof School);
  }

  @Test
  public void getId_returnsIdOfSchool_true() {
    school.save();
    assertTrue(school.getId() > 0);
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    School secondSchool = new School("Codecademy", false, true, false, "http://www.codecademy.com");
    assertTrue(school.equals(secondSchool));
  }

  @Test
  public void save_savesObjectToDatabase_true(){
    school.save();
    String sql = "SELECT * FROM schools WHERE name='Codecademy'";
    School secondSchool;
    try(Connection con = DB.sql2o.open()){
      secondSchool = con.createQuery(sql).executeAndFetchFirst(School.class);
    }
    assertTrue(school.equals(secondSchool));
  }

  @Test
  public void all_returnsAllInstancesOfSchool_true(){
    school.save();
    School secondSchool = new School("Treehouse", false, true, true, "http://treehouse.com");
    secondSchool.save();
    assertTrue(School.all().contains(school));
    assertTrue(School.all().contains(secondSchool));
  }

  @Test
  public void find_returnsSchoolWithSameId_secondSchool() {
    school.save();
    School secondSchool = new School("Treehouse", false, true, true, "http://treehouse.com");
    secondSchool.save();
    assertEquals(School.find(secondSchool.getId()), secondSchool);
  }

  @Test
  public void getCourses_retrievesAllCoursesWithSchoolId_courseList(){
    school.save();
    Course course = new Course("Make a Website", "web development", 1, school.getId());
    course.save();
    Course secondCourse = new Course("Learn Sass", "web development", 2, school.getId());
    secondCourse.save();
    Course[] courses = new Course[] {course, secondCourse};
    assertTrue(school.getCourses().containsAll(Arrays.asList(courses)));
  }

  @Test
  public void saveRatings_savesCultureValueAndUXRatingsIntoDatabase_true(){
    school.save();
    school.saveRatings(3, 4, 5);
    String sqlCulture = "SELECT culture_rating FROM school_ratings WHERE school_id=:id";
    String sqlValue = "SELECT value_rating FROM school_ratings WHERE school_id=:id";
    String sqlUX = "SELECT ux_rating FROM school_ratings WHERE school_id=:id";
    Integer outputCulture;
    Integer outputValue;
    Integer outputUX;
    try(Connection con = DB.sql2o.open()){
      outputCulture = con.createQuery(sqlCulture).addParameter("id", school.getId()).executeScalar(Integer.class);
      outputValue = con.createQuery(sqlValue).addParameter("id", school.getId()).executeScalar(Integer.class);
      outputUX = con.createQuery(sqlUX).addParameter("id", school.getId()).executeScalar(Integer.class);
    }
    assertEquals((int) 3, (int) outputCulture);
    assertEquals((int) 4, (int) outputValue);
    assertEquals((int) 5, (int) outputUX);
  }

  @Test
  public void getAverage_returnsArrayOfCultureValueAndUXRatings_Array(){
    school.save();
    school.saveRatings(2, 3, 1);
    school.saveRatings(4, 5, 2);
    Float[] schoolRatings = new Float[] {3.0f, 4.0f, 1.5f};
    assertTrue(school.getAverages().containsAll(Arrays.asList(schoolRatings)));
  }

  @Test
  public void getNumRatings_returnsArrayOfTheNumberOfCultureValueAndUXRatings_int(){
    school.save();
    school.saveRatings(2, 3, 1);
    school.saveRatings(4, 5, 2);
    assertEquals(2, school.getNumRatings());
  }
}
