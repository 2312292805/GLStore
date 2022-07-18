package com.GLstore.pojo.vo;
//查询条件的封装
public class ProductInfoVo {
    //商品名称
    private String pname;
    //商品类型
    private Integer typeid;
    //最低价格
    private Double lprice;
    //最高价格
    private Double hprice;
    //页码设置
    private Integer page=1;
    //创建全参构造方法


    public ProductInfoVo(String pname, Integer typeid, Double lprice, Double hprice, Integer page) {
        this.pname = pname;
        this.typeid = typeid;
        this.lprice = lprice;
        this.hprice = hprice;
        this.page = page;
    }

    //创建无参构造方法,重点是为了防止方法继承的时候出现找不到无参构造而导致出错的情况,super()
    public ProductInfoVo() {

    }
    //Set和Get方法

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public Double getLprice() {
        return lprice;
    }

    public void setLprice(Double lprice) {
        this.lprice = lprice;
    }

    public Double getHprice() {
        return hprice;
    }

    public void setHprice(Double hprice) {
        this.hprice = hprice;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    //toString方法
    @Override

    public String toString() {
        return "ProductInfoVo{" +
                "pname= '" + pname + '\'' +
                ", typeid=" + typeid +
                ", lprice=" + lprice +
                ", hprice=" + hprice +
                ", page=" + page +
                '}';
    }
}
