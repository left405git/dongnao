package com.istock.base.enumation;

import java.util.List;

public class EnumTool {

	public String findText(String name , Number value){
		return Enums.getInstance().getEnumsDescByValue(name, value.intValue());
	}
	
	public String findTextByCode(String name ,String code){
		return Enums.getInstance().getEnumsDescByCode(name, code);
	}
	
	public int findValueByCode(String team , String code){
		return Enums.getInstance().getEnumsValueByCode(team, code);
	}
	
	public String findText4List(Number value , List<BaseEnum> enumList){
		if(enumList != null){
			for(BaseEnum myEnum : enumList){
				if(myEnum.getValue() == value.intValue()){
					return myEnum.getDescription();
				}
			}
		}
		return null;
	}
}
