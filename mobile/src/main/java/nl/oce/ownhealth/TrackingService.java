package nl.oce.ownhealth;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TrackingService extends Service implements
        DataApi.DataListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,DataApi.DataItemResult {

    String WEARABLE_DATA_PATH = "/wearable_data";
    GoogleApiClient googleClient;
    Service context;
    Boolean isRunning = false;

    int SLEEPTIME = 30000;

    String output;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        output = "";
        super.onCreate();
        context = this;
        isRunning = true;
        googleClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        new Thread(new Runnable() {
            public void run() {
                while(isRunning){
                    try {
                        work();
                        Thread.sleep(SLEEPTIME);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

    public TrackingService() {

    }

    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }

    public void work(){
        if(googleClient == null){
            googleClient = new GoogleApiClient.Builder(this)
                    .addApi(Wearable.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
        }
        if(googleClient != null){
            if(!googleClient.isConnected()){
                googleClient.connect();
            }
            if(googleClient.isConnected()){
                Wearable.DataApi.removeListener(googleClient,this);
                Wearable.DataApi.addListener(googleClient, this);
                String message = "Hello wearable\n give me your sensors";
                new AskForSensorsThread("/message_path", message).start();
            }
        }
    }


    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
        DataMap dataMap;
        Log.v("CONNECTION", "DATA CHANGED");
        for (DataEvent event : dataEventBuffer) {

            // Check the data type
            if (event.getType() == DataEvent.TYPE_CHANGED) {
                // Check the data path
                String path = event.getDataItem().getUri().getPath();
                if (path.equals(WEARABLE_DATA_PATH)) {

                }
                dataMap = DataMapItem.fromDataItem(event.getDataItem()).getDataMap();
                Log.v("myTag", "DataMap received on Phone: " + dataMap);
                Wearable.DataApi.removeListener(googleClient, this);
                safeData(dataMap);
            }
        }
    }

    public void safeData(DataMap data){
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.GERMAN);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        output +=dateFormat.format(date)+","+ data+"\n";

        try {
            File sdcard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            // to this path add a new directory path
            File file = new File(sdcard.getAbsolutePath() ,"trackedData.txt");
            if(!file.exists()){
                file.createNewFile();
            }

            FileOutputStream os =  new FileOutputStream(file);
            os.write(output.getBytes());
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    @Override
    public void onConnected(Bundle bundle) {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public DataItem getDataItem() {
        Log.v("CONNECTION", "got data");
        return null;
    }

    @Override
    public Status getStatus() {
        Log.v("CONNECTION", "got status");
        return null;
    }

    class AskForSensorsThread extends Thread {
        String path;
        String message;


        AskForSensorsThread(String p, String msg) {
            path = p;
            message = msg;
        }

        public void run() {
            NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleClient).await();
            for (Node node : nodes.getNodes()) {
                MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleClient, node.getId(), path, message.getBytes()).await();
                if (result.getStatus().isSuccess()) {
                    Log.v("Smartphone", "Message: {" + message + "} sent to: " + node.getDisplayName());
                } else {
                    // Log an error
                    Log.v("Smartphone", "ERROR: failed to send Message");
                }
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.v("CONNECTION", "SUSPENDED");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        googleClient.connect();
        Log.v("CONNECTION", "FAILED");
    }


}
