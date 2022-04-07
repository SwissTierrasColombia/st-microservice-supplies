package com.ai.st.microservice.supplies.business;

import org.springframework.stereotype.Component;

@Component
public class SupplyStateBusiness {

    public static final Long SUPPLY_STATE_ACTIVE = (long) 1;
    public static final Long SUPPLY_STATE_INACTIVE = (long) 2;
    public static final Long SUPPLY_STATE_REMOVED = (long) 3;

}
