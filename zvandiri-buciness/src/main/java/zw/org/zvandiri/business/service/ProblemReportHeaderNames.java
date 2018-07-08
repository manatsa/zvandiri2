/*
 * Copyright 2016 Tasunungurwa Muzinda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zw.org.zvandiri.business.service;

/**
 *
 * @author Tasunungurwa Muzinda
 */
public interface ProblemReportHeaderNames {
    
    public String [] headerNames = {
        "Province",
        "District",
        "Facility",
        "Number of contacts",
        "Number of patients",
        "Number of newly registered patients",
        "Number exiting the program",
        "Number newly initiated on ART",
        "Number currently on ART",
        "Number on ART for 3 months",
        "Number on ART for 6 months",
        "Number on ART for 12 months",
        "Number on ART for 24 months",
        "Number on ART for 36 months",
        "Number on ART for greater than 36 months"
    };
    
    public String [] coreheaderNames = {
        "Number of contacts",
        "Number of patients",
        "Number of newly registered patients",
        "Number exiting the program",
        "Number newly initiated on ART",
        "Number currently on ART",
        "Number on ART for greater than 12 months"
    };
}
