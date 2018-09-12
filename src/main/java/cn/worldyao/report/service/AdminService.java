package cn.worldyao.report.service;

import cn.worldyao.report.entity.Admin;
import cn.worldyao.report.entity.GroupNum;
import cn.worldyao.report.entity.ReportInfo;
import cn.worldyao.report.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author 董尧
 */
@Service
public interface AdminService {

	/**
	 * 管理员登录接口
	 * @param adUsername
	 * @param adPassword
	 * @return
	 */
	public abstract Admin adminLogin(String adUsername, String adPassword);

	/**
	 * 显示各组数据统计
	 * @param repClass
	 * @param repWeek
	 * @return
	 */
	public abstract List<GroupNum> eachClassGroupReportedNum(int repClass, int repWeek);

	/**
	 * 获取学生信息列表
	 * @param stuClass
	 * @param stuGroup
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public abstract List<Student> getStudentList(int stuClass, int stuGroup, int pageSize, int pageNum);

	/**
	 * 获取学生信息总数（分页功能使用）
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	public abstract int getStudentListNum(int stuClass, int stuGroup);

	/**
	 * 获取周报总数（分页功能使用）
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @return
	 */
	public abstract int getReportListNum(int stuClass, int stuGroup, int repWeek);

	/**
	 * 周报提交列表
	 * @param stuClass
	 * @param stuGroup
	 * @param repWeek
	 * @param pageSize
	 * @param pageNum
	 * @return
	 */
	public abstract List<ReportInfo> showReportList(int stuClass, int stuGroup, int repWeek, int pageNum, int pageSize);
}
