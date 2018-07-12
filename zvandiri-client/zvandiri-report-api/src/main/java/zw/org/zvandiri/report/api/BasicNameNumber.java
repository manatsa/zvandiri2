/*
 * Copyright 2015 Judge Muzinda.
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

import java.io.Serializable;

/**
 *
 * @author Judge Muzinda
 */
public class BasicNameNumber implements Serializable {
 
    private final String name;
    private final Integer num;
    private final Integer num6Months;
    private final Integer num12Months;
    private String url = "#";

    public BasicNameNumber(String name, Integer num, Integer num6Months, Integer num12Months) {
        this.name = name;
        this.num = num;
        this.num6Months = num6Months;
        this.num12Months = num12Months;
    }

    public BasicNameNumber(String name, Integer num, String url,Integer num6Months, Integer num12Months) {
        this.name = name;
        this.num = num;
        this.url = url;
        this.num6Months = num6Months;
        this.num12Months = num12Months;
    }

    public String getName() {
        return name;
    }

    public Integer getNum() {
        return num != null ? num : 0;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNum6Months() {
        return num6Months != null ? num6Months : 0;
    }

    public Integer getNum12Months() {
        return num12Months != null ? num12Months : 0;
    }
}