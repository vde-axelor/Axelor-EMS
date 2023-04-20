package com.axelor.event.service;

import java.time.LocalDate;

import com.axelor.ems.db.Discount;
import com.axelor.ems.db.Event;

public interface EventService {
	public Event countEntry(Event e);
	public Event totalAmount(Event e);
	public Discount totalDiscount(Discount d, Event e);
	LocalDate checkDate(Discount d, Event e);
}
