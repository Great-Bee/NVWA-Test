package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.test.bean.ResponseConstant;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.util.NVWAHttp;

/**
 * 布局API
 */
public class U00002PageLayout implements ResponseConstant
{
    @Test
    public void Layout()
    {
        String url = ResponseConstant.HOST + URL_Layout + "/asdfasdf";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("token", TOKEN);

        NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);

        System.out.println(nvwaResponse.isOk());
        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap);
    }
}
