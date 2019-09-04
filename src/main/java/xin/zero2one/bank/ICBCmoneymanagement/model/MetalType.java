package xin.zero2one.bank.ICBCmoneymanagement.model;

/**
 * @author ZJD
 * @date 2019/1/18
 */
public enum  MetalType {
    RMB_GOLD("人民币账户黄金", 0),
    RMB_SILVER("人民币账户白银", 1),
    RMB_PLATINUM("人民币账户铂金", 2),
    RMB_PALLADIUM("人民币账户钯金", 3),
    DOLLAR_GOLD("美元账户黄金", 4),
    DOLLAR_SILVER("美元账户白银", 5),
    DOLLAR_PLATINUM("美元账户铂金", 6),
    DOLLAR_PALLADIUM("美元账户钯金", 7);

    private String metalName;
    private int metalValue;
    private MetalType(String metalName, int metalValue){
        this.metalName = metalName;
        this.metalValue = metalValue;
    }

    public static MetalType getMetalType(String metalName){
        MetalType[] values = MetalType.values();
        for(MetalType metalType : values){
            if (metalType.getMetalName().equals(metalName)){
                return metalType;
            }
        }
        return  null;
    }

    public String getMetalName(){
        return this.metalName;
    }

    public int getMetalValue(){
        return this.metalValue;
    }




}
