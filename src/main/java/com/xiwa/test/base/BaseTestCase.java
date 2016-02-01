package com.xiwa.test.base;

import com.google.gson.JsonArray;
import com.xiwa.base.util.DataUtil;
import com.xiwa.base.util.StringUtil;
import com.xiwa.nvwa.bean.*;
import com.xiwa.test.BaseTest;
import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.DBUtils;
import com.xiwa.test.util.NVWAHttp;
import net.sf.json.JSONObject;
import org.junit.Test;
import sun.jvm.hotspot.ci.ciObjArrayKlass;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaobc on 16/1/30.
 */
public class BaseTestCase extends BaseTest {

    //缓存OI
    protected OI oi = new OI();
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

    //当前去 id 为测试field
    protected Field nameField = new Field();

    /**
     * 添加OI
     * id(INT)，name(varchar)，tel(varchar)，
     */
    @Test
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
    @Test
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


    /**
     * 初始化数据 oi
     */
    private void buildOi(){
        oi.setIdentified("testCase");
        oi.setName("testCase");
        oi.setTableName("testCase");

        String createTableSql = "CREATE TABLE " +oi.getTableName()+" "+
                "(id int(11) primary key not NULL auto_increment, " +
                " name VARCHAR(64), " +
                " tel VARCHAR(64))";

        Object o = DBUtils.insertAndDeleteData(createTableSql);
        System.out.println("建表结束。。。。");

        String insertDataSql = "insert into xiwa_nvwa_oi(name,identified,tableName,status,createDate) "+
                "values('"+oi.getName()+"','"+oi.getIdentified()+"','"+oi.getTableName()+"','normal','2016-02-01 12:20:30')";
        System.out.println(insertDataSql);
        Object o2 = DBUtils.insertAndDeleteData(insertDataSql);
        System.out.println("插入oi结束。。。。");

        oi.setId(DataUtil.getInt(o2,0));
    }

    /**
     * 添加完oi后 添加字段
     */
    private void buildFieldAfterOi(){

        String fieldSqlId = "insert into xiwa_nvwa_field(name,fieldName,notNull,oiIdentified,dataTypeField,length,serialNumber) "+
                "values('ID','id',0,'"+oi.getIdentified()+"','INT',11,'Field_id_123123123123123')";
        System.out.println(fieldSqlId);
        Object o = DBUtils.insertAndDeleteData(fieldSqlId);
        System.out.println("插入Field A 结束。。。。");
        fieldIdList.add(DataUtil.getInt(o, 0));

        nameField.setId(DataUtil.getInt(o, 0));
        nameField.setSerialNumber("Field_id_123123123123123");

        String fieldSqlName = "insert into xiwa_nvwa_field(name,fieldName,notNull,oiIdentified,dataTypeField,length,serialNumber) "+
                "values('name','name',1,'"+oi.getIdentified()+"','VARCHAR',64,'Field_id_123123asqw23123')";
        System.out.println(fieldSqlName);
        Object o2 = DBUtils.insertAndDeleteData(fieldSqlName);
        System.out.println("插入Field B 结束。。。。");
        fieldIdList.add(DataUtil.getInt(o2, 0));


        String fieldSqlTel = "insert into xiwa_nvwa_field(name,fieldName,notNull,oiIdentified,dataTypeField,length,serialNumber) "+
                "values('tel','tel',1,'"+oi.getIdentified()+"','VARCHAR',64,'Field_id_12dfgd3asqw23123')";
        System.out.println(fieldSqlTel);
        Object o3 = DBUtils.insertAndDeleteData(fieldSqlTel);
        System.out.println("插入Field C 结束。。。。");
        fieldIdList.add(DataUtil.getInt(o3, 0));

    }

    /**
     * 初始化container
     */
    private void buildContainer(){
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
    private void buildPage(){
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
    private void buildElements(){
        String elementsFormSql = "insert into xiwa_nvwa_element(containerId,name,componentAlias,serialNumber,pageId,fieldSerialNumber) "+
                "values("+formContainer.getId()+",'1-form','text','Element_123123sq',0,'"+nameField.getSerialNumber()+"')";
        System.out.println(elementsFormSql);
        Object o = DBUtils.insertAndDeleteData(elementsFormSql);
        System.out.println("插入 form  element 结束。。。。");
        elementsIdList.add(DataUtil.getInt(o, 0));

        String elementsGridSql = "insert into xiwa_nvwa_element(containerId,name,componentAlias,serialNumber,pageId,fieldSerialNumber) "+
                "values("+gridContianer.getId()+",'1-grid','text','Element_1231dssq',0,'"+nameField.getSerialNumber()+"')";
        System.out.println(elementsGridSql);
        Object o2 = DBUtils.insertAndDeleteData(elementsGridSql);
        System.out.println("插入 form  element 结束。。。。");
        elementsIdList.add(DataUtil.getInt(o2, 0));
    }

    /**
     * 初始化  element layout
     */
    private void buildElementLayout(){
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
     * 插入 新表数据
     */
    private void buildOiData(){
        String insertSql = "insert into "+oi.getTableName()+"(name,tel) "+
                "values('xiaobc','123123123')";
        System.out.println(insertSql);
        DBUtils.insertAndDeleteData(insertSql);

        insertSql = "insert into "+oi.getTableName()+"(name,tel) "+
                "values('xiaobc2','2123123123')";
        System.out.println(insertSql);
        DBUtils.insertAndDeleteData(insertSql);

        insertSql = "insert into "+oi.getTableName()+"(name,tel) "+
                "values('xiaobc3','3123123123')";
        System.out.println(insertSql);
        DBUtils.insertAndDeleteData(insertSql);

    }

    /**
     * 测试前的数据准备
     */
    public void createData(){
        this.buildOi();

        this.buildFieldAfterOi();

        this.buildContainer();

        this.buildPage();

        this.buildElements();

        this.buildElementLayout();

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
    }

    /**
     * 删除 elementLayout
     */
    private void deleteElementLayout(){
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
    private void deleteElement(){
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
    }

    /**
     * 删除field
     */
    private void deleteField(){
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
    private void deleteContainer(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_container where id in ("+formContainer.getId()+","+gridContianer.getId()+")");
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  container 结束。。。。");
    }

    /**
     * 删除container
     */
    private void deletePage(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_page where id in ("+formPage.getId()+","+gridPage.getId()+")");
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  page 结束。。。。");
    }

    /**
     * 删除刚才新建的表
     */
    private void deleteTable(){
        StringBuilder deleteSql = new StringBuilder("DROP TABLE "+oi.getTableName());
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("清除 table 结束。。。。");
    }

    /**
     * 删除oi
     */
    private void deleteOi(){
        StringBuilder deleteSql = new StringBuilder("delete from xiwa_nvwa_oi where id="+oi.getId());
        System.out.println(deleteSql);
        Object o = DBUtils.insertAndDeleteData(deleteSql.toString());
        System.out.println("删除  oi 结束。。。。");
    }


}
