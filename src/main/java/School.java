import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class School{
  private String name;
  private boolean offline;
  private boolean coding_only;
  private boolean paid;
  private String url;
  private float aggregate_score;
  private int id;

  public School(String name, boolean offline, boolean coding_only, boolean paid, String url) {
    this.name = name;
    this.offline = offline;
    this.coding_only = coding_only;
    this.paid = paid;
    this.url = url;
    aggregate_score = 0;
  }

  public String getName() {
    return name;
  }

  public boolean isOffline() {
    return offline;
  }

  public boolean isCodingOnly() {
    return coding_only;
  }

  public boolean isPaid() {
    return paid;
  }

  public String getUrl() {
    return url;
  }

  public float getAggregateScore() {
    return aggregate_score;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object otherSchool) {
    if (!(otherSchool instanceof School)) {
      return false;
    } else {
      School newSchool = (School) otherSchool;
      return this.getName().equals(newSchool.getName()) && this.isOffline() == (newSchool.isOffline()) && this.isCodingOnly() == newSchool.isCodingOnly() && this.isPaid() == newSchool.isPaid() && this.getUrl().equals(newSchool.getUrl()) && this.getAggregateScore() == newSchool.getAggregateScore();
    }
  }

  public void save(){
    String sql = "INSERT INTO schools (name, offline, coding_only, paid, url, aggregate_score) VALUES (:name, :offline, :coding_only, :paid, :url, :aggregate_score)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("offline", this.offline)
        .addParameter("coding_only", this.coding_only)
        .addParameter("paid", this.paid)
        .addParameter("url", this.url)
        .addParameter("aggregate_score", this.aggregate_score)
        .executeUpdate()
        .getKey();
    }
  }

  public static List<School> all(){
    String sql = "SELECT * FROM schools";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql).executeAndFetch(School.class);
    }
  }

  public static School find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM schools where id=:id";
      School school = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(School.class);
      return school;
    }
  }

  public List<Course> getCourses(){
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM courses where schoolId=:id";
      return con.createQuery(sql)
        .addParameter("id", this.id)
        .executeAndFetch(Course.class);
    }
  }

}
