package de.crysxd.mobilefitness.data;

import java.util.Comparator;

/**
 * A {@link Comparator} comparing {@link MfRecord} instances base on their creation time
 */
public class MfRecordTimeComparator implements Comparator<MfRecord> {
    @Override
    public int compare(MfRecord record1, MfRecord record2) {
        return record2.getTime().compareTo(record1.getTime());

    }
}
