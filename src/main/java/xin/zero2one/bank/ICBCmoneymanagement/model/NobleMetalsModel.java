package xin.zero2one.bank.ICBCmoneymanagement.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoujundong on 2018/10/8.
 */
public class NobleMetalsModel {

    private String refreshInterval;
    private String TranErrorCode;
    private String sysdate;
    private String TranErrorDisplayMsg;
    private String acGoldAllGoldTyle;

    private List<MetalModel> market = new ArrayList<>();

    public String getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(String refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    public String getTranErrorCode() {
        return TranErrorCode;
    }

    public void setTranErrorCode(String tranErrorCode) {
        TranErrorCode = tranErrorCode;
    }

    public String getSysdate() {
        return sysdate;
    }

    public void setSysdate(String sysdate) {
        this.sysdate = sysdate;
    }

    public String getTranErrorDisplayMsg() {
        return TranErrorDisplayMsg;
    }

    public void setTranErrorDisplayMsg(String tranErrorDisplayMsg) {
        TranErrorDisplayMsg = tranErrorDisplayMsg;
    }

    public String getAcGoldAllGoldTyle() {
        return acGoldAllGoldTyle;
    }

    public void setAcGoldAllGoldTyle(String acGoldAllGoldTyle) {
        this.acGoldAllGoldTyle = acGoldAllGoldTyle;
    }

    public List<MetalModel> getMarket() {
        return market;
    }

    public void setMarket(List<MetalModel> market) {
        this.market = market;
    }
}
