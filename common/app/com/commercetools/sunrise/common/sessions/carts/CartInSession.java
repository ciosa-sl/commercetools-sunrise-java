package com.commercetools.sunrise.common.sessions.carts;

import com.commercetools.sunrise.common.injection.RequestScoped;
import com.commercetools.sunrise.common.sessions.DataFromResourceStoringOperations;
import com.commercetools.sunrise.common.sessions.ObjectStoringSessionStrategy;
import com.commercetools.sunrise.shoppingcart.MiniCartBean;
import com.commercetools.sunrise.shoppingcart.MiniCartBeanFactory;
import io.sphere.sdk.carts.Cart;
import play.Configuration;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.Optional;

/**
 * Keeps some parts from the cart in session, such as cart ID and mini cart.
 */
@RequestScoped
public class CartInSession extends DataFromResourceStoringOperations<Cart> {

    private static final String DEFAULT_CART_ID_SESSION_KEY = "sunrise-cart-id";
    private static final String DEFAULT_MINI_CART_SESSION_KEY = "sunrise-mini-cart";

    private final String cartIdSessionKey;
    private final String miniCartSessionKey;
    private final ObjectStoringSessionStrategy session;
    private final MiniCartBeanFactory miniCartBeanFactory;

    @Inject
    public CartInSession(final Configuration configuration, final ObjectStoringSessionStrategy session, final MiniCartBeanFactory miniCartBeanFactory) {
        this.cartIdSessionKey = configuration.getString("session.cart.cartId", DEFAULT_CART_ID_SESSION_KEY);
        this.miniCartSessionKey = configuration.getString("session.cart.miniCart", DEFAULT_MINI_CART_SESSION_KEY);
        this.session = session;
        this.miniCartBeanFactory = miniCartBeanFactory;
    }

    protected final ObjectStoringSessionStrategy getSession() {
        return session;
    }

    public Optional<String> findCartId() {
        return session.findValueByKey(cartIdSessionKey);
    }

    public Optional<MiniCartBean> findMiniCart() {
        return session.findObjectByKey(miniCartSessionKey, MiniCartBean.class);
    }

    @Override
    public void store(@Nullable final Cart cart) {
        super.store(cart);
    }

    @Override
    public void remove() {
        super.remove();
    }

    @Override
    protected void storeAssociatedData(final Cart cart) {
        session.overwriteObjectByKey(miniCartSessionKey, miniCartBeanFactory.create(cart));
        session.overwriteValueByKey(cartIdSessionKey, cart.getId());
    }

    @Override
    protected void removeAssociatedData() {
        session.removeObjectByKey(miniCartSessionKey);
        session.removeValueByKey(cartIdSessionKey);
    }
}