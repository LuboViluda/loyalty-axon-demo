package com.playground.loaylty.loaltyaxondemo.query;

import com.playground.loaylty.loaltyaxondemo.query.PointChange.ChangeType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PointsChangeTracker {
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

}
