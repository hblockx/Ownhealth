package nl.oce.ownhealth;

import android.app.Service;
import android.content.Intent;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.Vibrator;
import android.service.carrier.CarrierMessagingService;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.WearableStatusCodes;

public class WearListenerService extends WearableListenerService  {

    String WEARABLE_DATA_PATH = "/wearable_data";
    GoogleApiClient googleClient;

    Sensor mHeartRateSensor;
    SensorManager mSensorManager;

    int currentHR;
    String currentLocation;

    HeartRateListener heartRateListener;

    public WearListenerService() {


    }

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {


        if (messageEvent.getPath().equals("/message_path")) {
            final String message = new String(messageEvent.getData());
            Log.v("Watch", "Message path received on watch is: " + messageEvent.getPath());
            Log.v("Watch", "Message received on watch is: " + message);


            googleClient = new GoogleApiClient.Builder(this)
                    .addApi(Wearable.API)
                    .build();

            googleClient.connect();

            registerSensorListener();

        }
        else {
            super.onMessageReceived(messageEvent);
        }
    }

    private void registerSensorListener() {
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        heartRateListener = new HeartRateListener();
        this.mSensorManager.registerListener(heartRateListener, mSensorManager
                .getDefaultSensor(Sensor.TYPE_HEART_RATE), SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void unregisterSensorListener() {
        Log.v("Watch", "Listener is uregistered: " + this.mSensorManager.toString());
        Log.v("Watch", "Listener is uregistered: " + this.toString());
        Log.v("Watch", "Listener is uregistered: " + heartRateListener.toString());
        mSensorManager = ((SensorManager)getSystemService(SENSOR_SERVICE));
        this.mSensorManager.unregisterListener(heartRateListener, mSensorManager
                .getDefaultSensor(Sensor.TYPE_HEART_RATE));

    }



    class CreateSensorDataThread extends Thread {
        String path;
        DataMap dataMap;
        GoogleApiClient client;
        // Constructor for sending data objects to the data layer
        CreateSensorDataThread(GoogleApiClient googleClient,String p, DataMap data) {
            path = p;
            dataMap = data;
            client = googleClient;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(client).await();
            for (Node node : nodes.getNodes()) {

                // Construct a DataRequest and send over the data layer
                PutDataMapRequest putDMR = PutDataMapRequest.create(path);
                putDMR.getDataMap().putAll(dataMap);
                PutDataRequest request = putDMR.asPutDataRequest();

                DataApi.DataItemResult result = Wearable.DataApi.putDataItem(client,request).await();
                if (result.getStatus().isSuccess()) {
                    Log.v("myTag", "DataMap: " + dataMap + " sent to: " + node.getDisplayName());
                } else {
                    // Log an error
                    Log.v("myTag", "ERROR: failed to send DataMap");
                }
            }
        }
    }



    class HeartRateListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                currentHR = (int) event.values[0];
                Log.v("HR", "" + currentHR);
                DataMap dataMap = new DataMap();

                if (currentHR > 0) {
                    dataMap.putString("hr", Integer.toString(currentHR));

                    //    GoogleApiClient googleClient = new GoogleApiClient.Builder(this)
                    //            .addApi(Wearable.API)
                    //            .build();
                    new CreateSensorDataThread(googleClient, WEARABLE_DATA_PATH, dataMap).start();
                    Log.v("HR", "" + mSensorManager.toString());
                    if (mSensorManager != null && currentHR > 0) {
                        mSensorManager.unregisterListener(this);
                        unregisterSensorListener();
                    }
                }

            }


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

            // Does not matter @ HR
        }
    }
}
