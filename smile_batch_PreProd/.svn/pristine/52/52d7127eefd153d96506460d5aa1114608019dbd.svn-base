package com.bna.smile.model.domainecommun.service;


import com.bna.commun.util.ContextHandler;

import com.oxia.fwk.context.Context;
import com.oxia.fwk.core.ICriteria;
import com.oxia.fwk.core.IExpression;
import com.oxia.fwk.core.IMatchMode;
import com.oxia.fwk.core.ISearchEngine;
import com.oxia.fwk.core.ValueObject;

import com.oxia.fwk.searchengine.SearchEngine;


import java.lang.reflect.Field;

import java.util.List;

import org.hibernate.criterion.Order;


public class LovService {
    public LovService() {
    }

    public List getListeFind(Class vo, String fieldvalues, List listfield) {

        try {

            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            List l = 
                searchEngine.find(vo, getcriteria(vo, searchEngine, listfield, 
                                                  fieldvalues));

            return (l);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public List getListeFind(Class vo, String fieldvalues, List listfield, String where) {

        try {

            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            List l = 
                searchEngine.find(vo, getcriteria(vo, searchEngine, listfield, 
                                                  fieldvalues,where));

            return (l);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List getListeFindAll(Class vo) {

        try {

            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
            List l = searchEngine.findAll(vo);

            return (l);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public List getListeFindAll(Class vo, String where) {

        try {

            Context context = ContextHandler.getContext();
            ISearchEngine searchEngine = 
                (SearchEngine)context.getBean("searchEngine");
                
                
            IExpression expression = searchEngine.createExpression();
            ICriteria criteria = searchEngine.createCriteria();
            String col=where.substring(0,where.indexOf("="));
            String val=where.substring(where.indexOf("=")+1,where.length());
           
            Field f1 = null;

            try {
                
                f1 = vo.getDeclaredField(col);
                System.out.println("f.getGenericType() " + f1.getType());
            } catch (NoSuchFieldException e) {
                System.out.println("erreur NoSuchFieldException");
                throw new Exception("erreurfield");
            }

           
            
            
            Object crit1 = null;
            if (f1.getType().toString().equals("class java.lang.Integer")) {
                crit1 = new Integer(val);
                criteria.add(expression.like(col, crit1));
            }

            else if (f1.getType().toString().equals("class java.lang.String")) {
                crit1 = val;
                criteria.add(expression.ilike(col, 
                                             crit1.toString().trim() + "%"));
            } else if (f1.getType().toString().equals("class java.lang.Double")) {
                crit1 = new Double(val);
                criteria.add(expression.like(col, crit1));
            } else if (f1.getType().toString().equals("class java.lang.Long")) {
                crit1 = new Long(val);
                criteria.add(expression.like(col, crit1));
            }
                  
            List l = searchEngine.find(vo,criteria);

            return (l);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private ICriteria getcriteria(Class cl, ISearchEngine searchEngine, 
                                  List listfield, 
                                  String fieldvalues) throws Exception {


        // Map m=new HashMap();
        //while(fieldnames.indexOf("-")!=-1){


        IExpression expression = searchEngine.createExpression();
        ICriteria criteria = searchEngine.createCriteria();

        Field f = null;

        try {
            f = cl.getDeclaredField((String)listfield.get(0));
            System.out.println("f.getGenericType() " + f.getType());

        } catch (NoSuchFieldException e) {
            System.out.println("erreur NoSuchFieldException");
            throw new Exception("erreurfield");
        }
        criteria.addOrder(Order.asc((String)listfield.get(0)));
        Object crit = null;
        if (f.getType().toString().equals("class java.lang.Integer")) {
            crit = new Integer(fieldvalues);
            criteria.add(expression.like((String)listfield.get(0), crit));
        }

        else if (f.getType().toString().equals("class java.lang.String")) {
            crit = fieldvalues;
            criteria.add(expression.ilike((String)listfield.get(0), 
                                         crit.toString().trim() + "%"));
        } else if (f.getType().toString().equals("class java.lang.Double")) {
            crit = new Double(fieldvalues);
            criteria.add(expression.like((String)listfield.get(0), crit));
        } else if (f.getType().toString().equals("class java.lang.Long")) {
            crit = new Long(fieldvalues);
            criteria.add(expression.like((String)listfield.get(0), crit));
        }


        //  criteria.add(expression.eq((String)listfield.get(0),crit ));

        //criteria.add(expression.like((String)listfield.get(0),"1" ));
        // criteria.add(expression.eq("login", loginForm.getLogin()));

        return criteria;
    }
    
    
    private ICriteria getcriteria(Class cl, ISearchEngine searchEngine, 
                                  List listfield, 
                                  String fieldvalues, String where) throws Exception {


        // Map m=new HashMap();
        //while(fieldnames.indexOf("-")!=-1){


        IExpression expression = searchEngine.createExpression();
        ICriteria criteria = searchEngine.createCriteria();
        String col=where.substring(0,where.indexOf("="));
        String val=where.substring(where.indexOf("=")+1,where.length());
        Field f = null;
        Field f1 = null;

        try {
            f = cl.getDeclaredField((String)listfield.get(0));
            System.out.println("f.getGenericType() " + f.getType());
            f1 = cl.getDeclaredField(col);
            System.out.println("f.getGenericType() " + f1.getType());
        } catch (NoSuchFieldException e) {
            System.out.println("erreur NoSuchFieldException");
            throw new Exception("erreurfield");
        }

        Object crit = null;
        if (f.getType().toString().equals("class java.lang.Integer")) {
            crit = new Integer(fieldvalues);
            criteria.add(expression.like((String)listfield.get(0), crit));
        }

        else if (f.getType().toString().equals("class java.lang.String")) {
            crit = fieldvalues;
            criteria.add(expression.ilike((String)listfield.get(0), 
                                         crit.toString().trim() + "%"));
        } else if (f.getType().toString().equals("class java.lang.Double")) {
            crit = new Double(fieldvalues);
            criteria.add(expression.like((String)listfield.get(0), crit));
        } else if (f.getType().toString().equals("class java.lang.Long")) {
            crit = new Long(fieldvalues);
            criteria.add(expression.like((String)listfield.get(0), crit));
        }
        criteria.addOrder(Order.asc((String)listfield.get(0)));
        
        Object crit1 = null;
        if (f1.getType().toString().equals("class java.lang.Integer")) {
            crit1 = new Integer(val);
            criteria.add(expression.like(col, crit1));
        }

        else if (f1.getType().toString().equals("class java.lang.String")) {
            crit1 = val;
            criteria.add(expression.ilike(col, 
                                         crit1.toString().trim() + "%"));
        } else if (f1.getType().toString().equals("class java.lang.Double")) {
            crit1 = new Double(val);
            criteria.add(expression.like(col, crit1));
        } else if (f1.getType().toString().equals("class java.lang.Long")) {
            crit1 = new Long(val);
            criteria.add(expression.like(col, crit1));
        }


        //  criteria.add(expression.eq((String)listfield.get(0),crit ));

        //criteria.add(expression.like((String)listfield.get(0),"1" ));
        // criteria.add(expression.eq("login", loginForm.getLogin()));

        return criteria;
    }


}
