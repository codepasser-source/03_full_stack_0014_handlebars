package com.mattdamon.core.base;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.mattdamon.core.db.annotation.GeneratedValue;
import com.mattdamon.core.db.annotation.enums.GeneratedItem;
import com.mattdamon.core.db.annotation.enums.GeneratedStrategy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * BaseModel
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date Apr 15, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
@Getter
@Setter
@ToString
@XmlRootElement
public abstract class BaseModel extends BaseObject<BaseData> {

	private static final long serialVersionUID = 804967268405002794L;

	// Object ID
	@GeneratedValue(generator = GeneratedItem.ID, strategy = GeneratedStrategy.UUID)
	protected String id;
	// 创建时间
	@GeneratedValue(generator = GeneratedItem.CREATEAT)
	protected Date createAt;
	// 更新时间
	@GeneratedValue(generator = GeneratedItem.UPDATEAT)
	protected Date updateAt;
	// 是否删除
	@GeneratedValue(generator = GeneratedItem.DELETE)
	protected boolean isDelete;
	// 描述说明
	protected String summary;
	// 描述说明
	protected String description;

}
