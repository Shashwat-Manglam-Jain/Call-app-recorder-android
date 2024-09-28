package com.example.callapprecorderjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class callReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Intent serviceIntent = new Intent(context, callRecordingService.class);

        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            // Call is ringing
        } else if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            // Call received
            serviceIntent.putExtra("action", "StartRecording");
            context.startService(serviceIntent);
        } else if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            // Call ended
            serviceIntent.putExtra("action", "EndRecording");
            context.startService(serviceIntent);
        }
    }
}
