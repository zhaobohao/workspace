package com.gitee.easyopen.doc;

import java.util.List;

/**
 * <pre>
<tr>
     <td>${resultDefinition.name}</td>
     <td>${resultDefinition.dataType}</td>
     #if(${resultDefinition.dataType} == 'array')
     <td>
        <table>
            <tr>
                <th>名称</th>
                <th>类型</th>
                <th>示例值</th>
                <th>描述</th>
            </tr>
             #foreach($elDefinition in ${resultDefinition.elements})
             <tr>
                 <td>${elDefinition.name}</td>
                 <td>${elDefinition.dataType}</td>
                 <td>${elDefinition.example}</td>
                 <td>${elDefinition.description}</td>
             </tr>
             #end
       </table>
      </td>
     #else
         <td>${resultDefinition.example}</td>
     #end
     <td>${resultDefinition.description}</td>
 </tr>
 * </pre>
 * @author tanghc
 *
 */
public class ResultHtmlBuilder {
    
    public String buildHtml(ApiDocFieldDefinition definition) {
        StringBuilder html = new StringBuilder();
        html.append("<tr>")
            .append("<td>"+definition.getName()+"</td>")
            .append("<td>"+definition.getDataType()+"</td>")
            .append("<td>"+buildExample(definition)+"</td>")
            .append("<td>"+definition.getDescription()+"</td>");
        html.append("</tr>");
        
        return html.toString();
    }
    
    protected String buildExample(ApiDocFieldDefinition definition) {
        StringBuilder html = new StringBuilder();
        if(definition.getElements().size() > 0) {
            html.append("<table>")
                .append("<tr>")
                    .append("<th>名称</th>")
                    .append("<th>类型</th>")
                    .append("<th>示例值</th>")
                    .append("<th>描述</th>")
                .append("</tr>");
            
            List<ApiDocFieldDefinition> els = definition.getElements();
            for (ApiDocFieldDefinition apiDocFieldDefinition : els) {
                html.append("<tr>")
                    .append("<td>"+apiDocFieldDefinition.getName()+"</td>")
                    .append("<td>"+apiDocFieldDefinition.getDataType()+"</td>")
                    .append("<td>"+buildExample(apiDocFieldDefinition)+"</td>")
                    .append("<td>"+apiDocFieldDefinition.getDescription()+"</td>")
                .append("</tr>");
            }
            html.append("</table>");
        }else{
            html.append(buildExampleValue(definition));
        }
        return html.toString();
    }
    
    protected String buildExampleValue(ApiDocFieldDefinition definition) {
        return definition.getExample();
    }

}
