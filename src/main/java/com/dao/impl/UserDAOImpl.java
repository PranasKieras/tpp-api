package com.dao.impl;

import com.dao.UserDAO;
import com.dao.entity.PSUser;
import com.db.codegen.tables.records .PsUserRecord;
import com.google.inject.Inject;
import com.datasource.DataSourceProvider;
import com.google.inject.Singleton;
import com.service.entities.CreateUserTO;
import com.service.entities.FetchUserTO;
import lombok.NonNull;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.util.Optional;
import static com.db.codegen.Tables.PS_USER;

@Singleton
public class UserDAOImpl implements UserDAO {

    @Inject
    DataSourceProvider dataSourceProvider;

    @Override
    public Optional<PSUser> fetchUser(@NonNull FetchUserTO fetchUserTO) {
         Optional<PsUserRecord> userRecord = DSL.using(dataSourceProvider.getDataSource(), SQLDialect.H2)
                .selectFrom(PS_USER)
                .where(PS_USER.PERSONAL_ID.eq(fetchUserTO.getPersonalId()))
                .and(PS_USER.BANK_LOGIN_ID.eq(fetchUserTO.getBankLoginId()))
                .and(PS_USER.PHONE_NUMBER.eq(fetchUserTO.getPhoneNumber()))
                .fetchOptional();
        return userRecord.map(this::createUserContainer).orElseGet(this::getEmptyUser);
    }

    private Optional<PSUser> createUserContainer(@NonNull PsUserRecord user) {
        return Optional.of(new PSUser(user.getPersonalId(), user.getBankLoginId(), user.getPhoneNumber(), user.getToken()));
    }

    private Optional<PSUser> getEmptyUser(){
        return Optional.empty();
    }

    @Override
    public PSUser createUser(@NonNull CreateUserTO createUserTO) {
        PsUserRecord psUserRecord = DSL.using(dataSourceProvider.getDataSource(), SQLDialect.H2).newRecord(PS_USER);
        psUserRecord.setPersonalId(createUserTO.getPersonalId());
        psUserRecord.setBankLoginId(createUserTO.getBankLoginId());
        psUserRecord.setPhoneNumber(createUserTO.getPhoneNumber());
        psUserRecord.setToken(createUserTO.getLoginToken());
        psUserRecord.store();

        return new PSUser(psUserRecord.getPersonalId(),
                psUserRecord.getBankLoginId(),
                psUserRecord.getPhoneNumber(),
                psUserRecord.getToken());
    }
}
