package com.yahoo.workmonk.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.yahoo.workmonk.gridimagesearch.R;
import com.yahoo.workmonk.gridimagesearch.models.Settings;

public class SettingsActivity extends Activity {
    private Spinner imageSizeSpnr;
    private Spinner colorFilterSpnr;
    private Spinner imageTypeSpnr;
    private EditText etSiteFilter;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        settings = (Settings) getIntent().getSerializableExtra("settings");
        setupSpinners();

        etSiteFilter = (EditText) findViewById(R.id.etSiteNameValue);
        if(settings.siteFilter!=null && !settings.siteFilter.equals("")) {
            etSiteFilter.setText(settings.siteFilter, TextView.BufferType.EDITABLE);
        }
    }


    private void setupSpinners(){
        //Image Size
        imageSizeSpnr = (Spinner) findViewById(R.id.spnrImageSize);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.image_size, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageSizeSpnr.setAdapter(adapter);
        if(settings.imageSize!=null && !settings.imageSize.equals("Any")){
            ArrayAdapter tempAdapter = (ArrayAdapter) imageSizeSpnr.getAdapter();
            int spinnerPosition = tempAdapter.getPosition(settings.imageSize);
            imageSizeSpnr.setSelection(spinnerPosition);
        }


        //Color Filter
        colorFilterSpnr = (Spinner) findViewById(R.id.spnrColorFilter);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.color_filter, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorFilterSpnr.setAdapter(adapter);
        if(settings.colorFilter!=null && !settings.colorFilter.equals("Any")){
            ArrayAdapter tempAdapter = (ArrayAdapter) colorFilterSpnr.getAdapter();
            int spinnerPosition = tempAdapter.getPosition(settings.colorFilter);
            colorFilterSpnr.setSelection(spinnerPosition);
        }



        //Image Type
        imageTypeSpnr = (Spinner) findViewById(R.id.spnrImageType);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.image_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        imageTypeSpnr.setAdapter(adapter);
        if(settings.imageType!=null && !settings.imageType.equals("Any")){
            ArrayAdapter tempAdapter = (ArrayAdapter) imageTypeSpnr.getAdapter();
            int spinnerPosition = tempAdapter.getPosition(settings.imageType);
            imageTypeSpnr.setSelection(spinnerPosition);
        }


    }

    public void onSaveSubmit(View v){
        // Serialize form data and create result and submit to parent activity
//        EditText etValue = (EditText)findViewById(R.id.etValue);
//        String value = etValue.getText().toString();
        Intent i = new Intent();
        settings.imageSize = imageSizeSpnr.getSelectedItem().toString();
        settings.imageType = imageTypeSpnr.getSelectedItem().toString();
        settings.colorFilter = colorFilterSpnr.getSelectedItem().toString();
        settings.siteFilter = etSiteFilter.getText().toString();
        i.putExtra("settings", settings);
        setResult(RESULT_OK, i);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
