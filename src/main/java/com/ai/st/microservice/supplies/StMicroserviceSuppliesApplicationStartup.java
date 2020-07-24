package com.ai.st.microservice.supplies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.ai.st.microservice.supplies.business.SupplyAttachmentTypeBusiness;
import com.ai.st.microservice.supplies.business.SupplyClassBusiness;
import com.ai.st.microservice.supplies.business.SupplyStateBusiness;
import com.ai.st.microservice.supplies.entities.SupplyAttachmentTypeEntity;
import com.ai.st.microservice.supplies.entities.SupplyClassEntity;
import com.ai.st.microservice.supplies.entities.SupplyStateEntity;
import com.ai.st.microservice.supplies.services.ISupplyAttachmentTypeService;
import com.ai.st.microservice.supplies.services.ISupplyClassService;
import com.ai.st.microservice.supplies.services.ISupplyStateService;

@Component
public class StMicroserviceSuppliesApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

	private static final Logger log = LoggerFactory.getLogger(StMicroserviceSuppliesApplicationStartup.class);

	@Autowired
	private ISupplyClassService supplyClassService;

	@Autowired
	private ISupplyStateService supplyStateService;

	@Autowired
	private ISupplyAttachmentTypeService attachmentTypeService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		log.info("ST - Loading Domains ... ");
		this.initClasses();
		this.initStates();
		this.initAttachmentsTypes();
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

	public void initStates() {
		Long countStates = supplyStateService.getCount();
		if (countStates == 0) {

			try {

				SupplyStateEntity stateActive = new SupplyStateEntity();
				stateActive.setId(SupplyStateBusiness.SUPPLY_STATE_ACTIVE);
				stateActive.setName("ACTIVO");
				supplyStateService.createSupplyState(stateActive);

				SupplyStateEntity stateInactive = new SupplyStateEntity();
				stateInactive.setId(SupplyStateBusiness.SUPPLY_STATE_INACTIVE);
				stateInactive.setName("INACTIVO");
				supplyStateService.createSupplyState(stateInactive);

				SupplyStateEntity stateRemoved = new SupplyStateEntity();
				stateRemoved.setId(SupplyStateBusiness.SUPPLY_STATE_REMOVED);
				stateRemoved.setName("ELIMINADO");
				supplyStateService.createSupplyState(stateRemoved);

				log.info("The domains 'states' have been loaded!");
			} catch (Exception e) {
				log.error("Failed to load 'states' domains");
			}

		}

	}

	public void initAttachmentsTypes() {
		Long countTypes = attachmentTypeService.getCount();
		if (countTypes == 0) {

			try {

				SupplyAttachmentTypeEntity typeSupply = new SupplyAttachmentTypeEntity();
				typeSupply.setId(SupplyAttachmentTypeBusiness.SUPPLY_ATTACHMENT_TYPE_SUPPLY);
				typeSupply.setName("INSUMO");
				attachmentTypeService.createAttachmentType(typeSupply);

				SupplyAttachmentTypeEntity typeFTP = new SupplyAttachmentTypeEntity();
				typeFTP.setId(SupplyAttachmentTypeBusiness.SUPPLY_ATTACHMENT_TYPE_FTP);
				typeFTP.setName("FTP");
				attachmentTypeService.createAttachmentType(typeFTP);

				log.info("The domains 'attachments types' have been loaded!");
			} catch (Exception e) {
				log.error("Failed to load 'attachments types' domains");
			}

		}

	}

}
