package com.zte.km.dto;

/**
 * 购物车商品信息
 * @author 梨花雨凉、
 */
public class CartItem {
    private Long id;
    private String title;
    private Integer num;
    private Long price;
    private String image;
    private String[] images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String[] getImages() {
        if (image!=null && !image.isEmpty()){
            return image.split(",");
        }
        return null;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
