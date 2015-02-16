package org.isatools.isacreator.model;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by the ISATeam.
 * User: agbeltran
 * Date: 02/11/2012
 * Time: 12:11
 *
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class ProtocolTest {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolTest.class);

    @Before
    public void setUp() {

    }

    @Test
    public void test(){
         Protocol protocol = new Protocol("Extraction",
                 "Extraction",
                 "",
                 "",
                 "Extraction description",
                 "",
                 "",
                 "Post Extraction;Derivatization;",
                 ";",
                 "",
                 "",
                 "",
                 "",
                 ""
                 );

        String[] parameterNames = protocol.getProtocolParameterNames();
        for(String parameterName:parameterNames){
            logger.debug(parameterName);
        }

        assert(protocol.getProtocolParameterNames().length==2);
    }
}
