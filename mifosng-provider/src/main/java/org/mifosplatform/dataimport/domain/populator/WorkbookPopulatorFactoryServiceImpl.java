package org.mifosplatform.dataimport.domain.populator;

import java.io.IOException;

import org.mifosplatform.dataimport.domain.populator.client.ClientWorkbookPopulator;
import org.mifosplatform.organisation.office.service.OfficeReadPlatformService;
import org.mifosplatform.organisation.staff.service.StaffReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkbookPopulatorFactoryServiceImpl implements
		WorkbookPopulatorFactoryService {

	private final OfficeReadPlatformService officeReadPlatformService;
	private final StaffReadPlatformService staffReadPlatformService;

	@Autowired
	public WorkbookPopulatorFactoryServiceImpl(
			final OfficeReadPlatformService officeReadPlatformService,
			final StaffReadPlatformService staffReadPlatformService) {

		this.officeReadPlatformService = officeReadPlatformService;
		this.staffReadPlatformService = staffReadPlatformService;
	}

	@Override
	public WorkbookPopulator createWorkbookPopulator(String parameter,
			String template) throws IOException {
		// MifosRestClient restClient = new MifosRestClient();

		if (template.trim().equals("client"))

			return new ClientWorkbookPopulator(
					parameter,
					new OfficeSheetPopulator(officeReadPlatformService),
					new PersonnelSheetPopulator(Boolean.FALSE,
							officeReadPlatformService, staffReadPlatformService));

		throw new IllegalArgumentException("Can't find populator.");
	}

}
