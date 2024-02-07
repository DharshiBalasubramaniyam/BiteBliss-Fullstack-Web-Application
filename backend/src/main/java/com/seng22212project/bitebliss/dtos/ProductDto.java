package com.seng22212project.bitebliss.dtos;

public class ProductDto {

    private int product_id;
    private String product_name;
    private String price;
    private String description;
    private String imageUrl;

    public ProductDto() {
        super();
        this.product_id = product_id;
        this.product_name = product_name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


