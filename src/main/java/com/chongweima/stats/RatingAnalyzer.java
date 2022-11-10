package com.chongweima.stats;

import java.util.*;

public interface RatingAnalyzer {

    double mean();

    double median();

    int[] mode();

    static RatingAnalyzer newInstance(int[] ratings) throws AnalyzerConfigurationException {

        class RatingAnalyzerImpl implements RatingAnalyzer {

            private final int[] ratings;

            public RatingAnalyzerImpl(int[] ratings) throws IllegalArgumentException {
                if (ratings == null) {
                    throw new AnalyzerConfigurationException("Null Array, Invalid");
                } else if (ratings.length == 0) {
                    throw new AnalyzerConfigurationException("Empty Array, Invalid");
                } else {
                    this.ratings = ratings;
                }
            }

            @Override
            public double mean() {
                double sum = Arrays.stream(ratings).sum();
                //logic inside the constructor guarantee ratings is neither null nor empty, therefore ratings.length > 0.
                return sum / ratings.length;
            }

            @Override
            public double median() {
                //so it doesn't alter the order of original array
                int[] sortedArray = ratings.clone();
                Arrays.sort(sortedArray);

                int medianArrayIndex = sortedArray.length / 2;

                double median = sortedArray[medianArrayIndex];

                //when ratings have even elements in it.
                if (sortedArray.length % 2 == 0) {
                    median += sortedArray[medianArrayIndex - 1];
                    median /= 2.0;
                }

                return median;
            }

            @Override
            public int[] mode() {
                HashMap<Integer, Integer> maps = new HashMap<>();

                int biggestCount = 1;
                //build a hashmap from rating to remove the duplicated elements as well as find the count of each element
                for (int element : ratings) {
                    int elementCounts = 1;

                    if (maps.containsKey(element)) {
                        elementCounts = maps.get(element) + 1;
                        //find the biggest count, and the value with the biggest count will be the mode
                        if (elementCounts > biggestCount) {
                            biggestCount = elementCounts;
                        }
                    }
                    maps.put(element, elementCounts);
                }
                //find modes and covert them to List
                List<Integer> outputArrayList = buildModeArrayList(maps, biggestCount);
                //Convert modes List into int array
                return modeArray(outputArrayList);
            }

            private List<Integer> buildModeArrayList(Map<Integer, Integer> maps, int biggestCount) {
                List<Integer> outputArrayList = new ArrayList<>();

                for (Integer modeNumbers : maps.keySet()) {
                    if (maps.get(modeNumbers).equals(biggestCount)) {
                        outputArrayList.add(modeNumbers);
                    }
                }

                return outputArrayList;
            }

            private int[] modeArray(List<Integer> list) {
                int[] outputArray = new int[list.size()];

                for (int i = 0; i < list.size(); i++) {
                    outputArray[i] = list.get(i);
                }

                return outputArray;
            }
        }

        return new RatingAnalyzerImpl(ratings);
    }
}
