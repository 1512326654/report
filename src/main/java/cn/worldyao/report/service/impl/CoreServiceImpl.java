package cn.worldyao.report.service.impl;

import cn.worldyao.report.core.Core;
import cn.worldyao.report.dao.ReportDAO;
import cn.worldyao.report.service.CoreService;
import cn.worldyao.report.util.CommonUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 董尧
 */
@Service
public class CoreServiceImpl implements CoreService {

	@Resource
	private ReportDAO reportDAO;

	Core core = new Core();

	/**
	 * 将数据库中的周报信息写入到Excel，核心方法，该方法传入一个参数
	 * 这个参数的作用是判断班级
	 * 方法的实现中，已对周次进行了判断，默认的为当前周
	 *
	 * @param stuClass
	 * @param repWeek
	 * @return
	 */
	public String writeToExcel(int stuClass, int repWeek) {
		Map<String, Integer> map = new HashMap<String, Integer>(2);
		//默认当前周
		map.put("repClass", stuClass);
		map.put("repWeek", repWeek);
		return core.writeReport(stuClass, reportDAO.selReportByThisWeek(map));
	}

	/**
	 * 获取组长分配的标号
	 */
	public Map getLeaderSheetNum() {
		String fileName = "groupSheet.xt";
		Properties prop = new Properties();
		try {
			//获取配置文件
			InputStream in = new BufferedInputStream(new FileInputStream(CommonUtil.getFilePath(fileName)));
			if (prop.isEmpty()) {
				prop.load(in);
			}
			Map<String, String> map = new HashMap<String, String>(2);
			map.put("classOne", prop.getProperty("classOne"));
			map.put("classTwo", prop.getProperty("classTwo"));
			in.close();
			return map;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 修改组内周报的编号配置
	 * @param classOne
	 * @param classTwo
	 */
	public Map<String ,Object> setLeaderSheetNum(String classOne, String classTwo) {
		String fileName = "groupSheet.xt";
		Map<String ,Object> map = new HashMap<String, Object>(2);
		Properties prop = new Properties();
		try {
			OutputStream fileOutputStream = new FileOutputStream(CommonUtil.getFilePath(fileName));
			System.out.println("-----------------------------------------------------------");
			prop.setProperty("classOne", classOne);
			prop.setProperty("classTwo", classTwo);
			prop.store(fileOutputStream, "----------UPDATE TIME-----------");
			fileOutputStream.close();
			System.out.println("-----------------------------------------------------------");
			map.put("msg","修改成功");
			map.put("code",1);
		} catch (Exception ex) {
			map.put("msg","修改失败");
			map.put("code",0);
		}
		return map;
	}

}
