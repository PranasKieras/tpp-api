package com.datasource.impl;

import com.google.inject.Singleton;
import com.datasource.DataSourceProvider;
import lombok.SneakyThrows;
import org.apache.commons.dbcp.BasicDataSource;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import javax.sql.DataSource;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

@Singleton
public class H2DataSourceProvider implements DataSourceProvider {

    private static final String PROPERTIES_FILE_LOCATION = "/config.properties";

    private DataSource dataSource;

    public H2DataSourceProvider() {
        Properties props = getProperties();
        dataSource = createDataSource(props);
        setupData(props);
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    private void setupData(Properties properties) {
        String dbSetupScript = properties.getProperty("db.schema.script");

        DSLContext ctx = DSL.using(getDataSource(), SQLDialect.H2);
        ctx.query(getSQL(dbSetupScript)).execute();
    }

    private DataSource createDataSource(Properties properties) {
        final BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(properties.getProperty("db.driver"));
        basicDataSource.setUrl(properties.getProperty("db.url"));
        basicDataSource.setUsername(properties.getProperty("db.username"));
        basicDataSource.setPassword(properties.getProperty("db.password"));

        return basicDataSource;
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
