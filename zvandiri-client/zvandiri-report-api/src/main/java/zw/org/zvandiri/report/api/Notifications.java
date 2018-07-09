/*
 * Copyright 2017 Judge Muzinda.
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
package zw.org.zvandiri.report.api;

/**
 *
 * @author Judge Muzinda
 */
public interface Notifications {

    /*public final String[] labels = {
        "New contacts received in past 1 week",
        "New contacts received in past 2 weeks",
        "Clients with no contact past 1 month",
        "Clients with no contact past 3 months",
        "Clients with 12 months before graduation",
        "Clients with no contact past 6 months",
        "Clients with no contact past 12 months",        
        "External referrals send and no feedback received past 1 week",
        "External referrals send and no feedback received past 2 weeks",
        "External referrals send and no feedback received past 1 month",        
        "Clients with 6 months before graduation",
        "Clients with 3 months before graduation",
        "Clients with 12 months before graduation"
    };*/
    
    public final String [] labels = {
        "Number of new clients registered",
        "Number of clients with contacts",
        "Clients About To Graduate",
        "Cumulative internal referrals initiated",
        "Cumulative internal referrals confirmed",
        "Cumulative external referrals initiated",
        "Cumulative external referrals confirmed",
    };
}
