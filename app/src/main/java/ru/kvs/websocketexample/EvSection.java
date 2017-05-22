package ru.kvs.websocketexample;

import java.util.List;

public class EvSection {
    private double maxHeight;
    private double maxWidth;
    private int numberOfSpots;
    private int numberOfSpotsAvailable;
    private double hourlyPrice;
    private List<EvSpot> evSpots;

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

    public List<EvSpot> getEvSpots() {
        return evSpots;
    }

    public void setEvSpots(List<EvSpot> evSpots) {
        this.evSpots = evSpots;
    }
}
