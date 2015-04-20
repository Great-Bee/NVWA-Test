package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;

import net.sf.json.JSONObject;

import org.junit.Test;

import static org.junit.Assert.assertTrue; //静态导入

public class U00004List implements ResponseConstant
{

    @Test
    public void testList()
    {
        String url = ResponseConstant.HOST + "/nvwaUser/list/api/asdfasdf";
        url += "?orderBy=13";
//		url +="&groupBy=1";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("token", TOKEN);
/*		JSONObject formData=new JSONObject();
		formData.put("536e32ed61cd49079e762168be4676c0", "1111");
		formData.put("5af79494442363a3e163fb5fbed46739", "2222");
		formData.put("ed941f25c312273bb0d0f51bb3d6e789", "3333");
		formData.put("9dd50d967f31127a0936483ffe733b05", "4444");
		postData.put("form_list", formData.toString());*/
        NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);

        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap);
    }
}
