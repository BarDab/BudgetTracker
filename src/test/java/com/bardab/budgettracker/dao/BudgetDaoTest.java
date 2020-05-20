package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.Budget;
import com.bardab.budgettracker.model.categories.PlannedExpenses;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BudgetDaoTest {
    private SessionFactory sessionFactory;
    private BudgetDao budgetDao;
    Budget budget;
    @BeforeEach
    public void before() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Budget.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:./mem");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        sessionFactory = configuration.buildSessionFactory();
        budgetDao = new BudgetDao(sessionFactory);
        budget = new Budget();
        budget.setIncome(2000.0);
        budget.setPlannedExpenses(new PlannedExpenses());
        budget.setInvestments(250.0);
        budget.setMonthCode(20205);
    }
    @AfterEach
    public void AfterAll(){
        sessionFactory.close();
    }



    @Test
    public void addBudgetForecast(){
        budgetDao.addEntity(budget);
        Budget testBudget = budgetDao.findByID(budget.getId());
        assertNotNull(testBudget);
        assertEquals(budget.getId(), testBudget.getId());
        assertEquals(budget.getIncome(), testBudget.getIncome());
        assertEquals(budget.getMonthCode(), testBudget.getMonthCode());
        assertEquals(budget.getPlannedExpenses(), testBudget.getPlannedExpenses());
        assertEquals(budget.getMonthCode(), testBudget.getMonthCode());
        assertEquals(budget.getInvestments(), testBudget.getInvestments());
    }


}