package com.bardab.budgettracker.gui;

import com.bardab.budgettracker.dao.TransactionDao;
import com.bardab.budgettracker.model.Transaction;
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


    public static HashMap<String, Double> expenseCategoriesWithValuesForPieChart(LocalDate dateFrom, LocalDate dateTo, List<String> categories) {
        List<Transaction> transactions = transactionDao.getAllTransactions(dateFrom, dateTo, categories);
        transactions.forEach(e -> System.out.println(e.getTransactionDate()));
        HashMap<String, Double> categoriesWithValues = new HashMap<>();
        for (String category : categories) {
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

    public static HashMap<String, LinkedHashMap<LocalDate, Double>> expenseCategoriesWithDailyTotalValues(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories) {
        List<Transaction> transactions = transactionDao.getAllTransactions(dateFrom, dateTo, listOfCategories);
        HashMap<String, LinkedHashMap<LocalDate, Double>> categoriesWithDailyValues = new HashMap<>();
        LinkedHashMap<LocalDate, Double> map;
        for (String category : listOfCategories) {
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

    public static List<XYChart.Series<String, Number>> getListOfSeriesForXYChart(LocalDate dateFrom, LocalDate dateTo, List<String> categories) {
        HashMap<String, LinkedHashMap<LocalDate, Double>> mapHashMap = expenseCategoriesWithDailyTotalValues(dateFrom, dateTo, categories);
        List<XYChart.Series<String, Number>> listOfSeries = new ArrayList<>();
        for (String category : mapHashMap.keySet()) {
            HashMap<LocalDate, Double> dateDoubleHashMap = mapHashMap.get(category);


            XYChart.Series series = new XYChart.Series();
            series.setName(category);
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

    public static ObservableList<PieChart.Data> getSeriesForPieChart(LocalDate dateFrom, LocalDate dateTo, List<String> categories) {
        ArrayList<PieChart.Data> pieChartData = new ArrayList<>();
        expenseCategoriesWithValuesForPieChart(dateFrom, dateTo, categories).forEach(
                (e, f) -> pieChartData.add(new PieChart.Data(e, f))
        );
        return FXCollections.observableArrayList(pieChartData);
    }

    public static ObservableList<PieChart.Data> getSeriesForPieChart(List<Transaction> transactions) {
        List<String> categories = new ArrayList<>();
        LocalDate dateFrom = getLatestDate(transactions);
        LocalDate dateTo = getEarliestDate(transactions);

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);


            if (!categories.contains(transaction.getCategory())) {
                categories.add(transaction.getCategory());
            }
        }

        return getSeriesForPieChart(dateFrom, dateTo, categories);
    }

    public static List<XYChart.Series<String, Number>> getListOfSeriesForXYChart(List<Transaction> transactions) {
        List<String> categories = new ArrayList<>();
        LocalDate dateFrom = getLatestDate(transactions);
        LocalDate dateTo = getEarliestDate(transactions);

        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            if (!categories.contains(transaction.getCategory())) {
                categories.add(transaction.getCategory());
            }
        }

        return getListOfSeriesForXYChart(dateFrom, dateTo, categories);
    }


    public static LocalDate getEarliestDate(List<Transaction> transactions){
        LocalDate date = transactions.get(0).getTransactionDate();


        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);

            if (transaction.getTransactionDate().compareTo(date) < 0) {
                date = transaction.getTransactionDate();
            }


        }
        return date;

    }
    public static LocalDate getLatestDate(List<Transaction> transactions){
        LocalDate date = transactions.get(0).getTransactionDate();


        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);

            if (transaction.getTransactionDate().compareTo(date) > 0) {
                date = transaction.getTransactionDate();
            }


        }
        return date;

    }


}
