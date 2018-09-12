package cn.worldyao.report.controller;

import cn.worldyao.report.entity.JSONDate;
import cn.worldyao.report.entity.ReportBase;
import cn.worldyao.report.entity.Student;
import cn.worldyao.report.entity.StudentUser;
import cn.worldyao.report.service.StudentService;
import cn.worldyao.report.service.UserService;
import cn.worldyao.report.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 董尧
 */
@Controller
@RequestMapping("user")
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private StudentService studentService;

	/**
	 * 用户注册
	 *
	 * @param userName
	 * @param userPass
	 * @return
	 */
	@RequestMapping(value = "/userRegist", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String userRegist(String userName, String userPass) {
		if (!userService.checkUsername(userName)) {
			return JSON.toJSONString(userService.userRegist(userName, userPass));

		} else {
			return JSON.toJSONString("用户名已占用！！");
		}
	}


	/**
	 * 显示用户信息
	 * @param stuClass
	 * @param stuGroup
	 * @return
	 */
	@RequestMapping(value = "/showStudentInfo", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String showStudentInfo(int stuClass, int stuGroup) {
		System.out.println(stuClass);
		System.out.println(stuGroup);
		List<StudentUser> list = userService.showStudentInfo(stuClass, stuGroup);
		for (StudentUser studentUser : list){
			System.out.println(studentUser);
		}
		return new JSONDate(list,0).toString();

	}

	/**
	 * 密码重置
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/alterPass", method = {RequestMethod.POST}, produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String alterPass(int userId) {

		int returnResult = userService.alterPassword("123456",userId);
		System.out.println(returnResult);

		return returnResult + "";

	}

	/**
	 * QQ登录
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/qqLogin", method = RequestMethod.GET)
	@ResponseBody
	public void qqGetCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html;charset=UTF-8");
			//请求qq互联网页授权，回调域名地址请求
			response.sendRedirect(new Oauth().getAuthorizeURL(request));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (QQConnectException e) {
			e.printStackTrace();
		}
	}


	/**--------------------------------------------页面跳转----------------------------------------------**/
	//页面跳转

	/**
	 * 跳转页面到userList
	 * <p>
	 * //	 * @param stuClass
	 * //	 * @param stuGroup
	 *
	 * @return
	 */
	@RequestMapping(value = "/userList", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView userList() {
		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("stuClass", stuClass);
//		modelAndView.addObject("stuGroup", stuGroup);
		modelAndView.setViewName("week/user-list");
		return modelAndView;
	}


	@RequestMapping(value = "/afterlogin" , method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public String handleRequest(HttpServletRequest request, HttpServletResponse response, RedirectAttributes attributes) throws Exception {
//		ModelAndView modelAndView = new ModelAndView();
		String url = "";
		try {
			AccessToken accessTokenObj = (new Oauth()).getAccessTokenByRequest(request);
			String accessToken   = null,
					openID        = null;
			long tokenExpireIn = 0L;
			if (accessTokenObj.getAccessToken().equals("")) {
				System.out.print("没有获取到响应参数");
			}else{
				accessToken = accessTokenObj.getAccessToken();
				tokenExpireIn = accessTokenObj.getExpireIn();
				OpenID openIDObj =  new OpenID(accessToken);
				openID = openIDObj.getUserOpenID();
				//获取用户的OPENID
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				//获取用户的昵称
				String name = userInfoBean.getNickname();
				String name2 = filterEmoji(name,"xt");

				System.out.println("------------------>" + openID);
				System.out.println("------------------>" + name2);

				Map map = isConnection(name2, openID);
				System.out.println(map);
				System.out.println(map.get("modelAndView"));
				url = map.get("modelAndView").toString();
				System.out.println(url);

				map.remove("modelAndView");
				System.out.println(map);
//
				attributes.addFlashAttribute("info", map);
//				modelAndView.setViewName(url);


			}
		}catch(Exception e){
			System.out.println("-------------------------->出错");
			e.printStackTrace();
		}
		return url;
	}

	private String filterEmoji(String source,String slipStr) {
		if(StringUtils.isNotBlank(source)){
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
		}else{
			return source;
		}
	}



	public Map<String,Object> isConnection(String name, String openID){
		cn.worldyao.report.entity.User user = userService.QQCheck(openID);
		Map map = new HashMap();
		if (user != null){
			System.out.println("-------------------" + user);
			if (user.getUser_stuId() == 0) {
				//未关联
				Student student = new Student();
				student.setStuName(user.getUserName());
				System.out.println("---------------------->" + student);

				map.put("student", student);
				map.put("userId", user.getUserId());

				map.put("flag", false);
				map.put("modelAndView", "redirect:/student/toTndex");
				System.out.println(map.get("modelAndView"));


//				model.addAttribute("info", map);

//				modelAndView.setViewName("redirect:/student/toTndex");
			} else {
				//已关联
				Student student = userService.studentConnection(user.getUser_stuId());

				map.put("student", student);
				map.put("flag", true);
				System.out.println(student);
//					验证是否为组长
				if (student.getStuLeader() == -1) {
					List<ReportBase> list = studentService.timeLine(student.getStuClass(),student.getStuSheet());
					for (ReportBase reportBase : list){
						reportBase.setRepAddTime(CommonUtil.timeTrans(reportBase.getRepAddTime()));
						System.out.println(reportBase);
					}
					map.put("timeLine",list);
					map.put("modelAndView", "redirect:/student/toTndex");
				} else if (student.getStuLeader() == 3) {
					System.out.println("--------------------------------");
					List<ReportBase> list = studentService.timeLine(student.getStuClass(),student.getStuSheet());
					for (ReportBase reportBase : list){
						reportBase.setRepAddTime(CommonUtil.timeTrans(reportBase.getRepAddTime()));
						System.out.println(reportBase);
					}
					map.put("timeLine",list);
					map.put("modelAndView", "redirect:/student/weekIndex");
				}
			}
		}else {
			if (Regist(name,openID)){
				Map map2 = isConnection(name, openID);
				return map2;
			}
		}
		return map;
	}


	/**
	 * 跳转页面到report-status
	 * <p>
	 * //	 * @param stuClass
	 * //	 * @param stuGroup
	 *
	 * @return
	 */
/*	@RequestMapping(value = "/reportStatus", method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView reportStatus() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forward:/afterlogin");
		return modelAndView;
	}*/

	/**
	 * 验证是否注册
	 * @param userQQ
	 * @return
	 */
	public boolean isRegist(String userQQ){
		cn.worldyao.report.entity.User user = userService.QQCheck(userQQ);
		System.out.println(user);
		return (user != null) ? true:false;
	}

	/**
	 * 进行注册
	 * @param userName
	 * @param userQQ
	 * @return
	 */
	public boolean Regist(String userName, String userQQ){
		int returnResult = userService.QQRegist(userName, userQQ);
		return (returnResult == 1) ? true:false;
	}

}
