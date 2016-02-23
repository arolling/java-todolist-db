import java.util.List;
import org.sql2o.*;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.Date;

public class Task {

  private int id;
  private int categoryId;
  private String description;
  private java.sql.Timestamp createdAt;
  //private String dueDate;

  @Override
  public boolean equals(Object otherTask) {
    if (!(otherTask instanceof Task)) {
      return false;
    } else {
      Task newTask = (Task) otherTask;
      return this.getDescription().equals(newTask.getDescription()) &&
        this.getId() == newTask.getId() &&
        this.getCreatedAt().equals(newTask.getCreatedAt()) &&
        this.getCategoryId() == newTask.getCategoryId();
    }
  }

  public int getId() {
    return id;
  }

  public int getCategoryId() {
    return categoryId;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public String getDescription() {
    return description;
  }

  public Task(String description, int categoryId) {
    this.description = description;
    this.categoryId = categoryId;
    //dueDate = "";
    Date date = new Date();
    long time = date.getTime();
    createdAt = new java.sql.Timestamp(time);

  }

  // public void addDueDate(String dueDate) {
  //   this.dueDate = dueDate;
  // }
  //
  // public String getDueDate() {
  //   return this.dueDate;
  // }


  //DATABASE STUFF BELOW

  public static List<Task> all() {
    String sql = "SELECT id, description, categoryId, createdat FROM Tasks";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Task.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO Tasks (description, categoryid, createdat) VALUES (:description, :categoryId, :createdAt)";
      id = (int) con.createQuery(sql, true)
        .addParameter("description", description)
        .addParameter("categoryId", categoryId)
        .addParameter("createdAt", createdAt)
        //.addParameter("due_date", dueDate)
        //.setTimestamp(3, createdAt)
        .executeUpdate()
        .getKey();
    }
  }

  public static Task find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM Tasks WHERE id = :id";
      Task task = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Task.class);
      return task;
    }
  }

  public void update(String description) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE tasks SET description = :description WHERE id = :id";
      con.createQuery(sql)
        .addParameter("description", description)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM tasks WHERE id = :id";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

 }
