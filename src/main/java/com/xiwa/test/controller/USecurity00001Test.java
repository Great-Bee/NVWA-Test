package com.xiwa.test.controller;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by bingcheng on 2015/4/28.
 */
public class USecurity00001Test implements ResponseConstant {

    @Test
    public void register()
    {
        //获取验证码
        String url = ResponseConstant.HOST + URL_Security + "/createValidate";
        HttpClient client = NVWAHttp.getReturnClient(url);

        String url2 = ResponseConstant.HOST + URL_Security + "/register";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("loginName", "18620510854");
        postData.put("password","123456");
        postData.put("code",NVWAHttp.validateCode);

        NVWAResponse nvwaResponse = NVWAHttp.post(client,url2, postData);

        System.out.println(nvwaResponse.isOk());
        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap);
    }

}
