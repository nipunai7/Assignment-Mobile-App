package kln.ac.assignment;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import kln.ac.assignment.databinding.ActivityMapsBinding;

public class MainActivity extends Activity implements LocationListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationListener locationListener;
    private LocationManager locationManager;
    private final long MIN_TIME = 1000; //minimum time interval between location updates, in milliseconds
    private final long MIN_DISTANCE = 5; //minimum distance between location updates, in meters
    double longti;
    double lat;
    View contentView;
    

    TextView txtLat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contentView = findViewById(R.id.button2);
        contentView.setVisibility(View.GONE);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PackageManager.PERMISSION_GRANTED);


        txtLat = (TextView) findViewById(R.id.textView);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.textView);
        lat = location.getLatitude();
        longti = location.getLongitude();
       // txtLat.setText("Latitude:" + lat + ", Longitude:" + longti);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    public void sendSMS(View view){

        String mobileNo="0768652634";
        String message="I'm Nipuna Munasinghe IM/2017/047. Please help Me. I'm in https://maps.google.com/?q="+lat+","+longti;


        try{
            if(!mobileNo.equals("") && !message.equals("")){
                SmsManager smgr = SmsManager.getDefault();
                smgr.sendTextMessage(mobileNo,null,message,null,null);
                Toast.makeText(getApplicationContext(),"SMS Sent to "+mobileNo,Toast.LENGTH_LONG).show();
                contentView.setVisibility(View.VISIBLE);
                contentView = findViewById(R.id.button);
                contentView.setVisibility(View.GONE);
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"SMS Sending Failed "+e,Toast.LENGTH_LONG).show();
        }
    }

    public void finishApp(){
        finish();
    }

}