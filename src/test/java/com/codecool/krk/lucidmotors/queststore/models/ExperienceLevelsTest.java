package com.codecool.krk.lucidmotors.queststore.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.TreeMap;

class ExperienceLevelsTest {
    ExperienceLevels experienceLevels;

    @BeforeEach
    public void prepareObject() {
        this.experienceLevels = new ExperienceLevels();
    }

    @Test
    public void testAddTestLevel() {
        this.experienceLevels.addLevel(10, 1);
        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();
        expectedLevels.put(1, 10);
        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }

    @Test
    public void testCannotAddTheSameLevel() {
        this.experienceLevels.addLevel(10, 1);
        this.experienceLevels.addLevel(12, 1);

        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();
        expectedLevels.put(1, 10);
        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }

    @Test
    public void testTwoLevelsWithSameCoins() {
        this.experienceLevels.addLevel(10, 1);
        this.experienceLevels.addLevel(10, 2);

        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();
        expectedLevels.put(1, 10);
        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }

    @Test
    public void testUpdateNonExistingLevel() {
        this.experienceLevels.updateLevel(10, 1);

        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();

        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }

    @Test
    public void testUpdateExistingLevel() {
        this.experienceLevels.addLevel(5, 1);
        this.experienceLevels.updateLevel(10, 1);

        TreeMap<Integer, Integer> expectedLevels = new TreeMap<>();
        expectedLevels.put(1, 10);

        assertEquals(expectedLevels, this.experienceLevels.getLevels());
    }

    @Test
    public void testComputeLevelMin() {
        this.experienceLevels.addLevel(10, 1);
        this.experienceLevels.addLevel(30, 2);

        assertEquals(Integer.valueOf(1), this.experienceLevels.computeStudentLevel(10));
    }

    @Test
    public void testComputeLevelMax() {
        this.experienceLevels.addLevel(10, 1);
        this.experienceLevels.addLevel(30, 2);

        assertEquals(Integer.valueOf(1), this.experienceLevels.computeStudentLevel(29));
    }

    @Test
    public void testComputeLevelLowerThanFirstLevel() {
        this.experienceLevels.addLevel(10, 1);
        this.experienceLevels.addLevel(30, 2);

        assertEquals(Integer.valueOf(0), this.experienceLevels.computeStudentLevel(9));
    }

    @Test
    public void testComputeLevelWhenThereIsNoLevels() {
        assertEquals(Integer.valueOf(0), this.experienceLevels.computeStudentLevel(30));
    }

}