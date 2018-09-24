package cn.com.bsfit.frms.vue.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.vue.controller.pagination.BacktestPaginationFormatting;
import cn.com.bsfit.frms.vue.controller.pagination.PaginationMultiTypeValuesHelper;

@RestController
@RequestMapping("/api/backTestTrade")
public class BackTestTradeController {

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Map<String, PaginationMultiTypeValuesHelper> getBackTestTradeAll(
			final @RequestParam(value = "begin", required = false) String begin,
			final @RequestParam(value = "end", required = false) String end,
			final @RequestParam(value = "secCode", required = false) String secCode,
			final @RequestParam(value = "secName", required = false) String secName,
			final @RequestParam(value = "page", required = false) Integer pages,
			final @RequestParam(value = "pageSize", required = false) int pageSize) throws ParseException {
		
		Pageable pageable = new PageRequest(pages == null ? 1 : pages - 1, pageSize,
				new Sort(Direction.DESC, "BUY_TIME"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 开始时间
		Date beginDate = begin == null || "".equals(begin) ? new Date() : sdf.parse(begin);
		// 结束时间
		Date endDate = end == null || "".equals(end) ? new Date() : sdf.parse(end);

		BacktestPaginationFormatting paginInstance = new BacktestPaginationFormatting();
		return paginInstance.filterQuery(beginDate, endDate, secCode, secName, pageable);
	}
}
