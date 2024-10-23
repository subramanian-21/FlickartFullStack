import api from "./api";

export const addProductReviewApi = async (data) => {
    try {
        const response = await api.post(`/user/product/review`, data);
        return Promise.resolve(response.data);
    } catch (error) {
        return Promise.reject(error);
    }
}