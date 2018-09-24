package cn.com.bsfit.frms.vue.type;

import org.springframework.data.domain.Page;

public abstract class Types<T> {

	public abstract Page<T> query();

	public Integer getCount() {
		return this.query().getSize();
	};

	public Integer getPageNumber() {
		return this.query().getNumber();
	};

	public Long getTotal() {
		return this.query().getTotalElements();
	};
	
	public Object getContent() {
		return this.query().getContent();
	};
}
