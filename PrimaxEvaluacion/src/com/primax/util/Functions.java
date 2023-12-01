package com.primax.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class Functions {

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	public static boolean compararFechaHoy(Date date) {
		Calendar hoy = Calendar.getInstance();
		Calendar fechaComp = Calendar.getInstance();
		fechaComp.setTime(date);

		if (hoy.get(Calendar.DAY_OF_MONTH) == fechaComp.get(Calendar.DAY_OF_MONTH)) {
			if (hoy.get(Calendar.MONTH) == fechaComp.get(Calendar.MONTH)) {
				if (hoy.get(Calendar.YEAR) == fechaComp.get(Calendar.YEAR)) {
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}

	public static boolean compararFechas(Date before, Date after) {
		Calendar hoy = Calendar.getInstance();
		hoy.setTime(after);
		Calendar fechaComp = Calendar.getInstance();
		fechaComp.setTime(before);

		if (hoy.get(Calendar.DAY_OF_MONTH) == fechaComp.get(Calendar.DAY_OF_MONTH)) {
			if (hoy.get(Calendar.MONTH) == fechaComp.get(Calendar.MONTH)) {
				if (hoy.get(Calendar.YEAR) == fechaComp.get(Calendar.YEAR)) {
					return true;
				}
				return false;
			}
			return false;
		}
		return false;
	}
}
