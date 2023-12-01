package com.primax.adapters;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapterMonthDay extends XmlAdapter<String, Date> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");

    @Override
    public Date unmarshal(String v) throws Exception {
	Date fecha = dateFormat.parse(v);
	return fecha;
    }

    @Override
    public String marshal(Date v) throws Exception {
	return dateFormat.format(v.getTime());
    }

}
