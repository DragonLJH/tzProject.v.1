package yb.dragon.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import yb.dragon.common.Common;
import yb.dragon.common.Jump;
import yb.dragon.common.Msg;
import yb.dragon.service.JumpService;
import yb.dragon.service.MsgService;

@RestController
@CrossOrigin(value = "*") // 支持跨域
public class MsgController {

	@Autowired
	private MsgService msgService;
	@Autowired
	private JumpService jumpService;

	private Common common = new Common();

	// 导入ip的数据库
	@RequestMapping("/updataMsg")
	@CrossOrigin(value = "*") // 支持跨域
	public void updataMsg(Msg msg) {
		long firstNum = common.ipToLong(msg.getFirstIp());
		long endNum = common.ipToLong(msg.getEndIp());
		msg.setFirstNum(firstNum);
		msg.setEndNum(endNum);
		msgService.updataMsg(msg);
	}

	@RequestMapping("/queryMsgByip")
	@CrossOrigin(value = "*") // 支持跨域
	public Msg queryMsgByip(long ip) {
		return msgService.queryMsgByip(ip);
	}

	@RequestMapping("/getServerName")
	@CrossOrigin(value = "*") // 支持跨域
	public String getServerName(HttpServletRequest request) {
		return request.getServerName(); // 获取域名
	}

	@RequestMapping("/test/getRequestURI")
	@CrossOrigin(value = "*") // 支持跨域
	public String getRequestURI(HttpServletRequest request) {
		return request.getRequestURI(); // 获取文件路径
	}

	@RequestMapping("/getJumpMsg/{fileName}")
	@CrossOrigin(value = "*") // 支持跨域
	public Jump getJumpMsg(@PathVariable("fileName") String fileName) {
		Jump jumpMsg = jumpService.queryJumpByUuid(fileName);
		return jumpMsg; // 获取文件路径
	}

	@RequestMapping("/showIp")
	@CrossOrigin(value = "*") // 支持跨域
	public String showIp(HttpServletRequest request) {
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
		return ip; // 获取域名
	}

	@RequestMapping("/showMSG")
	@CrossOrigin(value = "*") // 支持跨域
	public Msg showMSG(HttpServletRequest request) {
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
		return msg; // 获取域名
	}

	@RequestMapping("/createFile/{domain}/{uuid}")
	@CrossOrigin(value = "*") // 支持跨域
	public void createFile(@PathVariable("domain") String domain, @PathVariable("uuid") String uuid){
		File f = new File("/www/wwwroot/" + domain + "/" + uuid, "index.html");
		if (f.exists()) {
			// 文件已经存在，输出文件的相关信息
			System.out.println(f.getAbsolutePath());
			System.out.println(f.getName());
			System.out.println(f.length());
		} else {
			// 先创建文件所在的目录
			f.getParentFile().mkdirs();
			try {
				// 创建新文件
				f.createNewFile();
			} catch (IOException e) {
				System.out.println("创建新文件时出现了错误。。。");
				e.printStackTrace();
			}
		}

	}

}
