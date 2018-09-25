package cn.com.bsfit.frms.vue.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.com.bsfit.frms.vue.mapper.BackTestMapper;

@RestController
@RequestMapping("/api/backTest")
public class BackTestController {

	@Autowired
	private BackTestMapper backTestMapper;

	@RequestMapping(value = "/getBackTestAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBackTestAll(final @RequestParam(value = "begin", required = false) String begin,
			final @RequestParam(value = "end", required = false) String end,
			final @RequestParam(value = "secCode", required = false) String secCode,
			final @RequestParam(value = "secName", required = false) String secName,
			final @RequestParam(value = "page", required = false, defaultValue = "1") int page,
			final @RequestParam(value = "limit", required = false, defaultValue = "5") int limit)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 开始时间
		Date beginDate = begin == null || "".equals(begin.trim()) ? getStartTime(0) : sdf.parse(begin);
		// 结束时间
		Date endDate = end == null || "".equals(end.trim()) ? getnowEndTime(0) : sdf.parse(end);

		System.out.println("page:" + page + " limit:" + limit);

		String secCodeStr = secCode == null || "".equals(secCode.trim()) ? "%" : secCode.trim() + "%";
		String secNameStr = secName == null || "".equals(secName.trim()) ? "%" : secName.trim() + "%";

		PageHelper.startPage(page, limit);
		List<Map<String, Object>> appList = backTestMapper.findAll(beginDate, endDate, secCodeStr, secNameStr);
		PageInfo<Map<String, Object>> appsPageInfo = new PageInfo<>(appList);

		return new ResponseEntity<PageInfo<Map<String, Object>>>(appsPageInfo, HttpStatus.OK);
	}

	@RequestMapping(value = "/getBackTestAllList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBackTestAllList(final @RequestParam(value = "begin", required = false) String begin,
			final @RequestParam(value = "end", required = false) String end,
			final @RequestParam(value = "secCode", required = false) String secCode,
			final @RequestParam(value = "secName", required = false) String secName) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 开始时间
		Date beginDate = begin == null || "".equals(begin.trim()) ? getStartTime(0) : sdf.parse(begin);
		// 结束时间
		Date endDate = end == null || "".equals(end.trim()) ? getnowEndTime(0) : sdf.parse(end);

		String secCodeStr = secCode == null || "".equals(secCode.trim()) ? "%" : secCode.trim() + "%";
		String secNameStr = secName == null || "".equals(secName.trim()) ? "%" : secName.trim() + "%";

		List<Map<String, Object>> appList = backTestMapper.findAll(beginDate, endDate, secCodeStr, secNameStr);

		return new ResponseEntity<List<Map<String, Object>>>(appList, HttpStatus.OK);
	}

	@RequestMapping(value = "/getGfAllList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getGfAllList(final @RequestParam(value = "testingId", required = false, defaultValue = "530") long testingId) {
		List<Map<String, Object>> res = backTestMapper.findGfline(testingId);
		List<Map<String, Object>> last = backTestMapper.findGfTab(testingId);

		Map<String, Object> data = new HashMap<>();
		List<Object> buyTimes = new ArrayList<>();
		List<Object> returnRates = new ArrayList<>();
		List<Object> returnRateBms = new ArrayList<>();
		for(Map<String, Object> map : res) {
			for(Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if("BUY_TIME".equals(key)) {
					buyTimes.add(value);
				}
				if("RETURN_RATE".equals(key)) {
					returnRates.add(value);
				}
				if("RETURN_RATE_BM".equals(key)) {
					returnRateBms.add(value);
				}
			}
		}
		
		data.put("list", res);
		data.put("buyTimes", buyTimes);
		data.put("returnRates", returnRates);
		data.put("returnRateBms", returnRateBms);
		data.put("table", last);
		return new ResponseEntity<Map<String, Object>>(data, HttpStatus.OK);
	}

	/**
	 * 获取n天前的开始时间
	 * 
	 * @param days
	 * @return
	 */
	private static Date getStartTime(int days) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);

		cal.add(Calendar.DAY_OF_MONTH, -1 * days);
		return cal.getTime();
	}

	/**
	 * 获取n天前的结束时间
	 *
	 * @param days
	 * @return
	 */
	public static Date getnowEndTime(int days) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);

		cal.add(Calendar.DAY_OF_MONTH, -1 * days);
		return cal.getTime();
	}
}
