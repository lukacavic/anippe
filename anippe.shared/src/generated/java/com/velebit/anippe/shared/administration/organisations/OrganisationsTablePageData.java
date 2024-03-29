package com.velebit.anippe.shared.administration.organisations;

import javax.annotation.Generated;

import org.eclipse.scout.rt.shared.data.basic.table.AbstractTableRowData;
import org.eclipse.scout.rt.shared.data.page.AbstractTablePageData;

/**
 * <b>NOTE:</b><br>
 * This class is auto generated by the Scout SDK. No manual modifications
 * recommended.
 */
@Generated(value = "com.velebit.anippe.client.administration.organisations.OrganisationsTablePage", comments = "This class is auto generated by the Scout SDK. No manual modifications recommended.")
public class OrganisationsTablePageData extends AbstractTablePageData {
	private static final long serialVersionUID = 1L;

	@Override
	public OrganisationsTableRowData addRow() {
		return (OrganisationsTableRowData) super.addRow();
	}

	@Override
	public OrganisationsTableRowData addRow(int rowState) {
		return (OrganisationsTableRowData) super.addRow(rowState);
	}

	@Override
	public OrganisationsTableRowData createRow() {
		return new OrganisationsTableRowData();
	}

	@Override
	public Class<? extends AbstractTableRowData> getRowType() {
		return OrganisationsTableRowData.class;
	}

	@Override
	public OrganisationsTableRowData[] getRows() {
		return (OrganisationsTableRowData[]) super.getRows();
	}

	@Override
	public OrganisationsTableRowData rowAt(int index) {
		return (OrganisationsTableRowData) super.rowAt(index);
	}

	public void setRows(OrganisationsTableRowData[] rows) {
		super.setRows(rows);
	}

	public static class OrganisationsTableRowData extends AbstractTableRowData {
		private static final long serialVersionUID = 1L;
		public static final String organisationId = "organisationId";
		public static final String name = "name";
		private Integer m_organisationId;
		private String m_name;

		public Integer getOrganisationId() {
			return m_organisationId;
		}

		public void setOrganisationId(Integer newOrganisationId) {
			m_organisationId = newOrganisationId;
		}

		public String getName() {
			return m_name;
		}

		public void setName(String newName) {
			m_name = newName;
		}
	}
}
