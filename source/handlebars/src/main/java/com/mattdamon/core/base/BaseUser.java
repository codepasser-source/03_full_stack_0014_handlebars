package com.mattdamon.core.base;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * BaseUser
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 19, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@Getter
@Setter
@ToString
@XmlRootElement
public class BaseUser extends BaseModel {

	private static final long serialVersionUID = -4862403158086165860L;

	private String name;
	private String gender;
	private String email;
	private int age;
	private String userName;
	private String password;
	private String passwdMD5;
	private String parentId;

}
