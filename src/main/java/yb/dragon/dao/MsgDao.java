package yb.dragon.dao;

import org.apache.ibatis.annotations.*;

import yb.dragon.common.Msg;

@Mapper
public interface MsgDao {

	@Update("INSERT INTO msg (firstIp,endIp,firstNum,endNum,province,city) VALUES (#{firstIp},#{endIp},#{firstNum},#{endNum},#{province},#{city});")
	void updataMsg(Msg msg);
	
	@Select("SELECT firstIp,endIp,firstNum,endNum,province,city FROM msg where  firstNum <= #{ip} and #{ip} <= endNum LIMIT 1;")
	Msg queryMsgByip(long ip);
	
}
