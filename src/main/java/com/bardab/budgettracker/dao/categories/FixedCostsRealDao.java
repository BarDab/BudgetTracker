package com.bardab.budgettracker.dao.categories;

import com.bardab.budgettracker.dao.AbstractDAO;
import com.bardab.budgettracker.model.MonthlyBalance;
import com.bardab.budgettracker.model.categories.FixedCostsReal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class FixedCostsRealDao extends AbstractDAO<FixedCostsReal> {

    private SessionFactory sessionFactory;

    public FixedCostsRealDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    public FixedCostsReal findByID(long id){
        FixedCostsReal fixedCostsReal =null;
        Session session = null;
        try{
            session=sessionFactory.openSession();
            session.beginTransaction();
            fixedCostsReal = (FixedCostsReal) session.get(FixedCostsReal.class,id);

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
        return fixedCostsReal;
    }
}
