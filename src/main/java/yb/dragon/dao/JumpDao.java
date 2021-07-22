package yb.dragon.dao;

import java.util.List;

import org.apache.ibatis.annotations.*;

import yb.dragon.common.Jump;

@Mapper
public interface JumpDao {

	@Select("SELECT jumpurl,url,uuid,shield FROM jump where uuid = #{uuid} LIMIT 1;")
	Jump queryJumpByUuid(String uuid);

	@Select("SELECT jumpurl,url,uuid,shield FROM jump order by id desc;")
	List<Jump> queryAll();

	@Select("SELECT jumpurl,url,uuid,shield FROM jump where url = #{url} order by id desc;")
	List<Jump> queryByUrl(String url);

	@Select("SELECT count(id) FROM jump")
	int countJump();
	
	@Select("SELECT distinct url FROM jump order by id desc;")
	List<String> queryUrl();

	@Insert("INSERT INTO jump (jumpurl,url,uuid,shield) VALUES (#{jumpurl},#{url},#{uuid},#{shield});")
	void updataJump(Jump jump);
	
	@Update("UPDATE jump SET jumpurl = #{jumpurl},shield = #{shield} WHERE (uuid = #{uuid});")
	void updataShieldByUuid(Jump jump);
	
	@Delete("DELETE FROM jump WHERE uuid = #{uuid} limit 1;")
	void delectByUuid(String uuid);

}
