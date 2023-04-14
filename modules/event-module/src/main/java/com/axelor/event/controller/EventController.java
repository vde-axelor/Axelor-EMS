package com.axelor.event.controller;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import javax.inject.Inject;

import org.checkerframework.checker.index.qual.LengthOf;

import com.axelor.ems.db.Discount;
import com.axelor.ems.db.Event;
import com.axelor.ems.db.EventRegistration;
import com.axelor.event.serviceimpl.EventServiceImpl;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class EventController {
@Inject EventServiceImpl es;
	
	public void totalGuest(ActionRequest request, ActionResponse response) {

	Event event =  request.getContext().asType(Event.class);
	event = es.countEntry(event);
	response.setValue("totalentry", event.getTotalentry());
	}
	
	public void totalAmount(ActionRequest request, ActionResponse response) {
		Event event =  request.getContext().asType(Event.class);
		event = es.totalAmount(event);
		response.setValue("amountcollect", event.getAmountcollect());
	}
	
	public void discountAmount(ActionRequest request, ActionResponse response) {
		Discount discount =  request.getContext().asType(Discount.class);
		Event e=request.getContext().getParentContext().asType(Event.class);
		discount = es.totalDiscoint(discount, e);
		response.setValue("discount_amount", discount.getDiscount_amount());
	}
	
	
	public void totalDiscountAmount(ActionRequest request, ActionResponse response) {
		Event event =  request.getContext().asType(Event.class);
		event = es.totalDiscountAmount(event);
		response.setValue("totaldisc", event.getTotaldisc());
	}
	
	public void checkdate(ActionRequest request, ActionResponse response) {
		Event event =  request.getContext().asType(Event.class);
		if((event.getStartdate().compareTo(event.getEnddate())>0) || (event.getRegopen().compareTo(event.getRegclose())>0) )
		{
			response.setAlert("Enter Proper Date");
		}
		else {
			response.setFlash("Enter Date is Currect");
		}
	}
	
	public void checkcapacity(ActionRequest request, ActionResponse response) {
		Event event =  request.getContext().asType(Event.class);
        event = es.countEntry(event);
        boolean isSame = event.getCapacity().equals(event);

        if(isSame) {
            response.setNotify("Perfect..");
        }
        else {
        	response.setAlert("Capacity Exceed..");
        }
		
	}
	
public void checkDateEr(ActionRequest request, ActionResponse response) {
        
        //EventRegistration eventRegistration = request.getContext().asType(EventRegistration.class);
        
        EventRegistration er =  request.getContext().getParentContext().asType(EventRegistration.class);

        System.err.println(er);

        
        
        
        //EventRegistration er =  request.getContext().getParentContext().asType(EventRegistration.class);
        //Event event =request.getContext().asType(Event.class);

        
        //System.err.println(er.getRegdate());
        //System.err.println(event);
        
//        LocalDateTime dateTime = er.getRegdate();
//        
//
//        LocalDate date = convert(dateTime);
//        LocalDate regD = event.getRegopen();
//        LocalDate colD = event.getRegclose();
//        if (date.isAfter(regD) && date.isBefore(colD)) {
//            System.out.println("Datetime is between start and end");
//        } else {
//           response.setError("Date is closed");
//        }
        

    }

}
