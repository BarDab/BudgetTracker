package com.bardab.budgettracker.dao;

import com.bardab.budgettracker.model.FixedCosts;
import com.bardab.budgettracker.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metamodel.spi.MetamodelImplementor;
import org.hibernate.persister.entity.AbstractEntityPersister;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FixedCostsDao extends AbstractDAO<FixedCosts> {
    private SessionFactory sessionFactory;

    public FixedCostsDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return this.sessionFactory;
    }

    @Override
    public FixedCosts findByID(long id) {
        FixedCosts budgetForecast=null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            budgetForecast = session.get(FixedCosts.class,id);
            session.getTransaction().commit();
        }
        catch (Exception e){
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
        return budgetForecast;
    }

    public FixedCosts getLastRecord(){
        FixedCosts fixedCosts =null;
        Session session=null;
        try{
            session = getSessionFactory().openSession();
            session.beginTransaction();
            Query query = session.createQuery("from FixedCosts order by ID desc");
            query.setMaxResults(1);
            fixedCosts = (FixedCosts) query.getSingleResult();
            session.getTransaction().commit();
        }
        catch (Exception e){
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
        return fixedCosts;
    }

    public LinkedHashMap<String,Double> getFixedCostsTypesWithValues(){
            LinkedHashMap<String,Double> typesWithLastValue = new LinkedHashMap<>();
            String dataFromLastRecord =getLastRecord().toString();

            Pattern valuePattern = Pattern.compile("(\\d+)((\\.\\d{1,2}))|null");
            Matcher valueMatcher = valuePattern.matcher(dataFromLastRecord);

            Pattern namesPattern = Pattern.compile("(?<=((\\,)(\\s)))(\\w+)(?=((\\=)((\\d)|n)))");
            Matcher nameMatcher = namesPattern.matcher(dataFromLastRecord);

            nameMatcher.reset();
            valueMatcher.reset();

            while(nameMatcher.find()){
                String name = null;
                name =dataFromLastRecord.substring(nameMatcher.start(),nameMatcher.end());

                Double value = null;

                valueMatcher.find();
                if(dataFromLastRecord.substring(valueMatcher.start(),valueMatcher.end()).equals("null")){
                    value =0.0;
                }
                else value = Double.valueOf(dataFromLastRecord.substring(valueMatcher.start(),valueMatcher.end()));

                typesWithLastValue.put(name,value);
            }
            return typesWithLastValue;
    }

}


