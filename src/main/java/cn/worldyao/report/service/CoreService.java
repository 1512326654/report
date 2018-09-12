package cn.worldyao.report.service;

import java.util.Map;

/**
 *
 * @author 董尧
 */
public interface CoreService {


	/**
	 * 核心接口
	 * @param stuClass
	 * @param repWeek
	 * @return
	 */
	public abstract String writeToExcel(int stuClass, int repWeek);

	/**
	 * 获取组长分配的标号
	 * @return
	 */
	public abstract Map getLeaderSheetNum();

	/**
	 * 修改组内周报的编号配置
	 * @param classOne
	 * @param classTwo
	 * @return
	 */
	public abstract Map<String ,Object> setLeaderSheetNum(String classOne, String classTwo);

}
