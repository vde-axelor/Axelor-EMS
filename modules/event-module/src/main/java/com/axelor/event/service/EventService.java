package com.axelor.event.service;

import com.axelor.ems.db.Discount;
import com.axelor.ems.db.Event;

public interface EventService {
	public Event countEntry(Event e);
	public Event totalAmount(Event e);
	public Discount totalDiscoint(Discount d, Event e);
	 public Event totalDiscountAmount(Event e);
	 public Event datecheck(Event e);
	 public Event checkcapacity(Event e);
}
