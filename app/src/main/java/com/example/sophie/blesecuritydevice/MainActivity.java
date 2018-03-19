package com.example.sophie.blesecuritydevice;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    //components
    BluetoothAdapter bluetooth_adapter = null;
    Context context;
    //variables
    //TO DO : array_of_found_devices;
    public boolean scanning_now = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.context = context;
// S W I T C H   B L E   O N
            //I use bluetooth manager to get BT adapter
            final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
            bluetooth_adapter = bluetoothManager.getAdapter();
            // check bluetooth is available and on
            if (bluetooth_adapter == null || !bluetooth_adapter.isEnabled()) {
                Log.d("xyz", "Bluetooth is NOT switched on");
                //request turn on if not already
                Intent enableBtIntent = new Intent( BluetoothAdapter.ACTION_REQUEST_ENABLE);
                enableBtIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(enableBtIntent);
            }
            Log.d("xyz", "Bluetooth is switched on");

    // E N D   O F   O N C R E A T E
    }

// S T A R T   S C A N N I N G  O N  S T A R T  B U T T O N   C L I C K
    public void scanning(View view) {
        //check if BT is there and on
        if (bluetooth_adapter != null && bluetooth_adapter.isEnabled() && scanning_now == false) {
            //notify the app I'm scanning
            scanning_now = true;
            //find devices
            bluetooth_adapter.startDiscovery();
            //check for action FOUND A DEVICE
            IntentFilter discoveredADevice = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            //create result of a device found
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    //receive an action from the intent
                    final String action = intent.getAction();
                    //check if it is ACTION FOUND
                    if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                        //TO DO:
                        // retrieve a uuid
                        // check if it is in the database
                        //if yes: get data, start measuring the distance, eventually beep
                            //if not: ignore the beacon
                    }
                }
            };
            //combine the filter and the action
            registerReceiver(broadcastReceiver, discoveredADevice);
        } else {
            if (scanning_now){
                //notify the user I am already scanning
                Toast.makeText(context, "Already scanning.", Toast.LENGTH_SHORT).show();
            } else {
            //notify the user, BT requirements are not met
                Toast.makeText(context, "Please, turn on your bluetooth to continue.", Toast.LENGTH_LONG).show();
            }
        }
        // E N D   O F  S T A R T   S C A N N I N G
    }

    public void finish_scanning(View view){
        //check if I am scanning
        if(scanning_now){
            //TO DO : stop scanning
            //notify the app I'm not scanning
            scanning_now = false;
        } else {
            //notify the user I'm not scanning
            Toast.makeText(context, "Not scanning now.", Toast.LENGTH_SHORT).show();
        }
    }

}
