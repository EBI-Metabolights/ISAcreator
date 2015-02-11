package org.isatools.isacreator.io.importisa;

import org.isatools.isacreator.managers.ConfigurationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Created by the ISA team
 *
 * Date: 09/03/2011
 * Time: 14:27
 *
 * @author <a href="mailto:eamonnmag@gmail.com">Eamonn Maguire</a>
 * @author <a href="mailto:alejandra.gonzalez.beltran@gmail.com">Alejandra Gonzalez-Beltran</a>
 */
public class ISAtabFilesImporter extends ISAtabImporter {

    private static final Logger log = LoggerFactory.getLogger(ISAtabFilesImporter.class.getName());


    /**
     * ImportISAFiles provides a facility for you to import ISATAB files
     * and convert these files into Java Objects for you to use.
     *
     * This constructor can be used from the API (without accessing GUI elements).
     *
     * @param configDir - the directory containing the configuration files you wish to use.
     */
    public ISAtabFilesImporter(String configDir) {
        super();
        ConfigurationManager.loadConfigurations(configDir);
    }

    /**
     * Import an ISATAB file set!
     *
     * @param parentDir - Directory containing the ISATAB files. Should include a file of type
     * @return boolean if successful or not!
     */
    public boolean importFile(String parentDir){
        return commonImportFile(parentDir);
    }




}
