package cn.worldyao.report.core;

import cn.worldyao.report.entity.Report;
import cn.worldyao.report.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * 该个类是核心类
 * 2018-07-24更新
 *
 * @author 董尧
 */
public class Core {

	private static int[] classOne = null;
	private static int[] classTwo = null;

	/**
	 * 加载配置
	 */
	static {
		String fileName = "groupSheet.xt";
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(CommonUtil.getFilePath(fileName)));
			Properties prop = new Properties();
			if (prop.isEmpty()) {
				prop.load(in);
			}
			String[] sheetOne = prop.getProperty("classOne").split(",");
			String[] sheetTwo = prop.getProperty("classTwo").split(",");

			classOne = stringToInt(sheetOne);
			classTwo = stringToInt(sheetTwo);

			System.out.println("--------------" + JSON.toJSONString(classOne));
			System.out.println("--------------" + JSON.toJSONString(classTwo));
			in.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e2) {
		}
	}

	/**
	 * 字符串数组转换为整形数组
	 * @param sheet
	 * @return
	 */
	public static int[] stringToInt(String[] sheet) {
		int[] temp = new int[sheet.length];
		for (int i = 0; i < sheet.length; i++) {
			temp[i] = Integer.parseInt(sheet[i]);
		}
		return temp;
	}

	/**
	 * 判断写入操作
	 *
	 * @param classNum 班级号1为1班，2为2班
	 */
	public String writeReport(int classNum, List<Report> list) {
		String fileName = null;
		int[] groupSheet = null;
		//判断是1班还是2班
		if (classNum == 1) {
			fileName = "1e.xlsx";
			groupSheet = classOne;
		} else if (classNum == 2) {
			fileName = "2e.xlsx";
			groupSheet = classTwo;
		}
		System.out.println(fileName);
		System.out.println(JSON.toJSONString(groupSheet));
		return writeToExcel(CommonUtil.getFilePath(fileName), groupSheet, list);
	}


	/**
	 * 该个方法是用来写入Excel
	 * 2018-08-12更新方
	 * 由上一层方法传过来的文件名，即对应的班级名称所对应的文件名，传递过来分组数组，和该班级的链表
	 *
	 * @param file
	 * @param groupSheet
	 * @param list
	 */
	private String writeToExcel(File file, int[] groupSheet, List<Report> list) {
		//基本行
		final int BASE_ROW = 2;
		//行跨度
		final int ROW_NUM = 15;
		//行的起始
		int weekRow = 0;
		int row_num = 0;
		//是否为组长标志
		boolean groupReport;
		FileInputStream fileInputStream;
		BufferedOutputStream bufferedOutputStream = null;
		System.out.println(file);
		try {
			System.out.println(file);
			fileInputStream = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(fileInputStream);
			for (Report report : list) {
				groupReport = false;
				//判断是否为组周报
				for (int i = 0; i < groupSheet.length; i++) {
					if (groupSheet[i] == report.getRepSheet()) {
						groupReport = true;
					}
				}
				//判断是否为组长
				if (groupReport) {
					weekRow = BASE_ROW + ((2 * ROW_NUM) * (report.getRepWeek() - 1));
					row_num = (2 * ROW_NUM - 1);
				} else {
					weekRow = BASE_ROW + (ROW_NUM * (report.getRepWeek() - 1));
					row_num = (ROW_NUM - 1);
				}
				//确定工作表
				Sheet sheet = workbook.getSheetAt(report.getRepSheet());

				//创建周次合并单元格
				CellRangeAddress weekCellRange = new CellRangeAddress(weekRow, (weekRow + row_num), 0, 3);
				//创建内容合并单元格
				CellRangeAddress contextCellRange = new CellRangeAddress(weekRow, (weekRow + row_num), 4, 15);
				//将两个单元格加入工作表
				sheet.addMergedRegion(weekCellRange);
				sheet.addMergedRegion(contextCellRange);
				//创建行
				Row row = sheet.createRow(weekRow);
				//创建周次信息单元格
				Cell weekCell = row.createCell(0);
				//设置周次信息单元格的样式
				weekCell.setCellStyle(setCellStyle(workbook));
				//设置周次信息单元格的边框
				setBorderStyle(sheet, weekCellRange);
				//设置周次信息单元格，水平举重，垂直居中
				setAlign(weekCell, Halign.H_CENTER, Valign.V_CENTER);
				//向周次信息单元格写入值
				weekCell.setCellValue(CommonUtil.thisWeekInfo());
				//创建内容单元格
				Cell contextCell = row.createCell(4);
				//设置内容单元格样式
				contextCell.setCellStyle(setCellStyle(workbook));
				//设置单元格的边框
				setBorderStyle(sheet, contextCellRange);
				//设置该个单元格，水平左对齐，垂直居中
				setAlign(contextCell, Halign.H_LEFT, Valign.V_CENTER);
				contextCell.setCellValue(report.getRepContext());
				System.out.println(report.getRepContext());
				bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
				workbook.write(bufferedOutputStream);
			}
			//关闭流
			fileInputStream.close();
			bufferedOutputStream.close();
		} catch (Exception e) {
			e.getStackTrace();
			return "转换异常";
		}
		return "转换完成";
	}

	/**
	 * 该个方法是用来设置合并单元格的边框
	 *
	 * @param sheet
	 * @param region
	 */
	private void setBorderStyle(Sheet sheet, CellRangeAddress region) {
		// 合并单元格左边框样式
		RegionUtil.setBorderLeft(BorderStyle.MEDIUM_DASHED, region, sheet);
		RegionUtil.setLeftBorderColor(IndexedColors.LIGHT_ORANGE.getIndex(), region, sheet);
		// 合并单元格上边框样式
		RegionUtil.setBorderTop(BorderStyle.MEDIUM_DASHED, region, sheet);
		RegionUtil.setTopBorderColor(IndexedColors.LIGHT_ORANGE.getIndex(), region, sheet);
		// 合并单元格右边框样式
		RegionUtil.setBorderRight(BorderStyle.MEDIUM_DASHED, region, sheet);
		RegionUtil.setRightBorderColor(IndexedColors.LIGHT_ORANGE.getIndex(), region, sheet);
		// 合并单元格下边框样式
		RegionUtil.setBorderBottom(BorderStyle.MEDIUM_DASHED, region, sheet);
		RegionUtil.setBottomBorderColor(IndexedColors.LIGHT_ORANGE.getIndex(), region, sheet);
	}

	/**
	 * @param workbook 工作表
	 * @param fontSize 字体大小
	 * @param fontName 字体
	 * @param fontBold 字体是否加粗
	 * @param wrapText 文本是否自动换行
	 * @return
	 */
	private CellStyle setCellStyle(Workbook workbook, int fontSize, String fontName, boolean fontBold, boolean wrapText) {
		//创建单元格的样式
		CellStyle cellStyle = workbook.createCellStyle();
		//设置字体
		Font font = workbook.createFont();
		//设置字体大小
		font.setFontHeightInPoints((short) fontSize);
		//设置字体
		font.setFontName("新宋体");
		//设置是否加粗
		font.setBold(fontBold);
		//单元格字体样式设置为当前的字体样式
		cellStyle.setFont(font);
		//设置自动换行
		cellStyle.setWrapText(wrapText);
		//设置单元格的背景颜色
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setFillForegroundColor(IndexedColors.WHITE1.index);
		return cellStyle;
	}

	/**
	 * 该个方法文本自动换行
	 *
	 * @param workbook 工作表
	 * @param fontSize 字体大小
	 * @param fontName 字体
	 * @param fontBold 字体是否加粗
	 * @return
	 */
	private CellStyle setCellStyle(Workbook workbook, int fontSize, String fontName, boolean fontBold) {
		return setCellStyle(workbook, fontSize, fontName, fontBold, true);
	}

	/**
	 * 该个方法字体默认不加粗
	 * 该个方法文本自动换行
	 *
	 * @param workbook 工作表
	 * @param fontSize 字体大小
	 * @param fontName 字体
	 * @return
	 */
	private CellStyle setCellStyle(Workbook workbook, int fontSize, String fontName) {
		return setCellStyle(workbook, fontSize, fontName, false);
	}

	/**
	 * 该个方法字体默认加粗
	 * 该个方法文本自动换行
	 * 设置默认字体为“新宋体”
	 *
	 * @param workbook 工作表
	 * @param fontSize 字体大小
	 * @return
	 */
	private CellStyle setCellStyle(Workbook workbook, int fontSize) {
		return setCellStyle(workbook, fontSize, "新宋体");
	}

	/**
	 * 该个方法字体默认加粗
	 * 该个方法文本自动换行
	 * 设置默认字体为“新宋体”
	 * 设置字体的默认大小为12
	 *
	 * @param workbook 工作表
	 * @return
	 */
	private CellStyle setCellStyle(Workbook workbook) {
		return setCellStyle(workbook, 12);
	}

	/**
	 * 设置水平垂直对齐方式
	 *
	 * @param cell
	 * @param hAlign
	 * @param vAlign
	 */
	private void setAlign(Cell cell, HorizontalAlignment hAlign, VerticalAlignment vAlign) {
		CellUtil.setAlignment(cell, hAlign);
		CellUtil.setVerticalAlignment(cell, vAlign);
	}

	/**
	 * 该个类是水平对齐方式
	 */
	static class Halign {
		static HorizontalAlignment H_LEFT = HorizontalAlignment.LEFT;
		static HorizontalAlignment H_RIGHT = HorizontalAlignment.RIGHT;
		static HorizontalAlignment H_CENTER = HorizontalAlignment.CENTER;
	}

	/**
	 * 该个类是垂直对齐方式
	 */
	static class Valign {
		static VerticalAlignment V_TOP = VerticalAlignment.TOP;
		static VerticalAlignment V_CENTER = VerticalAlignment.CENTER;
		static VerticalAlignment V_BOTTOM = VerticalAlignment.BOTTOM;
	}
}
