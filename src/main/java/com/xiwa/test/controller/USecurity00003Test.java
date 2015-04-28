package com.xiwa.test.controller;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;
import net.sf.json.JSONObject;
import org.junit.Test;


/**
 * Created by bingcheng on 2015/4/28.
 */
public class USecurity00003Test implements ResponseConstant {

    @Test
    public void createValidate()
    {
        String url = ResponseConstant.HOST + URL_Security + "/createValidate";

        NVWAResponse nvwaResponse = NVWAHttp.sendGetRequest(url);

        System.out.println(nvwaResponse.isOk());
        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap);
    }

}
