package com.istock.base.enumation;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**初始化Enums
 * @author senvon.shi
 *
 */
@Repository
public class EnumInitialize implements InitializingBean{

	@Autowired
	private EnumsDataProvider provider;
	
	public void loadEnum(){
		List<BaseEnum> enumList = provider.findEnumsData();
		if(enumList != null && enumList.size()>0){
			Enums.getInstance().init(enumList);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		loadEnum();
	}
}
