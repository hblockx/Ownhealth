package nl.oce.ownhealth;

import android.os.AsyncTask;

import nl.oce.ownhealth.JSon.CreateMockUserAndJson;
import nl.oce.ownhealth.JSon.UserJSonProvider;

/**
 * Created by Zoldi on 8/26/2015.
 */
public class ConnectionTask extends AsyncTask<String, Integer, String> {

    @Override
    protected String doInBackground(String... params) {


        UDPSender udpsender = new UDPSender("192.168.33.251", 5000);
        udpsender.sendData(params[0]);

        return null;
    }
}
