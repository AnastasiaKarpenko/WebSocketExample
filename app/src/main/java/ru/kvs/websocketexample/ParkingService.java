package ru.kvs.websocketexample;

import java.util.ArrayList;
import java.util.List;

public class ParkingService {
    private String id;
    private List<ParkingLot> mParkingLots;

    public ParkingService() {
        mParkingLots = new ArrayList<>();
    }

    public List<ParkingLot> getParkingLots() {
        return mParkingLots;
    }

    public void setParkingLots(List<ParkingLot> parkingLots) {
        mParkingLots = parkingLots;
    }

    public void addParkingLot(ParkingLot parkingLot) {
        mParkingLots.add(parkingLot);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
