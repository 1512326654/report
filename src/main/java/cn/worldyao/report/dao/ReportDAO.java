package cn.worldyao.report.dao;

import cn.worldyao.report.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 更新 2018 - 08 -06
 * @author 董尧
 *
 */
@Mapper
public interface ReportDAO {

	/**
	 * 添加周报
	 * @param map
	 * @return
	 */
	public abstract int applyReport(Map<String, Object> map);

	/**
	 * 通过条件查询周报
	 * 条件为：班级号【可选】，周次号【可选】，工作表号【可选】
	 * @param map
	 * @return
	 */
	public abstract List<Report> selReport(Map<String, Object> map);

	/**
	 * 修改周报
	 * @param map
	 * @return
	 */
	public abstract int alterReported(Map<String, Object> map);

	/**
	 * 通过周报序号修改周报
	 * @param map
	 * @return
	 */
	public abstract int alterReportedByREPID(Map<String, Object> map);

	/**
	 * 周报提交状态
	 * @param map
	 * @return
	 */
	public abstract List<MemberReportStatus> showGroupReportStatus(Map<String, Integer> map);

	/**
	 * 周报提交列表
	 * @param map
	 * @return
	 */
	public abstract List<ReportInfo> showReportList(Map<String, Integer> map);

	/**
	 * 周报显示
	 * @param repId
	 * @return
	 */
	public abstract String showReportByREPID(int repId);

	/**
	 * 显示各组数据统计
	 * @param map
	 * @return
	 */
	public abstract  List<GroupNum> eachClassGroupReportedNum(Map<String, Integer> map);

	/**
	 * 获取周报总数
	 * @param map
	 * @return
	 */
	public abstract int getReportListNum(Map<String, Integer> map);

	/**
	 * 时间线
	 * @param map
	 * @return
	 */
	public abstract List<ReportBase> timeLine(Map<String, Integer> map);

	/**
	 * 查询本的周报列表
	 * @param map
	 * @return
	 */
	public abstract List<Report> selReportByThisWeek(Map<String, Integer> map);

	/**
	 *
	 * @param map
	 * @return
	 */
	public abstract int alterStudentInfo(Map<String, Integer> map);
}
