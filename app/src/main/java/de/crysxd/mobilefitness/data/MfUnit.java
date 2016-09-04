package de.crysxd.mobilefitness.data;

/**
 * A enum for common units
 */
public enum MfUnit {
    KG, SECONDS, METER, UNDEFINED;

    /**
     * Returns the suffix for this unit
     *
     * @return the suffix
     */
    public String getSuffix() {
        switch (this) {
            case KG:
                return "kg";
            case SECONDS:
                return "s";
            case METER:
                return "m";
            default:
                return "";
        }
    }
}
