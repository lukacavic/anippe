package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.projects.OverviewForm.MainBox.GroupBox;
import com.velebit.anippe.client.projects.OverviewForm.MainBox.GroupBox.OverviewTileField.TileGrid;
import com.velebit.anippe.shared.projects.IOverviewService;
import com.velebit.anippe.shared.projects.OverviewFormData;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.chart.client.ui.form.fields.tile.AbstractChartTile;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.ChartAxisBean;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.ChartData;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.IChartAxisBean;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.MonupleChartValueGroupBean;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.tilefield.AbstractTileField;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileGrid;
import org.eclipse.scout.rt.client.ui.tile.ITile;
import org.eclipse.scout.rt.client.ui.tile.TileGridLayoutConfig;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.shared.ui.UserAgentUtility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@FormData(value = OverviewFormData.class, sdkCommand = FormData.SdkCommand.CREATE)
public class OverviewForm extends AbstractForm {

    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    protected String getConfiguredTitle() {
        return TEXTS.get("Overview");
    }

    public MainBox getMainBox() {
        return getFieldByClass(MainBox.class);
    }

    public GroupBox getGroupBox() {
        return getFieldByClass(GroupBox.class);
    }

    @Order(1000)
    public class MainBox extends AbstractGroupBox {
        @Order(1000)
        public class GroupBox extends AbstractGroupBox {

            public class OverviewTileField extends AbstractTileField<TileGrid> {

                @Override
                protected boolean getConfiguredStatusVisible() {
                    return false;
                }

                @Override
                protected boolean getConfiguredLabelVisible() {
                    return false;
                }

                @Override
                protected int getConfiguredGridW() {
                    return FULL_WIDTH;
                }

                @Override
                protected boolean getConfiguredGridUseUiHeight() {
                    if (UserAgentUtility.isMobileDevice() || UserAgentUtility.isTabletDevice()) {
                        return true;
                    }
                    return false;
                }

                public class TileGrid extends AbstractTileGrid<ITile> {

                    @Override
                    protected int getConfiguredGridColumnCount() {
                        if (UserAgentUtility.isTabletDevice()) {
                            return 2;
                        }
                        return 4;
                    }

                    @Override
                    protected boolean getConfiguredScrollable() {

                        return true;
                    }

                    @Override
                    protected boolean getConfiguredAnimateTileInsertion() {
                        return true;
                    }

                    @Override
                    protected TileGridLayoutConfig getConfiguredLayoutConfig() {
                        return super.getConfiguredLayoutConfig().withHGap(10).withVGap(10).withRowHeight(140);

                    }

                    @Override
                    protected String getConfiguredLogicalGrid() {
                        return LOGICAL_GRID_HORIZONTAL;
                    }

                    @Order(400)
                    public class FinanceInvoiceStatus extends AbstractChartTile {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("InvoiceStatus");
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 2;
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 2;
                        }

                        @Override
                        protected void execInitTile() {
                            ChartData data = new ChartData();

                            List<IChartAxisBean> axis = new ArrayList<>();
                            Stream.of("Jan.", "Feb.", "Mar.", "Apr.", "May", "Jun.", "Jul.", "Aug.", "Sept.", "Oct.", "Nov.", "Dec.")
                                    .forEach(label -> axis.add(new ChartAxisBean(label, label)));

                            data.getAxes().add(axis);

                            MonupleChartValueGroupBean vanilla = new MonupleChartValueGroupBean();
                            vanilla.setGroupName("Vanilla");
                            IntStream.of(0, 0, 0, 94, 162, 465, 759, 537, 312, 106, 0, 0)
                                    .forEach(value -> vanilla.getValues().add(new BigDecimal(value)));
                            data.getChartValueGroups().add(vanilla);

                            MonupleChartValueGroupBean chocolate = new MonupleChartValueGroupBean();
                            chocolate.setGroupName("Chocolate");
                            IntStream.of(0, 0, 0, 81, 132, 243, 498, 615, 445, 217, 0, 0)
                                    .forEach(value -> chocolate.getValues().add(new BigDecimal(value)));
                            data.getChartValueGroups().add(chocolate);

                            MonupleChartValueGroupBean strawberry = new MonupleChartValueGroupBean();
                            strawberry.setGroupName("Strawberry");
                            IntStream.of(0, 0, 0, 59, 182, 391, 415, 261, 75, 31, 0, 0)
                                    .forEach(value -> strawberry.getValues().add(new BigDecimal(value)));
                            data.getChartValueGroups().add(strawberry);

                            getChart().setData(data);
                        }

                    }

                }
            }
        }
    }

    public void startModify() {
        startInternalExclusive(new ModifyHandler());
    }

    public void startNew() {
        startInternal(new NewHandler());
    }

    public class NewHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            OverviewFormData formData = new OverviewFormData();
            exportFormData(formData);
            formData = BEANS.get(IOverviewService.class).prepareCreate(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            OverviewFormData formData = new OverviewFormData();
            exportFormData(formData);
            formData = BEANS.get(IOverviewService.class).create(formData);
            importFormData(formData);
        }
    }

    public class ModifyHandler extends AbstractFormHandler {
        @Override
        protected void execLoad() {
            OverviewFormData formData = new OverviewFormData();
            exportFormData(formData);
            formData = BEANS.get(IOverviewService.class).load(formData);
            importFormData(formData);
        }

        @Override
        protected void execStore() {
            OverviewFormData formData = new OverviewFormData();
            exportFormData(formData);
            formData = BEANS.get(IOverviewService.class).store(formData);
            importFormData(formData);
        }
    }
}
