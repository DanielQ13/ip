package galath.task;

import org.junit.jupiter.api.Test;
import galath.task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class TaskTest {
    @Test
    public void getDescription_success() {
        // getDescription return the description of the task
        assertEquals("Watch Movie", new Task("Watch Movie").getDescription());
    }

    @Test
    public void getIsDone_success() {
        // getIsDone return the false for the new task
        assertFalse(new Task("Watch Movie").getIsDone());
    }
}
