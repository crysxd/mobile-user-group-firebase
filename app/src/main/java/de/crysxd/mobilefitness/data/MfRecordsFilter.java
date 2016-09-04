package de.crysxd.mobilefitness.data;

import java.util.List;

/**
 * A interface to filter a list of {@link MfRecord}
 */
public interface MfRecordsFilter {

    /**
     * Removes all unwanted entries from the list
     * @param list the list to be filtered
     */
    void filter(List<MfRecord> list);

}
