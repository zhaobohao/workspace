package com.mallplus.cms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mallplus.cms.mapper.CmsFavoriteMapper;
import com.mallplus.cms.service.ICmsFavoriteService;
import com.mallplus.cms.service.ICmsSubjectService;
import com.mallplus.common.entity.cms.CmsFavorite;
import com.mallplus.common.entity.cms.CmsSubject;
import com.mallplus.common.entity.pms.PmsProduct;
import com.mallplus.goods.mapper.PmsProductMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zscat
 * @since 2019-06-15
 */
@Service
public class CmsFavoriteServiceImpl extends ServiceImpl<CmsFavoriteMapper, CmsFavorite> implements ICmsFavoriteService {

    @Resource
    private CmsFavoriteMapper productCollectionRepository;
    @Resource
    private PmsProductMapper productMapper;
    @Resource
    private ICmsSubjectService subjectService;

    @Override
    public int addProduct(CmsFavorite productCollection) {
        int count = 0;
        CmsFavorite query = new CmsFavorite();
        query.setObjId(productCollection.getObjId());
        query.setMemberId(productCollection.getMemberId());
        query.setType(productCollection.getType());
        CmsFavorite findCollection = productCollectionRepository.selectOne(new QueryWrapper<>(query));
        if (findCollection == null) {
            productCollection.setAddTime(new Date());
            productCollectionRepository.insert(productCollection);
            if (productCollection.getType() == 1) {
                PmsProduct subject = productMapper.selectById(productCollection.getObjId());
                subject.setId(productCollection.getObjId());
                subject.setGiftPoint(subject.getGiftPoint() + 1);
                //更新到数据库
                productMapper.updateById(subject);
            }
            if (productCollection.getType() == 2) {
                CmsSubject subject = subjectService.getById(productCollection.getObjId());
                subject.setId(productCollection.getObjId());
                subject.setCollectCount(subject.getCollectCount() + 1);
                //更新到数据库
                subjectService.updateById(subject);
            }
            count = 1;
        } else {
            if (productCollection.getType() == 1) {
                PmsProduct subject = productMapper.selectById(productCollection.getObjId());
                subject.setId(productCollection.getObjId());
                subject.setGiftPoint(subject.getGiftPoint() - 1);
                //更新到数据库
                productMapper.updateById(subject);
            }
            if (productCollection.getType() == 2) {
                CmsSubject subject = subjectService.getById(productCollection.getObjId());
                subject.setId(productCollection.getObjId());
                subject.setCollectCount(subject.getCollectCount() - 1);
                //更新到数据库
                subjectService.updateById(subject);
            }
            return productCollectionRepository.delete(new QueryWrapper<>(query));
        }
        return count;
    }


    @Override
    public List<CmsFavorite> listProduct(Long memberId, int type) {
        return productCollectionRepository.selectList(new QueryWrapper<CmsFavorite>().eq("member_id", memberId).eq("type", type));
    }

    @Override
    public List<CmsFavorite> listCollect(Long memberId) {
        return productCollectionRepository.selectList(new QueryWrapper<CmsFavorite>().eq("member_id", memberId));
    }
}
