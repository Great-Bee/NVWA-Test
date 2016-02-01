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
import java.util.UUID;

/**
 * 脚本读取接口
 * Created by xiaobc on 16/1/29.
 */
public class CaseU00001002 extends BaseTestCase {

    private static final Logger logger = Logger.getLogger(CaseU00001002.class);

    private CustomScript jsFile = null;

    @Before
    public void setUp()
    {
        this.tearUp();
        super.setUp();
    }

    public void tearUp(){
        System.out.println("tearUp");
        jsFile = new CustomScript();
        jsFile.setAlias(UUID.randomUUID().toString());
    }
    @Test
    public void CaseU00001002Test(){

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

        jsFile = null;

        System.out.println("tearDown");
    }

}
