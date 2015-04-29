package com.xiwa.test.controller;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.Map;


/**
 * 创建验证码接口
 * Created by bingcheng on 2015/4/28.
 */
public class USecurity00003Test extends BaseControllerTest implements ResponseConstant {

    /**
     *创建验证码
     * 参数：无
     * 响应格式：{ok:true,dataMap:{},body:{xxxxxxxx(乱码图片){ok:true,code:1,message:xxx,dataMap:{validateCode:1234}}}}
     */
    @Test
    public void testUSecurity00003() {
        String url = ResponseConstant.HOST + URL_Security + "/createValidate";

        NVWAResponse nvwaResponse = NVWAHttp.sendGetRequest(url);

        if (nvwaResponse != null) {
            //验证返回值是不是ok
            assertTrue(nvwaResponse.isOk());
            if (nvwaResponse.isOk()) {
                //ok=true
                String response = nvwaResponse.getBody();
                int start = response.indexOf("{\"ok\"");
                String tempRes = response.substring(start,response.length());
                JSONObject resJson = JSONObject.fromObject(tempRes);
                assertTrue(resJson.getJSONObject("dataMap").containsKey("validateCode"));
                if(resJson.containsKey("dataMap")){
                    String validateCode = resJson.getJSONObject("dataMap").getString("validateCode");
                    logger.info("[USecurity00003Test][testUSecurity00003] validateCode="+validateCode);
                }
            } else {
                //ok=false
                //test false
                assertNotNull(nvwaResponse.getBody());
                //打印message的内容
                logger.error("[USecurity00003Test][testUSecurity00003] message:" + nvwaResponse.getBody());
            }
        } else {
            //返回的response为空
            //test false
            logger.error("[USecurity00003Test][testUSecurity00003]: response is null");

        }
    }
}
