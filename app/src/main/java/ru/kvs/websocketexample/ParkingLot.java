package ru.kvs.websocketexample;

import java.util.List;

public class ParkingLot {
    private String id;
    private String maxParkingHours;
    private String owner;
    private GeoCoordinates position;
    private OpeningHours openingHours;
    private List<ParkingSection> mParkingSectionList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaxParkingHours() {
        return maxParkingHours;
    }

    public void setMaxParkingHours(String maxParkingHours) {
        this.maxParkingHours = maxParkingHours;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public GeoCoordinates getPosition() {
        return position;
    }

    public void setPosition(GeoCoordinates position) {
        this.position = position;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public List<ParkingSection> getParkingSectionList() {
        return mParkingSectionList;
    }

    public void setParkingSectionList(List<ParkingSection> parkingSectionList) {
        mParkingSectionList = parkingSectionList;
    }

    public void addParkingSection(ParkingSection parkingSection) {
        mParkingSectionList.add(parkingSection);
    }
}
