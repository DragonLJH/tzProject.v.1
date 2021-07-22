package yb.dragon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import yb.dragon.common.Msg;
import yb.dragon.dao.MsgDao;

@Service
public class MsgService {

	@Autowired
	private MsgDao msgDao;
	
	@Transactional(rollbackFor = RuntimeException.class) // 异常回滚
	public void updataMsg(Msg msg) {
		msgDao.updataMsg(msg);
	};
	
	public Msg queryMsgByip(long ip) {
		return msgDao.queryMsgByip(ip);
	}
	
}
