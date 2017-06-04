package org.igoweb.brokenHeadphoneJack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override protected void onStart() {
    super.onStart();
    Log.i("BHJ", "Launching service");
    startService(new Intent(this, AudioWatcherService.class));
    Log.i("BHJ", "Service started");
  }
}
