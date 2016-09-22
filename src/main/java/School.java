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
    String sql = "INSERT INTO schools (name, offline, coding_only, paid, url) VALUES (:name, :offline, :coding_only, :paid, :url)";
    try(Connection con = DB.sql2o.open()){
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("offline", this.offline)
        .addParameter("coding_only", this.coding_only)
        .addParameter("paid", this.paid)
        .addParameter("url", this.url)
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

  public void saveRatings(int rating, int otherRating, int thirdRating){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO school_ratings (school_id, culture_rating, value_rating, ux_rating) VALUES (:school_id, :culture_rating, :value_rating, :ux_rating)";
      con.createQuery(sql)
        .addParameter("school_id", this.id)
        .addParameter("culture_rating", rating)
        .addParameter("value_rating", otherRating)
        .addParameter("ux_rating", thirdRating)
        .executeUpdate();
    }
  }

  public List<Float> getAverages() {
    String[] ratingWords = {"culture", "value", "ux"};
    List<Float> averageSchoolRatingsArray = new ArrayList<Float>();
    try(Connection con = DB.sql2o.open()){
      for(int i = 0; i < ratingWords.length; i++){
        String sqlString = "SELECT " + ratingWords[i]+ "_rating FROM school_ratings WHERE school_id=:school_id";
        List<Integer> ratingCount = con.createQuery(sqlString)
          .addParameter("school_id", id)
          .executeAndFetch(Integer.class);
        float average = (float) Math.round(ratingCount.stream().mapToDouble(r -> r)
        .average().getAsDouble()*10)/10;
        averageSchoolRatingsArray.add(average);
      }
      return averageSchoolRatingsArray;
    }
  }

  public int getNumRatings(){
    String sql = "SELECT count(id) FROM school_ratings WHERE school_id=:school_id";
    try (Connection con = DB.sql2o.open()) {
        return con.createQuery(sql)
        .addParameter("school_id", id)
        .executeScalar(Integer.class);
    }
  }
}
