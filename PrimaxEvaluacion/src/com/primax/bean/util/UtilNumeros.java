package com.primax.bean.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class UtilNumeros {

	public static double round(Double num, int places) {
		if (num != null)
			return new BigDecimal(num).setScale(places, RoundingMode.HALF_UP).doubleValue();
		return 0;
	}

}
