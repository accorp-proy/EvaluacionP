package com.primax.bean.vs.base;

import java.util.Map;

import javax.faces.context.FacesContext;

public class BeanUtil {

	public static <T> T getBean(String name, Class<T> type) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (T) context.getApplication().evaluateExpressionGet(context, name, type);
	}

	public static void delFromViewRoot(String value) {
		Map<String, Object> root = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
		root.remove(value);
	}

}
