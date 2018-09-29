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

	@Select("select * from BACK_TESTING_TRADE where TESTING_ID = #{testingId} and BUY_TIME >= #{begin} and BUY_TIME < #{end} and SEC_CODE like #{secCode} and SEC_NAME like #{secName}")
	List<Map<String, Object>> findAll(@Param("testingId") Long testingId, @Param("begin") Date begin, @Param("end") Date end, @Param("secCode") String secCode, @Param("secName") String secName);

	@Select("SELECT MAX(ID) FROM BACK_TESTING WHERE STRATEGY_ID = 83 AND STATUS = 3")
	Long findTestingId();

	@Delete("DELETE FROM BACK_TESTING_TRADE_HIGH_PRICE WHERE id =#{id}")
	void delete(final Long id);

	@Select("select return_rate / 100.00 return_rate,return_rate_bm / 100.00 return_rate_bm,TO_CHAR(buy_time, 'YYYY-MM-DD') BUY_TIME from back_testing_result_census where testing_id=#{testingId} order by buy_time asc")
	List<Map<String, Object>> findGfline(@Param("testingId") long testingId);
	
	@Select("select a.*　from (select a.*,row_number()over(order by buy_time desc) rn  from back_testing_result_census a where a.testing_id=#{testingId}) a where rn=1")
	List<Map<String, Object>> findGfTab(@Param("testingId") long testingId);
}
