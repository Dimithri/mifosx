package org.mifosplatform.infrastructure.dataimport.domain.populator;

import java.io.IOException;

public interface WorkbookPopulatorFactoryService {

    public WorkbookPopulator createWorkbookPopulator(String parameter, String template) throws IOException;
}
