package com.batch.test.itemreader;


import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * 只读一次的itemreader.返回给process的可用值为 String "0";
 * @author zhaobo
 */
public class SingleItemReader implements ItemReader<String> {
    private Boolean executeStatus=Boolean.TRUE;
    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        String ret;
        if(executeStatus)
        {
            ret="0";
            executeStatus=Boolean.FALSE;
        }else
        {
            ret=null;
            executeStatus=Boolean.TRUE;
        }
        return ret;
    }
}
