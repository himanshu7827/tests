/**********************************************************
 Project:	   DWH_TB
 File:         ParamObject.java
 Created:      Aug 9, 2017
 Last Changed: Aug 9, 2017
 Author:       Shivam Bhatnagar

This code is copyright (c) 2017 C-DAC Noida.

 ***********************************************************/
package in.pms.global.misc;

public class ParamObject {

	public ParamObject() {

	}

	public ParamObject(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	private String name;
	private String value;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
