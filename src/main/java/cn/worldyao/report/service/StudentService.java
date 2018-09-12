package cn.worldyao.report.service;

import cn.worldyao.report.entity.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 服务类
 * @author 董尧
 */
@Resource
public interface StudentService {
	/**
	 * 学生登录服务接口
	 *
	 * @param stuName
	 * @param stuSheet
	 * @return
	 */
	public abstract Student studentLogin(String stuName, int stuSheet);

	/**
	 * 学生添加周报服务接口
	 * @param stuClass
	 * @param stuSheet
	 * @param stuContext
	 * @return
	 */
	public abstract boolean applyReport(int stuId, int stuClass, int stuSheet, String stuContext);

	/**
	 * 查询周报
	 * @param stuClass
	 * @param stuWeek
	 * @param stuSheet
	 * @return
	 */
	public abstract String showThisWeekReport(int stuClass, int stuWeek, int stuSheet);

	/**
	 * 查询本周是否提交过周报
	 * @param stuClass
	 * @param stuWeek
	 * @param stuSheet
	 * @return
	 */
	public abstract boolean studentThisReported(int stuClass, int stuWeek, int stuSheet);

	/**
	 * 本人修改周报
	 * @param repContext
	 * @param repClass
	 * @param repSheet
	 * @return
	 */
	public abstract boolean studentAlter(int repClass, int repSheet, String repContext);

	/**
	 * 本组组长查询本组本周组员周报提交状态
	 * @param stuClass
	 * @param repWeek
	 * @param stuGroup
	 * @return
	 */
	public abstract List<MemberReportStatus> showMemberReportStatus(int stuClass, int stuGroup, int repWeek);

	/**
	 * 周报提交数据统计
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public abstract GroupReported showGroupDataStatistics(int stuClass, int stuGroup, int repWeek);

	/**
	 * 查询组周报提交状态
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public abstract List<MemberReportStatus> showGroupReportStatus(int stuClass, int stuGroup, int repWeek);

	/**
	 * 周报提交列表
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public abstract List<ReportInfo> showReportList(int stuClass, int stuGroup, int repWeek);

	/**
	 * 周报显示
	 * @param repId
	 * @return
	 */
	public abstract String showReportByREPID(int repId);

	/**
	 * 通过周报序号修改周报
	 * @param repId
	 * @param repContext
	 * @return
	 */
	public abstract boolean alterReportedByREPID(int repId, String repContext);


	/**
	 * 信息关联
	 * @param map
	 * @return
	 */
	public abstract int stuConnectionInfo(Map<String, Integer> map);

	/**
	 * 时间线
	 * @param repClass
	 * @param repSheet
	 * @return
	 */
	public abstract List<ReportBase> timeLine(int repClass, int repSheet);

	/**
	 * 提交意见
	 * @param suggest
	 * @return
	 */
	public abstract int addSuggest(Suggest suggest);

	/**
	 * 修改学生信息，不包括姓名
	 * @param student
	 * @return
	 */
	public abstract int alterStudentInfo(Student student);
}
