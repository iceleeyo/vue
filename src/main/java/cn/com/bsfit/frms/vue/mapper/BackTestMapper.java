package cn.com.bsfit.frms.vue.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BackTestMapper {

	@Select("select * from back_testing_trade where BUY_TIME >= #{begin} and BUY_TIME < #{end} and SEC_CODE like #{secCode} and SEC_NAME like #{secName}")
	List<Map<String, Object>> findAll(@Param("begin") Date begin, @Param("end") Date end, @Param("secCode") String secCode, @Param("secName") String secName);

	@Delete("DELETE FROM back_testing_trade WHERE id =#{id}")
	void delete(final Long id);
}
