package com.yahoo.workmonk.gridimagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.yahoo.workmonk.gridimagesearch.adapters.EndlessScrollListener;
import com.yahoo.workmonk.gridimagesearch.adapters.ImageResultsAdapter;
import com.yahoo.workmonk.gridimagesearch.models.ImageResult;
import com.yahoo.workmonk.gridimagesearch.R;
import com.yahoo.workmonk.gridimagesearch.models.Settings;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;


public class SearchActivity extends Activity {
    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private Settings settings = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        imageResults = new ArrayList<ImageResult>();
        aImageResults = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(aImageResults);
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMoreDataFromApi(page);
            }
        });
        if(settings==null)
            settings = new Settings();
    }


    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the image display activity
                //Create an intent
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                //Get the image results to display
                ImageResult result = imageResults.get(position);
                //Pass image result into the intent
                i.putExtra("result", result);
                //Launch the new activity
                startActivity(i);
            }
        });
    }

    public void onImageSearch(View v) {
        onImageSearchInfinite(v, 0);
    }

    public void onImageSearchInfinite(View v, final int startIndex){
        String query = etQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?q=" + query + "&v=1.0&rsz=8";
        if(startIndex!=0){
            searchUrl = searchUrl + "&start=" + startIndex;
        }
        String finalUrl = addSettingsToUrl(searchUrl);
        finalUrl = finalUrl.replaceAll("\\+", "%20");

        Log.i("FINAL URL", finalUrl);
        try {
            client.get(finalUrl, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    JSONArray imageResultsJson = null;
                    try {
                        imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                        if (startIndex == 0) {
                            imageResults.clear(); //clear when new search
                        }
                        aImageResults.addAll(ImageResult.fromJsonArray(imageResultsJson));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(SearchActivity.this, "No Results, check query", Toast.LENGTH_SHORT).show();
                    }
                    Log.i("INFO", imageResults.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.d("STATUS CODE", String.valueOf(statusCode));
                    if (!isOnline()) {
                        Toast.makeText(SearchActivity.this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SearchActivity.this, "No Results, check query", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e){
            Toast.makeText(SearchActivity.this, "No Results, check query", Toast.LENGTH_SHORT).show();
        }
    }

    public void launchSettingsMenu(MenuItem mi) {
        Intent i = new Intent(this, SettingsActivity.class);
        i.putExtra("settings", settings);
        startActivityForResult(i, 5);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 5 && resultCode == RESULT_OK){
            Settings settings1 = (Settings) data.getSerializableExtra("settings");
            settings = settings1;
            onImageSearchInfinite(null, 0);
        }
    }

    private String addSettingsToUrl(String searchUrl){
        if(settings.imageType!=null && settings.imageType!="" && settings.imageType!="Any"){
            searchUrl = searchUrl + "&imgtype=" + settings.imageType;
        }

        if(settings.imageSize!=null && settings.imageSize!="" && settings.imageSize!="Any"){
            searchUrl = searchUrl + "&imgsz=" + settings.imageSize;
        }

        if(settings.colorFilter!=null && settings.colorFilter!="" && settings.colorFilter!="Any"){
            searchUrl = searchUrl + "&imgcolor=" + settings.colorFilter;
        }

        if(settings.siteFilter!=null && settings.siteFilter!="" && settings.siteFilter!="Any"){
            searchUrl = searchUrl + "&as_sitesearch=" + settings.siteFilter;
        }


        return searchUrl;
    }

    public void loadMoreDataFromApi(int page){
        int startIndex = page * 8;
        onImageSearchInfinite(null, startIndex);
    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal==0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search, menu);
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
