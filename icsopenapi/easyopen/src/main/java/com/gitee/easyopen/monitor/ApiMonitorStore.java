package com.gitee.easyopen.monitor;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.util.StringUtils;

import com.gitee.easyopen.ApiParam;
import com.gitee.easyopen.bean.ApiSearch;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.util.ListUtil;

/**
 * 存放监控数据
 * @author tanghc
 */
public class ApiMonitorStore implements MonitorStore {

    private static final String COLUMN_VISITCOUNT = "visitCount";
    private static final String COLUMN_AVGCONSUMEMILLISECONDS = "avgConsumeMilliseconds";
    private static final String COLUMN_MAXCONSUMEMILLISECONDS = "maxConsumeMilliseconds";
    private static final String COLUMN_ERRORCOUNT = "errorCount";

    /** key:"name version" */
    private Map<String, MonitorApiInfo> store = new HashMap<>();

    @Override
    public synchronized void stat(ApiParam param, long starTimeMillis, long endTimeMillis, Object argu, Object result,
            Exception e) {
        String key = this.getKey(param.fatchName(), param.fatchVersion());
        MonitorApiInfo monitorApiInfo = store.get(key);
        if (monitorApiInfo == null) {
            monitorApiInfo = new MonitorApiInfo();
            monitorApiInfo.setName(param.fatchName());
            monitorApiInfo.setVersion(param.fatchVersion());
            store.put(key, monitorApiInfo);
        }
        // 访问次数+1
        long visitCount = monitorApiInfo.getVisitCount() + 1;
        // 本次访问耗时
        long consumeMilliseconds = endTimeMillis - starTimeMillis;
        // 总耗时
        long sumConsumeMilliseconds = monitorApiInfo.getSumConsumeMilliseconds() + consumeMilliseconds;
        // 平均耗时=总耗时/访问次数
        BigDecimal avgConsume = new BigDecimal(sumConsumeMilliseconds).divide(new BigDecimal(visitCount), 2,
                BigDecimal.ROUND_UP);
        double avgConsumeMilliseconds = avgConsume.doubleValue();
        // 上一次最大耗时
        long maxConsumeMilliseconds = monitorApiInfo.getMaxConsumeMilliseconds();
        
        // 出错次数
        int errorCount = monitorApiInfo.getErrorCount();
        if(e != null) {
            this.errorHandler(param, argu, result, e, monitorApiInfo);
            errorCount++;
        }

        monitorApiInfo.setMaxConsumeMilliseconds(Math.max(consumeMilliseconds, maxConsumeMilliseconds));

        monitorApiInfo.setVisitCount(visitCount);
        monitorApiInfo.setSumConsumeMilliseconds(sumConsumeMilliseconds);
        monitorApiInfo.setAvgConsumeMilliseconds(avgConsumeMilliseconds);
        monitorApiInfo.setErrorCount(errorCount);
    }

    @Override
    public int getTotal(ApiSearch apiSearch) {
        String name = apiSearch.getName();
        int total = 0;
        Set<String> keys = store.keySet();
        if (name == null) {
            return keys.size();
        }
        for (String key : keys) {
            if (key.contains(name)) {
                total++;
            }
        }
        return total;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MonitorApiInfo> getList(final ApiSearch apiSearch) {
        String name = apiSearch.getName();
        Collection<Entry<String, MonitorApiInfo>> entrys = store.entrySet();

        if (name != null) {
            entrys = CollectionUtils.select(entrys, new Predicate() {
                @Override
                public boolean evaluate(Object object) {
                    Entry<String, MonitorApiInfo> apiInfo = (Entry<String, MonitorApiInfo>) object;
                    return apiInfo.getKey().contains(apiSearch.getName());
                }
            });
        }

        List<MonitorApiInfo> retList = new ArrayList<>(entrys.size());

        for (Entry<String, MonitorApiInfo> entry : entrys) {
            retList.add(entry.getValue());
        }

        // 排序
        Collections.sort(retList, new Comparator<MonitorApiInfo>() {
            @Override
            public int compare(MonitorApiInfo o1, MonitorApiInfo o2) {
                MonitorApiInfo monitorApiInfo1 = o1;
                MonitorApiInfo monitorApiInfo2 = o2;
                String sortname = apiSearch.getSort();
                String sortorder = apiSearch.getOrder();
                if (Consts.SORT_DESC.equalsIgnoreCase(sortorder)) {
                    monitorApiInfo1 = o2;
                    monitorApiInfo2 = o1;
                }
                if (COLUMN_VISITCOUNT.equals(sortname)) {
                    return Long.compare(monitorApiInfo1.getVisitCount(), monitorApiInfo2.getVisitCount());
                } else if (COLUMN_AVGCONSUMEMILLISECONDS.equals(sortname)) {
                    return Double.compare(monitorApiInfo1.getAvgConsumeMilliseconds(),
                            monitorApiInfo2.getAvgConsumeMilliseconds());
                } else if (COLUMN_MAXCONSUMEMILLISECONDS.equals(sortname)) {
                    return Long.compare(monitorApiInfo1.getMaxConsumeMilliseconds(),
                            monitorApiInfo2.getMaxConsumeMilliseconds());
                } else if (COLUMN_ERRORCOUNT.equals(sortname)) {
                    return Integer.compare(monitorApiInfo1.getErrorCount(), monitorApiInfo2.getErrorCount());
                } else {
                    return monitorApiInfo1.getName().compareTo(monitorApiInfo2.getName());
                }
            }
        });

        // 分页
        int pageIndex = apiSearch.getPage();
        int pageSize = apiSearch.getRows();
        
        return ListUtil.page(retList, pageIndex, pageSize);
    }

    @Override
    public void clean(String name, String version) {
        if (StringUtils.isEmpty(name)) {
            store.clear();
        } else {
            String key = this.getKey(name, version);
            store.remove(key);
        }
    }

    @Override
    public void errorHandler(ApiParam param, Object argu, Object result, Exception e, MonitorApiInfo t) {
        String errorMsg = this.getErrorMsg(param, argu, result, e);
        this.setErrorMsg(t, errorMsg);
    }

    private String getKey(String name, String version) {
        if (version == null) {
            version = "";
        }
        return ApiParam.buildNameVersion(name, version);
    }

    protected String getErrorMsg(ApiParam param, Object argu, Object result, Exception e) {
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        StringBuilder msg = new StringBuilder();
        String paramStr = param.toJSONString();
        try {
            paramStr = URLDecoder.decode(paramStr, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
        }
        msg.append("请求参数:").append(paramStr).append("\r\n").append("错误信息:").append(writer.toString());
        return msg.toString();
    }

    protected <T extends MonitorApiInfo> void setErrorMsg(T t, String errorMsg) {
        t.getErrors().offer(errorMsg);
    }
    
}
