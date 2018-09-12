package cn.worldyao.report.controller;

import cn.worldyao.report.entity.ReportBase;
import cn.worldyao.report.entity.Student;
import cn.worldyao.report.entity.User;
import cn.worldyao.report.service.StudentService;
import cn.worldyao.report.service.UserService;
import cn.worldyao.report.util.CommonUtil;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/afterlogin.do")
@SessionAttributes("info")
public class QQLoginOK {

	@Resource
	private UserService userService;
	@Resource
	private StudentService studentService;

	@RequestMapping
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
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
				System.out.println("当前openId" + openID);
				UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
				UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
				String name = userInfoBean.getNickname();

				Map map = isConnection(name, openID);
				System.out.println(map);
				System.out.println(map.get("modelAndView"));
				String url = map.get("modelAndView").toString();
				System.out.println(url);

				map.remove("modelAndView");
				System.out.println(map);
//
				model.addAttribute("info", map);
				modelAndView.setViewName(url);


			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}

	public Map<String,Object> isConnection(String name, String openID){
		User user = userService.QQCheck(openID);
		Map map = new HashMap();
		if (user != null){
			System.out.println("-------------------" + user.getUser_stuId());
			if (user.getUser_stuId() == 0) {
				//未关联
				Student student = new Student();
				student.setStuName(user.getUserName());
				System.out.println("---------------------->" + student);

				map.put("student", student);
				map.put("userId", user.getUserId());

				map.put("flag", false);
				map.put("modelAndView", "forward:/student/toTndex");
				System.out.println(map.get("modelAndView"));



//				model.addAttribute("info", map);

//				modelAndView.setViewName("redirect:/student/toTndex");
			} else {
//						已关联
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

					map.put("modelAndView", "forward:/student/toTndex");

//						modelAndView.addObject("list",list);
//					modelAndView.setViewName("redirect:/student/toTndex");
				} else if (student.getStuLeader() == 3) {
					map.put("modelAndView", "forward:/student/weekIndex");
//					modelAndView.setViewName("week/index");
				}
//				model.addAttribute("info", map);
//				System.out.println(student);
//				System.out.println("-------------15" + modelAndView);

			}
		}else {
			if (Regist(name,openID)){
				System.out.println("注册成功！！");
				Map map2 = isConnection(name, openID);
				System.out.println("-----------" + map2);
				return map2;
//				reportStatus();

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
		User user = userService.QQCheck(userQQ);
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
