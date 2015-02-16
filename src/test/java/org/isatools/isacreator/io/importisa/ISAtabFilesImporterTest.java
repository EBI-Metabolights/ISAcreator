package org.isatools.isacreator.io.importisa;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.isatools.errorreporter.model.ErrorMessage;
import org.isatools.errorreporter.model.ISAFileErrorReport;
import org.isatools.isacreator.io.CommonTestIO;
import org.isatools.isacreator.model.Investigation;
import org.isatools.isacreator.ontologymanager.OntologyManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertTrue;

/**
 * Test class for ISAtabFilesImporter
 * <p/>
 * It assumes that package.sh was run before running this test, as this script downloads the configuration files into ISAcreator/Configurations (Note: wget must be installed for this to work).
 * <p/>
 * Created by ISA Team
 * <p/>
 * Date: 04/07/2012
 * Time: 05:54
 *
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class ISAtabFilesImporterTest implements CommonTestIO {
    private static final Logger logger = LoggerFactory.getLogger(ISAtabFilesImporterTest.class);

    private String configDir = null;
    private static Logger log = LoggerFactory.getLogger(ISAtabFilesImporterTest.class);


    private ISAtabFilesImporter importer = null;
    private String isatabParentDir = null;

    @Before
    public void setUp() {
        String baseDir = System.getProperty("basedir");

        if (baseDir == null) {
            try {
                baseDir = new File(".").getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        configDir = baseDir + DEFAULT_CONFIG_DIR;

        log.debug("configDir=" + configDir);
        importer = new ISAtabFilesImporter(configDir);
        isatabParentDir = baseDir + "/src/test/resources/test-data/BII-I-1";
        log.debug("isatabParentDir=" + isatabParentDir);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void importFileTest() {
        importer.importFile(isatabParentDir);
        Investigation inv = importer.getInvestigation();

        assert (inv != null);

        for (ISAFileErrorReport report : importer.getMessages()) {
            logger.debug(report.getFileName());
            for (ErrorMessage message : report.getMessages()) {
                logger.debug(message.getErrorLevel().toString() + " > " + message.getMessage());
            }
        }

        //if import worked ok, there should not be error messages
        assert (importer.getMessages().size() == 0);

        logger.debug("ontologies used=" + OntologyManager.getOntologiesUsed());
        logger.debug("ontology description=" + OntologyManager.getOntologyDescription("OBI"));
        //logger.debug("ontology selection history=" + OntologyManager.getOntologySelectionHistory());
        logger.debug("ontology selection history size=" + OntologyManager.getOntologyTermsSize());
        logger.debug("ontology term=" + OntologyManager.getOntologyTerm("OBI:metabolite profiling"));

        assertTrue("Oh no, I didnt' get the expected number of studies :(", inv.getStudies().size() == 2);

    }


}
