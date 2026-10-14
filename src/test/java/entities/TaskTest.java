package entities;

import org.junit.jupiter.api.Test;
import services.HouseholdService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


public class TaskTest {

    @Test
    public void nyOpgaveErIkkeFærdig(){
        Task task = new Task(1, LocalDate.now());
        assertFalse(task.isDone());
    }



}


