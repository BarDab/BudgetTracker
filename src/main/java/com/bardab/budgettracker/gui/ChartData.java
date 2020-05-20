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
import java.util.List;

public class ChartData {


    private static SessionFactory sessionFactory = HibernateUtil.getInstance().getSessionFactory();
    private static TransactionDao transactionDao = new TransactionDao(sessionFactory);


    public static HashMap<String, Double> expenseCategoriesWithValuesForPieChart(LocalDate dateFrom, LocalDate dateTo, List<String> categories) {
        List<Transaction> transactions = transactionDao.getAllTransactions(dateFrom, dateTo, categories);
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

    public static HashMap<String, HashMap<LocalDate, Double>> expenseCategoriesWithDailyTotalValues(LocalDate dateFrom, LocalDate dateTo, List<String> listOfCategories) {
        List<Transaction> transactions = transactionDao.getAllTransactions(dateFrom, dateTo, listOfCategories);
        HashMap<String, HashMap<LocalDate, Double>> categoriesWithDailyValues = new HashMap<>();
        HashMap<LocalDate, Double> map;
        for (String category : listOfCategories) {
            map = new HashMap<>();
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


    public static List<XYChart.Series<String, Double>> getListOfSeriesForLineChart(LocalDate dateFrom, LocalDate dateTo, List<String> categories) {
        HashMap<String, HashMap<LocalDate, Double>> mapHashMap = expenseCategoriesWithDailyTotalValues(dateFrom, dateTo, categories);
        List<XYChart.Series<String, Double>> listOfSeries = new ArrayList<>();
        for (String category : mapHashMap.keySet()) {
            HashMap<LocalDate, Double> dateDoubleHashMap = mapHashMap.get(category);
            dateDoubleHashMap.forEach((e, f) -> System.out.println(category + e.toString() + f.toString()));
            XYChart.Series series = new XYChart.Series();
            series.setName(category);
            int i = 0;
            for (LocalDate localDate : dateDoubleHashMap.keySet()) {
                XYChart.Data data = new XYChart.Data(localDate.toString(), dateDoubleHashMap.get(localDate));
                series.getData().add(data);

            }
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


}
