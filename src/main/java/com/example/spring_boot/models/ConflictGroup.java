package com.example.spring_boot.models;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import com.example.spring_boot.models.*;


public class ConflictGroup {

    private LocalTime minTime;
    private LocalTime maxTime;
    private List<SubGroup> subGroups = new ArrayList<SubGroup>();

    public ConflictGroup() {
    }

    public ConflictGroup(LocalTime minTime, LocalTime maxTime) {
        this.minTime = minTime;
        this.maxTime = maxTime;
    }

    // new conflict group
    public ConflictGroup(Event event) {

        LocalTime startTime = event.getStartTime();
        LocalTime endTime = event.getEndTime();

        this.minTime = startTime;
        this.maxTime = endTime;

        SubGroup subgroup = new SubGroup(startTime, endTime, event);
        subGroups.add(subgroup);
    }

    public void setMinTime(LocalTime minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(LocalTime maxTime) {
        this.maxTime = maxTime;
    }

    public LocalTime getMinTime() {
        return this.minTime;
    }

    public LocalTime getMaxTime() {
        return this.maxTime;
    }

    public List<SubGroup> getSubGroups() {
        return this.subGroups;
    }

    public boolean addToConflictGroup(Event event) {

        boolean result = false;

        LocalTime startTime = event.getStartTime();
        LocalTime endTime = event.getEndTime();

        System.out.println("range before adjustment");
        System.out.println(minTime.toString());
        System.out.print("-");
        System.out.println(maxTime.toString());

        // 4 clash cases

        // 8 12 14 15
        // TODO: IF EVENTS ARE SORTED BY TIME START, UNNECESSARY CODE CAN BE REMOVED
    
        // left side overlap from min max 
        if (startTime.isBefore(minTime) && endTime.isAfter(minTime)) {
            this.minTime = startTime;
            result = true;
            System.out.println("left overlap");

            // larger overlap from min max on both sides
            if (endTime.isAfter(maxTime)) {
                this.maxTime = endTime;
                System.out.println("double overlap");

            }
        }

        // right side overlap from min max
        if (endTime.isAfter(maxTime) && startTime.isBefore(maxTime)) {
            this.maxTime = endTime;
            result = true;
            System.out.println("right overlap");

        }

        // perfect overlap or tiny overlap inside min max
        if ((startTime.equals(minTime) && endTime.equals(maxTime)) || (startTime.isAfter(minTime) && endTime.isBefore(maxTime))) {
            result = true;
            System.out.println("perfect / small overlap");

        }



        if (!result) {
            System.out.println("no conflicts with this group");
            return false;
        }

        System.out.println("conflicts EXISTS");

        System.out.println("event range");
        System.out.print(event.getStartTime().toString());
        System.out.print("-");
        System.out.println(event.getEndTime());

        System.out.println("conflict group range =");
        System.out.print(minTime.toString());
        System.out.print("-");
        System.out.println(maxTime.toString());

        


        boolean placedInSubGroup = false;

        // search through existing subgroups
            for (SubGroup subGroup : subGroups) {
                
                placedInSubGroup = subGroup.addToSubGroup(event);

                if (placedInSubGroup) {
                    System.out.println("placed in existing subgroup");
                    return true;
                }

            }

        // if not found, create new subgroup
        SubGroup newSubGroup = new SubGroup(startTime, endTime, event);
        subGroups.add(newSubGroup);

        return true;


    }

    


}

// for event in event

// for conflict group in conflict groups

// if groups size = 0

// start conflict group