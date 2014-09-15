package com.yahoo.workmonk.gridimagesearch.models;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by workmonk on 9/15/14.
 */
public class ImageResult implements Serializable{
    public String fullUrl;
    public String thumbUrl;
    public String title;

    public ImageResult(JSONObject jsonObject){
        try{
            this.fullUrl = jsonObject.getString("url");
            this.thumbUrl = jsonObject.getString("tbUrl");
            this.title = jsonObject.getString("title");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJsonArray(JSONArray jsonArray){
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for(int i=0; i< jsonArray.length(); i++){
            try {
                results.add(new ImageResult(jsonArray.getJSONObject(i)));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return results;
    }
}
