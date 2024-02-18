import axios from "axios";

export const baseUrl="http://localhost:8080";

export const http = axios.create({
    baseURL:baseUrl
})