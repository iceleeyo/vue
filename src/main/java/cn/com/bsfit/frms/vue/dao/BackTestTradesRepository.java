package cn.com.bsfit.frms.vue.dao;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cn.com.bsfit.frms.vue.entities.BackTestTrade;

@Repository("backTestTradesRepository")
public interface BackTestTradesRepository extends JpaRepository<BackTestTrade, Long> {

	@Override
	Page<BackTestTrade> findAll(Pageable pageable);

//	Page<BackTestTrade> findByDate(Date begin, Date end, String secCode, String secName, Pageable pageable);
	
	Page<BackTestTrade> findBySecCode(String secCode, Pageable pageable);
	
	Page<BackTestTrade> findBySecName(String secName, Pageable pageable);
}
