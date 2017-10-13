/**
 * Bristlecone Test Tools for Databases
 * Copyright (C) 2006-2013 Continuent Inc.
 * Contact: bristlecone@lists.forge.continuent.org
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of version 2 of the GNU General Public License as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 *
 * Initial developer(s): Linas Virbalas.
 * Contributor(s):
 */

package com.continuent.bristlecone.benchmark.test;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import com.continuent.bristlecone.benchmark.db.Column;
import com.continuent.bristlecone.benchmark.db.DataGenerator;
import com.continuent.bristlecone.benchmark.db.DataGeneratorFactory;
import junit.framework.TestCase;

public class DataGeneratorTest extends TestCase
{
  private BigDecimal getBigDecimal(double doubleValue)
  {
    return new BigDecimal(Double.toString(doubleValue)).stripTrailingZeros();
  }

  private BigDecimal getBigDecimal(float floatValue)
  {
    return new BigDecimal(Float.toString(floatValue)).stripTrailingZeros();
  }

  /**
   * Test that double generator generates at least a few numbers with decimal
   * digits.
   */
  public void testDoubleDistribution() throws Exception
  {
    Column c = new Column("d", java.sql.Types.DOUBLE);
    DataGenerator dg = DataGeneratorFactory.getInstance().getGenerator(c);

    int decimals = 0;
    int runs = 100;
    for (int i = 0; i < runs; i++)
    {
      Double obj = (Double) dg.generate();
      double d = obj.doubleValue();
      BigDecimal bd = getBigDecimal(d);

      if (bd.scale() > 0)
      {
        System.out.println(d + " = " + bd);
        decimals++;
      }
    }

    System.out.println("Numbers with decimals: " + decimals + " out of " + runs
        + " runs");

    assertTrue("No double values with decimal numbers generated", decimals > 0);
  }

  /**
   * Test that float generator generates at least a few numbers with decimal
   * digits.
   */
  public void testFloatDistribution() throws Exception
  {
    Column c = new Column("f", java.sql.Types.FLOAT);
    DataGenerator dg = DataGeneratorFactory.getInstance().getGenerator(c);

    int decimals = 0;
    int runs = 100;
    for (int i = 0; i < runs; i++)
    {
      Float obj = (Float) dg.generate();
      float f = obj.floatValue();
      BigDecimal bf = getBigDecimal(f);

      if (bf.scale() > 0)
      {
        System.out.println(f + " = " + bf);
        decimals++;
      }
    }

    System.out.println("Numbers with decimals: " + decimals + " out of " + runs
        + " runs");

    assertTrue("No float values with decimal numbers generated", decimals > 0);
  }

  /**
   * Test that invalid dates are not generated.
   */
  public void testInvalidDates() throws Exception
  {
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    dateFormatter.setLenient(false);

    Column c = new Column("d", java.sql.Types.DATE);
    DataGenerator dg = DataGeneratorFactory.getInstance().getGenerator(c);

    int runs = 10000;
    for (int i = 0; i < runs; i++)
    {
      Date date = (Date) dg.generate();

      // Make a string out of this date - this doesn't fix invalid dates.
      String sDate = dateFormatter.format((Date) date);

      // Now try to parse it - ParseException is thrown date is invalid.
      dateFormatter.parse(sDate);
    }
  }
}
