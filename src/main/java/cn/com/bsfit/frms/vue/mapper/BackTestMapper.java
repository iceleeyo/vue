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
	
	
	@Select("select return_rate / 100.00 return_rate,return_rate_bm / 100.00 return_rate_bm,TO_CHAR(buy_time, 'YYYY-MM-DD') BUY_TIME from back_testing_result_census where testing_id=#{testingId} order by buy_time asc")
	List<Map<String, Object>> findGfline(@Param("testingId") long testingId);
	
	@Select("select a.*　from (select a.*,row_number()over(order by buy_time desc) rn  from back_testing_result_census a where a.testing_id=#{testingId}) a where rn=1")
	List<Map<String, Object>> findGfTab(@Param("testingId") long testingId);
}
