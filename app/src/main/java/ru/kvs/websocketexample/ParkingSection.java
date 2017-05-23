package ru.kvs.websocketexample;

import java.util.List;

public class ParkingSection {
    private String id;
    private double maxHeight;
    private double maxWidth;
    private int numberOfSpots;
    private int numberOfSpotsAvailable;
    private double hourlyPrice;
    private List<ParkingSpot> mParkingSpots;

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

    public List<ParkingSpot> getParkingSpots() {
        return mParkingSpots;
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
        this.mParkingSpots = parkingSpots;
    }

    public void addSpot(ParkingSpot parkingSpot) {
        mParkingSpots.add(parkingSpot);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
