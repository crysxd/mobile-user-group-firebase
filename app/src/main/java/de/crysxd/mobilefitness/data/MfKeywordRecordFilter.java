package de.crysxd.mobilefitness.data;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Filters a list of records with a keyword
 */
public class MfKeywordRecordFilter implements MfRecordsFilter {

    /**
     * The keyword
     */
    private String mKeyword;

    /**
     * Creates a new instance
     *
     * @param keyword the keyword for which the search results shall be filtered
     */
    public MfKeywordRecordFilter(String keyword) {
        mKeyword = keyword;
    }

    @Override
    public void filter(List<MfRecord> list) {
        List<MfRecord> clone = new ArrayList<>(list);

        for (MfRecord r : clone) {
            int length = Math.min(mKeyword.length(), r.getExercise().length());
            if (StringUtils.getJaroWinklerDistance(
                    r.getExercise().substring(0, length), mKeyword.substring(0, length)) < .9) {
                list.remove(r);
            }
        }
    }
}
