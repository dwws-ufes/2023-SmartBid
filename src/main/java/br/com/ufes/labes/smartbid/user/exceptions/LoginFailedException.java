package br.com.ufes.labes.smartbid.user.exceptions;

public class LoginFailedException extends Exception {
    /** Reason for the failed login. */
    private LoginFailedReason reason;

    public LoginFailedException(LoginFailedReason reason) {
        this.reason = reason;
    }

    public LoginFailedException(Throwable t, LoginFailedReason reason) {
        super(t);
        this.reason = reason;
    }

    public LoginFailedReason getReason() {
        return reason;
    }

    /**
     * Enumeration of the reasons that an authentication might fail.
     *
     * @author Vítor E. Silva Souza (http://www.inf.ufes.br/~vitorsouza/)
     */
    public enum LoginFailedReason {
        /**
         * The provided username is unknown, i.e., there's no user with the given username in the
         * database.
         */
        UNKNOWN_USERNAME("unknownUsername"),

        /**
         * The provided password is incorrect. The user with the given username has a different
         * password.
         */
        INCORRECT_PASSWORD("incorrectPassword"),

        /** Multiple users with the given username exist in the database, which is an inconsistency! */
        MULTIPLE_USERS("multipleUsers"),

        /**
         * Marvin itself is OK with the authentication, but the Jakarta EE container is not, responding
         * with an exception.
         */
        CONTAINER_REJECTED("containerRejected"),

        /**
         * Marvin could not check with the container if the authentication is OK because the HTTP
         * request doesn't exist.
         */
        INFRASTRUCTURE_PROBLEMS("infrastructureProblems");

        /** A unique identifier for the reason. */
        private String id;

        private LoginFailedReason(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
