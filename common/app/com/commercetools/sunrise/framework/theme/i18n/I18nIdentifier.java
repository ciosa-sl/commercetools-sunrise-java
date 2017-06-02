package com.commercetools.sunrise.framework.theme.i18n;

import com.commercetools.sunrise.framework.SunriseModel;

/**
 * i18n Identifier, consisting of the message key and the bundle.
 *
 * - {@code bundle}: usually represents a module (e.g. catalog)
 * - {@code messageKey}: can contain multiple path elements (e.g. pagination.next)
 *
 * @see I18nIdentifier
 */
public final class I18nIdentifier extends SunriseModel {

    private final String bundle;
    private final String messageKey;

    private I18nIdentifier(final String bundle, final String messageKey) {
        this.bundle = bundle;
        this.messageKey = messageKey;
    }

    public String getBundle() {
        return bundle;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public static I18nIdentifier of(final String bundle, final String key) {
        return new I18nIdentifier(bundle, key);
    }
}
