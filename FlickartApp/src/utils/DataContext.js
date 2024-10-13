// DataContext.js
import React, { createContext, useEffect, useState } from "react";
import validateUserApi from "./api/validateUser";

export const DataContext = createContext();

export const DataProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [isValidUser, setIsValidUser] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const fetchUserData = async () => {
        try {
            const userData = await validateUserApi();
            setUser(userData.response);
            setIsValidUser(true);
        } catch (err) {
            setError(err);
            setIsValidUser(false);
        } finally {
            setLoading(false);
        }
    };
    useEffect(() => {
        fetchUserData();
    }, []);

    function isProductInCart(productId) {
        if (user) {
            return user.cart?.cartItems?.find((product) => product.productId === productId);
        }
    }
    return (
        <DataContext.Provider value={{ user, cartItems:user?.cart?.cartItems,cartId: user?.cart?.cartId, loading, error , isValidUser, isProductInCart, fetchUserData }}>
            {children}
        </DataContext.Provider>
    );
};
