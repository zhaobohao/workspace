import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;
import org.testng.annotations.Test;

public class ApiDocTest {
    @Test
    public void test_gDocs(){
        DocsConfig docsConfig = new DocsConfig();
        docsConfig.setProjectPath("D:\\workspace\\springBootExample\\springboot-apidoc");
        docsConfig.setApiVersion("V1.0");
        docsConfig.setAutoGenerate(Boolean.TRUE);
        docsConfig.setDocsPath("D:\\workspace\\springBootExample\\springboot-apidoc/apidocs");
        Docs.buildHtmlDocs(docsConfig);
    }
}
