/**
 ISAcreator is a component of the ISA software suite (http://www.isa-tools.org)

 License:
 ISAcreator is licensed under the Common Public Attribution License version 1.0 (CPAL)

 EXHIBIT A. CPAL version 1.0
 �The contents of this file are subject to the CPAL version 1.0 (the �License�);
 you may not use this file except in compliance with the License. You may obtain a
 copy of the License at http://isa-tools.org/licenses/ISAcreator-license.html.
 The License is based on the Mozilla Public License version 1.1 but Sections
 14 and 15 have been added to cover use of software over a computer network and
 provide for limited attribution for the Original Developer. In addition, Exhibit
 A has been modified to be consistent with Exhibit B.

 Software distributed under the License is distributed on an �AS IS� basis,
 WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 the specific language governing rights and limitations under the License.

 The Original Code is ISAcreator.
 The Original Developer is the Initial Developer. The Initial Developer of the
 Original Code is the ISA Team (Eamonn Maguire, eamonnmag@gmail.com;
 Philippe Rocca-Serra, proccaserra@gmail.com; Susanna-Assunta Sansone, sa.sanson@gmail.com;
 http://www.isa-tools.org). All portions of the code written by the ISA Team are
 Copyright (c) 2007-2011 ISA Team. All Rights Reserved.

 EXHIBIT B. Attribution Information
 Attribution Copyright Notice: Copyright (c) 2008-2011 ISA Team
 Attribution Phrase: Developed by the ISA Team
 Attribution URL: http://www.isa-tools.org
 Graphic Image provided in the Covered Code as file: http://isa-tools.org/licenses/icons/poweredByISAtools.png
 Display of Attribution Information is required in Larger Works which are defined in the CPAL as a work which combines Covered Code or portions thereof with code not governed by the terms of the CPAL.

 Sponsors:
 The ISA Team and the ISA software suite have been funded by the EU Carcinogenomics project (http://www.carcinogenomics.eu), the UK BBSRC (http://www.bbsrc.ac.uk), the UK NERC-NEBC (http://nebc.nerc.ac.uk) and in part by the EU NuGO consortium (http://www.nugo.org/everyone).
 */

package org.isatools.isacreator.model;

import org.isatools.isacreator.gui.StudySubData;

import java.io.Serializable;


/**
 * Object to store representation of a Contact.
 *
 * @author Eamonn Maguire
 */
public class Contact extends ISASection implements StudySubData, Serializable {

    public static final String CONTACT_LAST_NAME = "Study Person Last Name";
    public static final String CONTACT_FIRST_NAME = "Study Person First Name";
    public static final String CONTACT_MID_INITIAL = "Study Person Mid Initials";
    public static final String CONTACT_EMAIL = "Study Person Email";
    public static final String CONTACT_PHONE = "Study Person Phone";
    public static final String CONTACT_FAX = "Study Person Fax";
    public static final String CONTACT_ADDRESS = "Study Person Address";
    public static final String CONTACT_AFFILIATION = "Study Person Affiliation";
    public static final String CONTACT_ROLE = "Study Person Roles";
    public static final String CONTACT_ROLE_TERM_ACCESSION = "Study Person Roles Term Accession Number";
    public static final String CONTACT_ROLE_TERM_SOURCE_REF = "Study Person Roles Term Source REF";

    /**
     * Contact Constructor
     *
     * @param lastName    - last name of contact
     * @param firstName   - first name of contact
     * @param midInitial  - initial(s) for contact
     * @param email       - email address
     * @param phone       - phone no
     * @param fax         - fax no
     * @param address     - address
     * @param affiliation - where they are from e.g.EBI
     * @param role        - persons role e.g. curator.
     */
    public Contact(String lastName, String firstName, String midInitial,
                   String email, String phone, String fax, String address,
                   String affiliation, String role) {
        this(lastName, firstName, midInitial, email,
                phone, fax, address, affiliation, role, "", "");
    }

    /**
     * Contact Constructor
     *
     * @param lastName          - last name of contact
     * @param firstName         - first name of contact
     * @param midInitial        - initial(s) for contact
     * @param email             - email address
     * @param phone             - phone no
     * @param fax               - fax no
     * @param address           - address
     * @param affiliation       - where they are from e.g.EBI
     * @param role              - persons role e.g. curator.
     * @param roleTermAccession - accession for the role term.
     * @param roleTermSourceRef - source ref for the role term.
     */
    public Contact(String lastName, String firstName, String midInitial,
                   String email, String phone, String fax, String address,
                   String affiliation, String role, String roleTermAccession,
                   String roleTermSourceRef) {
        super();

        fieldValues.put(CONTACT_LAST_NAME, lastName);
        fieldValues.put(CONTACT_FIRST_NAME, firstName);
        fieldValues.put(CONTACT_MID_INITIAL, midInitial);
        fieldValues.put(CONTACT_EMAIL, email);
        fieldValues.put(CONTACT_PHONE, phone);
        fieldValues.put(CONTACT_FAX, fax);
        fieldValues.put(CONTACT_ADDRESS, address);
        fieldValues.put(CONTACT_AFFILIATION, affiliation);
        fieldValues.put(CONTACT_ROLE, role);
        fieldValues.put(CONTACT_ROLE_TERM_ACCESSION, roleTermAccession);
        fieldValues.put(CONTACT_ROLE_TERM_SOURCE_REF, roleTermSourceRef);
    }

    /**
     * Returns the Contact's address.
     *
     * @return String representing the Contacts address
     */
    public String getAddress() {
        return fieldValues.get(CONTACT_ADDRESS);
    }

    /**
     * Returns the Contact's affiliation.
     *
     * @return String representing the Contacts affiliation
     */
    public String getAffiliation() {
        return fieldValues.get(CONTACT_AFFILIATION);
    }


    /**
     * Returns the Contact's email.
     *
     * @return String representing the Contacts email
     */
    public String getEmail() {
        return fieldValues.get(CONTACT_EMAIL);
    }


    /**
     * Returns the Contact's fax number.
     *
     * @return String representing the Contacts number
     */
    public String getFax() {
        return fieldValues.get(CONTACT_FAX);
    }


    /**
     * Returns the Contact's First name (forename).
     *
     * @return String representing the Contacts First name (forename)
     */
    public String getFirstName() {
        return fieldValues.get(CONTACT_FIRST_NAME);
    }

    /**
     * Returns the Contact's identifier. This method is required by the Implemented Class.
     *
     * @return String representing the Contacts First name (forename) , Last name (surname) & email address
     * @see org.isatools.isacreator.gui.StudySubData
     */
    public String getIdentifier() {
        return getFirstName() + " " + getLastName() + " " + getEmail();
    }

    /**
     * Returns the Contact's Last name (surname).
     *
     * @return String representing the Contacts Last name (surname)
     */
    public String getLastName() {
        return fieldValues.get(CONTACT_LAST_NAME);
    }

    /**
     * Returns the Contact's Mid Initial.
     *
     * @return String representing the Contacts Mid Initial
     */
    public String getMidInitial() {
        return fieldValues.get(CONTACT_MID_INITIAL);
    }

    /**
     * Returns the Contact's Phone.
     *
     * @return String representing the Contacts Phone
     */
    public String getPhone() {
        return fieldValues.get(CONTACT_PHONE);
    }

    /**
     * Returns the Contact's Role.
     *
     * @return String representing the Contacts Role
     */
    public String getRole() {
        return fieldValues.get(CONTACT_ROLE);
    }

    /**
     * Returns the Contact's Role Term Accession.
     *
     * @return String representing the Contacts Role Term Accession
     */
    public String getRoleTermAccession() {
        return fieldValues.get(CONTACT_ROLE_TERM_ACCESSION);
    }

    /**
     * Returns the Contact's Role Term Source REF.
     *
     * @return String representing the Contacts Role Term Source REF
     */
    public String getRoleTermSourceRef() {
        return fieldValues.get(CONTACT_ROLE_TERM_SOURCE_REF);
    }

    /**
     * Set the role of the contact to some String.
     *
     * @param role - the Contact's role.
     */
    public void setRole(String role) {
        fieldValues.put(CONTACT_ROLE, role);
    }

    @Override
    public String toString() {
        return getLastName() + ", " + getFirstName();
    }
}
