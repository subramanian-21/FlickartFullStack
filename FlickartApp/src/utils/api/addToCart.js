import api from "./api";
export async function addToCartApi(productId, cartId) {
    try {
        const response = await api.post("/user/cart/add", {productId: productId, cartId: cartId});
        return Promise.resolve(response.data);
    } catch (error) {
        return Promise.reject(error);
    }
}