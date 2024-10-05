import api from "./api";

export default async function validateUserApi () {
    try {
        const response = await api.get("/user");
        return Promise.resolve(response.data);
    } catch (error) {
        return Promise.reject(error);
    }
}