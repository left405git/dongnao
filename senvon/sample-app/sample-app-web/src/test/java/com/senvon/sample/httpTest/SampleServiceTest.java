package com.senvon.sample.httpTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.senvon.sample.BaseResponse;
import com.senvon.sample.SampleService;
import com.senvon.sample.model.MenuInfoVO;

/**要运行此service,一定要先运行服务器
 * @author senvon
 *
 */
@RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-test.xml"})
public class SampleServiceTest {

	@Autowired
	private SampleService sampleService;
	
	@Test
	public void test1() throws Exception{
		BaseResponse<List<MenuInfoVO>> result = sampleService.findUserList("senvon", 1, 3);
		
	}
}
