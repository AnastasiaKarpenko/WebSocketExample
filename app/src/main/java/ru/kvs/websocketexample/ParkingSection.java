package ru.kvs.websocketexample;

import java.util.List;

public class ParkingSection {
    private String id;
    private double maxHeight;
    private double maxWidth;
    private int numberOfSpots;
    private int numberOfSpotsAvailable;
    private double hourlyPrice;
    private List<Spot> mSpots;

    public double getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(double maxHeight) {
        this.maxHeight = maxHeight;
    }

    public double getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(double maxWidth) {
        this.maxWidth = maxWidth;
    }

    public int getNumberOfSpots() {
        return numberOfSpots;
    }

    public void setNumberOfSpots(int numberOfSpots) {
        this.numberOfSpots = numberOfSpots;
    }

    public int getNumberOfSpotsAvailable() {
        return numberOfSpotsAvailable;
    }

    public void setNumberOfSpotsAvailable(int numberOfSpotsAvailable) {
        this.numberOfSpotsAvailable = numberOfSpotsAvailable;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(double hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public List<Spot> getSpots() {
        return mSpots;
    }

    public void setSpots(List<Spot> spots) {
        this.mSpots = spots;
    }

    public void addSpot(Spot spot) {
        mSpots.add(spot);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
