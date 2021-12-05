package dataExport;

import java.util.ArrayList;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import dataManagement.Person;
import dataManagement.Receipt;

public class PieChart extends JFrame {

	private int xPosition = 200;
	private int yPosition = 200;
	private int windowWidth = 750;
	private int windowHeight = 400;

	public PieChart(String applicationTitle, String chartTitle, Person person) {

		super(applicationTitle);
		PieDataset dataset = createDataset(person);
		JFreeChart chart = createChart(dataset, chartTitle);
		ChartPanel chartPanel = new ChartPanel(chart);
		this.setBounds(xPosition, yPosition, windowWidth, windowHeight);
		chartPanel.setPreferredSize(new java.awt.Dimension(680, 420));
		setContentPane(chartPanel);
		this.pack();
		this.setVisible(true);
	}

	private PieDataset createDataset(Person person) {

		ArrayList<Receipt> receipts = person.getReceiptsList();
		DefaultPieDataset result = new DefaultPieDataset();

		setValuesToDataset(receipts, result);

		return result;
	}

	private void setValuesToDataset(ArrayList<Receipt> receipts, DefaultPieDataset result) {

		if (!receipts.isEmpty()) {
			ArrayList<String> categories = receipts.get(0).getCategories();

			for (String category : categories) {
				String pieChartString = getCategoryValueString(receipts, category);
				Double totalCategoryValue = getTotalCategoryValue(receipts, category);
				result.setValue(pieChartString, totalCategoryValue);
			}
		}
	}

	private String getCategoryValueString(ArrayList<Receipt> receipts, String category) {

		String output = "";
		output += category + ": " + getTotalCategoryValue(receipts, category) + " $";
		return (output);
	}

	private Double getTotalCategoryValue(ArrayList<Receipt> receipts, String selectedCategory) {

		Double value = 0d;
		for (Receipt current : receipts) {
			if (current.getCategory().equals(selectedCategory)) {
				value += current.getAmount();
			}
		}
		return value;
	}

	private JFreeChart createChart(PieDataset dataset, String title) {

		JFreeChart chart = ChartFactory.createPieChart3D(title, dataset, true, true, false);

		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}