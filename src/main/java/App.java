import java.util.HashMap;
import java.util.Map;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import org.sql2o.*;

//TODO: add links to hypothetical forum for people who want more info/don't see their course

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    //can go to get("/schools/new") or get("/courses")
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("schools", School.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //coming from get("/schools/new")
    post("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("school-name");
      boolean offline = request.queryParamsValues("offline-component") != null;
      boolean coding_only = request.queryParamsValues("coding_only") != null;
      boolean paid = request.queryParamsValues("paid") != null;
      String url = request.queryParams("url");
      School newSchool = new School(name, offline, coding_only, paid, url);
      newSchool.save();
      model.put("success", newSchool.getName());
      model.put("schools", School.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //posting from here will send you to post( "/")
    get("/schools/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/school-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //sends you to get(/schools/:id/courses/new) or get(/schools/:id/courses/:id)
    get("/schools/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      School school  = School.find(Integer.parseInt(request.params("id")));
      model.put("school", school);
      model.put("courses", school.getCourses());
      model.put("template", "templates/school.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //coming from get(/schools/:id/courses/new)
    post("/schools/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String course = request.queryParams("course");
      String courseType = request.queryParams("course-type");
      int skillLevel = Integer.parseInt(request.queryParams("course-skill-level"));
      int initRating = Integer.parseInt(request.queryParams("course-rating"));
      int schoolId = Integer.parseInt(request.queryParams("schoolid"));
      Course newCourse = new Course(course, courseType, skillLevel, schoolId);
      newCourse.save();
      //newCourse.updateAggregate(initRating);
      School school  = School.find(Integer.parseInt(request.params("id")));
      model.put("success", course);
      model.put("school", school);
      model.put("courses", school.getCourses());
      model.put("template", "templates/school.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //coming from "/" and can got to get("/courses/new" or get("schools/:id/courses/:id"))
    get("/courses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //coming from get ("/courses/new")
    post("/courses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String course = request.queryParams("course");
      String courseType = request.queryParams("course-type");
      int skillLevel = Integer.parseInt(request.queryParams("course-skill-level"));
      int initRating = Integer.parseInt(request.queryParams("course-rating"));
      int schoolId = Integer.parseInt(request.queryParams("schoolid"));
      Course newCourse = new Course(course, courseType, skillLevel, schoolId);
      newCourse.save();
      //newCourse.updateAggregate(initRating);
      model.put("courses", Course.all());
      model.put("success", newCourse.getName());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //posting from here will send you to "/courses"
    get("/courses/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("post-url", "/courses");
      model.put("schools", School.all());
      model.put("template", "templates/course-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    //posting from here will send you to "/schools/:id"
    get("/schools/:id/courses/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      School school  = School.find(Integer.parseInt(request.params("id")));
      model.put("schoolid", school.getId());
      model.put("post-url", "/schools/"+school.getId());
      model.put("template", "templates/course-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/schools/:school_id/courses/:id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      Course course = Course.find(Integer.parseInt(request.params("id")));
      School school = School.find(course.getSchoolId());
      model.put("school", school);
      model.put("course", course);
      model.put("template", "templates/course.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/schools/:school_id/courses/:id", (request, response) -> {
      Course course = Course.find(Integer.parseInt(request.params("id")));
      int rating = Integer.parseInt(request.queryParams("rating"));
      //course.updateAggregate(rating);
      String url = String.format("/courses/%d", course.getId());
      response.redirect(url);
      return null;
    });


  }
}
