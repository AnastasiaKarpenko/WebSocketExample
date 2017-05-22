package ru.kvs.websocketexample;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketListener;

import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.R.id.message;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private WebSocket ws;
    private XmlParser3 xmlParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String text = "<omiEnvelope xmlns=\"http://www.opengroup.org/xsd/omi/1.0/\" version=\"1.0\" ttl=\"0\">\n" +
                "  <call msgformat=\"odf\">\n" +
                "    <msg>\n" +
                "      <Objects xmlns=\"http://www.opengroup.org/xsd/odf/1.0/\">\n" +
                "        <Object>\n" +
                "          <id>ParkingService</id>\n" +
                "          <InfoItem name=\"FindParking\">\n" +
                "            <value type=\"odf:Objects\">\n" +
                "              <Objects>\n" +
                "              <Object type=\"FindParkingRequest\">\n" +
                "                <id>Parameters</id>\n" +
                "                <InfoItem name=\"WantedSpotType\">\n" +
                "                  <value>ElectricVehicle</value>\n" +
                "                </InfoItem>\n" +
                "                <InfoItem name=\"ArrivalTime\">\n" +
                "                  <value type=\"dateTime\">yyyy-mm-ddThh:mm:ss.sss+-hh:mm</value>\n" +
                "                </InfoItem>\n" +
                "                <Object type=\"schema:GeoCoordinates\">\n" +
                "                  <id>Destination</id>\n" +
                "                  <InfoItem name=\"latitude\">\n" +
                "                    <value type=\"xs:double\">60.187556</value>\n" +
                "                  </InfoItem>\n" +
                "                  <InfoItem name=\"longitude\">\n" +
                "                    <value type=\"xs:double\">24.8213216</value>\n" +
                "                  </InfoItem>\n" +
                "                </Object>\n" +
                "              </Object>\n" +
                "            </Objects>\n" +
                "            </value>\n" +
                "          </InfoItem>\n" +
                "        </Object>\n" +
                "      </Objects>\n" +
                "    </msg>\n" +
                "  </call>\n" +
                "</omiEnvelope>";

        xmlParser = new XmlParser3();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
//                connect();
                parse();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if (ws != null) {
                    ws.sendText(text);
                }

            }
        }.execute();
    }

    private void connect() {
        try {
            ws = EchoClient.connect();
            ws.addListener(getListener());
        } catch (IOException e) {
            Log.d(TAG, "IOException", e);
        } catch (WebSocketException e) {
            Log.d(TAG, "WebSocketException", e);
           // System.out.println(e.getMessage());
           // e.printStackTrace();
            parse();
        }
    }

    private void parse()  {
        InputStream stream = null;
        try {
            stream = getAssets().open("xmlFile.xml");
            xmlParser.parse(stream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (ws != null) {
            ws.disconnect();
        }
    }


    public WebSocketListener getListener() {
        return new WebSocketAdapter() {
            public void onTextMessage(WebSocket websocket, String message) {
                InputStream stream = new ByteArrayInputStream(message.getBytes());
                try {
                    xmlParser.parse(stream);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
