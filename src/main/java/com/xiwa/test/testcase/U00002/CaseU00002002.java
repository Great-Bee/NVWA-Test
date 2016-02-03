package com.xiwa.test.testcase.U00002;

import com.xiwa.test.base.BaseTestCase;
import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;
import net.sf.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 脚本读取接口
 * Created by xiaobc on 16/1/29.
 */
public class CaseU00002002 extends BaseTestCase {

//    private static final Logger logger = Logger.getLogger(CaseU00002001.class);

    @Before
    public void setUp()
    {
        this.tearUp();
        super.setUp();
    }

    public void tearUp(){
        logger.info("-----------tearUp--------------");
        //创建数据
        super.createData();
    }

    @Test
    public void testCaseU00002002(){
        logger.info("----------------------test start-------------------------");

        //读取这个JS File 是否存在
        String url = ResponseConstant.HOST + URL_Layout+"/"+formPage.getAlias();
        Map<String,String> requestMap = new HashMap<String, String>();
        //发送get请求
        NVWAResponse nvwaResponse = NVWAHttp.sendProducerAuthPostRequest(url, requestMap);

        assertNotNull(nvwaResponse);
        if (nvwaResponse != null) {
            //验证返回值是不是ok
            assertTrue(nvwaResponse.isOk());
            if (nvwaResponse.isOk()) {
                //ok=true
                JSONObject dataMap = nvwaResponse.getDataMap();
                //dataMap不能为null
                assertNotNull(dataMap);
                logger.info("----------------------test dataMap-------------------------");
                logger.info(JSONObject.fromObject(nvwaResponse).toString());
                //验证dataMap里面的内容
                logger.info(nvwaResponse.isOk());
            } else {
                //ok=false
                //test false
                //打印message的内容
                logger.info("----------------------test dataMap-------------------------");
                logger.info(JSONObject.fromObject(nvwaResponse).toString());
            }
        } else {
            //返回的response为空
            //test false
            logger.info("[testCaseU00002002][testCaseU00002002]: response is null");
        }
        logger.info("----------------------test end-------------------------");
    }

    /**
     * Tear Down
     */
    @After
    public void tearDown() throws Exception{
        logger.info("---------------tearDown-------------------");
        //销毁数据
        super.deleteData();
    }

}
