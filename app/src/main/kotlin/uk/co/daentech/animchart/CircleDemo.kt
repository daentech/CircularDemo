package uk.co.daentech.animchart

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import java.util.Random

import uk.co.daentech.animchart.library.widgets.ProgressBar
import uk.co.daentech.animchart.library.widgets.ProgressCircle

public class CircleDemo() : Activity() {

    var progressCircle: ProgressCircle? = null
    var progressBar: ProgressBar? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_demo)

        progressCircle = findViewById(R.id.progress_circle) as ProgressCircle
        progressBar = findViewById(R.id.progress_bar) as ProgressBar

        progressCircle?.setProgress(0.5f)
        progressBar?.setProgress(0.5f)
        progressCircle?.startAnimation()
        progressBar?.startAnimation()
    }

    public override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.circle_demo, menu)
        return true
    }

    public fun animate(view: View) {
        val progress = Random().nextFloat()
        progressCircle?.setProgress(progress)
        progressCircle?.startAnimation()

        progressBar?.setProgress(progress)
        progressBar?.startAnimation()
    }
}
