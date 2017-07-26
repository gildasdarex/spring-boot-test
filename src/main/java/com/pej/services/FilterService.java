package com.pej.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pej.domains.Candidat;
import com.pej.pojo.FilterModel;
import com.pej.pojo.FilterResult;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by darextossa on 7/26/17.
 */
@Service()
public class FilterService {

    @PersistenceContext
    private EntityManager em;


    public FilterResult filter(HttpServletRequest request, Class clazz){

        FilterResult filterResult = new FilterResult();

        String criteriaParameter = request.getParameter("criteria");
        String pageParameter = request.getParameter("page");
        int page =  0;

        if(pageParameter != null && pageParameter != "") page = Integer.parseInt(pageParameter);

        int nextPage =  page;
        int previousPage =  page;

        filterResult.setCriteriaParameter(criteriaParameter);
        filterResult.setCurrentPage(page);


        List<FilterModel> criteres = new ArrayList<>();


        if(criteriaParameter != null && criteriaParameter != ""){
            Gson gson = new Gson();
            Type type = new TypeToken<List<FilterModel>>() {}.getType();
            criteres = gson.fromJson(criteriaParameter, type);

        }

        List<FilterModel> copyOfCriteres = ((List) ((ArrayList) criteres).clone());


        for (FilterModel filterModel : copyOfCriteres){
            filterModel.clean();
        }


        String query = getQuery(copyOfCriteres, clazz);

        //Query q = em.createNativeQuery(query, clazz);
        Query q =  em.createQuery(query);
        q.setFirstResult(page);
        q.setMaxResults(100);
        List<Object> data = q.getResultList();

        filterResult.setData(data);


        String countQuery = count(copyOfCriteres, clazz);



        //BigInteger sizeOfData = (BigInteger) em.createNativeQuery(countQuery).getSingleResult();
        Long sizeOfData = (Long) em.createQuery(countQuery).getSingleResult();
        System.out.println("sizeOfData " + sizeOfData);
        long numberOfIndex = sizeOfData.longValue()/100;
        int size = ((Double)Math.ceil(numberOfIndex)).intValue();

        int[] listOfPages = IntStream.range(1, size).toArray();
        filterResult.setListOfPages(listOfPages);

        List<Integer> listOfInt = new ArrayList<>();

        for(int i : listOfPages){
            listOfInt.add(new Integer(i));
        }

        System.out.println("listOfInt "+ listOfInt.contains(new Integer(page + 1)));
        System.out.println("listOfInt "+ listOfInt.contains(new Integer(page - 1)));

        if(listOfInt.contains(new Integer(page + 1)))  nextPage = page + 1;
        if(listOfInt.contains(new Integer(page - 1)))  previousPage = page - 1;


        filterResult.setNextPage(nextPage);
        filterResult.setPreviousPage(previousPage);



        return filterResult;



    }

    public List<Candidat> filterCandidat(List<FilterModel> filterModels, Class clazz){

        String query = getQuery(filterModels, clazz);
        List<Candidat> candidats = (List<Candidat>) em.createNativeQuery(query, clazz).getResultList();

        if(candidats == null ) return new ArrayList<Candidat>();

        return candidats;

    }




    private String getQuery(List<FilterModel> filterModels, Class clazz){
        if(filterModels == null || filterModels.size() == 0)
            return "select o from " + clazz.getSimpleName() +" o ";
        String query = "select o from " + clazz.getSimpleName() +" o where ";
        for(int i = 0; i< filterModels.size(); i++){
            FilterModel filterModel = filterModels.get(i);
            query = query + filterModel.getQuery() + " ";
            if (i != filterModels.size()-1) query = query + " and ";
        }

        System.out.println("select service is "+ query);
        return query;
    }


    private String count(List<FilterModel> filterModels, Class clazz){
        if(filterModels == null || filterModels.size() == 0)
            return "select count(o) from " + clazz.getSimpleName() +" o ";
        String query = "select count(o) from " + clazz.getSimpleName() +" o where ";
        for(int i = 0; i< filterModels.size(); i++){
            FilterModel filterModel = filterModels.get(i);
            query = query + filterModel.getQuery() + " ";
            if (i != filterModels.size()-1) query = query + " and ";
        }

        System.out.println("select service is "+ query);
        return query;
    }


}
