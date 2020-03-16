package com.playground.loaylty.loaltyaxondemo.query;

import java.time.LocalDate;

public class PointChange {
//    TODO this could be final if there is correct transformation from PointsChange to Dto
    private LocalDate localDate;
    private long change;
    private ChangeType changeType;

    public PointChange() {
    }

    public PointChange(LocalDate localDate, long change, ChangeType changeType) {
        this.localDate = localDate;
        this.change = change;
        this.changeType = changeType;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public long getChange() {
        return change;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public void setChange(long change) {
        this.change = change;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public enum ChangeType {
        CREATED,
        ADD_CREDIT,
        DISCARD_CREDIT,
        USED_CREDIT
    }
}


