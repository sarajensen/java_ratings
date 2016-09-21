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

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("courses", Course.all());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/courses", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      String course = request.queryParams("course");
      String courseType = request.queryParams("course-type");
      int skillLevel = Integer.parseInt(request.queryParams("course-skill-level"));
      int initRating = Integer.parseInt(request.queryParams("course-rating"));
      Course newCourse = new Course(course, courseType, skillLevel);
      newCourse.save();
      newCourse.updateAggregate(initRating);
      model.put("courses", Course.all());
      model.put("success", newCourse.getName());
      model.put("template", "templates/courses.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/courses/new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/course-form.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
