package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;

import net.sf.json.JSONObject;

import org.junit.Test;

import  static org.junit.Assert.assertTrue;

public class U00006DeleteById implements ResponseConstant
{
	@Test
	//根据ID来删除container的内容
	public void testDeleteID(){	
		///nvwaUser/delete/${page}/${container}/${id}
		String url=ResponseConstant.HOST+"/nvwaUser/delete/api/asdfasdf/5";
		Map<String,String> postData=new HashMap<String,String>();
		postData.put("token", TOKEN);
		NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);

		System.out.println(nvwaResponse.isOk());
		JSONObject dataMap = nvwaResponse.getDataMap();
		System.out.println(dataMap.toString());
	}
}
