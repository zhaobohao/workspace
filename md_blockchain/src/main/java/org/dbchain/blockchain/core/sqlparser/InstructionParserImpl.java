package org.dbchain.blockchain.core.sqlparser;

import javax.annotation.Resource;

import org.dbchain.blockchain.block.Instruction;
import org.dbchain.blockchain.block.InstructionBase;
import org.dbchain.blockchain.common.FastJsonUtil;
import org.dbchain.blockchain.core.model.base.BaseEntity;
import org.dbchain.blockchain.core.model.convert.ConvertTableName;
import org.springframework.stereotype.Service;

/**
 * 将区块内指令解析并入库
 * @author zhaobo create on 2020/3/21.
 */
@Service
public class InstructionParserImpl<T extends BaseEntity> implements InstructionParser {
    @Resource
    private ConvertTableName<T> convertTableName;
    @Resource
    private AbstractSqlParser<T>[] sqlParsers;

    @Override
    public boolean parse(InstructionBase instructionBase) {
        byte operation = instructionBase.getOperation();
        String table = instructionBase.getTable();
        String json = instructionBase.getOldJson();
        //表对应的类名，如MessageEntity.class
        Class<T> clazz = convertTableName.convertOf(table);
        T object = FastJsonUtil.toBean(json, clazz);
        for (AbstractSqlParser<T> sqlParser : sqlParsers) {
            if (clazz.equals(sqlParser.getEntityClass())) {
            	if(instructionBase instanceof Instruction){
            		object.setPublicKey(((Instruction)instructionBase).getPublicKey());
            	}
                sqlParser.parse(operation, instructionBase.getInstructionId(), object);
                break;
            }
        }

        return true;
    }
}
