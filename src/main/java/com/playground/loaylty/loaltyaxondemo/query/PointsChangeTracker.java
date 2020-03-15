package com.playground.loaylty.loaltyaxondemo.query;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

class PointsChangeTracker {
    private List<PointChange> pointChangeList;

    PointsChangeTracker() {
        pointChangeList = new ArrayList<>();
    }

    public void addPointsChange(LocalDate localDate, long change, ChangeType changeType) {
        pointChangeList.add(new PointChange(localDate, change, changeType));
    }

    public List<PointChange> getPointChangeList() {
        return pointChangeList;
    }

    static class PointChange {
        private final LocalDate localDate;
        private final long change;
        private final ChangeType changeType;

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
    }

    public enum ChangeType {
        ADD_CREDIT,
        DISCARD_CREDIT,
        USED_CREDIT
    }
}
