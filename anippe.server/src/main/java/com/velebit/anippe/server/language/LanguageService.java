package com.velebit.anippe.server.language;

import com.velebit.anippe.server.AbstractService;
import com.velebit.anippe.shared.language.ILanguageService;
import com.velebit.anippe.shared.language.Language;
import org.eclipse.scout.rt.platform.holders.BeanArrayHolder;
import org.eclipse.scout.rt.platform.holders.NVPair;
import org.eclipse.scout.rt.server.jdbc.SQL;

import java.util.Arrays;
import java.util.List;

public class LanguageService extends AbstractService implements ILanguageService {

	@Override
	public List<Language> all() {
		BeanArrayHolder<Language> rowData = new BeanArrayHolder<>(Language.class);

		StringBuffer varname1 = new StringBuffer();
		varname1.append("SELECT   id, ");
		varname1.append("         NAME, ");
		varname1.append("         code, ");
		varname1.append("         locale_code ");
		varname1.append("FROM     languages ");
		varname1.append("ORDER BY NAME ");
		varname1.append("into     :{row.id}, ");
		varname1.append("         :{row.name}, ");
		varname1.append("         :{row.code}, ");
		varname1.append("         :{row.localeCode}");
		SQL.selectInto(varname1.toString(), new NVPair("row", rowData));

		return Arrays.asList(rowData.getBeans());
	}

    @Override
    public Language getById(Integer languageId) {
        Language language = new Language();

        StringBuffer varname1 = new StringBuffer();
        varname1.append("SELECT   id, ");
        varname1.append("         name, ");
        varname1.append("         code, ");
        varname1.append("         locale_code ");
        varname1.append("FROM     languages ");
        varname1.append("WHERE id = :languageId ");
        varname1.append("ORDER BY NAME ");
        varname1.append("into     :{row.id}, ");
        varname1.append("         :{row.name}, ");
        varname1.append("         :{row.code}, ");
        varname1.append("         :{row.localeCode}");
        SQL.selectInto(varname1.toString(), new NVPair("row", language), new NVPair("languageId", languageId));

        return language.getId() != null ? language : null;
    }

}
