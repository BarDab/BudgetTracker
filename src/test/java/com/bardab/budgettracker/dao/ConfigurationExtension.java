package com.bardab.budgettracker.dao;


import com.bardab.budgettracker.model.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

public class ConfigurationExtension implements BeforeEachCallback {
    private SessionFactory sessionFactory;

    @Override
    public void beforeEach(ExtensionContext extensionContext) throws Exception {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Transaction.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./mem");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        sessionFactory = configuration.buildSessionFactory();
    }
}
