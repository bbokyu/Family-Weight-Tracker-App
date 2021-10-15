package model;

import com.sun.org.apache.xerces.internal.xs.ItemPSVI;
import model.exceptions.NegativeValueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    private Member testMember;

    @BeforeEach
    void runBefore() {
        testMember = new Member("Ethan", 180);
    }

    @Test
    void testConstructor() {
        assertEquals(testMember.getName(), "Ethan");
        assertEquals(testMember.getHeight(), 180);
        assertTrue(testMember.getWeightLogs().isEmpty());
    }

    @Test
    void testAddWeightLog() {
        try {
            testMember.addWeightLog(10.0);
        } catch (NegativeValueException e) {
            e.printStackTrace();
        }
        LocalDate today = LocalDate.now();
        ArrayList<Log> tempLog = testMember.getWeightLogs();
        assertEquals(tempLog.get(0).getWeight(),10.0);
        assertEquals(tempLog.get(0).getDate(),today);
    }

    @Test
    void testAddNegativeWeight() {
        assertThrows(NegativeValueException.class, () -> {
                testMember.addWeightLog(-10.2);
            });
        }

    @Test
    void testGetWeightLog() {
        try {
            testMember.addWeightLog(10.0);
        } catch (NegativeValueException e) {
            e.printStackTrace();
        }
        assertEquals(testMember.getWeightLogs().get(0).getWeight(), 10.0);
        assertEquals(testMember.getWeightLogs().get(0).getDate(), LocalDate.now());

    }

    @Test
    void testGetName() {
        assertEquals(testMember.getName(),"Ethan");
    }

    @Test
    void testGetHeight() {
        assertEquals(testMember.getHeight(),180);
    }


}







