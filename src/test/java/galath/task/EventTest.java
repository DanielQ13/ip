package galath.task;

import org.junit.jupiter.api.Test;

import galath.task.Event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class EventTest {
    @Test
    public void parseDateTime_exceptionThrown() throws Exception {
        try {
            assertEquals(0, new Event("Watch Movie", "2018-814-28", "2472-29-38").getTo());
            fail(); // the test should not reach this line
        } catch (Exception e) {
            assertEquals("Invalid date format. Please use: yyyy-MM-dd or yyyy-MM-dd HHmm\n     Example: 2019-12-02 or 2019-12-02 1800", e.getMessage());
        }

    }
}
