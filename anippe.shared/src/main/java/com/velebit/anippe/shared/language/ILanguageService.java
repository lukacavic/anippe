package com.velebit.anippe.shared.language;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

import java.util.List;

@TunnelToServer
public interface ILanguageService extends IService {
	public List<Language> all();

    Language getById(Integer languageId);
}
