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
public class USecurity00002Test extends BaseControllerTest implements ResponseConstant {

    /**
     * 免得再加上注册的请求，所以请先执行USecurity00001Test，再执行这个test
     * 参数：无
     * 响应格式：{ok:true,dataMap:{},body:{}}
     */
    @Test
    public void testUSecurity00002()
    {

        //获取验证码
        String url = ResponseConstant.HOST + URL_Security + "/createValidate";
        HttpClient client = NVWAHttp.getReturnClient(url);

        String url2 = ResponseConstant.HOST + URL_Security + "/login";

        Map<String, String> postData = new HashMap<String, String>();
        postData.put("loginName", "527710279@qq.com");
        postData.put("password","123456");
        postData.put("code",NVWAHttp.validateCode);

        NVWAResponse nvwaResponse = NVWAHttp.post(client, url2, postData);

        if (nvwaResponse != null) {
            //验证返回值是不是ok
            assertTrue(nvwaResponse.isOk());
            if (nvwaResponse.isOk()) {
                //ok=true
                Map<String, Object> dataMap = nvwaResponse.getDataMap();
                //dataMap不能为null
                //    assertNotNull(dataMap);
                //dataMap含有validateCode
                //    assertTrue(dataMap.containsKey("validateCode"));
                //    logger.info("[USecurity00002Test][testUSecurity00002] validateCode="+dataMap.get("validateCode"));
            } else {
                //ok=false
                //test false
                assertNotNull(nvwaResponse.getBody());
                //打印message的内容
                logger.error("[USecurity00002Test][testUSecurity00002] message:" + nvwaResponse.getBody());
            }
        } else {
            //返回的response为空
            //test false
            logger.error("[USecurity00002Test][testUSecurity00002]: response is null");

        }
    }

}
