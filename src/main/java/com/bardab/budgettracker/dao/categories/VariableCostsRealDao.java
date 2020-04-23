package com.bardab.budgettracker.dao.categories;

import com.bardab.budgettracker.dao.AbstractDAO;
import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.categories.FixedCostsReal;
import com.bardab.budgettracker.model.categories.VariableCostsReal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class VariableCostsRealDao extends AbstractDAO<VariableCostsReal> {

    private SessionFactory sessionFactory;

    public VariableCostsRealDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }
    public VariableCostsReal findByID(long id){
        VariableCostsReal variableCostsReal =null;
        Session session = null;
        try{
            session=sessionFactory.openSession();
            session.beginTransaction();
            variableCostsReal = (VariableCostsReal) session.get(VariableCostsReal.class,id);

        }    catch (Exception e){
            if(session.getTransaction()!=null){
                logger.info("\n ..........Transaction is being rolled back...........\n");
                session.getTransaction().rollback();
            }
            System.out.println(e.getMessage());
        }
        finally {
            if (session != null) {
                session.close();
            }
        }
        return variableCostsReal;
    }
}
