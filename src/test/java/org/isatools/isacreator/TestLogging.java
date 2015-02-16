package org.isatools.isacreator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: conesa
 * Date: 12/02/15
 * Time: 09:22
 */
public class TestLogging {

	private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);

	@Test
	public void simpleLogLine(){
		logger.info("hello!, 1,2,3, testing. Check if this line is shown in the right format. Info line");
		logger.debug("This is a debug line");
	}
}