package br.gov.sp.cps.produtos_soap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import java.util.Properties;
import br.gov.sp.cps.produtos_soap.exception.ProdutoNaoEncontradoException;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;

@EnableWs
@Configuration
public class WebServiceConfig implements WsConfigurer {
    @Bean
    public SoapFaultMappingExceptionResolver exceptionResolver() {

        SoapFaultMappingExceptionResolver resolver =
                new SoapFaultMappingExceptionResolver();

        SoapFaultDefinition faultDefinition =
                new SoapFaultDefinition();

        faultDefinition.setFaultCode(SoapFaultDefinition.CLIENT);

        resolver.setDefaultFault(faultDefinition);

        Properties errorMappings = new Properties();

        errorMappings.setProperty(
                ProdutoNaoEncontradoException.class.getName(),
                "CLIENT"
        );

        resolver.setExceptionMappings(errorMappings);

        resolver.setOrder(1);

        return resolver;
    }

    @Autowired
    private Wss4jSecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
    interceptors.add(securityInterceptor);
}

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext context) {

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();

        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);

        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "produtos")
    public DefaultWsdl11Definition produtosWsdl(XsdSchema produtosSchema) {

        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();

        wsdl.setPortTypeName("ProdutosPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("http://cps.sp.gov.br/produtos");
        wsdl.setSchema(produtosSchema);

        return wsdl;
    }

    @Bean
    public XsdSchema produtosSchema() {
        return new SimpleXsdSchema(
                new ClassPathResource("produtos.xsd")
        );
    }
}