package com.velebit.anippe.server.knowledgebase;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.knowledgebase.CategoriesFormData;
import com.velebit.anippe.shared.knowledgebase.ICategoriesService;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.List;

public class CategoriesService extends AbstractService implements ICategoriesService {
    @Override
    public CategoriesFormData prepareCreate(CategoriesFormData formData) {

        List<CategoriesFormData.CategoriesTable.CategoriesTableRowData> rows = fetchCategories(formData.getProjectId());
        formData.getCategoriesTable().setRows(rows.toArray(new CategoriesFormData.CategoriesTable.CategoriesTableRowData[0]));

        return formData;
    }

    @Override
    public CategoriesFormData create(CategoriesFormData formData) {
        return formData;
    }

    @Override
    public CategoriesFormData load(CategoriesFormData formData) {
        return formData;
    }

    @Override
    public CategoriesFormData store(CategoriesFormData formData) {
        return formData;
    }

    @Override
    public List<CategoriesFormData.CategoriesTable.CategoriesTableRowData> fetchCategories(Integer projectId) {
        BeanArrayHolder<CategoriesFormData.CategoriesTable.CategoriesTableRowData> holder = new BeanArrayHolder<>(CategoriesFormData.CategoriesTable.CategoriesTableRowData.class);

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   c.id, ");
        varname1.append("         c.NAME ");
        varname1.append("FROM     knowledge_categories c ");
        varname1.append("WHERE    c.deleted_at IS NULL ");
        varname1.append("AND      c.organisation_id = :organisationId ");

        if (projectId != null) {
            varname1.append(" AND c.project_id = :projectId ");
        }

        varname1.append("ORDER BY c.NAME ");
        varname1.append("into     :{rows.CategoryId}, ");
        varname1.append("         :{rows.Name}");
        SQL.selectInto(varname1.toString(), new NVPair("organisationId", getCurrentOrganisationId()), new NVPair("rows", holder), new NVPair("projectId", projectId));

        return CollectionUtility.arrayList(holder.getBeans());
    }

    @Override
    public void delete(Integer selectedValue) {
        SQL.update("UPDATE knowledge_categories SET deleted_at = now() WHERE id = :categoryId", new NVPair("categoryId", selectedValue));
    }
}
