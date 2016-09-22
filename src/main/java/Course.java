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

  }

  // public float getAverage(){
  //   numRatings++;
  //   ratingTotal+=rating;
  //   aggregate = (float) Math.round(((float)ratingTotal/(float)numRatings)*10)/10;
  //   try(Connection con = DB.sql2o.open()){
  //     String sql = "UPDATE courses SET aggregate=:aggregate, numRatings=:numRatings, ratingTotal=:ratingTotal WHERE id=:id";
  //     con.createQuery(sql)
  //       .addParameter("aggregate", aggregate)
  //       .addParameter("numRatings", numRatings)
  //       .addParameter("ratingTotal", ratingTotal)
  //       .addParameter("id", id)
  //       .executeUpdate();
  //   }
  // }

  public int getNumRatings(){
    return 0;
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
