package cn.eoe.app.entity;

/**
 * 24小时预报实体类
 * Created by 徐启 on 2019/5/15.
 */

public class Hourly {
    private String mhourlyTemperature;
    private String mhourlyCode;
    private String mhourlyTime;
    private String mhourlyLocationName;
   public Hourly(){
       this.mhourlyCode = "";
       this.mhourlyTemperature = "";
       this.mhourlyTime = "";
   }

    public String getMhourlyCode() {
        return mhourlyCode;
    }

    public void setMhourlyCode(String mhourlyCode) {
        this.mhourlyCode = mhourlyCode;
    }

    public String getMhourlyTemperature() {
        return mhourlyTemperature;
    }

    public void setMhourlyTemperature(String mhourlyTemperature) {
        this.mhourlyTemperature = mhourlyTemperature;
    }

    public String getMhourlyTime() {
        return mhourlyTime;
    }

    public void setMhourlyTime(String mhourlyTime) {
        this.mhourlyTime = mhourlyTime;
    }

    public String getMhourlyLocationName() {
        return mhourlyLocationName;
    }

    public void setMhourlyLocationName(String mhourlyLocationName) {
        this.mhourlyLocationName = mhourlyLocationName;
    }
}
