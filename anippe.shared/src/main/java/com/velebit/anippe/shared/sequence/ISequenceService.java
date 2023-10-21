package com.velebit.anippe.shared.sequence;

import org.eclipse.scout.rt.platform.service.IService;
import org.eclipse.scout.rt.shared.TunnelToServer;

@TunnelToServer
public interface ISequenceService extends IService {

	Integer getSequence(String identifier, String code);

	Integer getSequenceYearly(String identifier, String code);

	Integer getSequenceMonthlyYearly(String identifier, String code);

	Integer getSequence(String complaint, String string, String formatType);
}
