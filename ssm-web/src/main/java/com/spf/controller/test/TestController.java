package com.spf.controller.test;

import com.spf.common.BaseUtils;
import com.spf.common.ResultJson;
import com.spf.common.result.BizResult;
import com.spf.common.sql.SqlTemplate;
import com.spf.entity.UserEntity;
import com.spf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Controller
public class TestController extends BaseUtils {
	@Autowired
	UserService userService;
	@Autowired private SqlTemplate sqldao;
	
	@RequestMapping("testSave")
	public void save(){
		UserEntity up = new UserEntity();
		up.setAge("22");
		up.setName("SPF");
		up.setSex("N");
		ResultJson n = userService.save(up);
		System.out.println(n);
		backClient(toJSONString(n));
	}
	
	@RequestMapping(value="testfindById")
	@ResponseBody
	public void findById(){
		ResultJson n = userService.findById(4);
		System.out.println("===================》"+toJSONString(n));
		backClient(toJSONString(n));
	}
	
	@RequestMapping("testfindAll")
	public void findAll(){
		BizResult<List<Map<String, Object>>> n = userService.findAll();
		List<Map<String, Object>> list = n.getData();
		System.out.println(list);
	}
	@RequestMapping("/page")
	public String page(){
		return "test/ajaxpage";
	}

	@RequestMapping("sqltest")
	@ResponseBody
	public void sqltest() {
		String sql = "select * from sf_user";
		List<Map<String, Object>> list = sqldao.queryForList(sql);

		List<Map<String, Object>> pagelist = sqldao.queryForPageList(sql, 1, 3);

		String sql2 = "select * from sf_user where id=?";
		Map<String, Object> datamap = sqldao.queryFirst(sql2,"1");

		List<Map<String, Object>> pagelist2 = sqldao.queryForPageList("select * from sf_user where sex=?", "N", 1, 2);

		List<UserEntity> listDto = sqldao.queryForList(UserEntity.class,sql);
		String str = toJSONString(listDto);
		List<UserEntity> pagelistDto = sqldao.queryForPageList(UserEntity.class, sql, 1, 2);

		List<UserEntity> pagelistDto2 = sqldao.queryForPageList(UserEntity.class,"select * from sf_user where sex=?","N",1,2);

		UserEntity dto = sqldao.queryFirst(UserEntity.class, sql2, "1");

		String cout = "select count(0) count from sf_user";
		Object num = sqldao.queryForObject(cout,"count");
	}

	public static void main(String[] args) {
		//System.out.println(new SimpleDateFormat("yyyyMMddHHmmsssss").format(new java.util.Date()));
	}
}
