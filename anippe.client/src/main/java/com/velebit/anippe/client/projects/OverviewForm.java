package com.velebit.anippe.client.projects;

import com.velebit.anippe.client.projects.OverviewForm.MainBox.GroupBox;
import com.velebit.anippe.client.projects.OverviewForm.MainBox.GroupBox.OverviewTileField.TileGrid;
import com.velebit.anippe.shared.projects.IOverviewService;
import com.velebit.anippe.shared.projects.OverviewFormData;
import com.velebit.anippe.shared.projects.Project;
import org.eclipse.scout.rt.chart.client.ui.form.fields.tile.AbstractChartTile;
import org.eclipse.scout.rt.chart.shared.data.basic.chart.*;
import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
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
import java.util.Map;

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

                    @Order(200)
                    public class LeadsOverviewTile extends AbstractChartTile {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("LeadsOverview");
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 1;
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 1;
                        }

                        @Override
                        protected void execInitTile() {
                            ChartData cData = new ChartData();
                            Map<String, Integer> data = BEANS.get(IOverviewService.class).fetchLeadsByStatus(getProject().getId());

                            List<IChartAxisBean> axis = new ArrayList<>();
                            data.keySet().forEach(label -> axis.add(new ChartAxisBean(label, label)));
                            cData.getAxes().add(axis);

                            MonupleChartValueGroupBean total = new MonupleChartValueGroupBean();
                            total.setGroupName(TEXTS.get("Total"));

                            data.values().forEach(value -> total.getValues().add(BigDecimal.valueOf(value.longValue())));

                            cData.getChartValueGroups().add(total);

                            getChart().setData(cData);
                            getChart().setConfig(BEANS.get(IChartConfig.class).withType(IChartType.PIE).withLegendPosition(IChartConfig.LEFT));
                        }

                    }


                    @Order(300)
                    public class TicketsByStatusTile extends AbstractChartTile {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("TicketsOverview");
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 1;
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 1;
                        }

                        @Override
                        protected void execInitTile() {
                            ChartData cData = new ChartData();
                            Map<String, Integer> data = BEANS.get(IOverviewService.class).fetchTicketsByStatus(getProject().getId());

                            List<IChartAxisBean> axis = new ArrayList<>();
                            data.keySet().forEach(label -> axis.add(new ChartAxisBean(label, label)));
                            cData.getAxes().add(axis);

                            MonupleChartValueGroupBean total = new MonupleChartValueGroupBean();
                            total.setGroupName(TEXTS.get("Total"));

                            data.values().forEach(value -> total.getValues().add(BigDecimal.valueOf(value.longValue())));

                            cData.getChartValueGroups().add(total);

                            getChart().setData(cData);
                            getChart().setConfig(BEANS.get(IChartConfig.class).withType(IChartType.PIE).withLegendPosition(IChartConfig.LEFT));
                        }

                    }

                    @Order(400)
                    public class TicketsByAssignedUserTile extends AbstractChartTile {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("TicketsByAssignedUser");
                        }

                        @Override
                        protected int getConfiguredGridH() {
                            return 3;
                        }

                        @Override
                        protected int getConfiguredGridW() {
                            return 2;
                        }

                        @Override
                        protected void execInitTile() {
                            ChartData cData = new ChartData();
                            Map<String, Integer> data = BEANS.get(IOverviewService.class).fetchTicketsByAssignedUser(getProject().getId());

                            List<IChartAxisBean> axis = new ArrayList<>();
                            data.keySet().forEach(label -> axis.add(new ChartAxisBean(label, label)));
                            cData.getAxes().add(axis);

                            MonupleChartValueGroupBean total = new MonupleChartValueGroupBean();
                            total.setGroupName(TEXTS.get("Total"));

                            data.values().forEach(value -> total.getValues().add(BigDecimal.valueOf(value.longValue())));

                            cData.getChartValueGroups().add(total);

                            getChart().setData(cData);
                            getChart().setConfig(BEANS.get(IChartConfig.class).withType(IChartType.PIE).withLegendPosition(IChartConfig.LEFT));
                        }

                    }

                    @Order(500)
                    public class MonthlyCreatedTicketsTile extends AbstractChartTile {
                        @Override
                        protected String getConfiguredLabel() {
                            return TEXTS.get("MonthlyCreatedTickets");
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
                            ChartData cData = new ChartData();
                            Map<String, Integer> data = BEANS.get(IOverviewService.class).fetchTicketsByMonth(getProject().getId());

                            List<IChartAxisBean> axis = new ArrayList<>();
                            data.keySet().forEach(label -> axis.add(new ChartAxisBean(label, label)));
                            cData.getAxes().add(axis);

                            MonupleChartValueGroupBean total = new MonupleChartValueGroupBean();
                            total.setGroupName(TEXTS.get("Total"));

                            data.values().forEach(value -> total.getValues().add(BigDecimal.valueOf(value.longValue())));

                            cData.getChartValueGroups().add(total);

                            getChart().setData(cData);
                            getChart().setConfig(BEANS.get(IChartConfig.class).withType(IChartType.BAR).withLegendPosition(IChartConfig.BOTTOM));
                        }

                    }

                }
            }
        }
    }


}
