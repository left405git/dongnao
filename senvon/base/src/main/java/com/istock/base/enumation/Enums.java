/**
 * 
 */
package com.istock.base.enumation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author SenVon
 *
 */
public class Enums {

	private static Map<String,List<BaseEnum>> map = new HashMap<String,List<BaseEnum>>();
	private static Enums enums = new Enums();
	
	private BaseEnum allEnum = new BaseEnum(-1,"","ALL","全部");
	
	public void init(List<BaseEnum> dataList)
	{
		try{
			if(map.isEmpty()){
				List<BaseEnum> list = dataList;
				
				if(null != list)
				{
					for(BaseEnum en : list)
					{
						List<BaseEnum> enumList = (List<BaseEnum>)map.get(en.getTeam());
						if(null == enumList)
						{
							enumList = new ArrayList<BaseEnum>();
							map.put(en.getTeam(), enumList);
						}
						enumList.add(en);
					}
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Enums getInstance()
	{
		if(enums==null)
		{
			enums = new Enums();
		}
		return enums;
	}

	public List<BaseEnum> getEnumsByGroup(String group,boolean hasAll){
		List<BaseEnum> childList = (List<BaseEnum>)map.get(group);
		if(childList != null && childList.size()>0){
			List<BaseEnum> list = new ArrayList<BaseEnum>();
			if(hasAll){
				list.add(allEnum);
			}
			for(BaseEnum obj : childList){
				list.add(obj);
			}
			return list;
		}else
			return null;
	}
	
	public String[] getEnumsAllDesc(String group,boolean hasAll)
	{
		final List<BaseEnum> child = getEnumsByGroup(group,hasAll);
		if(child!=null&&child.size()>0)
		{
			String[] strs = new String[child.size()];
//			strs[0] = "全部";
			for(int i=0;i<child.size();i++)
			{
				BaseEnum e = child.get(i);
				strs[i] = e.getDescription();
			}
			return strs;
		}else{
			return null;
		}
	}
	
	public String getEnumsDescByValue(String group,Integer value)
	{
		final List<BaseEnum> child = map.get(group);
		//System.out.println(child.size());
		if(child!=null&&child.size()>0)
		{
			//String[] strs = new String[child.size()];
			String str=null;
			for(int i=0;i<child.size();i++)
			{
				BaseEnum e = child.get(i);
				if(e.getValue().equals(value))
				{
					str = e.getDescription();
					break;
				}
			}
			return str;
		}else{
			return null;
		}
	}
	
	public Integer getEnumsValueByDesc(String group,String desc)
	{
		final List<BaseEnum> child = map.get(group);
		if(child!=null&&child.size()>0)
		{
			//String[] strs = new String[child.size()];
			for(int i=0;i<child.size();i++)
			{
				BaseEnum e = child.get(i);
				if(e.getDescription().equalsIgnoreCase(desc))
				{
					return e.getValue();
				}
			}
		}
		return null;
	}
	
	public Integer getEnumsValueByCode(String group,String code)
	{
		final List<BaseEnum> child = map.get(group);
		if(child!=null&&child.size()>0)
		{
			for(int i=0;i<child.size();i++)
			{
				BaseEnum e = child.get(i);
				if(e.getCode().equalsIgnoreCase(code))
				{
					return e.getValue();
				}
			}
		}
		return null;
	}
	
	public String getEnumsCodeByValue(Integer value){
		for(Entry<String , List<BaseEnum>> entry : map.entrySet()){
			List<BaseEnum> enumList = entry.getValue();
			for(BaseEnum baseEnum : enumList){
				if(baseEnum.getValue().equals(value)){
					return baseEnum.getCode();
				}
			}
		}
		return null;
	}
	
	public String getEnumsDescByCode(String group,String code){
		final List<BaseEnum> child = map.get(group);
		if(child!=null&&child.size()>0)
		{
			//String[] strs = new String[child.size()];
			String str=null;
			for(int i=0;i<child.size();i++)
			{
				BaseEnum e = child.get(i);
				if(e.getCode().equalsIgnoreCase(code))
				{
					str = e.getDescription();
					break;
				}
			}
			return str;
		}else{
			return null;
		}
	}
}
