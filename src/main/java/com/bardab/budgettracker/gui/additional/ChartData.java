package com.bardab.budgettracker.gui.additional;

import com.bardab.budgettracker.dao.TransactionDao;
import com.bardab.budgettracker.model.Transaction;
import com.bardab.budgettracker.model.additional.Category;
import com.bardab.budgettracker.model.additional.CategoryFormatter;
import com.bardab.budgettracker.util.HibernateUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import org.hibernate.SessionFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ChartData {


    private static SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    private static TransactionDao transactionDao = new TransactionDao(sessionFactory);


    public static HashMap<Category, Double> expenseCategoriesWithValuesForPieChart(LocalDate dateFrom, LocalDate dateTo, List<Category> categories) {
        List<Transaction> transactions = transactionDao.getAllTransactions(dateFrom, dateTo, categories);
        transactions.forEach(e -> System.out.println(e.getTransactionDate()));
        HashMap<Category, Double> categoriesWithValues = new HashMap<>();
        for (Category category : categories) {
            Double incrementedValue = 0.0;
            for (Transaction transaction : transactions) {
                if (transaction.getCategory().equals(category)) {
                    incrementedValue = (incrementedValue + transaction.getValue());
                }
            }
            categoriesWithValues.put(category, DoubleFormatter.round(incrementedValue, 2));
        }
        return categoriesWithValues;
    }

    public static HashMap<Category, LinkedHashMap<LocalDate, Double>> expenseCategoriesWithDailyTotalValues(LocalDate dateFrom, LocalDate dateTo, List<Category> listOfCategories) {
        List<Transaction> transactions = transactionDao.getAllTransactions(dateFrom, dateTo, listOfCategories);
        HashMap<Category, LinkedHashMap<LocalDate, Double>> categoriesWithDailyValues = new HashMap<>();
        LinkedHashMap<LocalDate, Double> map;
        for (Category category : listOfCategories) {
            map = new LinkedHashMap<>();
            for (Transaction transaction : transactions) {
                if (transaction.getCategory().equals(category)) {
                    if (map.get(transaction.getTransactionDate()) == null) {
                        map.put(transaction.getTransactionDate(), DoubleFormatter.round(transaction.getValue(), 2));
                    } else {
                        Double incrementedValue = DoubleFormatter.round((map.get(transaction.getTransactionDate()) + transaction.getValue()), 2);
                        map.put(transaction.getTransactionDate(), incrementedValue);
                    }
                }
            }
            categoriesWithDailyValues.put(category, map);
        }
        return categoriesWithDailyValues;
    }


    public static ArrayList<String> getListOfDatesInSpecificTime(LocalDate fromDate, LocalDate toDate) {
        ArrayList<String> datesInString = new ArrayList<>();
        Object[] dates = fromDate.datesUntil(toDate).toArray();
        for (Object object : dates) {
            datesInString.add(object.toString());
        }
        datesInString.add(toDate.toString());

        return datesInString;

    }

    public static List<XYChart.Series<String, Number>> getListOfSeriesForXYChart(LocalDate dateFrom, LocalDate dateTo, List<Category> categories) {
        HashMap<Category, LinkedHashMap<LocalDate, Double>> mapHashMap = expenseCategoriesWithDailyTotalValues(dateFrom, dateTo, categories);
        List<XYChart.Series<String, Number>> listOfSeries = new ArrayList<>();
        for (Category category : mapHashMap.keySet()) {
            HashMap<LocalDate, Double> dateDoubleHashMap = mapHashMap.get(category);


            XYChart.Series series = new XYChart.Series();
            series.setName(category.name());
            int i = 0;
            for (LocalDate localDate : dateDoubleHashMap.keySet()) {
                XYChart.Data data = new XYChart.Data(localDate.toString(), dateDoubleHashMap.get(localDate));
                series.getData().add(data);

            }
//            series.getData().sort(Comparator.comparingInt());
            listOfSeries.add(series);
        }
        return listOfSeries;
    }

    public static ObservableList<PieChart.Data> getSeriesForPieChart(LocalDate dateFrom, LocalDate dateTo, List<Category> categories) {
        ArrayList<PieChart.Data> pieChartData = new ArrayList<>();
        expenseCategoriesWithValuesForPieChart(dateFrom, dateTo, categories).forEach(
                (e, f) -> pieChartData.add(new PieChart.Data(CategoryFormatter.getCategoryNameInPresentable(e), f))
        );
        return FXCollections.observableArrayList(pieChartData);
    }

    public static ObservableList<PieChart.Data> getSeriesForPieChart(List<Transaction> transactions) {
        List<Category> categories = new ArrayList<>();
        LocalDate dateFrom = getLatestDate(transactions);
        LocalDate dateTo = getEarliestDate(transactions);

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction =  transactions.get(i);
            if (!categories.contains(transaction.getCategory())) {
                categories.add(transaction.getCategory());
            }
        }

        return getSeriesForPieChart(dateFrom, dateTo, categories);
    }

    public static List<XYChart.Series<String, Number>> getListOfSeriesForXYChart(List<Transaction> transactions) {
        List<Category> categories = new ArrayList<>();
        LocalDate dateFrom = getLatestDate(transactions);
        LocalDate dateTo = getEarliestDate(transactions);

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction =  transactions.get(i);
            if (!categories.contains(transaction.getCategory())) {
                categories.add(transaction.getCategory());
            }
        }

        return getListOfSeriesForXYChart(dateFrom, dateTo, categories);
    }


    public static LocalDate getEarliestDate(List<Transaction> transactions){
        try{
        LocalDate date = transactions.get(0).getTransactionDate();


        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);

            if (transaction.getTransactionDate().compareTo(date) > 0) {
                date = transaction.getTransactionDate();
            }


        }
        return date;
        }
        catch (IndexOutOfBoundsException e){
            return  LocalDate.now();
        }

    }
    public static LocalDate getLatestDate(List<Transaction> transactions){
        try {
            LocalDate date = transactions.get(0).getTransactionDate();


            for (int i = 0; i < transactions.size(); i++) {
                Transaction transaction = transactions.get(i);

                if (transaction.getTransactionDate().compareTo(date) < 0) {
                    date = transaction.getTransactionDate();
                }


            }
            return date;
        }
        catch (IndexOutOfBoundsException e){
           return  LocalDate.now();
        }
    }


}
