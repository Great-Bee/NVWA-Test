package com.xiwa.test.bean;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * NVWA Response
 *
 * Created by lufaxdev on 2014/11/25.
 */
public class NVWAResponse
{
    private boolean ok;
    private JSONObject dataMap;
    private String message;
    String body;

    public boolean isOk()
    {
        return ok;
    }

    public void setOk(boolean ok)
    {
        this.ok = ok;
    }


    public JSONObject getDataMap()
    {
        return dataMap;
    }

    public void setDataMap(JSONObject dataMap)
    {
        this.dataMap = dataMap;
    }

    public String getBody()
    {
        return body;
    }

    public void setBody(String body)
    {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
