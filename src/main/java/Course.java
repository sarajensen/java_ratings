import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.OptionalDouble;

public class Course{
  private String name;
  private String subject;
  private int skill_level;
  private int id;
  private int schoolId;

  public Course(String name, String subject, int skill_level, int schoolId){
    this.name = name;
    this.subject = subject;
    this.skill_level = skill_level;
    this.schoolId = schoolId;
  }

  public String getName() {
    return name;
  }

  public String getSubject() {
    return subject;
  }

  public int getSkillLevel() {
    return skill_level;
  }

  public int getId() {
    return id;
  }

  public int getSchoolId() {
    return schoolId;
  }

  public void saveRating(int rating){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO course_ratings (course_id, rating) VALUES (:course_id, :rating)";
      con.createQuery(sql).addParameter("course_id", this.id)
        .addParameter("rating", rating).executeUpdate();
    }
  }

  public float getAverage(){
    //aggregate = (float) Math.round(((float)ratingTotal/(float)numRatings)*10)/10;
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT rating FROM course_ratings WHERE course_id=:course_id";
      List<Integer> ratingCount = con.createQuery(sql)
        .addParameter("course_id", id)
        .executeAndFetch(Integer.class);
      float average = (float) Math.round(ratingCount.stream().mapToDouble(r -> r)
      .average().getAsDouble()*10)/10;
      return average;
    }
  }

  public int getNumRatings(){
    String sql = "SELECT count(id) FROM course_ratings WHERE course_id=:course_id";
    try (Connection con = DB.sql2o.open()) {
        return con.createQuery(sql)
        .addParameter("course_id", id)
        .executeScalar(Integer.class);
    }
  }

  @Override
  public boolean equals(Object otherCourse) {
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getName().equals(newCourse.getName()) && this.getSubject().equals(newCourse.getSubject()) && this.getSkillLevel() == newCourse.getSkillLevel() && this.getSchoolId() == newCourse.getSchoolId();
    }

  }

  public void save(){
    String sql = "INSERT INTO courses (name, subject, skill_level, schoolId) VALUES (:name, :subject, :skill_level, :schoolId)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("subject", this.subject)
        .addParameter("skill_level", this.skill_level)
        .addParameter("schoolId", this.schoolId)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<Course> all(){
    String sql = "SELECT * FROM courses";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(Course.class);
    }
  }

  public static Course find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where id=:id";
      Course course = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Course.class);
      return course;
    }
  }

}
