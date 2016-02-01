package com.xiwa.test.testcase.U00001;

import com.xiwa.nvwa.bean.CustomScript;
import com.xiwa.test.BaseTest;
import com.xiwa.test.base.BaseTestCase;
import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 脚本读取接口
 * Created by xiaobc on 16/1/29.
 */
public class CaseU00001004 extends BaseTestCase {

    private static final Logger logger = Logger.getLogger(CaseU00001004.class);

    private CustomScript jsFile = null;

    @Before
    public void setUp()
    {
        this.tearUp();
        super.setUp();
    }

    public void tearUp(){
        System.out.println("tearUp");

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

        jsFile = cs;
    }
    @Test
    public void CaseU00001004Test(){

        //读取这个JS File 是否存在
        String url = ResponseConstant.HOST + URL_Custom_Script_Read;
        url += jsFile.getAlias() + ".js";
        //发送get请求
        NVWAResponse nvwaResponse = NVWAHttp.sendGetRequest(url);

        System.out.println("Success get js content:");
        System.out.println(nvwaResponse.getBody());

        this.tearDown();
    }

    /**
     * Tear Down
     */
    public void tearDown() {
        System.out.println("tearDown");

        String url = ResponseConstant.HOST + URL_Custom_Script_Delete;
        int id = jsFile.getId();

        Map<String, String> postData = new HashMap<String, String>();
        postData.put("id", String.valueOf(id));

        NVWAResponse nvwaResponse = NVWAHttp.sendProducerAuthPostRequest(url, postData);
        if (nvwaResponse.isOk()) {

            System.out.println("delete Success");
        }
        else {
            System.out.println("delete Fail ");
        }
        jsFile = null;
    }

}
