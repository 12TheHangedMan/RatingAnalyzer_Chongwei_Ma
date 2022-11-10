package com.chongweima.stats;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RatingAnalyzerImplTest {

    static final int[] NULL_RATING = null;
    static final int[] EMPTY_RATING = new int[]{};

    static Map<int[], Double> testMeanMap = new HashMap<>();
    static Map<int[], Double> testMedianMap = new HashMap<>();
    static Map<int[], int[]> testModeMap = new HashMap<>();

    static List<int[]> ratingAnalyzerList;

    @Before
    public void parameterInitialization() throws AnalyzerConfigurationException {
        int[] rating1 = new int[]{2, 5, 2, 3, 1, 4, 5, 2}; // mean = 3.0; median = 2.5; mode = {2}
        int[] rating2 = new int[]{2, 5, 2, 3, 1, 5, 5, 2}; // mean = 3.125; median = 2.5; mode = {2, 5}
        int[] rating3 = new int[]{3, 8, 2}; // mean = 4.33; median = 3; mode = {3, 8 ,2}
        int[] rating4 = new int[]{3}; // mean = 3; median = 3; mode = {3}

        testMeanMap.put(rating1, 3.0);
        testMeanMap.put(rating2, 3.125);
        testMeanMap.put(rating3, 4.333);
        testMeanMap.put(rating4, 3.0);

        testMedianMap.put(rating1, 2.5);
        testMedianMap.put(rating2, 2.5);
        testMedianMap.put(rating3, 3.0);
        testMedianMap.put(rating4, 3.0);

        testModeMap.put(rating1, new int[]{2});
        testModeMap.put(rating2, new int[]{2, 5});
        testModeMap.put(rating3, new int[]{2, 3, 8});
        testModeMap.put(rating4, new int[]{3});

        ratingAnalyzerList = new ArrayList<>(testMeanMap.keySet());
    }

    @Test
    public void testNullInput() {
        try {
            RatingAnalyzer.newInstance(NULL_RATING);
        } catch (AnalyzerConfigurationException e) {
            Assert.assertEquals("Null Array, Invalid", e.getMessage());
        }
    }

    @Test
    public void testEmptyInput() throws AnalyzerConfigurationException {
        try {
            RatingAnalyzer.newInstance(EMPTY_RATING);
        } catch (AnalyzerConfigurationException e) {
            Assert.assertEquals("Empty Array, Invalid", e.getMessage());
        }
    }

    //to guarantee the logic, have to test multiple ratings. Here I used lambda.
    @Test
    public void testMean() {
        ratingAnalyzerList.forEach(
                rating_analyzer ->
                        Assert.assertEquals(testMeanMap.get(rating_analyzer),
                                RatingAnalyzer.newInstance(rating_analyzer).mean(),
                                0.001));
    }
    //to guarantee the logic, have to test multiple ratings. Here I used lambda.
    @Test
    public void testMedian() {
        ratingAnalyzerList.forEach(
                rating_analyzer ->
                        Assert.assertEquals(testMedianMap.get(rating_analyzer),
                                RatingAnalyzer.newInstance(rating_analyzer).median(),
                                0.1));
    }
    //to guarantee the logic, have to test multiple ratings. Here I used lambda.
    @Test
    public void testMode() throws AnalyzerConfigurationException, IllegalArgumentException {
        ratingAnalyzerList.forEach(
                rating_analyzer ->
                        Assert.assertArrayEquals(testModeMap.get(rating_analyzer),
                                RatingAnalyzer.newInstance(rating_analyzer).mode()));
    }
}