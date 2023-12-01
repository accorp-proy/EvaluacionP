package com.primax.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.primax.util.RowPoi.CellPos;

public class POIReader {
	/**
	 * Support only for XLSX return list {@link RowPoi}
	 * 
	 * @param stream
	 *            for resources loading
	 * @param page
	 *            index in the sheet
	 * 
	 * @return {@code List<RowPoi>}
	 * 
	 */

	public static List<RowPoi> getColumsFromXLSXFile(InputStream stream, int page, boolean isEncripted, String clave) {
		List<RowPoi> lisRes = new ArrayList<RowPoi>();
		InputStream dataStream = null;
		try {

			if (isEncripted) {
				POIFSFileSystem fileSystem = new POIFSFileSystem(stream);
				EncryptionInfo info = new EncryptionInfo(fileSystem);
				Decryptor d = Decryptor.getInstance(info);

				if (!d.verifyPassword(clave)) {
					throw new RuntimeException("Unable to process: document is encrypted");
				}
				dataStream = d.getDataStream(fileSystem);
			} else {
				dataStream = stream;
			}

			XSSFWorkbook workBook = new XSSFWorkbook(dataStream);
			XSSFSheet sheet = workBook.getSheetAt(page);

			int rowStart = sheet.getFirstRowNum();
			int rowEnd = sheet.getLastRowNum();

			for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
				Row r = sheet.getRow(rowNum);
				RowPoi poiRow = new RowPoi();
				poiRow.setIndex(rowNum);
				if (r == null) {
					lisRes.add(poiRow);
					continue;
				}
				int lastColumn = r.getLastCellNum();
				for (int cn = 0; cn <= lastColumn; cn++) {

					CellPos celda = poiRow.getNewCell();
					celda.setIndex(cn);
					Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (c == null) {
						celda.setValue(null);
					} else {
						int type = c.getCellType();
						switch (type) {
						case Cell.CELL_TYPE_STRING:
							celda.setValue(c.getStringCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(c)) {
								celda.setValue(c.getDateCellValue());
							} else {
								celda.setValue(c.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_FORMULA:
							try {
								celda.setValue(c.getNumericCellValue());
							} catch (Exception e) {
								try {
									celda.setValue(c.getStringCellValue());
								} catch (Exception e2) {
									System.out.println("Valor no Reconocido :" + c.getCellFormula());
								}
							}
							break;
						default:
							celda.setValue(c.getStringCellValue());
							break;
						}
					}
				}
				lisRes.add(poiRow);
			}
			workBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lisRes;
	}

	public static List<RowPoi> getColumsFromXLSFile(InputStream stream, int page, boolean isEncripted, String clave) {
		List<RowPoi> lisRes = new ArrayList<RowPoi>();
		try {
			if (isEncripted) {
				Biff8EncryptionKey.setCurrentUserPassword(clave);
			}
			HSSFWorkbook workBook = new HSSFWorkbook(stream);
			HSSFSheet sheet = workBook.getSheetAt(page);
			int rowStart = sheet.getFirstRowNum();
			int rowEnd = sheet.getLastRowNum();

			for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
				Row r = sheet.getRow(rowNum);
				RowPoi poiRow = new RowPoi();
				poiRow.setIndex(rowNum);
				if (r == null) {
					lisRes.add(poiRow);
					continue;
				}
				int lastColumn = r.getLastCellNum();
				for (int cn = 0; cn < lastColumn; cn++) {
					CellPos celda = poiRow.getNewCell();
					celda.setIndex(cn);
					Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
					if (c == null) {
						celda.setValue(null);
					} else {
						int type = c.getCellType();
						switch (type) {
						case Cell.CELL_TYPE_STRING:
							celda.setValue(c.getStringCellValue());
							break;
						case Cell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(c)) {
								celda.setValue(c.getDateCellValue());
							} else {
								celda.setValue(c.getNumericCellValue());
							}
							break;
						case Cell.CELL_TYPE_FORMULA:
							try {
								celda.setValue(c.getNumericCellValue());
							} catch (Exception e) {
								celda.setValue(c.getStringCellValue());
							}
							break;
						default:
							celda.setValue(c.getStringCellValue());
							break;
						}
					}
				}
				lisRes.add(poiRow);
			}
			workBook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lisRes;
	}

}
