
package org.springclouddev.develop.service.impl;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.boot.upload.service.UploadFileHandler;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.develop.entity.DbInstance;
import org.springclouddev.develop.entity.TableInfo;
import org.springclouddev.develop.service.IDbInstanceService;
import org.springclouddev.develop.vo.TableInfoVO;
import org.springclouddev.develop.mapper.TableInfoMapper;
import org.springclouddev.develop.service.ITableInfoService;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.develop.wrapper.TableInfoWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  服务实现类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@Service
@Slf4j
@AllArgsConstructor
public class TableInfoServiceImpl extends BaseServiceImpl<TableInfoMapper, TableInfo> implements ITableInfoService, UploadFileHandler {
	private IDbInstanceService dbInstanceService;
	@Override
	public IPage<TableInfoVO> selectTableInfoPage(IPage<TableInfoVO> page, TableInfoVO tableInfo) {
		return page.setRecords(baseMapper.selectTableInfoPage(page, tableInfo));
	}

	@Override
	public List<TableInfoVO> tree(String parentId) {
		List<TableInfoVO> retList=new LinkedList<TableInfoVO>();
	List<DbInstance> dbInstancesList=	dbInstanceService.list();
	dbInstancesList.forEach(dbInstance -> {
		TableInfoVO tableInfoVO=new TableInfoVO();
		tableInfoVO.setId(dbInstance.getId());
		tableInfoVO.setIsLeaf(1);
		tableInfoVO.setParentId(0L);
		tableInfoVO.setName(dbInstance.getName());
		tableInfoVO.setChildren(baseMapper.selectList(Condition.getQueryWrapper(new TableInfo().setDbInstanceId(tableInfoVO.getId()).setCategory(1))).stream().map(tableinfo->TableInfoWrapper.build().entityVO(tableinfo)).collect(Collectors.toList()));
		retList.add(tableInfoVO);
	});
		return retList;
	}

	@Override
	public void handler(MultipartFile upfile, Map<String, String> params) throws Exception{
		Long dbInstanceId=Long.valueOf(params.get("dbInstanceId"));
		log.info("dbid is {}", dbInstanceId);
		log.info("file size is {}", upfile.getSize());
		log.info("file name is {}", upfile.getName());
		// 拿到文件开始解析入库
		Integer size = ExcelUtil.getReader(upfile.getInputStream()).getSheetCount();
		for (int sheet = 0; sheet < size; sheet++) {
			ExcelReader reader = ExcelUtil.getReader(upfile.getInputStream(), sheet, true);
			List<Object> row = reader.readRow(0);
			TableInfo table = new TableInfo();
			table.setCategory(1);
			table.setParentId(0L);
			table.setName(row.get(1).toString());
			table.setComment(row.get(3).toString());
			table.setDbInstanceId(dbInstanceId);
			save(table);
//			 开始保存表的字段值
			int rowSize = reader.getRowCount();
			for (int i = 2; i < rowSize; i++) {
				List<Object> crow = reader.readRow(i);
				if (crow.size() == 0)
					break;
				TableInfo record = new TableInfo();
				record.setCategory(2);
				record.setParentId(table.getId());
				record.setDbInstanceId(dbInstanceId);

				record.setName(crow.get(0).toString());
				record.setTypeKey(crow.get(1).toString());
				record.setTypeValue(crow.get(2).toString());
				record.setIsEmpty(Integer.valueOf(crow.get(3).toString()));
				record.setDefaultValue(crow.get(4).toString());
				record.setComment(crow.get(5).toString());
				save(record);
			}
		}
	}
}
