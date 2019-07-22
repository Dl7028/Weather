package cn.eoe.app.entity;

/**
 * "LivingIndex"生活指数的实体类
 * Created by 徐启 on 2019/5/6.
 */

public class LivingIndex {

    private String locationName;
    private String acBrief;
    private String carWashingBrief;
    private String dressingBrief;
    private String fishingBrief;
    private String shoppingBrief;
    private String sportBrief;
    private String beerBrief;
    private String umbrellaBrief;
    private String allergyBrief;
    private String uvBrief;
    private String fluBrief;
    private String morningSportBrief;

    public LivingIndex(){
        this.locationName = "";
        this.acBrief = "";
        this.carWashingBrief = "";
        this.dressingBrief = "";
        this.fishingBrief = "";
        this.shoppingBrief = "";
        this.sportBrief = "";
        this.beerBrief = "";
        this.umbrellaBrief = "";
        this.allergyBrief = "";
        this.uvBrief = "";
        this.fluBrief = "";
        this.morningSportBrief = "";
    }

    public void setAcBrief(String acBrief) {
        this.acBrief = acBrief;
    }

    public void setAllergyBrief(String allergyBrief) {
        this.allergyBrief = allergyBrief;
    }

    public void setBeerBrief(String beerBrief) {
        this.beerBrief = beerBrief;
    }

    public void setCarWashingBrief(String carWashingBrief) {
        this.carWashingBrief = carWashingBrief;
    }

    public void setDressingBrief(String dressingBrief) {
        this.dressingBrief = dressingBrief;
    }

    public void setFishingBrief(String fishingBrief) {
        this.fishingBrief = fishingBrief;
    }

    public void setFluBrief(String fluBrief) {
        this.fluBrief = fluBrief;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setMorningSportBrief(String morningSportBrief) {
        this.morningSportBrief = morningSportBrief;
    }

    public void setShoppingBrief(String shoppingBrief) {
        this.shoppingBrief = shoppingBrief;
    }

    public void setSportBrief(String sportBrief) {
        this.sportBrief = sportBrief;
    }

    public void setUmbrellaBrief(String umbrellaBrief) {
        this.umbrellaBrief = umbrellaBrief;
    }

    public void setUvBrief(String uvBrief) {
        this.uvBrief = uvBrief;
    }

    public String getAcBrief() {
        return acBrief;
    }

    public String getAllergyBrief() {
        return allergyBrief;
    }

    public String getBeerBrief() {
        return beerBrief;
    }

    public String getCarWashingBrief() {
        return carWashingBrief;
    }

    public String getDressingBrief() {
        return dressingBrief;
    }

    public String getFishingBrief() {
        return fishingBrief;
    }

    public String getFluBrief() {
        return fluBrief;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getMorningSportBrief() {
        return morningSportBrief;
    }

    public String getShoppingBrief() {
        return shoppingBrief;
    }

    public String getSportBrief() {
        return sportBrief;
    }

    public String getUmbrellaBrief() {
        return umbrellaBrief;
    }

    public String getUvBrief() {
        return uvBrief;
    }
}
