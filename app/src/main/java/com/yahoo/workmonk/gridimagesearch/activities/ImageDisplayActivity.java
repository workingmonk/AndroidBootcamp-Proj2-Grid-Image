package com.yahoo.workmonk.gridimagesearch.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.yahoo.workmonk.gridimagesearch.R;
import com.yahoo.workmonk.gridimagesearch.models.ImageResult;

public class ImageDisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        getActionBar().hide();
        //Pull the url from the intent
        ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
        //Find the image view
        ImageView ivImageResults = (ImageView) findViewById(R.id.ivImageResult);
        //Load the image url int the imageView using Picasso
        Picasso.with(this).load(result.fullUrl).into(ivImageResults);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.image_display, menu);
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
}
