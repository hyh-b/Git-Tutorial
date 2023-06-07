package com.example.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.model1.ZipcodeDAO;
import com.example.model1.ZipcodeTO;

@RestController
//@Controller
@ComponentScan("com.example.model1")
public class ConfigController {
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ZipcodeDAO dao;
	
	@RequestMapping("/zipcode.do")
	public ModelAndView zipcode() {
		return new ModelAndView("zipcode");
	}
	
	
	@RequestMapping("/zipcode_ok.do")
	public ModelAndView zipcode_ok(HttpServletRequest request)throws Exception {
		System.out.println("data : "+request.getParameter("dong"));
		String strDong = request.getParameter("dong");
		ArrayList<ZipcodeTO> lists = dao.zipcodeList(strDong);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("zipcode_ok");
		modelAndView.addObject("lists",lists);
		return modelAndView;
	}
}
