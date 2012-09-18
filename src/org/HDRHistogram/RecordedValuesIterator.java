/**
 * RecordedValuesIterator.java
 * Written by Gil Tene of Azul Systems, and released to the public domain,
 * as explained at http://creativecommons.org/publicdomain/zero/1.0/
 *
 * @author Gil Tene
 * @version 1.0.1
 */

package org.HdrHistogram;

import java.util.Iterator;

/**
 * Used for iterating through all recorded histogram values using the finest granularity steps supported by the
 * underlying representation. The iteration steps through all non-zero recorded value counts, and terminates when
 * all recorded histogram values are exhausted.
 */

public class RecordedValuesIterator extends AbstractHistogramIterator implements Iterator<HistogramIterationValue> {
    int visitedSubBucketIndex;
    int visitedBucketIndex;

    /**
     * Reset iterator for re-use in a fresh iteration over the same histogram data set.
     */
    public void reset() {
        reset(histogram, rawCounts);
    }

    private void reset(final Histogram histogram, boolean rawCounts) {
        super.resetIterator(histogram, rawCounts);
        visitedSubBucketIndex = -1;
        visitedBucketIndex = -1;
    }

    RecordedValuesIterator(final Histogram histogram, boolean rawCounts) {
        reset(histogram, rawCounts);
    }

    void incrementIterationLevel() {
        visitedSubBucketIndex = currentSubBucketIndex;
        visitedBucketIndex = currentBucketIndex;
    }

    boolean reachedIterationLevel() {
        long currentIJCount = countsArray[histogram.countsArrayIndex(currentBucketIndex, currentSubBucketIndex, rawCounts)];
        return (currentIJCount != 0) &&
                ((visitedSubBucketIndex != currentSubBucketIndex) || (visitedBucketIndex != currentBucketIndex));
    }
}
