package com.xiwa.test.testcase.U00003;

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
import java.util.UUID;

/**
 * 脚本读取接口
 * Created by xiaobc on 16/1/29.
 */
public class CaseU00003014 extends BaseTestCase {

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

        //删除 OIB
//        super.delete
    }

    @Test
    public void testCaseU00003014(){
        logger.info("----------------------test start-------------------------");
        try{
            this.mainTest();
        }catch(Exception e){
            //清除数据
            super.deleteData();
        }
        logger.info("----------------------test end-------------------------");
    }

    /**
     * 如果 ID 设置成随机的字符串，请求不会进入 read方法   所以测试无效
     */
    private void mainTest(){
        //读取这个JS File 是否存在
        String url = ResponseConstant.HOST + URL_User_Read+"/api/"+ formContainer.getAlias() +"/"+dataIdListA.get(0);//dataIdListA.get(0)
        Map<String,String> requestMap = new HashMap<String, String>();
        requestMap.put("token", Token);
        //发送get请求
        NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, requestMap);//sendPostRequest   sendProducerAuthPostRequest

        assertNotNull(nvwaResponse);
        if (nvwaResponse != null) {
            if (nvwaResponse.isOk()) {
                //验证返回值是不是ok
                assertTrue(nvwaResponse.isOk());
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
            logger.info("[testCaseU00003014][testCaseU00003014]: response is null");
        }
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
