package test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {

	@org.junit.Test
	public void test1() {
		String json="{'flag':false,'data':null,'errorMsg':'用户名已存在'}";
		ObjectMapper mapper=new ObjectMapper();
		
	}
}
