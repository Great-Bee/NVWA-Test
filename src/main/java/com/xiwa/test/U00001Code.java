package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.nvwa.bean.CustomScript;
import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue; //静态导入

/**
 * 脚本读取接口
 */
public class U00001Code extends BaseTest
{
    @Before
    public void setUp()
    {
        super.setUp();
    }

    /**
     * 创建一个JS File
     *
     * @return
     */
    private CustomScript createJSFile()
    {
        String url = ResponseConstant.HOST + URL_Custom_Script_Add;

        String testId = String.valueOf(System.currentTimeMillis());

        final CustomScript cs = new CustomScript();
        cs.setName("name_" + testId);
        cs.setAlias("alias_" + testId);
        cs.setType("javascript");
        cs.setDescription("description_" + testId);
        cs.setCode("define([],function(http,Message) {\n" +
                   "    return {\n" +
                   "        execute: function(t) {\n" +
                   "        }\n" +
                   "    };\n" +
                   "});");

        Map<String, String> postData = new HashMap<String, String>();
        postData.put("name", cs.getName());
        postData.put("alias", cs.getAlias());
        postData.put("type", cs.getType());
        postData.put("description", cs.getDescription());
        postData.put("code", cs.getCode());

        NVWAResponse nvwaResponse = NVWAHttp.sendProducerAuthPostRequest(url, postData);
        if (nvwaResponse.isOk()) {
            int id = nvwaResponse.getDataMap().getInt("id");
            cs.setId(id);
            System.out.println("Success create js file :" + id);
        }
        else {
            System.out.println("Fail create js file");
        }

        return cs;
    }

    /**
     * 加载JS
     */
    @Test
    public void loadJS()
    {
        CustomScript cs = createJSFile();

        //读取这个JS File 是否存在
        String url = ResponseConstant.HOST + URL_Custom_Script_Read;
        url += cs.getAlias() + ".js";
        //发送get请求
        NVWAResponse nvwaResponse = NVWAHttp.sendGetRequest(url);
        System.out.println("Success get js content:");
        System.out.println(nvwaResponse.getBody());
    }
}

