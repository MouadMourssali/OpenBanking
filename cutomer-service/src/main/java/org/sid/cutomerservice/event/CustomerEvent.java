package org.sid.cutomerservice.event;

import org.sid.cutomerservice.dto.CustomerDto;
import org.springframework.context.ApplicationEvent;

public class CustomerEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1L;

    private String eventType;
    private CustomerDto customerDto;

    public CustomerEvent(Object source, String eventType, CustomerDto customerDto) {
        super(source);
        this.eventType = eventType;
        this.customerDto = customerDto;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public CustomerDto getCustomer() {
        return customerDto;
    }

    public void setAccount(CustomerDto customerDto) {
        this.customerDto = customerDto;
    }

    @Override
    public String toString() {
        return "CustomerEvent [eventType=" + eventType + ", customer=" + customerDto + "]";
    }

}
