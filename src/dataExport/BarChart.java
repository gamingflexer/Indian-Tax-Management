package dataExport;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import dataManagement.Person;

public class BarChart extends JFrame {

	public BarChart(String applicationTitle, String chartTitle, Person person) {

		super(applicationTitle);
		CategoryDataset dataset = createDataset(person);
		JFreeChart chart = createChart(dataset, chartTitle);
		ChartPanel chartPanel = new ChartPanel(chart);
		this.setBounds(200, 200, 750, 400);
		chartPanel.setPreferredSize(new java.awt.Dimension(680, 420));
		setContentPane(chartPanel);
		this.pack();
		this.setVisible(true);
	}

	private CategoryDataset createDataset(Person person) {

		DefaultCategoryDataset result = new DefaultCategoryDataset();
		result.setValue(person.calculateTaxBeforeReceipts(), "Basic Tax", "Tax Calculation");
		Double taxIncrease = (person.calculateFinalTax() - person.calculateTaxBeforeReceipts());
		result.setValue(taxIncrease, "Tax Increase", "Tax Calculation");
		result.setValue(person.calculateFinalTax(), "Total Tax", "Tax Calculation");
		return result;
	}

	private JFreeChart createChart(CategoryDataset dataset, String title) {

		JFreeChart barChart = ChartFactory.createBarChart(title, null, "Amount", dataset);

		return barChart;
	}
	
}
