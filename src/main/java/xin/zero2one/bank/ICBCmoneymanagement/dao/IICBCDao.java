package xin.zero2one.bank.ICBCmoneymanagement.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xin.zero2one.bank.ICBCmoneymanagement.model.MetalModel;

import java.io.Serializable;

/**
 * Created by zhoujundong on 2018/10/31.
 */
public interface IICBCDao extends JpaRepository<MetalModel, Long>, JpaSpecificationExecutor<MetalModel>, Serializable {


}
