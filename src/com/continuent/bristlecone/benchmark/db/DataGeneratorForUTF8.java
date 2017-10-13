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

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Generates UTF8 strings with the specified length.
 * 
 * @author <a href="mailto:csimon@vmware.com">Csaba Endre Simon</a>
 */
public class DataGeneratorForUTF8 implements DataGenerator
{
    private static Logger   logger = Logger.getLogger(DataGeneratorForUTF8.class);
    private int             length;
    private List<Character> isoChars;

    /** Create a new instance with an upper bound. */
    public DataGeneratorForUTF8(int length)
    {
        this.length = length;
        this.isoChars = getCharacterRepertoire();
    }

    /** Generate a string of UTF8 characters with the given length. */
    @Override
    public Object generate()
    {
        StringBuffer buf = new StringBuffer();
        int size = isoChars.size();

        for (int i = 0; i < length; i++)
        {
            int nextCharIndex = (int) (Math.random() * size);
            buf.append(isoChars.get(nextCharIndex));
        }
        return buf.toString();
    }

    /**
     * Generates UTF8 character repertoire.
     * 
     * @return the list of UTF8 characters generated.
     */
    private List<Character> getCharacterRepertoire()
    {
        List<Character> isoChars = new LinkedList<Character>();
        for (int i = 0; i < 65536; i++)
        {
            if (Character.isLetterOrDigit(i))
            {
                char c = (char) i;
                if (logger.isDebugEnabled())
                    logger.debug("Codepoint: " + i + " character: " + c);
                isoChars.add(c);
            }
        }
        return isoChars;
    }
}