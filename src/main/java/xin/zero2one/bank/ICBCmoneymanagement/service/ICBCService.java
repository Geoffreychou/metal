package xin.zero2one.bank.ICBCmoneymanagement.service;

import xin.zero2one.bank.ICBCmoneymanagement.model.MetalModel;
import xin.zero2one.bank.ICBCmoneymanagement.model.MetalType;

import java.util.List;
import java.util.Map;

/**
 * Created by zhoujundong on 2018/10/31.
 */
public interface ICBCService {

    List<MetalModel> findAllMetalModels();

    List<MetalModel> findMetalModelsByPage(int page, int size);

    List<MetalModel> findMetalModel(int page, int size, int type);

    MetalModel saveMetalModel(MetalModel metalModel);

    List<MetalModel> getRealTimeMetalModel();

    List<MetalModel> getAndSaveMetalModel();

    void setCeil(double ceil, String metalType);

    void setFloor(double floor, String metalType);

    void sendMsg();

    Map<MetalType,Double> findCeilSettings();


    Map<MetalType,Double> findFloorSettings();

}
