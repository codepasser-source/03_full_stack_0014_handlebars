package com.mattdamon.core.env;

import com.mattdamon.core.base.BaseCookie;
import com.mattdamon.core.base.BaseUser;
import com.mattdamon.core.utils.StringUtiler;

/**
 * 
 * UserHolder
 *
 * @author <A>cheng.yy@neusoft.com</A>
 *
 * @date May 19, 2015
 *
 * @Copyright: © 2001-2015 东软集团股份有限公司
 *
 */
public final class UserHolder {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ThreadLocal<BaseUser> user = new ThreadLocal();
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ThreadLocal<BaseCookie> cookies = new ThreadLocal();

	public static void putCurrentUser(BaseUser baseUser) {
		user.set(baseUser);
	}

	public static BaseUser getCurrentUser() {
		return (BaseUser) user.get();
	}

	public static void removeUser() {
		user.remove();
	}

	public static String getUserId() {
		BaseUser baseUser = getCurrentUser();
		return baseUser != null ? baseUser.getId() : null;
	}

	public static String getMasterId() {
		BaseUser baseUser = getCurrentUser();
		if (baseUser == null) {
			return null;
		} else {
			String parentId = baseUser.getParentId();
			return (parentId != null && !StringUtiler.isNullOrEmpty(parentId)) ? parentId
					: baseUser.getId();
		}
	}

	public static void putCurrentCookies(BaseCookie baseCookie) {
		cookies.set(baseCookie);
	}

	public static BaseCookie getCurrentCookies() {
		return (BaseCookie) cookies.get();
	}

	public static void clearCookies() {
		cookies.remove();
	}

	public static BaseUser getLoginUserDemo() {
		BaseUser user = new BaseUser();
		user.setId("1234567890");
		user.setName("Tom");
		user.setAge(31);
		user.setEmail("cheng.yy@neusoft.com");
		user.setParentId("0987654321");
		return user;
	}

}
