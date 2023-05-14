package pl.db.gogreen.atm.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum RequestTypeEnum {

    FAILURE_RESTART(1),
    PRIORITY(2),
    SIGNAL_LOW(3),
    STANDARD(4);

    private final int priority;

    RequestTypeEnum(int priority) {
        this.priority = priority;
    }

    public int getPriority(){
        return priority;
    }

    @JsonCreator
    public static RequestTypeEnum fromValue(String value) {
        for (RequestTypeEnum b : RequestTypeEnum.values()) {
            if (b.name().equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}