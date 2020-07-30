package com.trisoft.ecommerce_new.Orders;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class order_list implements Serializable {

    @SerializedName("YorderID")
    @Expose
    private Integer yorderID;
    @SerializedName("Prod_ID")
    @Expose
    private String prodID;
    @SerializedName("Qty")
    @Expose
    private String qty;
    @SerializedName("Rate")
    @Expose
    private String rate;
    @SerializedName("Total")
    @Expose
    private String total;
    @SerializedName("Loginid")
    @Expose
    private String loginid;
    @SerializedName("DeliverAddress")
    @Expose
    private String deliverAddress;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
    @SerializedName("Status")
    @Expose
    private Object status;
    @SerializedName("Prod_detail")
    @Expose
    private ProdDetail prodDetail;
    @SerializedName("images")
    @Expose
    private List<Image> images = null;

    public Integer getYorderID() {
        return yorderID;
    }

    public void setYorderID(Integer yorderID) {
        this.yorderID = yorderID;
    }

    public String getProdID() {
        return prodID;
    }

    public void setProdID(String prodID) {
        this.prodID = prodID;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getDeliverAddress() {
        return deliverAddress;
    }

    public void setDeliverAddress(String deliverAddress) {
        this.deliverAddress = deliverAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public ProdDetail getProdDetail() {
        return prodDetail;
    }

    public void setProdDetail(ProdDetail prodDetail) {
        this.prodDetail = prodDetail;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }




public class Image {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ImageName")
    @Expose
    private String imageName;
    @SerializedName("ImagePath")
    @Expose
    private String imagePath;
    @SerializedName("UploadedDate")
    @Expose
    private String uploadedDate;
    @SerializedName("UploadedBy")
    @Expose
    private String uploadedBy;
    @SerializedName("Prod_ID")
    @Expose
    private Integer prodID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(String uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public String getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }

    public Integer getProdID() {
        return prodID;
    }

    public void setProdID(Integer prodID) {
        this.prodID = prodID;
    }

}
    public class ProdDetail implements Serializable {

        @SerializedName("Prod_ID")
        @Expose
        private Integer prodID;
        @SerializedName("SKU")
        @Expose
        private String sKU;
        @SerializedName("Prod_Name")
        @Expose
        private String prodName;
        @SerializedName("Prod_Desc")
        @Expose
        private String prodDesc;
        @SerializedName("Sub_Desc")
        @Expose
        private Object subDesc;
        @SerializedName("Quantity")
        @Expose
        private Integer quantity;
        @SerializedName("MRP")
        @Expose
        private String mRP;
        @SerializedName("Retail_Price")
        @Expose
        private String retailPrice;
        @SerializedName("ProdValue")
        @Expose
        private String prodValue;
        @SerializedName("Brand")
        @Expose
        private String brand;
        @SerializedName("CategoryId")
        @Expose
        private Integer categoryId;
        @SerializedName("GroupName")
        @Expose
        private String groupName;
        @SerializedName("Unit")
        @Expose
        private String unit;
        @SerializedName("Manufacturers")
        @Expose
        private String manufacturers;
        @SerializedName("MarkedBy")
        @Expose
        private String markedBy;
        @SerializedName("Seller")
        @Expose
        private String seller;
        @SerializedName("ProductStatus")
        @Expose
        private String productStatus;
        @SerializedName("BarCode")
        @Expose
        private String barCode;
        @SerializedName("SubmitedDate")
        @Expose
        private String submitedDate;
        @SerializedName("SubmitedBy")
        @Expose
        private String submitedBy;
        @SerializedName("ProductType")
        @Expose
        private String productType;
        @SerializedName("Cess")
        @Expose
        private String cess;

        public Integer getProdID() {
            return prodID;
        }

        public void setProdID(Integer prodID) {
            this.prodID = prodID;
        }

        public String getSKU() {
            return sKU;
        }

        public void setSKU(String sKU) {
            this.sKU = sKU;
        }

        public String getProdName() {
            return prodName;
        }

        public void setProdName(String prodName) {
            this.prodName = prodName;
        }

        public String getProdDesc() {
            return prodDesc;
        }

        public void setProdDesc(String prodDesc) {
            this.prodDesc = prodDesc;
        }

        public Object getSubDesc() {
            return subDesc;
        }

        public void setSubDesc(Object subDesc) {
            this.subDesc = subDesc;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getMRP() {
            return mRP;
        }

        public void setMRP(String mRP) {
            this.mRP = mRP;
        }

        public String getRetailPrice() {
            return retailPrice;
        }

        public void setRetailPrice(String retailPrice) {
            this.retailPrice = retailPrice;
        }

        public String getProdValue() {
            return prodValue;
        }

        public void setProdValue(String prodValue) {
            this.prodValue = prodValue;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getManufacturers() {
            return manufacturers;
        }

        public void setManufacturers(String manufacturers) {
            this.manufacturers = manufacturers;
        }

        public String getMarkedBy() {
            return markedBy;
        }

        public void setMarkedBy(String markedBy) {
            this.markedBy = markedBy;
        }

        public String getSeller() {
            return seller;
        }

        public void setSeller(String seller) {
            this.seller = seller;
        }

        public String getProductStatus() {
            return productStatus;
        }

        public void setProductStatus(String productStatus) {
            this.productStatus = productStatus;
        }

        public String getBarCode() {
            return barCode;
        }

        public void setBarCode(String barCode) {
            this.barCode = barCode;
        }

        public String getSubmitedDate() {
            return submitedDate;
        }

        public void setSubmitedDate(String submitedDate) {
            this.submitedDate = submitedDate;
        }

        public String getSubmitedBy() {
            return submitedBy;
        }

        public void setSubmitedBy(String submitedBy) {
            this.submitedBy = submitedBy;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getCess() {
            return cess;
        }

        public void setCess(String cess) {
            this.cess = cess;
        }
    }

    }
