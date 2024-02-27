import { useEffect, useState } from "react";
import CategoryService from "../api-service/categoryService";

function useCategories() {
    const [categories, setCategories] = useState([]);

    useEffect(() => {
        const getCategories = async () => {
            const category_response = await CategoryService.get_categories().then(
                (response) => {
                    setCategories(response.data)
                },
                (error) => {
                    console.log(error)
                }
            )
        }

        getCategories()
    }, [])

    return [categories];
}

export default useCategories;