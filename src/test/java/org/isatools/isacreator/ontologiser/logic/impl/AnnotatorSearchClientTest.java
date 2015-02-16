package org.isatools.isacreator.ontologiser.logic.impl;

import org.isatools.isacreator.ontologymanager.bioportal.model.AnnotatorResult;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;


public class AnnotatorSearchClientTest {
    private static final Logger logger = LoggerFactory.getLogger(AnnotatorSearchClientTest.class);
    @Test
    public void testAnnotatorClient() {
         logger.debug("_____Testing NCBO Annotator search client____");
        AnnotatorSearchClient sc = new AnnotatorSearchClient();

        Set<String> testTerms = new HashSet<String>();
        testTerms.add("CY3");
        testTerms.add("DOSE");
        testTerms.add("ASSAY");
        testTerms.add("Ethanol");
        testTerms.add("drug vehicle (90% ethanol/10% tween-20)");

        Map<String, Map<String, AnnotatorResult>> result = sc.searchForTerms(testTerms);

        for (String key : result.keySet()) {
            logger.debug(key + " matched:");
            for (String ontologyId : result.get(key).keySet()) {
                logger.debug("\t" + ontologyId + " -> " + result.get(key).get(ontologyId).getOntologyTerm().getOntologyTermName() + " (" + result.get(key).get(ontologyId).getOntologySource().getOntologyDisplayLabel() + ")");
            }
        }

        assertTrue("No matches found", result.size() > 0);
    }
}
