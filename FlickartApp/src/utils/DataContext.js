// DataContext.js
import React, { createContext, useCallback, useEffect, useMemo, useState } from "react";
import validateUserApi from "./api/validateUser";

export const DataContext = createContext();

export const DataProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [isValidUser, setIsValidUser] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const fetchUserData = useCallback(async () => {
        try {
            const userData = await validateUserApi();
            console.log(userData.response);
            setUser(userData.response);
            setIsValidUser(true);
        } catch (err) {
            setError(err);
            setIsValidUser(false);
        } finally {
            setLoading(false);
        }
    }, []);
    useEffect(() => {
        fetchUserData();
    }, [fetchUserData]);
    const cartItems = useMemo(() => user?.cart?.cartItems || [], [user]);
    const cartId = useMemo(() => user?.cart?.cartId, [user]);

    const isProductInCart = useCallback(
        (productId) => {
            return cartItems.find((product) => product.productId === productId);
        },
        [cartItems]
    );
      const contextValue = useMemo(() => ({
        user,
        userId: user?.userId,
        cartItems,
        cartId,
        loading,
        error,
        isValidUser,
        isProductInCart,
        fetchUserData,
    }), [user, cartItems, cartId, loading, error, isValidUser, fetchUserData, isProductInCart]);

    return (
        <DataContext.Provider value={contextValue}>
            {children}
        </DataContext.Provider>
    );
};
