package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.BudgetForecast;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetForecastDaoTest {
    private SessionFactory sessionFactory;
    private BudgetForecastDao budgetForecastDao;
    BudgetForecast budgetForecast;
    @BeforeEach
    public void before() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(BudgetForecast.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./mem");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        sessionFactory = configuration.buildSessionFactory();
        budgetForecastDao = new BudgetForecastDao(sessionFactory);
        budgetForecast = new BudgetForecast();
        budgetForecast.setIncome(2000.0);
        budgetForecast.setFixedSpending(1000.0);
        budgetForecast.setSavingGoal(250.0);
        budgetForecast.setMonthCode("2020_5");
    }
    @AfterEach
    public void AfterAll(){
        sessionFactory.close();
    }



    @Test
    public void addBudgetForecast(){
        budgetForecastDao.addTransaction(budgetForecast);
        BudgetForecast testBudgetForecast = budgetForecastDao.findByID(budgetForecast.getId());
        assertNotNull(testBudgetForecast);
        assertEquals(budgetForecast.getId(),testBudgetForecast.getId());
        assertEquals(budgetForecast.getIncome(),testBudgetForecast.getIncome());
        assertEquals(budgetForecast.getMonthCode(),testBudgetForecast.getMonthCode());
        assertEquals(budgetForecast.getFixedSpending(),testBudgetForecast.getFixedSpending());
        assertEquals(budgetForecast.getMonthCode(),testBudgetForecast.getMonthCode());
        assertEquals(budgetForecast.getSavingGoal(),testBudgetForecast.getSavingGoal());
    }


}