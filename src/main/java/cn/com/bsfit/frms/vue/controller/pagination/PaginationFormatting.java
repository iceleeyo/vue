package cn.com.bsfit.frms.vue.controller.pagination;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import cn.com.bsfit.frms.vue.dao.PersonsRepository;
import cn.com.bsfit.frms.vue.entities.Persons;
import cn.com.bsfit.frms.vue.type.Types;

@Component
class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtil.applicationContext == null) {
			SpringUtil.applicationContext = applicationContext;
		}
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
}

abstract class BasePaginationInfo extends Types<Persons> {

	public Pageable pageable;

	public PersonsRepository instance = SpringUtil.getBean(PersonsRepository.class);

	public String sex, email;

	public BasePaginationInfo(String sexName, String emailName, Pageable pageable) {

		this.pageable = pageable;

		this.sex = sexName;

		this.email = emailName;
	}
}

class AllType extends BasePaginationInfo {

	public AllType(String sexName, String emailName, Pageable pageable) {
		super(sexName, emailName, pageable);
	}

	@Override
	public Page<Persons> query() {
		return this.instance.findAll(this.pageable);
	}
}

class SexEmailType extends BasePaginationInfo {

	public SexEmailType(String sexName, String emailName, Pageable pageable) {
		super(sexName, emailName, pageable);
	}

	@Override
	public Page<Persons> query() {
		return this.instance.findBySexAndEmailContains(this.sex, this.email, this.pageable);
	}
}

class SexType extends BasePaginationInfo {

	public SexType(String sexName, String emailName, Pageable pageable) {
		super(sexName, emailName, pageable);
	}

	@Override
	public Page<Persons> query() {
		return this.instance.findBySex(this.sex, this.pageable);
	}
}

public class PaginationFormatting {

	private PaginationMultiTypeValuesHelper multiValue = new PaginationMultiTypeValuesHelper();

	private Map<String, PaginationMultiTypeValuesHelper> results = new HashMap<>();

	public Map<String, PaginationMultiTypeValuesHelper> filterQuery(String sex, String email, Pageable pageable) {

		Types<Persons> typeInstance;

		if (sex.length() == 0 && email.length() == 0) {

			typeInstance = new AllType(sex, email, pageable);

		} else if (sex.length() > 0 && email.length() > 0) {

			typeInstance = new SexEmailType(sex, email, pageable);

		} else {
			typeInstance = new SexType(sex, email, pageable);
		}

		this.multiValue.setCount(typeInstance.getCount());

		this.multiValue.setPage(typeInstance.getPageNumber() + 1);

		this.multiValue.setResults(typeInstance.getContent());

		this.multiValue.setTotal(typeInstance.getTotal());

		this.results.put("data", this.multiValue);

		return results;
	}

}