/**
 * Bristlecone Test Tools for Databases
 * Copyright (C) 2006-2007 Continuent Inc.
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
 * Initial developer(s): Robert Hodges and Ralph Hannus.
 * Contributor(s):
 */

package com.continuent.bristlecone.benchmark.db;

/**
 * SQL Server DBMS dialect information.
 * 
 * @author rhodges
 */
public class SqlDialectForSQLServer extends AbstractSqlDialect
{
    /** Return the SQL Server driver. */
    public String getDriver()
    {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }

    /** Returns true if the JDBC URL looks like a SQL Server URL. */
    public boolean supportsJdbcUrl(String url)
    {
        return (url.startsWith("jdbc:sqlserver"));
    }

    /**
     * {@inheritDoc}
     * 
     * @see com.continuent.bristlecone.benchmark.db.SqlDialect#getSetDefaultSchema(java.lang.String)
     */
    public String getSetDefaultSchema(String schema)
    {
        return "USE " + schema;
    }

    /**
     * SQL Server uses "identity" as keyword for auto_increment.
     */
    public String implementationAutoIncrementKeyword()
    {
        return "identity";
    }

    /** Add support for specialized PostgreSQL BLOB/CLOB names. */
    public String implementationTypeName(int type)
    {
        switch (type)
        {
            case java.sql.Types.BLOB :
                return "longvarbinary";
            case java.sql.Types.CLOB :
                return "longvarchar";
            case java.sql.Types.TIMESTAMP :
                return "datetime";
            default :
                return super.implementationTypeName(type);
        }
    }

    public String implementationSpecificSuffix(Column c)
    {
        return super.implementationSpecifcSuffix(c);
    }

}