package com.ai.st.microservice.supplies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.supplies.business.SupplyClassBusiness;
import com.ai.st.microservice.supplies.entities.SupplyClassEntity;
import com.ai.st.microservice.supplies.services.ISupplyClassService;

@Component
public class StMicroserviceSuppliesApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(StMicroserviceSuppliesApplicationStartup.class);

	@Autowired
	private ISupplyClassService supplyClassService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("ST - Loading Domains ... ");
		this.initClasses();
	}

	public void initClasses() {
		Long countClasses = supplyClassService.getCount();
		if (countClasses == 0) {

			try {

				SupplyClassEntity classOperator = new SupplyClassEntity();
				classOperator.setId(SupplyClassBusiness.SUPPLY_CLASS_OPERATOR);
				classOperator.setName("INSUMO PARA OPERADOR");
				supplyClassService.createSupplyClass(classOperator);

				SupplyClassEntity classManager = new SupplyClassEntity();
				classManager.setId(SupplyClassBusiness.SUPPLY_CLASS_MANAGER);
				classManager.setName("INSUMO PARA GESTOR");
				supplyClassService.createSupplyClass(classManager);

				log.info("The domains 'classes' have been loaded!");
			} catch (Exception e) {
				log.error("Failed to load 'classes' domains");
			}

		}

	}

}
