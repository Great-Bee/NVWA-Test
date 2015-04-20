package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;

import net.sf.json.JSONObject;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class U00007DeleteByCondition implements ResponseConstant
{
    @Test
    //根据Condition来删除container的内容   /nvwaUser/delete/${page}/${container}/
    public void testDeleteCondition()
    {
        String url = ResponseConstant.HOST + "/nvwaUser/delete/api/asdfasdf";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("token", TOKEN);
        JSONObject formData = new JSONObject();
        formData.put("536e32ed61cd49079e762168be4676c0", "zzz");//表tb_nvwa_demo中的name字段测试
//		formData.put("6e68308b9fcf764f4d53bc2e77af908c", "hhh");
        postData.put("condition_list", formData.toString());
        NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);

        System.out.println(nvwaResponse.isOk());
        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap.toString());
    }
}
