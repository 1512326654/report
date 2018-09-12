package cn.worldyao.report.service;

import cn.worldyao.report.entity.Student;
import cn.worldyao.report.entity.StudentUser;
import cn.worldyao.report.entity.User;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * @author 董尧
 */
@Resource
public interface UserService {

	/**
	 * 用户名验证
	 * @param userName
	 * @return
	 */
	public abstract boolean checkUsername(String userName);

	/**
	 * 用户注册
	 * @param userName
	 * @param userPass
	 * @return
	 */
	public abstract String userRegist(String userName, String userPass);

	/**
	 * 用户登录
	 * @param userName
	 * @param userPass
	 * @return
	 */
	public abstract User userLogin(String userName, String userPass);

	/**
	 * 用户关联
	 * @param stuId
	 * @param userId
	 * @return
	 */
	public abstract boolean connectionInfo(int stuId, int userId);

	/**
	 * 关联显示
	 * @param stuId
	 * @return
	 */
	public abstract Student studentConnection(int stuId);

	/**
	 * 显示用户信息
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	public abstract List<StudentUser> showStudentInfo(int stuClass, int stuGroup);

	/**
	 * 用户修改密码，管理员进行重置密码
	 * @param userPass
	 * @param userId
	 * @return
	 */
	public abstract int alterPassword(String userPass, int userId);

	/**
	 * 验证是否存在QQ
	 * @param userQQ
	 * @return
	 */
	public abstract User QQCheck(String userQQ);

	/**
	 * 使用QQ注册
	 * @param userName
	 * @param userQQ
	 * @return
	 */
	public abstract int QQRegist(String userName, String userQQ);

	/**
	 *
	 * @return
	 */
	public abstract List<StudentUser> showAssociationStauts();
}
