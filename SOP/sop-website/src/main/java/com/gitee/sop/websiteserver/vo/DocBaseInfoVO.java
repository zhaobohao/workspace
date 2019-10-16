package com.gitee.sop.websiteserver.vo;

import lombok.Data;

import java.util.List;

/**
 * @author tanghc
 */
@Data
public class DocBaseInfoVO {
    private String urlTest;
    private String urlProd;
    private List<DocInfoVO> docInfoList;
}
