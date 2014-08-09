package uk.co.daentech.circulardemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Random;

import uk.co.daentech.circulardemo.widgets.ProgressBar;
import uk.co.daentech.circulardemo.widgets.ProgressCircle;


public class CircleDemo extends Activity {

    ProgressCircle progressCircle;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_demo);

        progressCircle = (ProgressCircle) findViewById(R.id.progress_circle);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        progressCircle.startAnimation();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.circle_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void animate(View view) {
        float val = new Random().nextFloat();
        progressCircle.setProgress(val);
        progressCircle.startAnimation();

        progressBar.setProgress(val);
        progressBar.startAnimation();
    }
}
