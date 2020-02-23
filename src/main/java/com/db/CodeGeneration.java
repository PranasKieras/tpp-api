package com.db;

import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.*;

/**
 * config class to generate the db data service layer
 */
public class CodeGeneration {
    /**
     * method generates the classes in the src/db folder
     */
    public static void main(String [] args){
        Configuration configuration = new Configuration()
                .withGenerator(new Generator()
                        .withDatabase(new Database()
                                .withName("org.jooq.meta.extensions.ddl.DDLDatabase")
                                .withProperties(
                                        new Property()
                                                .withKey("scripts")
                                                .withValue("src/main/resources/sql/setup-db.sql")
                                        ))
                        .withGenerate(new Generate()
                                .withPojos(Boolean.TRUE)
                                .withDeprecationOnUnknownTypes(Boolean.FALSE))
                        .withTarget(new Target()
                                .withPackageName("com.db.codegen").withDirectory("src/main/java")));
        try {
            GenerationTool.generate(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
