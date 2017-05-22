package ru.kvs.websocketexample;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

public class XmlParserTwo {
    private static final String TAG = "XmlParser";

    // We don't use namespaces
    private static final String ns = null;

    public void parse(InputStream in) throws XmlPullParserException, IOException {
        try {

/*
*   setup()
*   while ( END OF XML){ ps.add(readParkingLot);}
*
* readParkingLot{ if nexttag == hours new Hours}
*
* */

//////// SETUP PHASE ////////////
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            readOmiEnvelope(parser);
            readResponse(parser);
            readResult(parser);
            readMsg(parser);
            readObjects(parser);
            readObject(parser);

            parser.require(XmlPullParser.START_TAG, ns, "id");

            String tagValue = parser.nextText();
            if (!tagValue.equals("ParkingService")) {
                System.out.println("WE ARE IN TROUBLES");
                return;
            }

////////// END SETUP PHASE ////////////
            ParkingService parkingService = new ParkingService();

            while (parser.next() != XmlPullParser.END_TAG){
                ParkingLot pl = readParkingLot(parser);
                parkingService.addParkingLot(pl);
            }
            // FINISH PARSING NOW YOU CAN SORT

        } finally {
            in.close();
        }
    }

    private ParkingLot readParkingLot(XmlPullParser parser)throws IOException, XmlPullParserException {
//        readObject(parser);
        parser.require(XmlPullParser.START_TAG, ns, "id");
        String tagValue = parser.nextText();

        System.out.println("TAGVALUE: "+tagValue);


        return null;
    }

    private void readOmiEnvelope(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "omiEnvelope");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();

            // Starts by looking for the entry tag
            if (name.equals("response")) {
               return;// readResponse(parser);
            } else {
                skip(parser);
            }
        }


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
                return; //readResult(parser);
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
                return;
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
                return;
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
                return;
            } else {
                skip(parser);
            }
        }
    }

    private void readObject(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "Object");

        String attrType = parser.getAttributeValue(ns, "type");
        Log.d(TAG, "Attr : " + attrType);


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            Log.d(TAG, "Next tag name : " + name);


            // Starts by looking for the entry tag
            if (name.equals("id")) {
                return; //readId(parser);
            } else {
                skip(parser);
            }
        }
    }

    private void readId(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "id");

        String tagValue = parser.nextText();




        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            Log.d(TAG, "Next tag name: " + name);


            // Starts by looking for the entry tag
            if (name.equals("InfoItem")) {
                readInfoItem(parser);
            } else if (name.equals("Object")) {
                readObject(parser);
            } else {
                skip(parser);
            }
        }
    }

    private void readInfoItem(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "InfoItem");

        String attrName = parser.getAttributeValue(ns, "name");
        Log.d(TAG, "Attr : " + attrName);


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            Log.d(TAG, "Next tag name: " + name);


            // Starts by looking for the entry tag
            if (name.equals("Object")) {
                readObject(parser);

            } else if (name.equals("id")) {
                readId(parser);

            } else if (name.equals("InfoItem")) {
                readInfoItem(parser);
            } else if (name.equals("value")) {
                readValue(parser);
            } else {
                skip(parser);
            }

        }
    }

    private void readValue(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "value");

        String tagValue = parser.nextText();
        Log.d(TAG, "Tag value : " + tagValue);


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = parser.getName();
            Log.d(TAG, "Next tag name: " + name);


            // Starts by looking for the entry tag
            if (name.equals("Object")) {
                readObject(parser);

            } else if (name.equals("id")) {
                readId(parser);

            } else if (name.equals("InfoItem")) {
                readInfoItem(parser);
            } else if (name.equals("value")) {
                readValue(parser);
            }

        }
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

    private boolean setup (XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "omiEnvelope");

        String name = parser.getName();
        if (!name.equals("response")) {
            Log.d(TAG, "Problem : response not received");
            return false;
        }
        name = parser.nextText();
        return true;
    }
}

