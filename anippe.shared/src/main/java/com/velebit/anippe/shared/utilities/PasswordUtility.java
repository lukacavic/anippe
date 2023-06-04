package com.velebit.anippe.shared.utilities;

import org.eclipse.scout.rt.platform.util.StringUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

public class PasswordUtility {
    public static final int PASSWORD_LENGTH_MIN = 4;
    public static final int PASSWORD_LENGTH_MAX = 64;

    private static Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");

    private static final Logger LOG = LoggerFactory.getLogger(PasswordUtility.class);

    public static boolean matchesPasswordPolicy(String password) {
        if (!StringUtility.hasText(password)) {
            LOG.error("Empty plain password provided");
            return false;
        }

        if (password.length() < PASSWORD_LENGTH_MIN || password.length() > PASSWORD_LENGTH_MAX) {
            LOG.error("Provided password is too short or too long");
            return false;
        }

        return true;
    }

    /**
     * Provides a password hash for the provided plain text password. Hashing is
     * done using BCrypt
     */
    public static String calculateEncodedPassword(String passwordPlain) {
        return BCrypt.hashpw(passwordPlain, BCrypt.gensalt());
    }

    public static boolean passwordIsValid(String passwordPlain, String passwordEncoded) {
        if (!StringUtility.hasText(passwordPlain)) {
            LOG.error("Empty plain password");
            return false;
        }

        if (!StringUtility.hasText(passwordEncoded)) {
            LOG.error("Empty encoded password");
            return false;
        }

        if (!BCRYPT_PATTERN.matcher(passwordEncoded).matches()) {
            LOG.warn("Encoded password does not look like BCrypt");
            return false;
        }

        return BCrypt.checkpw(passwordPlain.toString(), passwordEncoded);
    }
}
