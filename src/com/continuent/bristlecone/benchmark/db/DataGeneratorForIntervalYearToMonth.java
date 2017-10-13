/**
 * Bristlecone Test Tools for Databases
 * Copyright (C) 2015 VMware, Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Initial developer(s): Csaba Endre Simon, Robert Hodges
 * Contributor(s):
 */

package com.continuent.bristlecone.benchmark.db;

import com.continuent.bristlecone.utils.Utils;

/**
 * Generates year to month interval data with the specified length. The format
 * is YYYY-MM for "INTERVAL (4) TO MONTH" where 4 is the length of the year.
 *
 * @author <a href="mailto:csimon@vmware.com">Csaba Endre Simon</a>
 */
public class DataGeneratorForIntervalYearToMonth implements DataGenerator
{
    private int length;

    /** Create a new instance with an upper bound. */
    public DataGeneratorForIntervalYearToMonth(int length)
    {
        this.length = length;
    }

    /** Generate a string of YEAR-MONTH format. */
    @Override
    public Object generate()
    {
        int year = Utils.randomInt(0, (int)Math.pow(10, length) - 1);
        int month = Utils.randomInt(1, 11);
        return Integer.toString(year) + "-" + Integer.toString(month);
    }
}