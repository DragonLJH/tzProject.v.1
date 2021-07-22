package yb.dragon.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import yb.dragon.common.Common;
import yb.dragon.common.Jump;
import yb.dragon.common.Msg;
import yb.dragon.service.JumpService;
import yb.dragon.service.MsgService;

@Controller
@CrossOrigin(value = "*") // 支持跨域
public class JumpController {

	@Autowired
	private JumpService jumpService;

	@Autowired
	private MsgService msgService;

	private Common common = new Common();

	
	// 根据fileName获取屏蔽地区，根据ip获取访问地址，判断访问地址是否在屏蔽地区里，是（跳审核页），否（跳软文审核页）
	@RequestMapping("/tz/{fileName}")
	@CrossOrigin(value = "*") // 支持跨域
	public String decideToJump(@PathVariable("fileName") String fileName, HttpServletRequest request) {
		// 根据文件名获取到跳转信息（现url，跳转url，uuid，shield(屏蔽地区)）
		Jump jumpMsg = jumpService.queryJumpByUuid(fileName);
		// 获取ip地址
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 将获取的ip根据ipToLong转为ipNum
		long ipNum = common.ipToLong(ip);
		// 获取msg信息（firstIp、endIp、firstNum、endNum、province(省份)、city(城市)）
		Msg msg = msgService.queryMsgByip(ipNum);
		// 判断ip地址获取地区是否在屏蔽地区内然后跳转                                  
		if (msg != null
				&& !(jumpMsg.getShield().contains(msg.getProvince()) || jumpMsg.getShield().contains(msg.getCity()))) {
			return "redirect:" + jumpMsg.getJumpurl();
		}
		return "redirect:http://" + jumpMsg.getUrl() + "/" + jumpMsg.getUuid();
	}
	
	//高德api
	// 根据fileName获取屏蔽地区，根据ip获取访问地址，判断访问地址是否在屏蔽地区里，是（跳审核页），否（跳软文审核页）
//	@RequestMapping("/tz/{fileName}")
//	@CrossOrigin(value = "*") // 支持跨域
//	public String decideToJumpGD(@PathVariable("fileName") String fileName, HttpServletRequest request) {
//		// 根据文件名获取到跳转信息（现url，跳转url，uuid，shield(屏蔽地区)）
//		Jump jumpMsg = jumpService.queryJumpByUuid(fileName);
//		// 获取ip地址
//		String ip = request.getHeader("x-forwarded-for");
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//		
//		String loadJson = common.loadJson(ip);
//		
//		JSONObject jsonObject = JSONObject.parseObject(loadJson);
//		String province = jsonObject.getString("province");
//		String city = jsonObject.getString("city");
//		if (!(jumpMsg.getShield().contains(province) || jumpMsg.getShield().contains(city))) {
//			return "redirect:" + jumpMsg.getJumpurl();
//		}
//		return "redirect:http://" + jumpMsg.getUrl() + "/" + jumpMsg.getUuid();
//	}
	
	
	

//	//根据fileName获取屏蔽地区，根据ip获取访问地址，判断访问地址是否在屏蔽地区里，是（跳审核页），否（跳软文审核页）
//	@RequestMapping("/tz/{fileName}")
//	@CrossOrigin(value = "*") // 支持跨域
//	public String decideToJump(@PathVariable("fileName") String fileName, HttpServletRequest request) {
//		System.out.println("request.getRequestURI()----"+request.getRequestURI());
//		// 根据文件名获取到跳转信息（现url，跳转url，uuid，shield(屏蔽地区)）
//		Jump jumpMsg = jumpService.queryJumpByUuid(fileName);
//		// 获取ip地址
//		String ip = request.getHeader("x-forwarded-for");
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP");
//		}
//		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//			ip = request.getRemoteAddr();
//		}
//		//将获取的ip根据ipToLong转为ipNum
//		long ipNum = common.ipToLong(ip);
//		//获取msg信息（firstIp、endIp、firstNum、endNum、province(省份)、city(城市)）
//		Msg msg = msgService.queryMsgByip(ipNum);
//		boolean flag1 = jumpMsg.getShield().contains(msg.getProvince());
//		System.out.println("flag1:"+flag1);
//		boolean flag2 = jumpMsg.getShield().contains(msg.getCity());
//		System.out.println("flag2:"+flag2);
//		System.out.println("(flag1 || flag2)---"+(flag1 || flag2));
//		// 判断ip地址获取地区是否在屏蔽地区内然后跳转
//		if (msg != null && !(flag1 || flag2)) {
//			return "redirect:" + jumpMsg.getJumpurl();
//		}
//		return "redirect:" + jumpMsg.getUrl();
//	}

	@RequestMapping("/queryUrl")
	@ResponseBody
	@CrossOrigin(value = "*") // 支持跨域
	public List<String> queryUrl() {
		return jumpService.queryUrl();
	}

	@RequestMapping("/queryByUrl")
	@ResponseBody
	@CrossOrigin(value = "*") // 支持跨域
	public List<Jump> queryByUrl(Jump jump) {
		List<Jump> list = new ArrayList<Jump>();
		list = jumpService.queryByUrl(jump.getUrl());
		return list;
	}

	// 新增数据
	@RequestMapping("/updataJump")
	@ResponseBody
	@CrossOrigin(value = "*") // 支持跨域
	public boolean updataJump(Jump jump) {
		boolean res = false;
		String uuid = common.MyUUID();
		jump.setUuid(uuid);
		res = jumpService.updataJump(jump);
		common.createFile(jump.getUrl(), uuid);
		return res;
	}
	
	@RequestMapping("/updataShieldByUuid")
	@ResponseBody
	@CrossOrigin(value = "*") // 支持跨域
	public boolean updataShieldByUuid(Jump jump){
		return jumpService.updataShieldByUuid(jump);
	}

	// 获取jump表所有的信息
	@RequestMapping("/queryAll")
	@ResponseBody
	@CrossOrigin(value = "*") // 支持跨域
	public List<Jump> queryAll() {
		List<Jump> list = new ArrayList<Jump>();
		list = jumpService.queryAll();
		return list;
	}

	// 获取jump表所有的信息
	@RequestMapping("/delectByUuid")
	@ResponseBody
	@CrossOrigin(value = "*") // 支持跨域
	public boolean delectByUuid(Jump jump) {
		boolean res = false;
		String domain = jumpService.queryJumpByUuid(jump.getUuid()).getUrl();
		common.deleteFile(domain,jump.getUuid());
		res = jumpService.delectByUuid(jump.getUuid());
		return res;
	}

}
