package com.gitee.easyopen.doc;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author tanghc
 */
public class ParamMarkdownHtmlBuilder {

    private static final String START_TD = "<td>";
    private static final String END_TD = "</td>";
    private static final String START_TR = "<tr>";
    private static final String END_TR = "</tr>";
    private static final String TRUE = "true";


    public String buildHtml(ApiDocFieldDefinition definition) {
        StringBuilder html = new StringBuilder();
        html.append(START_TR)
                .append(wrapTD(definition.getName()))
                .append("<td class=\"param-type\">" + definition.getDataType() + END_TD)
                .append(wrapTD(this.getRequireHtml(definition)))
                .append(wrapTD(buildExample(definition)))
                .append(wrapTD(definition.getDescription()));
        html.append(END_TR);

        return html.toString();
    }

    private String wrapTD(String content) {
        return START_TD + content + END_TD;
    }

    protected String buildExample(ApiDocFieldDefinition definition) {
        StringBuilder html = new StringBuilder();
        if (CollectionUtils.isNotEmpty(definition.getElements())) {
            html.append("<table parentname=\"" + definition.getName() + "\">")
                    .append(START_TR)
                    .append("<th>名称</th>")
                    .append("<th>类型</th>")
                    .append("<th>是否必须</th>")
                    .append("<th>示例值</th>")
                    .append("<th>描述</th>")
                    .append(END_TR);

            List<ApiDocFieldDefinition> els = definition.getElements();
            for (ApiDocFieldDefinition apiDocFieldDefinition : els) {
                html.append(START_TR)
                        .append(wrapTD(apiDocFieldDefinition.getName()))
                        .append("<td class=\"param-type\">" + apiDocFieldDefinition.getDataType() + END_TD)
                        .append(wrapTD(getRequireHtml(apiDocFieldDefinition)))
                        .append(wrapTD(buildExample(apiDocFieldDefinition)))
                        .append(wrapTD(apiDocFieldDefinition.getDescription()))
                        .append(END_TR);
            }
            html.append("</table>");
        } else {
            html.append(buildExampleValue(definition));
        }
        return html.toString();
    }

    private String getRequireHtml(ApiDocFieldDefinition definition) {
        if (TRUE.equals(definition.getRequired())) {
            return "<strong>是</strong>";
        } else {
            return "否";
        }
    }

    protected String buildExampleValue(ApiDocFieldDefinition definition) {
        return definition.getExample();
    }

}
