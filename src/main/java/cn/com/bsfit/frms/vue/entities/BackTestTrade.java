package cn.com.bsfit.frms.vue.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "back_testing_trade")
public class BackTestTrade implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "TESTING_ID")
	private int testingId;
	@Column(name = "MARKET_ID")
	private String marketId;
	@Column(name = "SEC_CODE")
	private String secCode;
	@Column(name = "SEC_NAME")
	private Date secName;
	@Column(name = "HOLD_DAYS")
	private int holdDays;
	@Column(name = "BUY_TIME")
	private Date buyTime;
	@Column(name = "BUY_PRICE")
	private long buyPrice;
	@Column(name = "SELL_TIME")
	private Date sellTime;
	@Column(name = "SELL_PRICE")
	private long sellPrice;
	@Column(name = "RETURN_RATE")
	private long returnRate;
	@Column(name = "BENCHMARK")
	private String benchmark;
	@Column(name = "BUY_PRICE_BM")
	private long buyPriceBm;
	@Column(name = "SELL_PRICE_BM")
	private long sellPriceBm;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTestingId() {
		return testingId;
	}
	public void setTestingId(int testingId) {
		this.testingId = testingId;
	}
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public String getSecCode() {
		return secCode;
	}
	public void setSecCode(String secCode) {
		this.secCode = secCode;
	}
	public Date getSecName() {
		return secName;
	}
	public void setSecName(Date secName) {
		this.secName = secName;
	}
	public int getHoldDays() {
		return holdDays;
	}
	public void setHoldDays(int holdDays) {
		this.holdDays = holdDays;
	}
	public Date getBuyTime() {
		return buyTime;
	}
	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}
	public long getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(long buyPrice) {
		this.buyPrice = buyPrice;
	}
	public Date getSellTime() {
		return sellTime;
	}
	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}
	public long getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(long sellPrice) {
		this.sellPrice = sellPrice;
	}
	public long getReturnRate() {
		return returnRate;
	}
	public void setReturnRate(long returnRate) {
		this.returnRate = returnRate;
	}
	public String getBenchmark() {
		return benchmark;
	}
	public void setBenchmark(String benchmark) {
		this.benchmark = benchmark;
	}
	public long getBuyPriceBm() {
		return buyPriceBm;
	}
	public void setBuyPriceBm(long buyPriceBm) {
		this.buyPriceBm = buyPriceBm;
	}
	public long getSellPriceBm() {
		return sellPriceBm;
	}
	public void setSellPriceBm(long sellPriceBm) {
		this.sellPriceBm = sellPriceBm;
	}
}
