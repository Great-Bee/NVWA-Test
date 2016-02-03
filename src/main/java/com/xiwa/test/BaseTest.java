package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.base.util.StringUtil;
import com.xiwa.message.bean.constant.SystemMessageTemplateAlias;
import com.xiwa.nvwa.bean.Container;
import com.xiwa.nvwa.bean.Field;
import com.xiwa.nvwa.bean.OI;
import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.bean.TestOI;
import com.xiwa.test.util.NVWAHttp;
import junit.framework.TestCase;
//import org.GNOME.Bonobo.FIELD_CONTENT_TYPE;

/**
 * 基础类
 *
 * @author CarlChen
 * @version 1.00 14-12-12 下午11:44
 */
public class BaseTest extends TestCase implements ResponseConstant {
    /**
     * 初始化
     */
    public void setUp() {
    }

    /**
     * 创建测试的OI
     *
     * @param suffix
     */
    protected TestOI createTestOI(String suffix) {
        suffix = StringUtil.isInvalid(suffix) ? "000" : suffix;
        OI oi = new OI();
        oi.setName("name_" + suffix);
        oi.setIdentified("identified_" + suffix);
        oi.setTableName("table_name_" + suffix);
        oi.setDescription("description_" + suffix);

        Field intFie = new Field();
        intFie.setName("Number");
        intFie.setDescription("This is a number");
        intFie.setFieldName("number_field");
        intFie.setDefaultValue("100");
        intFie.setNotNull(true);
        intFie.setDataTypeField("INT");
        intFie.setLength(10);

        Field doubleFie = new Field();
        doubleFie.setName("Double");
        doubleFie.setDescription("This is a double");
        doubleFie.setFieldName("double_field");
        doubleFie.setDefaultValue("100.00");
        doubleFie.setNotNull(true);
        doubleFie.setDataTypeField("DOUBLE");
        doubleFie.setLength(10);

        Field varchar = new Field();
        varchar.setName("Varchar");
        varchar.setDescription("This is a varchar");
        varchar.setFieldName("varchar_field");
        varchar.setNotNull(true);
        varchar.setDataTypeField("VARCHAR");
        varchar.setLength(256);

        Field text = new Field();
        text.setName("Text");
        text.setDescription("This is a text");
        text.setFieldName("text_field");
        text.setNotNull(true);
        text.setDataTypeField("TEXT");

        Field date = new Field();
        date.setName("Date");
        date.setDescription("This is a date");
        date.setFieldName("date_field");
        date.setNotNull(true);
        date.setDataTypeField("DATE");

        boolean oiExist = checkOIExist(oi.getIdentified());
        //检查该OI是否已经创建了
        if (!oiExist) {
            String url = ResponseConstant.HOST + URL_OI_Add;
            Map<String, String> postData = new HashMap<String, String>();
            postData.put("name", oi.getName());
            postData.put("identified", oi.getIdentified());
            postData.put("tableName", oi.getTableName());
            postData.put("description", oi.getDescription());
            postData.put("isSyncSchema", "true");
            NVWAResponse nvwaResponse = NVWAHttp.sendProducerAuthPostRequest(url, postData);
            if (nvwaResponse.isOk()) {
                int id = nvwaResponse.getDataMap().getInt("id");
                oi.setId(id);
                System.out.println("Success create oi :" + id);


                createTestField(oi, intFie);
                createTestField(oi, doubleFie);
                createTestField(oi, varchar);
                createTestField(oi, text);
                createTestField(oi, date);
            } else {
                System.out.println("Fail create oi");
            }
        } else {
            System.out.println("The oi has been exist.");
        }

        TestOI testOI = new TestOI();
        testOI.setOi(oi);
        testOI.setDate(date);
        testOI.setDoubleFie(doubleFie);
        testOI.setIntFie(intFie);
        testOI.setText(text);
        testOI.setVarchar(varchar);
        return testOI;
    }

    /**
     * 判断OI是否存在
     *
     * @param identified
     * @return
     */
    private boolean checkOIExist(String identified) {
        String url = ResponseConstant.HOST + URL_OI_Unique;
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("identified", identified);

        NVWAResponse nvwaResponse = NVWAHttp.sendProducerAuthPostRequest(url, postData);
        return !nvwaResponse.isOk();
    }

    /**
     * Create Field
     */
    private void createTestField(OI oi, Field field) {
        String url = ResponseConstant.HOST + URL_Field_Add;
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("identified", oi.getIdentified());

        postData.put("name", field.getName());
        postData.put("description", field.getDescription());
        postData.put("fieldName", field.getFieldName());
        postData.put("defaultValue", field.getDefaultValue());
        postData.put("notNull", String.valueOf(field.isNotNull()));
        postData.put("dataTypeField", field.getDataTypeField());
        postData.put("length", String.valueOf(field.getLength()));

        postData.put("isSyncSchema", "true");

        NVWAResponse nvwaResponse = NVWAHttp.sendProducerAuthPostRequest(url, postData);
        if (nvwaResponse.isOk()) {
            System.out.println("Success create field:" + field.getName());
        } else {
            System.out.println("Fail create field:" + field.getName());
        }
    }

    /**
     * Create Container
     */
    protected void createTestContainer(TestOI testOI) {
        Container container = new Container();

        String suffix = String.valueOf(System.currentTimeMillis());
        container.setName("container_name_" + suffix);
        container.setAlias("container_alias_" + suffix);
        container.setType("form");
        container.setOi(testOI.getOi().getIdentified());

        String url = HOST + URL_Container_Add;
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("name", container.getName());
        postData.put("alias", container.getAlias());
        postData.put("type", container.getType());
        postData.put("oi", container.getOi());

        NVWAResponse nvwaResponse = NVWAHttp.sendProducerAuthPostRequest(url, postData);
        if (nvwaResponse.isOk()) {
            System.out.println("Success create container:" + container.getName());
        } else {
            System.out.println("Fail create container:" + container.getName());
        }
    }
}
