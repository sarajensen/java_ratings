import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;

public class CourseTest{
  Course course;
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void setUp(){
    course = new Course("Make a Website", "web development", 1, 1);
  }

  @Test
  public void course_instantiatesCorrectly_Course() {
    assertTrue(course instanceof Course);
  }

  @Test
  public void getName_returnsNameOfCourse_String() {
    assertEquals("Make a Website", course.getName());
  }

  @Test
  public void getSubject_returnsSubjectOfCourse_String() {
    assertEquals("web development", course.getSubject());
  }

  @Test
  public void getSkillLevel_returnsSkillLevelOfCourse_int() {
    assertEquals(1, course.getSkillLevel());
  }

  // @Test
  // public void getAggregate_returnsAggregateOfCourse_Float() {
  //   assertEquals(0, course.getAggregate(), 0.001);
  // }

  @Test
  public void getId_returnsIdOfCourse_true() {
    course.save();
    assertTrue(course.getId() > 0);
  }

  @Test
  public void equals_returnsTrueIfPropertiesAreTheSame_true() {
    Course secondCourse = new Course("Make a Website", "web development", 1, 1);
    assertTrue(course.equals(secondCourse));
  }

  @Test
  public void save_savesObjectToDatabase_true(){
    course.save();
    String sql = "SELECT * FROM courses WHERE name='Make a Website'";
    Course secondCourse;
    try(Connection con = DB.sql2o.open()){
      secondCourse = con.createQuery(sql).executeAndFetchFirst(Course.class);
    }
    assertTrue(course.equals(secondCourse));
  }

  @Test
  public void all_returnsAllInstancesOfCourse_true(){
    course.save();
    Course secondCourse = new Course("Learn Sass", "web development", 2, 2);
    secondCourse.save();
    assertTrue(Course.all().contains(course));
    assertTrue(Course.all().contains(secondCourse));
  }

  @Test
  public void find_returnsCourseWithSameId_secondCourse() {
    course.save();
    Course secondCourse = new Course("Learn Sass", "web development", 2, 2);
    secondCourse.save();
    assertEquals(Course.find(secondCourse.getId()), secondCourse);
  }

  @Test
  public void saveRating_savesRating_true(){
    course.save();
    course.saveRating(3);
    String sql = "SELECT rating FROM course_ratings WHERE course_id=:id";
    Integer output;
    try(Connection con = DB.sql2o.open()){
      output = con.createQuery(sql).addParameter("id", course.getId()).executeScalar(Integer.class);
    }
    assertEquals((int) 3, (int) output);
  }

  // @Test
  // public void getAverage_returnsUpdatedAggregate_true(){
  //   course.getAverage(5);
  //   assertEquals(5, course.getAggregate(), .01);
  // }
  //
  // @Test
  // public void getAverage_updatesAggregateAverageProperly_true(){
  //   course.getAverage(5);
  //   course.getAverage(3);
  //   course.getAverage(3);
  //   assertEquals(3.7, course.getAggregate(), .01);
  // }
  //
  // @Test
  // public void getAverage_updatesAggregateInDatabase_true(){
  //   course.save();
  //   course.getAverage(5);
  //   String sql = "SELECT * FROM courses WHERE id=:id";
  //   Course secondCourse;
  //   try(Connection con = DB.sql2o.open()){
  //     secondCourse = con.createQuery(sql)
  //     .addParameter("id", course.getId())
  //     .executeAndFetchFirst(Course.class);
  //   }
  //   assertEquals(5, secondCourse.getAggregate(), .01);
  // }


}
