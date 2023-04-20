package com.axelor.event.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;
import com.axelor.ems.db.Discount;
import com.axelor.ems.db.Event;
import com.axelor.ems.db.EventRegistration;
import com.axelor.evemt.translation.Translation;
import com.axelor.event.serviceimpl.EventServiceImpl;
import com.axelor.i18n.I18n;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class EventController {
	@Inject
	EventServiceImpl es;

	public void totalGuest(ActionRequest request, ActionResponse response) {

		Event event = request.getContext().asType(Event.class);
		event = es.countEntry(event);
		response.setValue("totalentry", event.getTotalentry());
	}

	public void totalAmount(ActionRequest request, ActionResponse response) {
		Event event = request.getContext().asType(Event.class);
		event = es.totalAmount(event);
		response.setValue("amountcollect", event.getAmountcollect());
	}

	public void discountAmount(ActionRequest request, ActionResponse response) {
		Discount discount = request.getContext().asType(Discount.class);
		Event e = request.getContext().getParent().asType(Event.class);
		discount = es.totalDiscount(discount, e);
		response.setValue("discount_amount", discount.getDiscount_amount());
	}

	public void totalDiscountAmount(ActionRequest request, ActionResponse response) {
		Event event = request.getContext().asType(Event.class);
		BigDecimal totalAmount = event.getEventfee().multiply(new BigDecimal(event.getTotalentry()));
		BigDecimal amountcollect = event.getAmountcollect();
		BigDecimal discount = totalAmount.subtract(amountcollect);
		response.setValue("totaldisc", discount);
	}

	public void capicity(ActionRequest request, ActionResponse response) {
		Event event = request.getContext().asType(Event.class);
		event = es.countEntry(event);
		if (!event.getTotalentry().equals(event.getCapacity())) {
			response.setFlash(I18n.get(Translation.EVENT_TRANSLATION));
		}
	}

	public static LocalDate convert(LocalDateTime dateTime) {
		return dateTime.toLocalDate();
	}
	
	

	public void checkDate(ActionRequest request, ActionResponse response) {

		EventRegistration er = request.getContext().asType(EventRegistration.class);
		Event e = request.getContext().getParent().asType(Event.class);
		
		LocalDate date = convert(er.getRegdate());
		LocalDate regD = e.getRegopen();
		LocalDate colD = e.getRegclose();
		
			if (!(date.isAfter(regD) && date.isBefore(colD))) {
				response.setFlash("Date is closed");
				return;
			}
		 
		long daysBetween = ChronoUnit.DAYS.between(date, colD);

		List<Discount> discount = e.getDiscount();
		discount.forEach(i-> {
			if(daysBetween >= i.getBefore_days()) {
				BigDecimal discountAmount = i.getDiscount_amount();
				response.setValue("amount", e.getEventfee().subtract(discountAmount));
			}
		});

	}

	  public void checkREDate(ActionRequest request, ActionResponse response) {
		  Event e=request.getContext().asType(Event.class);
		  LocalDate date=convert(e.getStartdate());
		  if(e.getRegclose().isBefore(date)) {
			  response.setFlash("Currect");
		  }
		  else {
			  response.setFlash("Closing date must be before the event start date");
		  }
	  }
	
}
