package cn.worldyao.report.service.impl;

import cn.worldyao.report.dao.StudentDAO;
import cn.worldyao.report.dao.UserDAO;
import cn.worldyao.report.entity.Student;
import cn.worldyao.report.entity.StudentUser;
import cn.worldyao.report.entity.User;
import cn.worldyao.report.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.annotation.Resource;
//import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 董尧
 */
@Service
public class UserServiceImpl implements UserService {
	@Resource
	private UserDAO userDAO;
	@Resource
	private StudentDAO studentDAO;

	@Autowired
	private DataSourceTransactionManager transactionManager;
	/**
	 * 用户名验证
	 * @param userName
	 * @return
	 */
	public boolean checkUsername(String userName) {
		return (userDAO.checkUsername(userName) != null)?true:false;
	}

	/**
	 * 用户注册
	 * @param userName
	 * @param userPass
	 * @return
	 */
	public String userRegist(String userName, String userPass){
		Map<String,String> map = new HashMap<String, String>(2);
		System.out.println(DigestUtils.md5Hex(userPass).toUpperCase());
		map.put("userName", userName);
		map.put("userPass", DigestUtils.md5Hex(userPass).toUpperCase());
		return (userDAO.userRegist(map) != -1)?("注册成功"):("注册失败");
	}

	/**
	 * 用户登录
	 * @param userName
	 * @param userPass
	 * @return
	 */
	public User userLogin(String userName, String userPass) {
		Map<String,String> map = new HashMap<String, String>(2);
		map.put("userName", userName);
		map.put("userPass", userPass);
		return userDAO.userLogin(map);
	}

	/**
	 * 用户关联
	 * @param stuId
	 * @param userId
	 * @return
	 */
//	@Transactional
	public boolean connectionInfo(int stuId, int userId) {
		DefaultTransactionDefinition defaultTransactionDefinition = new DefaultTransactionDefinition();
		defaultTransactionDefinition.setName("connection");
		defaultTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = transactionManager.getTransaction(defaultTransactionDefinition);
		try {
			System.out.println(stuId);
			System.out.println(userId);
			Map<String,Integer> map = new HashMap<String, Integer>(2);
			map.put("stuId", stuId);
			map.put("userId", userId);
			userDAO.userConnectionInfo(map);
			studentDAO.stuConnectionInfo(map);
			return true;
		}catch (Exception e){
			transactionManager.rollback(status);
			return false;
		}
	}

	/**
	 *
	 * @param stuId
	 * @return
	 */
	public Student studentConnection(int stuId) {
		Map<String,Integer> map = new HashMap<String, Integer>(1);
		map.put("stuId", stuId);
		return userDAO.studentConnection(map);
	}

	/**
	 * 显示用户信息
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	public List<StudentUser> showStudentInfo(int stuClass, int stuGroup){
		Map<String,Integer> map = new HashMap<String, Integer>(2);
		map.put("stuClass", stuClass);
		map.put("stuGroup", stuGroup);
		return userDAO.showStudentInfo(map);
	}

	/**
	 * 用户修改密码，管理员进行重置密码
	 * @param userPass
	 * @param userId
	 * @return
	 */
	public int alterPassword(String userPass, int userId){
		Map<String,Object> map = new HashMap<String, Object>(2);
		//进行MD5加密
		map.put("userPass", DigestUtils.md5Hex(userPass).toUpperCase());
		map.put("userId", userId);
		return userDAO.alterPassword(map);
	}

	/**
	 * 验证是否存在QQ
	 * @param userQQ
	 * @return
	 */
	public User QQCheck(String userQQ){
		return userDAO.QQCheck(userQQ);
	}

	/**
	 * 使用QQ注册
	 * @param userName
	 * @param userQQ
	 * @return
	 */
	public int QQRegist(String userName, String userQQ){
		Map<String,String> map = new HashMap<String, String>(2);
		map.put("userName", userName);
		map.put("userQQ", userQQ);
		return userDAO.QQRegist(map);
	}

	/**
	 *
	 * @return
	 */
	public List<StudentUser> showAssociationStauts(){
		return userDAO.showAssociationStauts();
	}
}
