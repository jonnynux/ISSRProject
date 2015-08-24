package ui.subwindow;

import com.bibounde.vprotovis.BarChartComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import enums.IndexesType;
import model.Index;
import ui.MyApplication;
import utils.Utility;

/**
 * Subwindow for viewing barchart for single product indexes
 *
 * @author jonny
 */
public class BarChartSW extends Window {

    public BarChartSW(IndexesType indexesType, Index index) {
        super("Indici " + index.getType() + " " + index.getBrand());

        this.setModal(true);
        this.setReadOnly(true);

        final Window window = this;

        Button close = new Button("Chiudi", new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
                MyApplication.getProject().getMainWindow().removeWindow(window);
            }
        });

        BarChartComponent bar = new BarChartComponent();

        bar.setGroupNames(new String[]{"Restituzione", "Successo", "Scadenza", "Gradimento"});

        bar.addSerie("Master", new double[]{Utility.Trunc(index.getReturnedIndex()),
                    Utility.Trunc(index.getSuccessIndex()), Utility.Trunc(index.getExpiredIndex()),
                    Utility.Trunc(index.getApprovalIndex())});

        if (indexesType == IndexesType.LOCAL) {
            bar.addSerie("Temporali", new double[]{Utility.Trunc(index.getReturnedTempIndex()),
                        Utility.Trunc(index.getSuccessTempIndex()), Utility.Trunc(index.getExpiredTempIndex()),
                        Utility.Trunc(index.getApprovalTempIndex())});
        }

        bar.setChartWidth(700d);
        bar.setChartHeight(400d);

        bar.setMarginLeft(50d);
        bar.setMarginBottom(50d);

        bar.setXAxisVisible(true);
        bar.setXAxisLabelVisible(true);

        bar.setYAxisVisible(true);
        bar.setYAxisLabelVisible(true);
        bar.setYAxisLabelStep(1d);
        bar.setYAxisGridVisible(true);

        bar.setLegendVisible(true);
        bar.setLegendAreaWidth(150d);

        bar.setTooltipEnabled(true);

        VerticalLayout l = (VerticalLayout) this.getContent();
        l.setMargin(true);
        l.setSpacing(true);
        l.setSizeUndefined();
        l.addComponent(bar);
        l.addComponent(close);
    }
}
