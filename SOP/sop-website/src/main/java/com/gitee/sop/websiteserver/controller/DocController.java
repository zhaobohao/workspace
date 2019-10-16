package com.gitee.sop.websiteserver.controller;

import com.gitee.sop.websiteserver.bean.DocInfo;
import com.gitee.sop.websiteserver.manager.DocManager;
import com.gitee.sop.websiteserver.vo.DocBaseInfoVO;
import com.gitee.sop.websiteserver.vo.DocInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
@RestController
@RequestMapping("doc")
public class DocController {

    @Autowired
    DocManager docManager;

    @Value("${api.url-test}")
    String urlTest;

    @Value("${api.url-prod}")
    String urlProd;

    @Value("${api.pwd}")
    String pwd;

    @GetMapping("/getDocBaseInfo")
    public DocBaseInfoVO getDocBaseInfo() {
        List<DocInfoVO> docInfoList = docManager.listAll()
                .stream()
                .map(docInfo -> {
                    DocInfoVO vo = new DocInfoVO();
                    BeanUtils.copyProperties(docInfo, vo);
                    return vo;
                })
                .collect(Collectors.toList());

        DocBaseInfoVO baseInfoVO = new DocBaseInfoVO();
        baseInfoVO.setUrlTest(urlTest);
        baseInfoVO.setUrlProd(urlProd);
        baseInfoVO.setDocInfoList(docInfoList);
        return baseInfoVO;
    }

    @GetMapping("/docinfo/{title}")
    public DocInfo getDocModule(@PathVariable("title") String title) {
        return docManager.getByTitle(title);
    }

}
