/*
 *
 *  Copyright 2014 YODO international projects and consultancy
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
package nl.socrates.dom.party;

import org.apache.isis.applib.annotation.MemberOrder;

//import nl.socrates.dom.utils.StringUtils;

public enum PersonGenderType {
    UNKNOWN("onbekend"), 
    MALE("man"), 
    FEMALE("vrouw");
    
    private String title;
    
    PersonGenderType(String title) {
        this.title = title;
    }

    public String title() {
//        return StringUtils.enumTitle(name());
        return title;
    }

}
