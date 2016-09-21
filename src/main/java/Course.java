import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.OptionalDouble;

public class Course{
  private String name;
  private String subject;
  private int skill_level;
  private float aggregate;
  private List<Integer> numRatings;
  private int id;

  public Course(String name, String subject, int skill_level){
    this.name = name;
    this.subject = subject;
    this.skill_level = skill_level;
    aggregate = 0;
    numRatings = new ArrayList<Integer>();
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

  public float getAggregate() {
    return aggregate;
  }

  public int getId() {
    return id;
  }

  public void updateAggregate(int rating){
    numRatings.add(rating);
    aggregate = (float) Math.round(numRatings.stream().mapToDouble(r -> r)
      .average().getAsDouble()*10)/10;
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE courses SET aggregate=:aggregate WHERE id=:id";
      con.createQuery(sql)
        .addParameter("aggregate", aggregate)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  @Override
  public boolean equals(Object otherCourse) {
    if (!(otherCourse instanceof Course)) {
      return false;
    } else {
      Course newCourse = (Course) otherCourse;
      return this.getName().equals(newCourse.getName()) && this.getSubject().equals(newCourse.getSubject()) && this.getSkillLevel() == newCourse.getSkillLevel() && this.getAggregate() == newCourse.getAggregate();
    }

  }

  public void save(){
    String sql = "INSERT INTO courses (name, subject, skill_level, aggregate) VALUES (:name, :subject, :skill_level, :aggregate)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("subject", this.subject)
        .addParameter("skill_level", this.skill_level)
        .addParameter("aggregate", this.aggregate)
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
