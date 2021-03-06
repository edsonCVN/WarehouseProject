package ggc.core;

import java.io.Serializable;

public class StatusContext implements Serializable {

	private StatusState _currentStatus;
	private int _currentPoints;
	private static final long serialVersionUID = 209685192006L;

	public StatusContext() {
		_currentStatus = Normal.getNormalInstance();
		_currentPoints = 0;
	}

	public StatusState getCurrentStatus() {
		return _currentStatus;
	}

	public String statusToString() {
		return _currentStatus.getStatus();
	}

	public void setCurrentStatus(StatusState currentStatus) {
		_currentStatus = currentStatus;
	}

	public int getPoints() {
		return _currentPoints;
	}

	public void setPoints(int points) {
		_currentPoints = points;
	}

	public void update() {
		_currentStatus.updateState(this);
	}

	private String getPeriod(Date date, String productType) {
		int num = 0;

		if ("Simple".equals(productType)) {
			num = 5;
		} else {
			num = 3;
		}

		if ((date.getDeadlinePayment() - Date.now().getDate()) >= num) {
			return "P1";
		} else if (date.getDeadlinePayment() - Date.now().getDate() < num
				&& date.getDeadlinePayment() - Date.now().getDate() >= 0) {
			return "P2";
		} else if (Date.now().getDate() - date.getDeadlinePayment() <= num) {
			return "P3";
		} else {
			return "P4";
		}

	}

	private boolean inPaymentPeriod(Date date, String productType) {
		return "P1".equals(getPeriod(date, productType)) || "P2".equals(getPeriod(date, productType));
	}

	public double getValueToBePaid(Date date, Sale sale, String productType) {

		if (sale.getValuePaid() != 0) {
			return sale.getValuePaid();
		}

		if (inPaymentPeriod(date, productType)) {
			return sale.getBaseValue() - (sale.getBaseValue() * getDiscount(date, productType));
		} else if ("ELITE".equals(_currentStatus.getStatus()) && "P3".equals(getPeriod(date, productType))) {
			return sale.getBaseValue() - (sale.getBaseValue() * getDiscount(date, productType));
		} else {
			if (date.getPaymentDate() == 0) {
				date.setPaymentDate(Date.now().getDate());
			}
			return sale.getBaseValue() + (sale.getBaseValue() * getFee(date, productType));
		}
	}

	public double pay(Date date, Sale sale, String productType) {

		if (inPaymentPeriod(date, productType)) {
			double payedPrice = sale.getBaseValue() - (sale.getBaseValue() * getDiscount(date, productType));
			sale.setValuePaid(payedPrice);
			_currentPoints += (10 * payedPrice);
			handlePointChanging();
			return payedPrice;
		} else if ("ELITE".equals(_currentStatus.getStatus()) && "P3".equals(getPeriod(date, productType))) {
			double payedPrice = sale.getBaseValue() - (sale.getBaseValue() * getDiscount(date, productType));
			sale.setValuePaid(payedPrice);
			handleDelay(date);
			return payedPrice;
		} else {

			double payedPrice = sale.getBaseValue() + (sale.getBaseValue() * getFee(date, productType));
			sale.setValuePaid(payedPrice);
			handleDelay(date);
			return payedPrice;
		}
	}

	public double getFee(Date date, String productType) {
		return _currentStatus.applyFee(date, getPeriod(date, productType));
	}

	public double getDiscount(Date date, String productType) {
		return _currentStatus.applyDiscount(date, getPeriod(date, productType));
	}

	private void handleDelay(Date date) {
		int delay = Date.now().getDate() - date.getDeadlinePayment();

		if (delay > 15 && "ELITE".equals(_currentStatus.getStatus())) {
			_currentStatus.depromote(this);
			int points = (int) (0.25 * _currentPoints);
			setPoints(points);
		} else if (delay > 2 && "SELECTION".equals(_currentStatus.getStatus())) {
			_currentStatus.depromote(this);
			int points = (int) (0.10 * _currentPoints);
			setPoints(points);
		} else if ("NORMAL".equals(_currentStatus.getStatus())) {
			setPoints(0);
		}
	}

	private void handlePointChanging() {
		if (_currentPoints > 25000) {
			setCurrentStatus(Elite.getEliteInstance());
		} else if (_currentPoints < 25000 && _currentPoints > 2000) {
			setCurrentStatus(Selection.getSelectionInstance());
		} else {
			setCurrentStatus(Normal.getNormalInstance());
		}
	}

}