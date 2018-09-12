package cn.worldyao.report.service;

import cn.worldyao.report.entity.*;

import java.util.List;

/**
 *
 * @author 董尧
 */
public interface ReportService {

	/**
	 * 学生添加周报
	 * @param stuClass
	 * @param stuSheet
	 * @param stuContext
	 * @return
	 */
	public abstract int applyReport(int stuId, int stuClass, int stuSheet, String stuContext);


	/**
	 * 查询周报
	 * @param stuClass
	 * @param stuWeek
	 * @param stuSheet
	 * @return
	 */
	public abstract List<Report> selReport(int stuClass, int stuWeek, int stuSheet);

	/**
	 * 修改周报
	 * @param repContext
	 * @param repClass
	 * @param repSheet
	 * @return
	 */
	public abstract int studentAlter(int repClass, int repSheet, String repContext);


	/**
	 * 通过周报序号修改周报
	 * @param repId
	 * @param repContext
	 * @return
	 */
	public abstract boolean alterReportedByREPID(int repId, String repContext);

	/**
	 * 按班查询该班成员的周报提交状态
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public abstract List<MemberReportStatus> showGroupReportedStatus(int stuClass, int stuGroup, int repWeek);


	/**
	 * 查询组周报提交状态
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public abstract List<MemberReportStatus> showGroupReportStatus(int stuClass, int stuGroup, int repWeek);


	/**
	 * 周报提交列表(包含分页)
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public abstract List<ReportInfo> showReportList(int stuClass, int stuGroup, int repWeek, int pageNum, int pageSize);

	/**
	 * 周报显示
	 * @param repId
	 * @return
	 */
	public abstract String showReportByREPID(int repId);

	/**
	 * 显示各组数据统计
	 * @param repClass
	 * @param repWeek
	 * @return
	 */
	public abstract  List<GroupNum> eachClassGroupReportedNum(int repClass, int repWeek);

	/**
	 * 时间线
	 * @param repClass
	 * @param repSheet
	 * @return
	 */
	public abstract List<ReportBase> timeLine(int repClass, int repSheet);

	/**
	 * 查询本的周报列表
	 * @param repClass
	 * @param repWeek
	 * @return
	 */
	public abstract List<Report> selReportByThisWeek(int repClass, int repWeek);

	/**
	 *
	 * @param repClass
	 * @param repSheet
	 * @param stuId
	 * @return
	 */
	public abstract int alterStudentInfo(int repClass, int repSheet, int stuId);
}
