/*
 *
 *  Copyright 2012-2014 Eurocommercial Properties NV
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package nl.socrates.dom.communicationchannel;

import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.annotation.Mandatory;
import org.apache.isis.applib.annotation.Title;

import nl.socrates.dom.JdoColumnLength;

@javax.jdo.annotations.PersistenceCapable // identityType=IdentityType.DATASTORE inherited from superclass
@javax.jdo.annotations.Inheritance(strategy=InheritanceStrategy.SUPERCLASS_TABLE)
//no @DatastoreIdentity nor @Version, since inherited from supertype
@javax.jdo.annotations.Indices({
    @javax.jdo.annotations.Index(
            name="EmailAddress_emailAddress_IDX", members={"emailAddress"})
})
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "findByEmailAddress", language = "JDOQL",
                value = "SELECT "
                        + "FROM nl.socrates.dom.communicationchannel.EmailAddress "
                        + "WHERE owner == :owner "
                        + "&& emailAddress == :emailAddress")
})
public class EmailAddress extends CommunicationChannel {


    // //////////////////////////////////////

    private String emailAddress;

    @javax.jdo.annotations.Column(allowsNull="true", length=JdoColumnLength.EMAIL_ADDRESS)
    @Title
    @Mandatory
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String address) {
        this.emailAddress = address;
    }

}