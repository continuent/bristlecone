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
 * Generates day to seconds interval data with the specified length. The format
 * is DD HH:MM:SS.mmmm for "INTERVAL DAY (2) TO SECOND (4)" where 2 is the
 * length of the day and 4 is the length of the milliseconds.
 *
 * @author <a href="mailto:csimon@vmware.com">Csaba Endre Simon</a>
 */
public class DataGeneratorForIntervalDayToSecond implements DataGenerator
{
    private int dayLength;
    private int msLength;

    /** Create a new instance with an upper bound. */
    public DataGeneratorForIntervalDayToSecond(int dayLength, int msLength)
    {
        this.dayLength = dayLength;
        this.msLength = msLength;
    }

    /** Generate a string of "DAY HOUR:MINUTES:SECONDS.MILLISECONDS" format. */
    @Override
    public Object generate()
    {
        int day = Utils.randomInt(0, (int)Math.pow(10, dayLength) - 1);
        int hour = Utils.randomInt(0, 23);
        int min = Utils.randomInt(0, 59);
        int sec = Utils.randomInt(0, 59);
        int ms = Utils.randomInt(0, (int)Math.pow(10, msLength) - 1);
        return Integer.toString(day) + " " + Integer.toString(hour) + ":"
                + Integer.toString(min) + ":" + Integer.toString(sec) + "."
                + Integer.toString(ms);
    }
}