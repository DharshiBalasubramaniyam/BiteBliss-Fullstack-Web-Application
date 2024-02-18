import { http } from "./axios_helper";

export const loadProduct = () => {
    return http.get(`/products/view`).then(res=>res.data)
}