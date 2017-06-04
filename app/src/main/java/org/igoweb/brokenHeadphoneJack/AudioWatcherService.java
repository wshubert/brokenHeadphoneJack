package org.igoweb.brokenHeadphoneJack;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.IBinder;
import android.util.Log;

public class AudioWatcherService extends Service {

  private NotificationManager notificationManager;

  private AudioManager audioManager;

  private static final int NOTIFICATION_ID = 1;

  public AudioWatcherService() {
  }

  @Override public IBinder onBind(Intent intent) {
    throw new UnsupportedOperationException("Do not bind");
  }

  @Override
  public void onCreate() {
    Log.i("BHJ", "AudioWatcherService.onCreate()");
    notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);
    showNotification();
    monitorAudio();
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Log.i("BHJ", "AudioWatcherService.onStartCommand(flags=" + flags + ", startId=" + startId + ")");
    return START_STICKY;
  }

  /**
   * Show a notification while this service is running.
   */
  private void showNotification() {
    // In this sample, we'll use the same text for the ticker and the expanded notification
    CharSequence text = "Monitoring audio";

    // The PendingIntent to send us a command when somebody taps it.
    PendingIntent contentIntent = PendingIntent.getService(this, 0,
        new Intent(this, getClass()), 0);

    // Set the info for the views that show in the notification panel.
    Notification notification = new Notification.Builder(this)
        .setSmallIcon(R.drawable.notification_icon)  // the status icon
        .setTicker(text)  // the status text
        .setWhen(System.currentTimeMillis())  // the time stamp
        .setContentTitle("Broken Headphone Jack")  // the label of the entry
        .setContentText(text)  // the contents of the entry
        .setContentIntent(contentIntent)  // The intent to send when the entry is clicked
        .build();

    // Send the notification.
    notificationManager.notify(NOTIFICATION_ID, notification);
    Log.i("BHJ", "Notification shown");
  }

  private void monitorAudio() {
    AudioDeviceInfo[] devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS);
    Log.i("BHJ", "Device count: " + devices.length);
    for (AudioDeviceInfo device: devices) {
      Log.i("BHJ", "  Device: " + device.getType() + ", name: " + device.getProductName());
    }
  }
}
