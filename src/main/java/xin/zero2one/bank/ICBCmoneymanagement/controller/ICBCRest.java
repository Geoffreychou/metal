package xin.zero2one.bank.ICBCmoneymanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.zero2one.bank.ICBCmoneymanagement.model.MetalModel;
import xin.zero2one.bank.ICBCmoneymanagement.model.MetalType;
import xin.zero2one.bank.ICBCmoneymanagement.service.ICBCService;

import java.util.List;
import java.util.Map;

/**
 * Created by zhoujundong on 2018/10/31.
 */
@RestController
@RequestMapping("/icbc")
@CrossOrigin
public class ICBCRest {

    @Autowired
    private ICBCService icbcService;


    @GetMapping("/list")
    public List<MetalModel> getMetalModelsByPageAndType(@RequestParam("page") int page,
                                              @RequestParam("size") int size,
                                              @RequestParam("type") int type){
        List<MetalModel> metalModels = icbcService.findMetalModel(page, size, type);
        return metalModels;
    }

    @PostMapping("/ceil/{type}/{price}")
    public void setCeil(@PathVariable("type") String type, @PathVariable("price") double price){
        icbcService.setCeil(price, type);
    }

    @PostMapping("/floor/{type}/{price}")
    public void setFloor(@PathVariable("type") String type, @PathVariable("price") double price){
        icbcService.setFloor(price, type);
    }

    @GetMapping("/ceil")
    public Map<MetalType, Double> getCeil(){
        return icbcService.findCeilSettings();
    }

    @GetMapping("/floor")
    public Map<MetalType, Double> getFloor(){
        return icbcService.findFloorSettings();
    }
}
