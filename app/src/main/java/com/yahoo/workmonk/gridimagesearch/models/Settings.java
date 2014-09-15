package com.yahoo.workmonk.gridimagesearch.models;

import java.io.Serializable;

/**
 * Created by workmonk on 9/15/14.
 */
public class Settings implements Serializable{
    public String imageSize;
    public String colorFilter;
    public String imageType;
    public String siteFilter;

    public Settings(){
        imageSize = "Any";
        imageType = "Any";
        colorFilter = "Any";
        siteFilter = "";
    }
}
