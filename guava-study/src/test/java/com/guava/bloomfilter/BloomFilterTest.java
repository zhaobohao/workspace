package com.guava.bloomfilter;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;
import org.testng.annotations.Test;

public class BloomFilterTest {

    @Test
    public void test() {


        BloomFilter<String> dealIdBloomFilter = BloomFilter.create(new Funnel<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void funnel(String arg0, PrimitiveSink arg1) {

                arg1.putString(arg0, Charsets.UTF_8);
            }

        }, 1024 * 1024 * 32, 0.0000001d);
        String  deal_id="ou3209u3ojfasjdlfkj";
        boolean exists = dealIdBloomFilter.mightContain(deal_id);
        if(!exists){
            dealIdBloomFilter.put(deal_id);
        }
}
}
