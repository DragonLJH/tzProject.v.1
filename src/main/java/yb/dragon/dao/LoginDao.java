package yb.dragon.dao;

import org.apache.ibatis.annotations.*;

import yb.dragon.common.Login;

@Mapper
public interface LoginDao {

	@Select("SELECT numer,password FROM login WHERE number = #{numer};")
	Login queryByNum(String numer);
	
	
	
}
