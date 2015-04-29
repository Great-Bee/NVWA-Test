package com.xiwa.test.controller;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;


/**
 * 注册
 * Created by bingcheng on 2015/4/28.
 */
public class USecurity00001Test extends BaseControllerTest implements ResponseConstant {

    private static final Logger logger = Logger.getLogger(USecurity00001Test.class);

    /**
     *注册
     * 参数：无
     * 响应格式：{ok:true,dataMap:{},body:{}}
     */
    @Test
    public void testUSecurity00001()
    {
        //获取验证码
        String url = ResponseConstant.HOST + URL_Security + "/createValidate";
        HttpClient client = NVWAHttp.getReturnClient(url);

        String url2 = ResponseConstant.HOST + URL_Security + "/register";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("loginName", "527710279@qq.com");
        postData.put("password","123456");
        postData.put("code",NVWAHttp.validateCode);

        NVWAResponse nvwaResponse = NVWAHttp.post(client,url2, postData);

        if (nvwaResponse != null) {
            //验证返回值是不是ok
            assertTrue(nvwaResponse.isOk());
            if (nvwaResponse.isOk()) {
                //ok=true
            } else {
                //ok=false
                //test false
                assertNotNull(nvwaResponse.getBody());
                //打印message的内容
                logger.error("[USecurity00001Test][testUSecurity00001] message:" + nvwaResponse.getBody());
            }
        } else {
            //返回的response为空
            //test false
            logger.error("[UCommon0003Test][testUSecurity00001]: response is null");

        }
    }

}
