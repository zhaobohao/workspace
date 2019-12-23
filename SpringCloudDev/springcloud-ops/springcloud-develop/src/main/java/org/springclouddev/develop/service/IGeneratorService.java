package org.springclouddev.develop.service;

public interface IGeneratorService {
    /**
     * 生成ddl文件
      * @param ids
     * @return
     */
    byte[] generatorDdlFile(String ids) throws Exception;
}
