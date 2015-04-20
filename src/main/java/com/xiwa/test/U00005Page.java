package com.xiwa.test;

import java.util.HashMap;
import java.util.Map;

import com.xiwa.test.bean.NVWAResponse;
import com.xiwa.test.bean.ResponseConstant;
import com.xiwa.test.util.NVWAHttp;

import net.sf.json.JSONObject;

import org.junit.Test;

import  static org.junit.Assert.assertTrue;

public class U00005Page implements ResponseConstant
{
	@Test
	public void testPage(){
//		/nvwaUser/page/${page}/${container}
		String url=ResponseConstant.HOST+"/nvwaUser/page/api/asdfasdf";
		Map<String,String> postData=new HashMap<String,String>();
		postData.put("token", TOKEN);
		NVWAResponse nvwaResponse = NVWAHttp.sendPostRequest(url, postData);
		System.out.println(nvwaResponse.isOk());
		JSONObject dataMap = nvwaResponse.getDataMap();
		System.out.println(dataMap);
	}
}
