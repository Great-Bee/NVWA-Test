package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;

import net.sf.json.JSONObject;

import org.junit.Test;

public class U00008Create implements ResponseConstant
{
    @Test
    public void testCreate()
    {
        // 地址参数   /nvwaUser/create/${page}/${container}
        String url = ResponseConstant.HOST + "/nvwaUser/create/api/fa";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("token", TOKEN);

        JSONObject formData = new JSONObject();
        formData.put("a324d246cb231babdade9ae0c7903210", "aaaaa");
        formData.put("6e68308b9fcf764f4d53bc2e77af908c", "aaaaa");
        postData.put("form_list", formData.toString());
        NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);

        System.out.println(nvwaResponse.isOk());
        JSONObject data = nvwaResponse.getDataMap();
        System.out.println(data.toString());
    }
}
