package ru.kvs.websocketexample;

import android.util.Log;

import java.io.*;
import com.neovisionaries.ws.client.*;


public class EchoClient
{
    private static final String TAG = "EchoClient";

    /**
     * The echo server on websocket.org.
     */
    private static final String SERVER = "ws://biotope.cs.hut.fi/omi/node/";

    /**
     * The timeout value in milliseconds for socket connection.
     */
    private static final int TIMEOUT = 5000;

    static WebSocket connect() throws IOException, WebSocketException
    {
        return new WebSocketFactory()
                .setConnectionTimeout(TIMEOUT)
                .createSocket(SERVER)
                .addListener(new WebSocketAdapter() {
                    // A text message arrived from the server.
                    public void onTextMessage(WebSocket websocket, String message) {
                        Log.d(TAG, message);
                    }
                })
                .addExtension(WebSocketExtension.PERMESSAGE_DEFLATE)
                .connect();
    }
}
