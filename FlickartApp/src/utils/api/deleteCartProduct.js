import api from "./api";


export default async function deleteCartProductApi(quantity, productId, cartId, cartItemId) {
    try {
        const response = await api.delete(`/user/cart?productId=${productId}&cartId=${cartId}&quantity=${quantity}&cartItemId=${cartItemId}`);
        return Promise.resolve(response.data);
    } catch (error) {
        return Promise.reject(error);
    }
}