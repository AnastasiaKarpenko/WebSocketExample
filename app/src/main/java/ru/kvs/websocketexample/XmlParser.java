package ru.kvs.websocketexample;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class XmlParser {
    private static final String TAG = "XmlParser";

    // We don't use namespaces
    private static final String ns = null;
    public static final String PARKING_SERVICE = "ParkingService";
    public static final String ID = "id";
    public static final String PARKING_LOTS = "ParkingLots";
    public static final String SCHEMA_PARKING_FACILITY = "schema:ParkingFacility";
    public static final String VALUE = "value";
    public static final String INFO_ITEM = "InfoItem";

    private ParkingService parkingService;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            readOmiEnvelope(parser);
        } finally {
            in.close();
        }

        parkingService.getParkingLots();

        return new ArrayList();
    }

    private List readOmiEnvelope(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "omiEnvelope");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // Starts by looking for the entry tag
            if (name.equals("response")) {
                readResponse(parser);
            } else {
                skip(parser);
            }
        }

        return null;
    }

    private void readResponse(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "response");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // Starts by looking for the entry tag
            if (name.equals("result")) {
                readResult(parser);
            } else {
                skip(parser);
            }
        }
    }

    private void readResult(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "result");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // Starts by looking for the entry tag
            if (name.equals("msg")) {
                readMsg(parser);
            } else {
                skip(parser);
            }
        }
    }

    private void readMsg(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "msg");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // Starts by looking for the entry tag
            if (name.equals("Objects")) {
                readObjects(parser);
            } else {
                skip(parser);
            }
        }
    }

    private void readObjects(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Objects");

        String attrXmlns = parser.getAttributeValue(ns, "xmlns");
        Log.d(TAG, "Attr : " + attrXmlns);

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // Starts by looking for the entry tag
            if (name.equals("Object")) {
                readObject(parser);
            } else {
                skip(parser);
            }
        }
    }

    private void readObject(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Object");

        String attrName = parser.getAttributeValue(ns, "name");

        String attrType = parser.getAttributeValue(ns, "type");
        Log.d(TAG, "Attr : " + attrType);

        String id = readSimpleId(parser);

        Log.d(TAG, "ID: " + id);

        switch (id) {
            case PARKING_SERVICE:
                parkingService = new ParkingService();
                break;
            case PARKING_LOTS:
                readParkingLots(attrType, id, parser);
                break;
            default:
                readByType(attrType, id, parser);
        }

//        if (attrType == null) {
//            String id = readSimpleId(parser);
//
//            if (id.equals(PARKING_SERVICE)) {
//                parkingService = new ParkingService();
//            }
//        } else {
//            if (attrType.equals("schema:ParkingFacility")) {
//                String id = readSimpleId(parser);
//
//                ParkingLot parkingLot = new ParkingLot();
//                parkingService.addParkingLot(parkingLot);
//                readParkingFacility(parser, parkingLot);
//            } else if (attrType.equals("schema:GeoCoordinates")) {
//                Log.d(TAG, "attrType equals: " + attrType);
//            } else if (attrType.equals("schema:OpeningHoursSpecification")) {
//                Log.d(TAG, "attrType equals: " + attrType);
//            } else if (attrType.equals("list")) {
//                Log.d(TAG, "attrType equals: " + attrType);
//            } else if (attrType.equals("ParkingSpotType")) {
//                Log.d(TAG, "attrType equals: " + attrType);
//            } else if (attrType.equals("ParkingSpot")) {
//                Log.d(TAG, "attrType equals: " + attrType);
//            }
//        }
    }

    private void readParkingLots(String attrType, String id, XmlPullParser parser)
            throws IOException, XmlPullParserException {

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            Log.d(TAG, "readParkingLots: Next tag name : " + name);
        }
    }

    private void readByType(String attrType, String id, XmlPullParser parser) throws IOException, XmlPullParserException {
        switch (attrType) {
            case SCHEMA_PARKING_FACILITY:
                readParkingFacility(id, parser);
                break;
        }
    }

    private void readParkingFacility(String id, XmlPullParser parser) throws IOException, XmlPullParserException {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setId(id);
        parkingService.addParkingLot(parkingLot);

        readInfoItems(parkingLot, parser);
    }

    private String readSimpleId(XmlPullParser parser) throws IOException, XmlPullParserException {

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            Log.d(TAG, "readIdTest: Next tag name : " + name);


            if (name.equals(ID)) {
                return parser.nextText();
            }
        }

        return "";
    }

//    private void readParkingFacility(XmlPullParser parser, ParkingLot parkingLot)
//            throws IOException, XmlPullParserException {
//
//        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
//                continue;
//            }
//
//            String name = parser.getName();
//            Log.d(TAG, "readParkingFacility: Next tag name : " + name);
//
//
//            // Starts by looking for the entry tag
//            if (name.equals("id")) {
//                String id = readIdTest(parser);
//                parkingLot.setId(id);
//            } else {
//                Log.d(TAG, "readParkingFacility: Next tag name : " + name);
//                skip(parser);
//            }
//        }
//    }
//
//    private void readId(XmlPullParser parser) throws IOException, XmlPullParserException {
//        parser.require(XmlPullParser.START_TAG, ns, "id");
//
//        String tagValue = parser.nextText();
//        Log.d(TAG, "Tag id value : " + tagValue);
//
//        if (tagValue.equals("ParkingService")) {
//            parkingService = new ParkingService();
//        }
//
//        while (parser.next() != XmlPullParser.END_TAG) {
//            if (parser.getEventType() != XmlPullParser.START_TAG) {
//                continue;
//            }
//
//            String name = parser.getName();
//            Log.d(TAG, "Next tag name: " + name);
//
//
//            // Starts by looking for the entry tag
//            if (name.equals("InfoItem")) {
////                readInfoItem(parser);
//            } else if (name.equals("Object")) {
//                readObject(parser);
//            } else {
//                skip(parser);
//            }
//        }
//    }

    private void readInfoItems(ParkingLot parkingLot, XmlPullParser parser) throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            String attrName = parser.getAttributeValue(ns, "name");

            Log.d(TAG, "readInfoItems Next tag name: " + name);
            Log.d(TAG, "readInfoItems Attr : " + attrName);

            if (name.equals(INFO_ITEM)) {
                String value = readValue(parser);

                if(attrName.equals("MaxParkingHours")){
                    parkingLot.setMaxParkingHours(value);
                }

            } else {
                skip(parser);
            }
        }
    }

    private String readValue(XmlPullParser parser) throws IOException, XmlPullParserException {
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            Log.d(TAG, "readValue: Next tag name : " + name);


            if (name.equals(VALUE)) {
                return parser.nextText();
            }
        }

        return "";
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
