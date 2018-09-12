package cn.worldyao.report.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("page")
public class PageController {
	@RequestMapping("reportadd")
	public ModelAndView reportadd(){
		ModelAndView modelAndView = new ModelAndView("login2");
		return modelAndView;
	}

	@RequestMapping("404")
	public ModelAndView error(){
		ModelAndView modelAndView = new ModelAndView("404");
		return modelAndView;
	}

	@RequestMapping("admin")
	public ModelAndView admin(){
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}

	@RequestMapping(value = "regist" ,method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView regist(){
		ModelAndView modelAndView = new ModelAndView("regist");
		return modelAndView;
	}

	@RequestMapping(value = "login" ,method = {RequestMethod.GET}, produces = "text/html;charset=UTF-8")
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView("login2");
		return modelAndView;
	}
}
