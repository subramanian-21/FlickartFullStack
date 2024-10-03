import api from "./api";
export default async function getProducts(limit, offset, searchText) {
    try {
        const response = await api.get("/user/products/getAll", {params:{limit:limit, offset:offset, search:searchText}});
        return Promise.resolve(response.data);
    } catch (error) {
        return Promise.reject(error);
    }

}