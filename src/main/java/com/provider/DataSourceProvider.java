package com.provider;

import javax.sql.DataSource;

public interface DataSourceProvider {
    DataSource getDataSource();
}
