package cn.com.bsfit.frms.vue.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UsersMapper {

	@Results({ @Result(property = "createTime", column = "CREATE_TIME", javaType = Date.class), @Result(property = "expDate", column = "EXP_DATE", javaType = Integer.class) })
	@Select("select * from USERS_TAB where USERNAME = #{username} and PASSWORD = #{password}")
	List<Map<String, Object>> findUsersByNameAndPassword(@Param("username") String username, @Param("password") String password);

	@Select("select * from USERS_TAB where USERNAME like #{username}")
	List<Map<String, Object>> findAll(@Param("username") String username);
}
