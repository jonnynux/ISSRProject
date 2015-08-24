package ui.subwindow;

import com.bibounde.vprotovis.PieChartComponent;
import com.bibounde.vprotovis.chart.pie.PieLabelFormatter;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import java.util.List;
import model.Index;
import ui.MyApplication;
import utils.Utility;

/**
 * Subwindow for viewing piechart for all product indexes
 *
 * @author jonny
 */
public class PieChartSW extends Window {

    public PieChartSW(List<Index> indexes, int buttonIndex, String buttonText) {
        super("Indici di " + buttonText);

        this.setModal(true);
        this.setReadOnly(true);

        final Window window = this;

        Button close = new Button("Chiudi", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                MyApplication.getProject().getMainWindow().removeWindow(window);
            }
        });

        PieChartComponent pie = new PieChartComponent();

        double total = 0;
        for (Index i : indexes) {

            double value = getIndexValue(i, buttonIndex);
            total += value;
            pie.addSerie(i.getType() + " " + i.getBrand(), value, false);
        }


        pie.setChartWidth(700d);
        pie.setChartHeight(400d);

        pie.setMarginLeft(50d);
        pie.setMarginBottom(50d);

        pie.setLegendVisible(true);
        pie.setLegendAreaWidth(150d);

        pie.setTooltipEnabled(true);

        pie.setLabelVisible(true);

        final double totaleFormatter = total;
        PieLabelFormatter labelFormatter = new PieLabelFormatter() {

            @Override
            public boolean isVisible(double labelValue) {
                return 0.05d <= labelValue / totaleFormatter;
            }

            @Override
            public String format(double labelValue) {
                int percent = Double.valueOf(labelValue / totaleFormatter * 100).intValue();
                return percent + "%";
            }
        };
        pie.setLabelFormatter(labelFormatter);

        pie.setLabelColor("#FFFFFF");


        VerticalLayout l = (VerticalLayout) this.getContent();
        l.setMargin(true);
        l.setSpacing(true);
        l.setSizeUndefined();
        l.addComponent(pie);
        l.addComponent(close);
    }

    private double getIndexValue(Index i, int buttonIndex) {

        double value;
        switch (buttonIndex) {
            case 0:
                value = i.getReturnedIndex();
                break;
            case 1:
                value = i.getSuccessIndex();
                break;
            case 2:
                value = i.getExpiredIndex();
                break;
            case 3:
                value = i.getApprovalIndex();
                break;
            case 4:
                value = i.getReturnedTempIndex();
                break;
            case 5:
                value = i.getSuccessTempIndex();
                break;
            case 6:
                value = i.getExpiredTempIndex();
                break;
            case 7:
                value = i.getApprovalTempIndex();
                break;
            default:
                value = 0;
        }

        return Utility.Trunc(value);
    }
}
