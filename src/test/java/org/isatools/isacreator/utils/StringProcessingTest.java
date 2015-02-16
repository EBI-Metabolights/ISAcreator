package org.isatools.isacreator.utils;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static junit.framework.Assert.assertEquals;

/**
 * Created with IntelliJ IDEA.
 * User: eamonnmaguire
 * Date: 20/01/2014
 * Time: 13:41
 * To change this template use File | Settings | File Templates.
 */
public class StringProcessingTest {
    private static final Logger logger = LoggerFactory.getLogger(StringProcessingTest.class);

    @Test
    public void testSpaceRemovalInQualifier() {
        String comment = "Comment [Technical Validation]";
        comment = StringProcessing.removeSpaceFromQualifiedField(comment);
        logger.debug(comment);
        assertEquals("Comment should have no spaces...", comment, "Comment[Technical Validation]");
    }
}
