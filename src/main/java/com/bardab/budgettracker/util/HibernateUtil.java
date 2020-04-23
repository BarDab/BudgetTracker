package com.bardab.budgettracker.util;
import com.bardab.budgettracker.model.BudgetForecast;
import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.categories.FixedCostsForecast;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.categories.FixedCostsReal;
import com.bardab.budgettracker.model.categories.VariableCostsReal;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static HibernateUtil hibernateUtil = null;

    private HibernateUtil() {
    }

    public static HibernateUtil getInstance(){
        if(hibernateUtil==null)
        {
        hibernateUtil = new HibernateUtil();
        }
        return hibernateUtil;
    }
    public  SessionFactory getSessionFactory(){
        DataBaseCreator.createMySQLDataBase("root","admin","localhost",3306);

        if(sessionFactory == null){
        try{
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL,"jdbc:mysql://localhost:3306/transactions_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Warsaw");
            properties.put(Environment.USER,"root");
            properties.put(Environment.PASS,"admin");
            properties.put(Environment.DIALECT,"org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL,true);
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS,"thread");
            properties.put(Environment.HBM2DDL_AUTO, "update");


            configuration.setProperties(properties);
            configuration.addAnnotatedClass(Transaction.class);
//            configuration.addAnnotatedClass(BudgetForecast.class);
//            configuration.addAnnotatedClass(FixedCostsForecast.class);
            configuration.addAnnotatedClass(FixedCostsReal.class);
            configuration.addAnnotatedClass(VariableCostsReal.class);
            configuration.addAnnotatedClass(MonthlyBalance.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        }
        System.out.println(sessionFactory.toString());
        return sessionFactory;
    }

}
