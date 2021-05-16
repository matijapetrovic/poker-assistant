package ww10.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Layer;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;

public class AverageProfitPanel extends ChartPanel {

	private static final long serialVersionUID = 263366236582410291L;

	private final HashMap<String, XYSeries> playerToXYSeries;

	private final XYPlot xyPlot;

	public AverageProfitPanel(DataModel dataModel) {
		super(null);

		String[] playerNames = dataModel.getPlayerNames();

		// create chart
		playerToXYSeries = new HashMap<String, XYSeries>();
		for (String playerName : playerNames) {
			playerToXYSeries.put(playerName, new XYSeries(playerName));
			//TODO setDescription
		}

		XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
		for (String playerName : playerNames) {
			xySeriesCollection.addSeries(playerToXYSeries.get(playerName));
		}

		JFreeChart chart = ChartFactory.createXYLineChart(null, "Games", "Average Profit", xySeriesCollection, PlotOrientation.VERTICAL, true, false, false);
		//		chart.setBackgroundPaint(Color.WHITE);
		xyPlot = chart.getXYPlot();
		xyPlot.setBackgroundPaint(Color.WHITE);
		XYItemRenderer xyir = xyPlot.getRenderer();//.get.setOutlineStroke()
		try {
			xyir.setBaseStroke(new BasicStroke(3));
			// bug workaround
			((AbstractRenderer) xyir).setAutoPopulateSeriesStroke(false);

			//			xyir.setSeriesStroke(new BasicStroke(5));
			//			xyir.setSeriesStroke(0, ); //series line style
		} catch (Exception e) {
			System.err.println("Error setting style: " + e);
		}
		//		xyPlot.setDomainGridlinesVisible(true);
		xyPlot.setRangeGridlinesVisible(true);
		//		xyPlot.setDomainGridlinePaint(Color.black);
		xyPlot.setRangeGridlinePaint(Color.GRAY);
		xyPlot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

		//		// create some Pointers to the final bankrolls
		//		for (String playerName : playerNames) {
		//			double finalBankroll = playerToBankRoll.get(playerName);
		//			DecimalFormat moneyFormat = new DecimalFormat("0.00");
		//			String resultString = getChosenName(playerName) + ": € " + moneyFormat.format(finalBankroll / (snapshotCurrentGamesPlayed)) + "/game)";
		//			final XYPointerAnnotation pointer = new XYPointerAnnotation(resultString, Math.min(snapshotCurrentGamesPlayed, numGames), finalBankroll,
		//					Math.PI * 5.9 / 6);
		//			pointer.setBaseRadius(130.0);
		//			pointer.setTipRadius(1.0);
		//			pointer.setLabelOffset(10.0);
		//			pointer.setOutlineVisible(true);
		//			pointer.setBackgroundPaint(Color.WHITE);
		//			chart.getXYPlot().addAnnotation(pointer);
		//
		//		}

		// after the first permutation the next permutations get
		// merges with the existing data. We show a marker, what
		// data is already merged
		//		final Marker permutationEnd = new ValueMarker(snapshotCurrentGamesPlayed % numGames);
		//		permutationEnd.setLabel((currentSeatPermutation + 1) + " permutation(s)");
		//		permutationEnd.setLabelAnchor(RectangleAnchor.TOP_LEFT);
		//		permutationEnd.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
		//		chart.getXYPlot().addDomainMarker(permutationEnd);
		chart.getLegend().setPosition(RectangleEdge.RIGHT);

		this.setFillZoomRectangle(true);
		setBorder(BorderFactory.createLoweredBevelBorder());
		this.setChart(chart);
	}

	public void addDataPoints(int game, Map<String, Double> avgProfits) {
		for (Entry<String, Double> entry : avgProfits.entrySet()) {
			playerToXYSeries.get(entry.getKey()).add(game, entry.getValue());
		}
	}

	public void removeFirstDataPoints(int n) {
		Collection<XYSeries> series = playerToXYSeries.values();
		for (XYSeries s : series) {
			s.delete(0, n - 1);
		}
	}

	public void addMarker(int fCurrentGamesPlayed, String msg) {
		final Marker submission = new ValueMarker(fCurrentGamesPlayed);
		submission.setLabel(msg);
		submission.setLabelFont(new Font("sansserif", Font.PLAIN, 20));
		submission.setStroke(new BasicStroke(3));
		submission.setLabelAnchor(RectangleAnchor.TOP_LEFT);
		submission.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
		xyPlot.addDomainMarker(submission);
	}

	public void removeFirstMarkers(int nbMarkers) {
		if (nbMarkers > 0) {
			Object[] markers = xyPlot.getDomainMarkers(Layer.FOREGROUND).toArray();
			for (int i = 0; i < nbMarkers; i++) {
				xyPlot.removeDomainMarker((Marker) markers[i]);
			}
		}
	}

}