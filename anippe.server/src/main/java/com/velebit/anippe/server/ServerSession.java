package com.velebit.anippe.server;

import com.velebit.anippe.shared.auth.ILoginService;
import com.velebit.anippe.shared.organisations.Organisation;
import com.velebit.anippe.shared.beans.User;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.server.AbstractServerSession;
import org.eclipse.scout.rt.server.session.ServerSessionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lukacavic
 */
public class ServerSession extends AbstractServerSession {

  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LoggerFactory.getLogger(ServerSession.class);

  public ServerSession() {
    super(true);
  }

  /**
   * @return The {@link ServerSession} which is associated with the current thread, or {@code null} if not found.
   */
  public static ServerSession get() {
    return ServerSessionProvider.currentSession(ServerSession.class);
  }

  @Override
  protected void execLoadSession() {
    LOG.info("created a new session for {}", getUserId());

		// Set current user
		User currentUser = BEANS.get(ILoginService.class).getUserByUsername(getUserId());
		setCurrentUser(currentUser);

		// Set current organisation
		if (!currentUser.isSuperAdministrator()) {
			Organisation currentOrganisation = BEANS.get(ILoginService.class).getCurrentOrganisation(currentUser.getOrganisation().getId());
			setCurrentOrganisation(currentOrganisation);
		}
  }

	// User
	public void setCurrentUser(User user) {
		setSharedContextVariable("CURRENT_USER", User.class, user);
	}

	public User getCurrentUser() {
		return getSharedContextVariable("CURRENT_USER", User.class);
	}

	// Organisation
	public void setCurrentOrganisation(Organisation organisation) {
		setSharedContextVariable("CURRENT_ORGANISATION", Organisation.class, organisation);
	}

	public Organisation getCurrentOrganisation() {
		return getSharedContextVariable("CURRENT_ORGANISATION", Organisation.class);
	}
}
