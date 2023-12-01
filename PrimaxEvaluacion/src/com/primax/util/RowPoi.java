package com.primax.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Handler for xlsx, xls documents getting generic data from document's book
 * 
 * @author Rafael
 * 
 * 
 */

public class RowPoi {

	public RowPoi() {
		this.cells = new ArrayList<RowPoi.CellPos>();
	}

	public CellPos getNewCell() {
		CellPos celda = new CellPos();
		cells.add(celda);
		return celda;
	}

	private int index;

	private List<CellPos> cells;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List<CellPos> getCells() {
		return cells;
	}

	public void setCells(List<CellPos> cells) {
		this.cells = cells;
	}

	public class CellPos {
		private int index;
		private Object value;

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		@SuppressWarnings("unchecked")
		public <T> T getValue() {
			return (T) value;
		}

		public void setValue(Object value) {
			this.value = value;
		}

		public String getStringValue() {
			if (value != null) {
				String val = value.toString().trim().replace(".0", "").replace("\u00A0", "");
				return val;
			} else {
				return null;
			}
		}

		public Double getDoubleValue() {
			if (value != null) {
				try {
					Double valor = Double.parseDouble(value.toString().replaceAll(" ", "").replaceAll("\u00A0", ""));
					return valor;
				} catch (Exception e) {
					return 0D;
				}
			} else {
				return null;
			}
		}

		public Long getLongValue() {
			if (value != null) {
				try {
					Long valor = Long.parseLong(value.toString().replace(".0", "").replace(" ", "").replace("\u00A0", ""));
					return valor;
				} catch (Exception e) {
					return 0L;
				}
			} else {
				return null;
			}
		}

		public Date getDateValue() {
			if (value != null) {
				try {
					return (Date) value;
				} catch (Exception e) {
					return new Date();
				}
			} else {
				return null;
			}
		}

	}

}
