package cn.com.bsfit.frms.vue.controller.pagination;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.com.bsfit.frms.vue.dao.BackTestTradesRepository;
import cn.com.bsfit.frms.vue.entities.BackTestTrade;
import cn.com.bsfit.frms.vue.type.Types;

public class BacktestPaginationFormatting {

	private PaginationMultiTypeValuesHelper multiValue = new PaginationMultiTypeValuesHelper();
	private Map<String, PaginationMultiTypeValuesHelper> results = new HashMap<>();
	private final BackTestTradesRepository instance = SpringUtil.getBean(BackTestTradesRepository.class);

	public Map<String, PaginationMultiTypeValuesHelper> filterQuery(final Date begin, final Date end,
			final String secCode, final String secName, final Pageable pageable) {

		Types<BackTestTrade> typeInstance = new Types<BackTestTrade>() {
			@Override
			public Page<BackTestTrade> query() {
				return instance.findAll(pageable);
			}
		};

		this.multiValue.setCount(typeInstance.getCount());

		this.multiValue.setPage(typeInstance.getPageNumber() + 1);

		this.multiValue.setResults(typeInstance.getContent());

		this.multiValue.setTotal(typeInstance.getTotal());

		this.results.put("data", this.multiValue);

		return results;
	}
}