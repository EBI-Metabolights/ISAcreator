package org.isatools.isacreator.io.importisa;

import au.com.bytecode.opencsv.CSVReader;
import com.google.zxing.common.StringUtils;
import com.sun.tools.javac.util.Pair;
import org.apache.commons.collections15.OrderedMap;
import org.apache.commons.collections15.map.ListOrderedMap;
import org.isatools.isacreator.io.importisa.InvestigationFileProperties.InvestigationFileSections;
import org.isatools.isacreator.io.importisa.InvestigationFileProperties.InvestigationSection;
import org.isatools.isacreator.io.importisa.InvestigationFileProperties.InvestigationStructureLoader;
import org.isatools.isacreator.utils.CollectionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by the ISA team
 *
 * @author Eamonn Maguire (eamonnmag@gmail.com)
 *         <p/>
 *         Date: 07/03/2011
 *         Time: 11:26
 */
public class InvestigationImport {

    private static final Character TAB_DELIM = '\t';

    private Set<String> messages;
    private String currentMajorSection;


    public InvestigationImport() {
        messages = new HashSet<String>();
    }

    /**
     * imports the Investigation file into a Map data structure.
     *
     * @param investigationFile- File object representing Investigation file to be loaded.
     * @return Map is formatted like so:
     *         (Main section name) e.g. Investigation-1
     *         -> Section name e.g. InvestigationFileSections e.g. InvestigationFileSections.ONTOLOGY_SECTION
     *         -> Label for the ontology section e.g. Term Source Name
     *         -> Values for the given section/label e.g. OBI
     *         EFO
     *         etc
     */
    public Map<String, OrderedMap<InvestigationFileSections, OrderedMap<String, List<String>>>> importInvestigationFile(File investigationFile) throws IOException {

        Map<String, OrderedMap<InvestigationFileSections, OrderedMap<String, List<String>>>> importedInvestigationFile = new HashMap<String, OrderedMap<InvestigationFileSections, OrderedMap<String, List<String>>>>();

        List<String[]> investigationFileContents = loadFile(investigationFile);

        currentMajorSection = "Investigation-1";

        int studyCount = 1;

        importedInvestigationFile.put(currentMajorSection, new ListOrderedMap<InvestigationFileSections, OrderedMap<String, List<String>>>());

        InvestigationFileSections currentMinorSection = null;

        for (String[] line : investigationFileContents) {
            InvestigationFileSections tmpSection;

            if ((tmpSection = InvestigationFileSections.convertToInstance(line[0])) != null) {

                currentMinorSection = tmpSection;

                if (currentMinorSection == InvestigationFileSections.STUDY_SECTION) {
                    currentMajorSection = "Study-" + studyCount;
                    studyCount++;

                    importedInvestigationFile.put(currentMajorSection, new ListOrderedMap<InvestigationFileSections, OrderedMap<String, List<String>>>());
                }

                importedInvestigationFile.get(currentMajorSection).put(currentMinorSection, new ListOrderedMap<String, List<String>>());

            } else {

                String lineLabel = line[0].trim();

                if (!org.apache.axis.utils.StringUtils.isEmpty(lineLabel)) {
                    if (!importedInvestigationFile.get(currentMajorSection).get(currentMinorSection).containsKey(lineLabel)) {
                        importedInvestigationFile.get(currentMajorSection).get(currentMinorSection).put(lineLabel, new ArrayList<String>());
                    }

                    if (line.length > 1) {
                        for (int index = 1; index < line.length; index++) {
                            importedInvestigationFile.get(currentMajorSection).get(currentMinorSection).get(lineLabel).add(line[index]);
                        }
                    }
                }

            }
        }
        // todo improve logging...provide detailed information about what information is missing from the file, if there is something missing
        if (isValidInvestigationSections(importedInvestigationFile)) {
            System.out.println("Investigation file is valid...");
        } else {
            System.out.println("Investigation is invalid...here's why:");

            for(String message : messages)  {
                System.out.println(message);
            }

        }

        return importedInvestigationFile;
    }

    /**
     * Checks to make sure all required Investigation file sections have been entered
     *
     * @param investigationFile - Map containing investigation file structure
     * @return - true if valid, false otherwise.
     */
    private boolean isValidInvestigationSections(Map<String, OrderedMap<InvestigationFileSections, OrderedMap<String, List<String>>>> investigationFile) {

        InvestigationStructureLoader loader = new InvestigationStructureLoader();
        Map<InvestigationFileSections, InvestigationSection> sections = loader.loadInvestigationStructure();

        MessageFormat fmt = new MessageFormat("The field {0} is missing from the {1} section of the investigation file");

        for (String mainSection : investigationFile.keySet()) {

            // checking major section, e.g. study or investigation
            Set<InvestigationFileSections> majorSectionParts = new HashSet<InvestigationFileSections>();

            for (InvestigationFileSections section : investigationFile.get(mainSection).keySet()) {
                majorSectionParts.add(section);

                Set<String> minorSectionParts = new HashSet<String>();
                // we also want to check if the salient information for each minor section is in place.
                for (String sectionLabelsAndValues : investigationFile.get(mainSection).get(section).keySet()) {
                    minorSectionParts.add(sectionLabelsAndValues);
                }

                CollectionUtils<String, String> collectionUtils = new CollectionUtils<String, String>();
                Pair<Boolean, Set<String>> equalityResult = collectionUtils.compareSets(minorSectionParts, sections.get(section).getRequiredValues());
                if (!equalityResult.fst) {
                    for (String sectionValue : equalityResult.snd) {
                        messages.add(fmt.format(new Object[]{sectionValue, section}));
                    }
                }
                // check minor section for salient information
            }

            // check major section for salient information

            // the mainsection string is investigation-1 or study-2 - here we strip away from - onwards.
            Set<InvestigationFileSections> requiredSections = loader.getRequiredSections(mainSection.substring(0, mainSection.lastIndexOf("-")));
            CollectionUtils<InvestigationFileSections, InvestigationFileSections> collectionUtils = new CollectionUtils<InvestigationFileSections, InvestigationFileSections>();
            Pair<Boolean, Set<InvestigationFileSections>> equalityResult = collectionUtils.compareSets(majorSectionParts, requiredSections);

            if (equalityResult.fst) {
                System.out.println("All required elements have been found...");
            } else {
                System.out.println("Missing something " + equalityResult.snd);
                for (InvestigationFileSections section : equalityResult.snd) {
                    messages.add(fmt.format(new Object[]{section, mainSection.lastIndexOf("-")}));
                }


            }

        }

        return messages.size() == 0;
    }

    private List<String[]> loadFile(File investigationFile) throws IOException {

        if (investigationFile.exists()) {
            CSVReader csvReader = new CSVReader(new FileReader(investigationFile), TAB_DELIM);
            return csvReader.readAll();
        } else {
            throw new FileNotFoundException("The specified file " + investigationFile.getName() + "does not exist in " + investigationFile.getAbsolutePath());
        }
    }
}
