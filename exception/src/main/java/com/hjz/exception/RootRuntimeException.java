package com.hjz.exception;

import com.hjz.exception.utils.ExceptionUtils;

public abstract class RootRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -8531149904295764227L;

    static {
        // Eagerly load the NestedExceptionUtils class to avoid classloader deadlock
        // issues on OSGi when calling getMessage(). Reported by Don Brown; SPR-5607.
        ExceptionUtils.class.getName();
    }

    public RootRuntimeException() {
        super();
    }

    public RootRuntimeException(String message) {
        super(message);
    }

    public RootRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RootRuntimeException(Throwable cause) {
        super(cause);
    }

    protected RootRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Build a message for the given base message and root cause.
     *
     * @return the full exception message
     */
    public String getFullMessage() {
        return ExceptionUtils.buildMessage(super.getMessage(), getCause());
    }

    /**
     * Retrieve the innermost cause of this exception, if any.
     *
     * @return the innermost exception, or {@code null} if none
     */
    public Throwable getRootCause() {
        Throwable rootCause = null;
        Throwable cause = getCause();
        while (cause != null && cause != rootCause) {
            rootCause = cause;
            cause = cause.getCause();
        }
        return rootCause;
    }

    /**
     * Retrieve the most specific cause of this exception, that is,
     * either the innermost cause (root cause) or this exception itself.
     * <p>Differs from {@link #getRootCause()} in that it falls back
     * to the present exception if there is no root cause.
     *
     * @return the most specific cause (never {@code null})
     */
    public Throwable getMostSpecificCause() {
        Throwable rootCause = getRootCause();
        return (rootCause != null ? rootCause : this);
    }

    /**
     * Check whether this exception contains an exception of the given type:
     * either it is of the given class itself or it contains a nested cause
     * of the given type.
     *
     * @param exType the exception type to look for
     * @return whether there is a nested exception of the specified type
     */
    public boolean contains(Class<?> exType) {
        if (exType == null) {
            return false;
        }
        if (exType.isInstance(this)) {
            return true;
        }
        Throwable cause = getCause();
        if (cause == this) {
            return false;
        }
        if (cause instanceof RootRuntimeException) {
            return ((RootRuntimeException) cause).contains(exType);
        } else {
            while (cause != null) {
                if (exType.isInstance(cause)) {
                    return true;
                }
                if (cause.getCause() == cause) {
                    break;
                }
                cause = cause.getCause();
            }
            return false;
        }
    }
}
