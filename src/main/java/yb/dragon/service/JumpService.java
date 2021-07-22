package yb.dragon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yb.dragon.common.Jump;
import yb.dragon.dao.JumpDao;

@Service
public class JumpService {

	@Autowired
	private JumpDao jumpDao;

	public Jump queryJumpByUuid(String uuid) {
		return jumpDao.queryJumpByUuid(uuid);
	}
	
	public List<Jump> queryAll() {
		List<Jump> list = new ArrayList<Jump>();
		list = jumpDao.queryAll();
		return list;
	};
	
	public List<Jump> queryByUrl(String url){
		List<Jump> list = new ArrayList<Jump>();
		list = jumpDao.queryByUrl(url);
		return list;
	}
	
	public List<String> queryUrl() {
		return jumpDao.queryUrl();
	}
	

	@Transactional(rollbackFor = RuntimeException.class) // 异常回滚
	public boolean updataJump(Jump jump) {
		boolean res = false;
		int flag1 = jumpDao.countJump();
		jumpDao.updataJump(jump);
		int flag2 = jumpDao.countJump();
		if(flag2 > flag1) {
			res = true;
		}
		return res;
	}
	
	@Transactional(rollbackFor = RuntimeException.class) // 异常回滚
	public boolean updataShieldByUuid(Jump jump){
		boolean res = false;
		Jump oldJump = jumpDao.queryJumpByUuid(jump.getUuid());
		jumpDao.updataShieldByUuid(jump);
		if(!(jump.getJumpurl().equals(oldJump.getJumpurl()) && jump.getShield().equals(oldJump.getShield()))) {
			res = true;
		}
		return res;
	}
	
	@Transactional(rollbackFor = RuntimeException.class) // 异常回滚
	public boolean delectByUuid(String uuid) {
		boolean res = false;
		int flag1 = jumpDao.countJump();
		jumpDao.delectByUuid(uuid);
		int flag2 = jumpDao.countJump();
		if(flag2 < flag1) {
			res = true;
		}
		return res;
	}
}
