package cn.com.bsfit.frms.vue.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.com.bsfit.frms.vue.mapper.UsersMapper;

@RestController
@RequestMapping("/api/login")
public class LoginController {

	@Autowired
	private UsersMapper usersMapper;

	@RequestMapping(value = "/login")
	public ResponseEntity<?> login(final @RequestBody Map<String, String> reqMap) {

		String username = reqMap.get("username");
		String password = reqMap.get("password");

		Map<String, Object> dataMap = buildSuccessMap();

		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			dataMap.put("success", false);
			dataMap.put("msg", "用户名/密码不能为空!");
		}
		List<Map<String, Object>> users = usersMapper.findUsersByNameAndPassword(username, password);
		if (users == null || users.isEmpty()) {
			dataMap.put("success", false);
			dataMap.put("msg", "用户名/密码不正确!");
		}

		for (Map<String, Object> map : users) {
			Object createTime = map.get("createTime");
			Object expDate = map.get("expDate");

			if (isExpDate((Date) createTime, (Integer) expDate)) {
				dataMap.put("success", false);
				dataMap.put("msg", "当前用户已经过期,请联系管理员进行修改!");
			}
		}
		return new ResponseEntity<>(dataMap, HttpStatus.OK);
	}

	private boolean isExpDate(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, 1 * days);

		return new Date().getTime() >= cal.getTime().getTime();
	}

	private Map<String, Object> buildSuccessMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("success", true);
		map.put("msg", "登录成功!");
		return map;
	}
}
