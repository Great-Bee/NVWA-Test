package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;

import net.sf.json.JSONObject;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class U00009UpdateById implements ResponseConstant
{
    @Test
    public void testUpdate()
    {
        //地址参数    /nvwaUser/update/${page}/${container}/${id}
        String url = ResponseConstant.HOST + "/nvwaUser/update/api/asdfasdf/1";
        Map<String, String> postData = new HashMap<String, String>();
//		postData.put(key, value);
        postData.put("token", TOKEN);
        JSONObject formData = new JSONObject();
        formData.put("536e32ed61cd49079e762168be4676c0", "update1");
        formData.put("5af79494442363a3e163fb5fbed46739", "update title1");
        formData.put("ed941f25c312273bb0d0f51bb3d6e789", "update mobilephone1");
        formData.put("9dd50d967f31127a0936483ffe733b05", "update xingming1");
        postData.put("form_list", formData.toString());
        NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);

        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap.toString());
    }
}