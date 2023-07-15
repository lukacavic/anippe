package com.velebit.anippe.client.projects;

import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.platform.html.HTML;
import org.eclipse.scout.rt.platform.html.IHtmlContent;

public class AbstractInformationField extends AbstractLabelField {

    private Integer count = 0;
    private String label = "";
    private String labelColor = "red";
    private String subLabel = "";

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getSubLabel() {
        return subLabel;
    }

    public void setSubLabel(String subLabel) {
        this.subLabel = subLabel;
    }

    @Override
    public boolean isLabelVisible() {
        return false;
    }

    @Override
    protected double getConfiguredGridWeightY() {
        return 0;
    }

    @Override
    protected boolean getConfiguredHtmlEnabled() {
        return true;
    }

    @Override
    protected int getConfiguredGridH() {
        return 2;
    }

    @Override
    protected void execInitField() {
        super.execInitField();

        IHtmlContent content = HTML.fragment(
                HTML.bold(getCount().toString()).style("font-size:18px;"),
                HTML.span(getLabel()).style("color:"+getLabelColor()+";font-size:14px;margin-left:10px;"),
                HTML.p(getSubLabel()).style("font-size:13px;margin-top:5px;margin-bottom:0px;"));

        setValue(content.toHtml());
    }
}

