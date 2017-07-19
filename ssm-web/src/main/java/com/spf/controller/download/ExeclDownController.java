package com.spf.controller.download;

import com.spf.common.BaseUtils;
import com.spf.common.ResultJson;
import com.spf.common.excel.ObjectExcelView;
import com.spf.common.result.BizResult;
import com.spf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @Author SPF
 * @Date 2017/5/24
 */
@Controller
public class ExeclDownController extends BaseUtils {

    @Autowired private UserService userService;

    @RequestMapping("/down/index")
    public String index(ModelMap model) {
        BizResult<List<Map<String, Object>>> n = userService.findAll();
        List<Map<String, Object>> list = n.getData();
        model.addAttribute("data", list);
        return "down/index";
    }

    @RequestMapping("/down")
    public ModelAndView down() {
        Map<String,Object> model = new HashMap<String, Object>();
        BizResult<List<Map<String, Object>>> n = userService.findAll();
        List<Map<String, Object>> list = n.getData();

        model.put("fileName","测试execl下载");
        model.put("sheetName","测试execl下载");
        model.put("titles", Arrays.asList(new String[]{"序号","姓名","年龄","性别","手机","密码"}));

        List<Map<String,Object>> r = new ArrayList<Map<String,Object>>();

        for (Map<String, Object> map : list) {
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("var1",map.get("id"));
            m.put("var2",map.get("name"));
            m.put("var3",map.get("age"));
            m.put("var4",map.get("sex"));
            m.put("var5",map.get("phone"));
            m.put("var6",map.get("pwd"));
            r.add(m);
        }
        model.put("varList",r);
        return new ModelAndView(new ObjectExcelView(),model);
    }

}
