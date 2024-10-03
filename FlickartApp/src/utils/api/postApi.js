import api from "./api";
export default async function postApi(url, data) {
    try{
        const response = await api.post(url, data);
        return Promise.resolve(response.data);
    }catch(error) {
        return Promise.reject(error);
    }

}