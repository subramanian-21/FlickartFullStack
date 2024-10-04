import api from "./api";
export default async function getProducts(limit, offset, searchText, category) {
    try {
        const response = await api.get("/user/products/getAll", {params:{limit:limit, offset:offset, search:searchText, category:category}});
        return Promise.resolve(response.data);
    } catch (error) {
        return Promise.reject(error);
    }

}
export const getProductsByCategory = async (limit, offset, category) => {
    try {
        const response = await api.get("/user/products/getAll", {params:{limit:limit, offset:offset,category:category}});
        return Promise.resolve(response.data);
    } catch (error) {
        return Promise.reject(error);
    }
}
export const getProduct = async (productId) => {
    try {
        const response = await api.get("/user/products/"+productId);
        return Promise.resolve(response.data);
    } catch (error) {
        return Promise.reject(error);
    }
}