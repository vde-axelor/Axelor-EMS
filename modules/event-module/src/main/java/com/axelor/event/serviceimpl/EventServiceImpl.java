package com.axelor.event.serviceimpl;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.axelor.ems.db.Discount;
import com.axelor.ems.db.Event;
import com.axelor.ems.db.EventRegistration;
import com.axelor.event.service.EventService;

public class EventServiceImpl implements EventService{
	@Override
	public Event countEntry(Event e) {
		 
		long totalEntry = e.getEventregistration().stream().count();
		int  te =(int)totalEntry; 
		e.setTotalentry(te);
		return e;
	}

	@Override
	public Event totalAmount(Event e) {
		BigDecimal totalAmount = BigDecimal.ZERO;
		totalAmount = e.getEventregistration().stream().map(EventRegistration::getAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
		e.setAmountcollect(totalAmount);
		return e;
	}

	@Override
	public Discount totalDiscount(Discount d, Event e) {
		BigDecimal fee = e.getEventfee();
		BigDecimal amount = d.getDiscount_percent().multiply(fee).divide(new BigDecimal(100));
		d.setDiscount_amount(amount);
		return d;
		
	}

	@Override
	public LocalDate checkDate(Discount d, Event e) {
		LocalDate date = e.getRegclose();
		int days = d.getBefore_days();
		LocalDate newDate = date.minusDays(days);
		return newDate;
	}
	
}
