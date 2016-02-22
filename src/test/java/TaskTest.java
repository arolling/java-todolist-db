import org.junit.*;
import static org.junit.Assert.*;

public class TaskTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_emptyAtFirst() {
    assertEquals(Task.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAretheSame() {
    Task firstTask = new Task("Mow the lawn", 1);
    Task secondTask = new Task("Mow the lawn", 1);
    assertTrue(firstTask.equals(secondTask));
  }

  @Test
  public void all_savesIntoDatabase_true() {
    Task myTask = new Task("Mow the lawn", 1);
    myTask.save();
    assertEquals(Task.all().get(0).getDescription(), "Mow the lawn");
  }

  @Test
  public void save_returnsTrueIfDescriptionsAretheSame() {
    Task myTask = new Task("Mow the lawn", 1);
    myTask.save();
    assertTrue(Task.all().get(0).equals(myTask));
  }

  @Test
  public void save_assignsIdToObject() {
    Task myTask = new Task("Mow the lawn", 1);
    myTask.save();
    Task savedTask = Task.all().get(0);
    assertEquals(myTask.getId(), savedTask.getId());
  }

  @Test
  public void find_findsTaskInDatabase_true() {
    Task myTask = new Task("Mow the lawn", 1);
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertTrue(myTask.equals(savedTask));
  }

  @Test
  public void save_savesCategoryIdIntoDB_true() {
    Category myCategory = new Category ("Household chores");
    myCategory.save();
    Task myTask = new Task("Mow the lawn", myCategory.getId());
    myTask.save();
    Task savedTask = Task.find(myTask.getId());
    assertEquals(savedTask.getCategoryId(), myCategory.getId());
  }

  @Test
  public void update_changesTaskDescription_false() {
    Task myTask = new Task("Feed the chickens", 1);
    myTask.save();
    myTask.update("Feed the cats");
    Task updatedTask = Task.find(myTask.getId());
    assertFalse(updatedTask.equals(myTask));
  }

  @Test
  public void delete_removesTaskFromDatabase_false() {
    Task myTask = new Task("Feed the chickens", 1);
    Task secondTask = new Task("Mow the lawn", 1);
    myTask.save();
    secondTask.save();
    myTask.delete();
    assertFalse(Task.all().contains(myTask));
  }

}
