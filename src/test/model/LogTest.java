package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogTest {

    private Log testLog;

    @BeforeEach
    void runBefore() {
        testLog = new Log(10.2);
    }

    @Test
    void testConstructor() {
        LocalDate today = LocalDate.now();
        assertEquals(testLog.getDate(), today);
        assertEquals(testLog.getWeight(), 10.2);
    }

    @Test
    void testGetWeight() {
        assertEquals(testLog.getWeight(), 10.2);
    }

    @Test
    void testGetDate() {
        LocalDate today = LocalDate.now();
        assertEquals(testLog.getDate(), today);
    }

}
