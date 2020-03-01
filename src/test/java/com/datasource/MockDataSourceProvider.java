package com.datasource;

import com.google.inject.Singleton;
import lombok.SneakyThrows;
import org.apache.commons.dbcp.BasicDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

@Singleton
public class MockDataSourceProvider implements DataSourceProvider {

    private static final String PROPERTIES_FILE_LOCATION = "/config.properties";

    private DataSource ds;

    public MockDataSourceProvider() {
        Properties props = getProperties();
        ds = createDataSource(props);
        setupData(props);
    }

    @Override
    public DataSource getDataSource() {
        return ds;
    }

    private void setupData(Properties properties) {
        String dbSetupScript = properties.getProperty("db.schema.script");
        String dataSetupScript = properties.getProperty("db.data.script");

        DSLContext ctx = DSL.using(getDataSource(), SQLDialect.H2);
        ctx.query(getSQL(dbSetupScript)).execute();
        ctx.query(getSQL(dataSetupScript)).execute();
    }

    private DataSource createDataSource(Properties properties) {
        final BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(properties.getProperty("db.driver"));
        ds.setUrl(properties.getProperty("db.url"));
        ds.setUsername(properties.getProperty("db.username"));
        ds.setPassword(properties.getProperty("db.password"));

        return ds;
    }

    @SneakyThrows
    private Properties getProperties() {
        final Properties properties = new Properties();
        try (InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE_LOCATION)) {
            properties.load(is);
        }
        return properties;
    }

    @SneakyThrows
    private String getSQL(String fileName) {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        URI uri = classloader.getResource(fileName).toURI();
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(uri), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        return contentBuilder.toString();
    }
}
