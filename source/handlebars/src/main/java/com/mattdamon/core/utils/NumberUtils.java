package com.mattdamon.core.utils;

import java.text.DecimalFormat;

/**
 * NumberUtils
 * <p/>
 * 格式化金额数字，format：0.00
 *
 * @author <A>jiao_yang@neusoft.com</A>
 * @date May 19, 2015
 * @Copyright: © 2001-2015 东软集团股份有限公司
 */
public class NumberUtils {
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static String formatPrice(Number price) {
        if (price == null) {
            return "";
        }
        return df.format(price.doubleValue() / 100.0D);
    }
}
