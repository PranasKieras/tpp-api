package com.datasource;

import javax.sql.DataSource;

public interface DataSourceProvider {
    DataSource getDataSource();
}
