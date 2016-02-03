package com.xiwa.test.base;

import com.xiwa.base.util.DataUtil;
import com.xiwa.nvwa.bean.*;
import com.xiwa.test.BaseTest;
import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.DBUtils;
import com.xiwa.test.util.NVWAHttp;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by xiaobc on 16/1/30.
 */
public class BaseTestCase extends BaseTest {

    protected static final Logger logger = Logger.getLogger(BaseTestCase.class);

    protected static final String Token = UUID.randomUUID().toString();
    //缓存OI
    protected OI oi = new OI();
    //缓存OI2
    protected OI oi2 = new OI();
    //缓存 连接器
    protected Connector connector = new Connector();
    //缓存page
    protected Page formPage = new Page();
    //缓存page
    protected Page gridPage = new Page();
    //缓存 form container
    protected Container formContainer = new Container();
    //缓存 grid container
    protected Container gridContianer = new Container();
    //缓存
    protected List<Integer> fieldIdList = new ArrayList<Integer>();
    //缓存 元素id列表
    protected List<Integer> elementsIdList = new ArrayList<Integer>();
    //缓存  元素layout id 列表
    protected List<Integer> elementsLayoutIdList = new ArrayList<Integer>();
    //缓存  condition
    protected Condition condition = new Condition();
    //当前去 id 为测试field
    protected Field nameField = new Field();
    //返回插入的oi数据主键list
    protected List<Integer> dataIdListA = new ArrayList<Integer>();
    //返回插入的oi数据主键list
    protected List<Integer> dataIdListB = new ArrayList<Integer>();

    //连接的 id 为测试field
    protected Field fIdField = new Field();
    //缓存服务器属性
    protected ElementServerAttribute serverAttrForm = new ElementServerAttribute();
    //缓存服务器属性
    protected ElementServerAttribute serverAttrGrid = new ElementServerAttribute();
    //缓存 创建的服务器token
    protected Integer appConfigId = 0;

    /**
     * 初始化 token
     */
    protected void buildAppConfig(){
        String sql = "insert into xiwa_nvwa_api_config(name,appName,appType,alias,token,createDate) " +
                "values('demo','demo','web','demo','"+Token+"','2016-01-01 12:23:23');";
        Object o = DBUtils.insertAndDeleteData(sql);
        System.out.println("插入 app Token  结束。。。。");
        appConfigId = DataUtil.getInt(o,0);
    }
    /**
     * 初始化数据 oi
     */
    protected void buildOi(){
        oi.setIdentified("testCase");
        oi.setName("testCase");
        oi.setTableName("testCase");

        String createTableSql = "CREATE TABLE " +oi.getTableName()+" "+
                "(id int(11) primary key not NULL auto_increment, " +
                " name VARCHAR(64), " +
                " tel VARCHAR(64), " +
                " fId int(11))";

        Object o = DBUtils.insertAndDeleteData(createTableSql,true);
        System.out.println("建表结束。。。。");

        String insertDataSql = "insert into xiwa_nvwa_oi(name,identified,tableName,status,createDate) "+
                "values('"+oi.getName()+"','"+oi.getIdentified()+"','"+oi.getTableName()+"','normal','2016-02-01 12:20:30')";
        System.out.println(insertDataSql);
        Object o2 = DBUtils.insertAndDeleteData(insertDataSql);
        System.out.println("插入oi结束。。。。");

        oi.setId(DataUtil.getInt(o2, 0));
    }

    /**
     * 添加完oi后 添加字段
     */
    protected void buildFieldAfterOi(){

        String fieldSqlId = "insert into xiwa_nvwa_field(name,fieldName,notNull,oiIdentified,dataTypeField,length,serialNumber,decimalLength) "+
                "values('ID','id',0,'"+oi.getIdentified()+"','INT',11,'Field_id_123123123123123',0)";
        System.out.println(fieldSqlId);
        Object o = DBUtils.insertAndDeleteData(fieldSqlId);
        System.out.println("插入Field A 结束。。。。");
        fieldIdList.add(DataUtil.getInt(o, 0));

        nameField.setId(DataUtil.getInt(o, 0));
        nameField.setSerialNumber("Field_id_123123123123123");

        String fieldSqlName = "insert into xiwa_nvwa_field(name,fieldName,notNull,oiIdentified,dataTypeField,length,serialNumber,decimalLength) "+
                "values('name','name',1,'"+oi.getIdentified()+"','VARCHAR',64,'Field_id_123123asqw23123',0)";
        System.out.println(fieldSqlName);
        Object o2 = DBUtils.insertAndDeleteData(fieldSqlName);
        System.out.println("插入Field B 结束。。。。");
        fieldIdList.add(DataUtil.getInt(o2, 0));


        String fieldSqlTel = "insert into xiwa_nvwa_field(name,fieldName,notNull,oiIdentified,dataTypeField,length,serialNumber,decimalLength) "+
                "values('tel','tel',1,'"+oi.getIdentified()+"','VARCHAR',64,'Field_id_12dfgd3asqw23123',0)";
        System.out.println(fieldSqlTel);
        Object o3 = DBUtils.insertAndDeleteData(fieldSqlTel);
        System.out.println("插入Field C 结束。。。。");
        fieldIdList.add(DataUtil.getInt(o3, 0));

        String fieldSqlFid = "insert into xiwa_nvwa_field(name,fieldName,notNull,oiIdentified,dataTypeField,length,serialNumber,decimalLength) "+
                "values('fId','fId',1,'"+oi.getIdentified()+"','INT',11,'Field_id_12dfgd3asfhjghj3',0)";
        System.out.println(fieldSqlFid);
        Object o4 = DBUtils.insertAndDeleteData(fieldSqlFid);
        System.out.println("插入Field D 结束。。。。");
        fieldIdList.add(DataUtil.getInt(o4, 0));

    }

    /**
     * 初始化数据 oiB
     */
    protected void buildOi2(){
        oi2.setIdentified("testCase2");
        oi2.setName("testCase2");
        oi2.setTableName("testCase2");

        String createTableSql = "CREATE TABLE " +oi2.getTableName()+" "+
                "(id int(11) primary key not NULL auto_increment, " +
                " score VARCHAR(64))";

        Object o = DBUtils.insertAndDeleteData(createTableSql,true);
        System.out.println("建表结束。。。。");

        String insertDataSql = "insert into xiwa_nvwa_oi(name,identified,tableName,status,createDate) "+
                "values('"+oi2.getName()+"','"+oi2.getIdentified()+"','"+oi2.getTableName()+"','normal','2016-02-01 12:20:30')";
        System.out.println(insertDataSql);
        Object o2 = DBUtils.insertAndDeleteData(insertDataSql);
        System.out.println("插入oi结束。。。。");

        oi2.setId(DataUtil.getInt(o2,0));
    }

    /**
     * 添加完oiB后 添加字段
     */
    protected void buildFieldAfterOi2(){
        String fieldSqlId = "insert into xiwa_nvwa_field(name,fieldName,notNull,oiIdentified,dataTypeField,length,serialNumber,decimalLength) "+
                "values('ID','id',0,'"+oi2.getIdentified()+"','INT',11,'Field_id_321123123123123',0)";
        System.out.println(fieldSqlId);
        Object o = DBUtils.insertAndDeleteData(fieldSqlId);
        System.out.println("插入Field E 结束。。。。");
        fieldIdList.add(DataUtil.getInt(o, 0));

        fIdField.setId(DataUtil.getInt(o, 0));
        fIdField.setSerialNumber("Field_id_321123123123123");

        String fieldSqlScore = "insert into xiwa_nvwa_field(name,fieldName,notNull,oiIdentified,dataTypeField,length,serialNumber,decimalLength) "+
                "values('score','score',1,'"+oi2.getIdentified()+"','VARCHAR',64,'Field_id_12kjl23asqw23123',0)";
        System.out.println(fieldSqlScore);
        Object o2 = DBUtils.insertAndDeleteData(fieldSqlScore);
        System.out.println("插入Field F 结束。。。。");
        fieldIdList.add(DataUtil.getInt(o2, 0));
    }

    /**
     * 添加连接器
     */
    protected void buildConnector(){
        String sql = "insert into xiwa_nvwa_connector(name,fromOI,fromField,toOI,toField,relation,alias) "+
                "values('test1-test2','"+oi.getIdentified()+"','fId','"+oi2.getIdentified()+"','id','otm','test1-test2')";
        System.out.println(sql);
        Object o2 = DBUtils.insertAndDeleteData(sql);
        System.out.println("插入 connector 结束。。。。");

        connector.setId(DataUtil.getInt(o2, 0));
    }

    /**
     * 初始化container
     */
    protected void buildContainer(){
        formContainer.setName("formC");
        formContainer.setAlias("formC");
        formContainer.setOi(DataUtil.getString(oi.getIdentified(), null));

        String formContainerSql = "insert into xiwa_nvwa_container(name,alias,oi,type,publicObject,producer,createDate) "+
                "values('"+formContainer.getName()+"','"+formContainer.getAlias()+"','"+formContainer.getOi()+"','form',0,'nvwa','2016-02-01 12:23:23')";
        System.out.println(formContainerSql);
        Object o = DBUtils.insertAndDeleteData(formContainerSql);
        System.out.println("插入form container 结束。。。。");
        formContainer.setId(DataUtil.getInt(o, 0));

        gridContianer.setName("gridC");
        gridContianer.setAlias("gridC");
        gridContianer.setOi(DataUtil.getString(oi.getId(), null));

        String gridContainerSql = "insert into xiwa_nvwa_container(name,alias,oi,type,publicObject,producer,createDate) "+
                "values('"+gridContianer.getName()+"','"+gridContianer.getAlias()+"','"+gridContianer.getOi()+"','grid',0,'nvwa','2016-02-01 12:23:23')";
        System.out.println(gridContainerSql);
        Object o2 = DBUtils.insertAndDeleteData(gridContainerSql);
        System.out.println("插入form container 结束。。。。");
        gridContianer.setId(DataUtil.getInt(o2, 0));
    }

    /**
     * 初始化 page
     */
    protected void buildPage(){
        formPage.setAlias("caseFormP");
        formPage.setName("caseFormP");
        formPage.setLayouts("[[\"formC\"]]");

        String formPageSql = "insert into xiwa_nvwa_page(name,alias,type,publicObject,producer,createDate,layouts) "+
                "values('"+formPage.getName()+"','"+formPage.getAlias()+"','simple_page',0,'nvwa','2016-02-01 12:23:23','"+formPage.getLayouts()+"')";
        System.out.println(formPageSql);
        Object o = DBUtils.insertAndDeleteData(formPageSql);
        System.out.println("插入form page 结束。。。。");
        formPage.setId(DataUtil.getInt(o, 0));

        gridPage.setAlias("caseGridP");
        gridPage.setName("caseGridP");
        gridPage.setLayouts("[[\"gridC\"]]");

        String gridPageSql = "insert into xiwa_nvwa_page(name,alias,type,publicObject,producer,createDate,layouts) "+
                "values('"+gridPage.getName()+"','"+gridPage.getAlias()+"','simple_page',0,'nvwa','2016-02-01 12:23:23','"+gridPage.getLayouts()+"')";
        System.out.println(gridPageSql);
        Object o2 = DBUtils.insertAndDeleteData(gridPageSql);
        System.out.println("插入 grid page 结束。。。。");
        gridPage.setId(DataUtil.getInt(o2, 0));
    }

    /**
     * 初始化 elements
     */
    protected void buildElements(){
        String elementsFormSql = "insert into xiwa_nvwa_element(containerId,name,componentAlias,serialNumber,pageId,fieldSerialNumber) "+
                "values("+formContainer.getId()+",'1-form','text','Element_123123sq',0,'"+nameField.getSerialNumber()+"')";
        System.out.println(elementsFormSql);
        Object o = DBUtils.insertAndDeleteData(elementsFormSql);
        System.out.println("插入 form  element 结束。。。。");
        elementsIdList.add(DataUtil.getInt(o, 0));

        //添加 element的服务器属性
        String elementsAttrFormSql = "insert into xiwa_nvwa_element_server_attribute(elementId,fieldName) "+
                "values("+elementsIdList.get(0)+",'id')";
        System.out.println(elementsAttrFormSql);
        Object o3 = DBUtils.insertAndDeleteData(elementsAttrFormSql);
        System.out.println("插入 form  element  server attribute 结束。。。。");
        serverAttrForm.setId(DataUtil.getInt(o3, 0));


        String elementsGridSql = "insert into xiwa_nvwa_element(containerId,name,componentAlias,serialNumber,pageId,fieldSerialNumber) "+
                "values("+gridContianer.getId()+",'1-grid','text','Element_1231dssq',0,'"+nameField.getSerialNumber()+"')";
        System.out.println(elementsGridSql);
        Object o2 = DBUtils.insertAndDeleteData(elementsGridSql);
        System.out.println("插入 form  element 结束。。。。");
        elementsIdList.add(DataUtil.getInt(o2, 0));

        //添加 element的服务器属性
        String elementsAttrGridSql = "insert into xiwa_nvwa_element_server_attribute(elementId,fieldName) "+
                "values("+elementsIdList.get(1)+",'id')";
        System.out.println(elementsAttrGridSql);
        Object o4 = DBUtils.insertAndDeleteData(elementsAttrGridSql);
        System.out.println("插入 form  element  server attribute 结束。。。。");
        serverAttrGrid.setId(DataUtil.getInt(o4, 0));
    }

    /**
     * 初始化  element layout
     */
    protected void buildElementLayout(){
        String elementsLayoutFormSql = "insert into xiwa_nvwa_element_layout(containerAlias,layouts) "+
                "values('"+formContainer.getAlias()+"','{\"rows\":[[\""+elementsIdList.get(0)+"\",\""+elementsIdList.get(1)+"\"]],\"button\":[[]]}')";
        System.out.println(elementsLayoutFormSql);
        Object o = DBUtils.insertAndDeleteData(elementsLayoutFormSql);
        System.out.println("插入 form  element layout 结束。。。。");
        elementsLayoutIdList.add(DataUtil.getInt(o, 0));

        String elementsLayoutGridSql = "insert into xiwa_nvwa_element_layout(containerAlias,layouts) "+
                "values('"+formContainer.getAlias()+"','{\"fixed\":[[\""+elementsIdList.get(0)+"\",\""+elementsIdList.get(1)+"\"]],\"unfixed\":[[]]}')";
        System.out.println(elementsLayoutGridSql);
        Object o2 = DBUtils.insertAndDeleteData(elementsLayoutGridSql);
        System.out.println("插入 grid  element layout 结束。。。。");
        elementsLayoutIdList.add(DataUtil.getInt(o2, 0));
    }

    /**
     * 初始化 grid 条件
     */
    protected void buildCondition(){
        condition.setSerialNumber("Condition_id_123123hsdasd");
        condition.setConditionFieldName(nameField.getName());
        condition.setConditionFieldValueType("request");
        condition.setConditionType("eq");
        condition.setContainerId(gridContianer.getId());
        condition.setFieldSerialNumber(nameField.getSerialNumber());

        String insertSql = "insert into xiwa_nvwa_condition(containerId,conditionFieldName,conditionFieldValueType,conditionType,serialNumber,fieldSerialNumber) "+
                "values("+gridContianer.getId()+",'"+nameField.getName()+"','request','eq','Condition_id_123123hsdasd','"+nameField.getSerialNumber()+"')";
        System.out.println(insertSql);
        Object i = DBUtils.insertAndDeleteData(insertSql);

        condition.setId(DataUtil.getInt(i, 0));
    }


    /**
     * 插入 新表数据
     */
    protected void buildOiDataOi2(){
        String insertSql = "insert into "+oi2.getTableName()+"(score) "+
                "values('100')";
        System.out.println(insertSql);
        Object id = DBUtils.insertAndDeleteData(insertSql,true);
        dataIdListB.add(DataUtil.getInt(id, 0));
    }

    /**
     * 插入 新表数据
     */
    protected void buildOiData(){
        String insertSql = "insert into "+oi.getTableName()+"(name,tel,fId) "+
                "values('xiaobc','123123123',"+dataIdListB.get(0)+")";
        System.out.println(insertSql);
        Object id = DBUtils.insertAndDeleteData(insertSql,true);
        dataIdListA.add(DataUtil.getInt(id,0));

        insertSql = "insert into "+oi.getTableName()+"(name,tel,fId) "+
                "values('xiaobc2','2123123123',"+dataIdListB.get(0)+")";
        System.out.println(insertSql);
        Object id2 = DBUtils.insertAndDeleteData(insertSql,true);
        dataIdListA.add(DataUtil.getInt(id2, 0));

        insertSql = "insert into "+oi.getTableName()+"(name,tel,fId) "+
                "values('xiaobc3','3123123123',"+dataIdListB.get(0)+")";
        System.out.println(insertSql);
        Object id3 = DBUtils.insertAndDeleteData(insertSql,true);
        dataIdListA.add(DataUtil.getInt(id3, 0));

    }

    /**
     * 测试前的数据准备
     */
    public void createData(){

        this.buildAppConfig();

        this.buildOi();

        this.buildFieldAfterOi();

        this.buildOi2();

        this.buildFieldAfterOi2();

        this.buildConnector();

        this.buildContainer();

        this.buildPage();

        this.buildElements();

        this.buildElementLayout();

        this.buildCondition();

        this.buildOiDataOi2();

        this.buildOiData();

    }

    /**
     * 测试
     */
    @Test
    public void dataTest(){
        this.createData();
        this.deleteData();
    }

    /**
     * 测试后删除数据
     */
    public void deleteData(){
        this.deleteElementLayout();

        this.deleteElement();

        this.deleteField();

        this.deleteContainer();

        this.deletePage();

        this.deleteTable();

        this.deleteOi();

        this.deleteConnector();

        this.deleteCondition();

        this.clearCache();

        this.deleteAppConfig();
    }

    /**
     * 删除 elementLayout
     */
    protected void deleteElementLayout(){
        StringBuilder deleteElementsLayoutSql = new StringBuilder("delete from xiwa_nvwa_element_layout where id in (");
        for(int i=0;i<elementsLayoutIdList.size();i++){
            deleteElementsLayoutSql.append(elementsLayoutIdList.get(i));
            if(i!=elementsLayoutIdList.size()-1){
                deleteElementsLayoutSql.append(",");
            }else{
                deleteElementsLayoutSql.append(")");
            }
        }
        System.out.println(deleteElementsLayoutSql.toString());
        Object o = DBUtils.insertAndDeleteData(deleteElementsLayoutSql.toString());
        System.out.println("删除  element layout 结束。。。。");
    }

    /**
     * 删除element
     */
    protected void deleteElement(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_element where id in (");
        for(int i=0;i<elementsIdList.size();i++){
            deleteSql.append(elementsIdList.get(i));
            if(i!=elementsIdList.size()-1){
                deleteSql.append(",");
            }else{
                deleteSql.append(")");
            }
        }
        System.out.println(deleteSql.toString());
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  element 结束。。。。");

        StringBuilder deleteSql2 = new StringBuilder("delete from xiwa_nvwa_element_server_attribute where id in ("+serverAttrForm.getId()+","+serverAttrGrid.getId()+")");
        System.out.println(deleteSql2.toString());
        Object o2 = DBUtils.insertAndDeleteData(deleteSql2.toString());
        System.out.println("删除  element server attribulte 结束。。。。");
    }

    /**
     * 删除field
     */
    protected void deleteField(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_field where id in (");
        for(int i=0;i<fieldIdList.size();i++){
            deleteSql.append(fieldIdList.get(i));
            if(i!=fieldIdList.size()-1){
                deleteSql.append(",");
            }else{
                deleteSql.append(")");
            }
        }
        System.out.println(deleteSql.toString());
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  Field 结束。。。。");
    }

    /**
     * 删除container
     */
    protected void deleteContainer(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_container where id in ("+formContainer.getId()+","+gridContianer.getId()+")");
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  container 结束。。。。");
    }

    /**
     * 删除container
     */
    protected void deletePage(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_page where id in ("+formPage.getId()+","+gridPage.getId()+")");
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  page 结束。。。。");
    }

    /**
     * 删除刚才新建的表
     */
    protected void deleteTable(){
        StringBuilder deleteSql = new StringBuilder("DROP TABLE "+oi.getTableName());
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString(),true);
        System.out.println("清除 table 结束。。。。");

        StringBuilder deleteSql2 = new StringBuilder("DROP TABLE "+oi2.getTableName());
        System.out.println(deleteSql2);
        Object o2 = DBUtils.insertAndDeleteData(deleteSql2.toString(),true);
        System.out.println("清除 table2 结束。。。。");
    }

    /**
     * 删除条件
     */
    protected void deleteCondition(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_condition where id="+ condition.getId());
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  condition 结束。。。。");
    }

    /**
     * 删除oi
     */
    protected void deleteOi(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_oi where id="+oi.getId());
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  oi 结束。。。。");

        //删除 oi2
        StringBuilder deleteSql2 = new StringBuilder("delete from xiwa_nvwa_oi where id="+oi2.getId());
        System.out.println(deleteSql2);
        Object o2 = DBUtils.insertAndDeleteData(deleteSql2.toString());
        System.out.println("删除  oi2 结束。。。。");
    }

    /**
     * 删除 连接器
     */
    protected void deleteConnector(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_connector where id="+connector.getId());
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  connector 结束。。。。");
    }

    /**
     * 删除 appConfig
     */
    protected void deleteAppConfig(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_api_config where id="+appConfigId);
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除 app config 结束。。。。");

    }

    /**
     * 清除缓存
     */
    protected void clearCache(){
        //缓存OI
        oi = new OI();
        //缓存OI2
        oi2 = new OI();
        //缓存 连接器
       connector = new Connector();
        //缓存page
        formPage = new Page();
        //缓存page
        gridPage = new Page();
        //缓存 form container
        formContainer = new Container();
        //缓存 grid container
        gridContianer = new Container();
        //缓存
        fieldIdList = new ArrayList<Integer>();
        //缓存 元素id列表
        elementsIdList = new ArrayList<Integer>();
        //缓存  元素layout id 列表
         elementsLayoutIdList = new ArrayList<Integer>();
        //缓存  condition
        condition = new Condition();
        //当前去 id 为测试field
        nameField = new Field();
        //返回插入的oi数据主键list
        dataIdListA = new ArrayList<Integer>();
        //返回插入的oi数据主键list
        dataIdListB = new ArrayList<Integer>();
        //连接的 id 为测试field
        fIdField = new Field();
        //缓存服务器属性
        serverAttrForm = new ElementServerAttribute();
        //缓存服务器属性
        serverAttrGrid = new ElementServerAttribute();

        appConfigId = 0;
    }




/**
     **************************下面的接口请求构造数据   目前 直接用上面的sql造数据*****************************
*/

    /**
     * 添加OI
     * id(INT)，name(varchar)，tel(varchar)，
     */
    public void addOi(){
        String url = ResponseConstant.HOST + "/nvwa/oi/add";
        Map<String, String> postData = new HashMap<String, String>();
        postData.put("name", "caseTest");
        postData.put("description","test");
        postData.put("isSyncSchema", "true");
        oi = (OI) this.mapToObject(OI.class,postData);

        NVWAResponse nvwaResponse = NVWAHttp.sendProducerAuthPostRequest(url, postData);

        System.out.println(nvwaResponse.isOk());
        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap.toString());

        if(dataMap.containsKey("id")){
            oi.setId(DataUtil.getInt(dataMap.get("id"),0));
        }
    }

    /**
     * 删除Oi
     * 根据id删除刚才创建的oi
     */
    public void deleteOiTest(){
        String url = ResponseConstant.HOST + "/nvwa/oi/delete";
        Map<String, String> postData = new HashMap<String, String>();
        //    postData.put("id", DataUtil.getString(oi.getId(),null));
        postData.put("id", "5");

        NVWAResponse nvwaResponse = NVWAHttp.sendProducerAuthPostRequest(url, postData);

        System.out.println(nvwaResponse.isOk());
        JSONObject dataMap = nvwaResponse.getDataMap();
        System.out.println(dataMap.toString());
    }

    /**
     * map 转换成 object对象
     *
     * @param map
     * @return
     */
    protected Object mapToObject(Class type, Map<String, String> map) {
        Object obj = null;
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);// 获取类属性
            obj = type.newInstance(); // 创建 JavaBean 对象
            // 给 JavaBean 对象的属性赋值
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();

                if (map.containsKey(propertyName)) {
                    // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                    Object value = map.get(propertyName);

                    Object[] args = new Object[1];
                    args[0] = value;

                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }


}
