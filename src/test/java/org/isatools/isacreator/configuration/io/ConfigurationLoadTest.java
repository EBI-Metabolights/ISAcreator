package org.isatools.isacreator.configuration.io;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class ConfigurationLoadTest {

    private static final Logger logger = LoggerFactory.getLogger(ConfigurationLoadTest.class);
    @Test
    public void configurationTestLoad() {
        logger.debug("_____TESTING configurationTestLoad()");

        File configurationDirectory = new File("Configurations");
        if(configurationDirectory.exists() && configurationDirectory.isDirectory()) {

            File[] configurationFiles = configurationDirectory.listFiles();

            assert configurationFiles != null;
            for(File file : configurationFiles) {

                if(!file.isHidden() && !file.getName().startsWith(".")) {

                    ConfigXMLParser parser = new ConfigXMLParser(ConfigurationLoadingSource.ISACREATOR, file.getAbsolutePath());

                    logger.debug("___loading configuration " + file.getName().toLowerCase());
                    parser.loadConfiguration();

                    assertTrue("Oh, the configuration size is 0!", parser.getTables().size() > 0);

                    logger.debug("Configuration " + file.getName().toLowerCase() +  " loaded successfully");
                }
            }
        }
    }

}
