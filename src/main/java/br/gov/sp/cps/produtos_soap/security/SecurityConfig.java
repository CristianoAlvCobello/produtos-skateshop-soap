package br.gov.sp.cps.produtos_soap.security;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.ext.WSPasswordCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;

@Configuration
public class SecurityConfig {

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {

        Wss4jSecurityInterceptor interceptor =
                new Wss4jSecurityInterceptor();

        interceptor.setValidationActions("UsernameToken");
        interceptor.setValidationCallbackHandler(
                passwordCallbackHandler()
        );

        return interceptor;
    }

    @Bean
    public CallbackHandler passwordCallbackHandler() {

        return new CallbackHandler() {

            @Override
            public void handle(Callback[] callbacks)
                    throws IOException, UnsupportedCallbackException {

                for (Callback callback : callbacks) {

                    if (callback instanceof WSPasswordCallback passwordCallback) {

                        if ("admin".equals(passwordCallback.getIdentifier())) {

                            passwordCallback.setPassword("123456");

                        } else {

                            throw new IOException(
                                    "Usuário não autorizado"
                            );
                        }
                    }
                }
            }
        };
    }
}