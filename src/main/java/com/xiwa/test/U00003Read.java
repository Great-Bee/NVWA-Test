package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.bean.TestOI;
import net.sf.json.JSONObject;

import org.junit.Test;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.util.NVWAHttp;

/**
 * 读取接口测试
 */
public class U00003Read extends BaseTest {
    /**
     * Read
     */
    @Test
    public void testContainerRead() {
        TestOI testOI = createTestOI(null);
        createTestContainer(testOI);

        /*
        String url = ResponseConstant.HOST + "/nvwaUser/read";
        //Page
        url += "/api";
        //Container
        url += "/fa";
        //ID
        url += "/1";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("token", TOKEN);
        NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);

        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap.toString());
        */
    }

    @Test
    public void testNoPage() {
        String url = ResponseConstant.HOST + "/nvwaUser/read/!@#$ZZ%&&(&)*/fa/1";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("token", TOKEN);
        NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);
        System.out.println(nvwaResponse.isOk());
        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap.toString());
    }

    @Test
    public void testNoContainer() {
        String url = ResponseConstant.HOST + "/nvwaUser/read/api/!@#$ZZ%&&(&)*/1";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("token", TOKEN);
        NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);
        System.out.println(nvwaResponse.isOk());
        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap.toString());
    }
}
