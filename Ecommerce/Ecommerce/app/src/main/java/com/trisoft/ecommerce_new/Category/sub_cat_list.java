package com.trisoft.ecommerce_new.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class sub_cat_list implements Serializable {


    @SerializedName("ID")
    @Expose
    private Integer iD;
    @SerializedName("CategoryName")
    @Expose
    private String categoryName;
    @SerializedName("ParentCategoryID")
    @Expose
    private Integer parentCategoryID;
    @SerializedName("CatLevel")
    @Expose
    private Integer catLevel;
    @SerializedName("ActiveStatus")
    @Expose
    private String activeStatus;


    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    @SerializedName("images")
    @Expose
    private String images;


    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getParentCategoryID() {
        return parentCategoryID;
    }

    public void setParentCategoryID(Integer parentCategoryID) {
        this.parentCategoryID = parentCategoryID;
    }

    public Integer getCatLevel() {
        return catLevel;
    }

    public void setCatLevel(Integer catLevel) {
        this.catLevel = catLevel;
    }

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }


}
