package pe.mil.ejercito.microservice;

import com.bxcode.tools.loader.base.MicroserviceBaseApplication;
import com.bxcode.tools.loader.componets.properties.MicroserviceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.validation.constraints.NotNull;

/**
 * Application
 * <p>
 * Application class.
 * <p>
 * THIS COMPONENT WAS BUILT ACCORDING TO THE DEVELOPMENT STANDARDS
 * AND THE BXCODE APPLICATION DEVELOPMENT PROCEDURE AND IS PROTECTED
 * BY THE LAWS OF INTELLECTUAL PROPERTY AND COPYRIGHT...
 *
 * @author cbaciliod
 * @author bacsystem.sac@gmail.com
 * @since 20/02/2024
 */

@SpringBootApplication(scanBasePackages = {
        "pe.mil.ejercito.microservice",
        "com.bxcode.tools.loader",
})
public class Application extends MicroserviceBaseApplication {



    public Application(@NotNull MicroserviceProperties microserviceProperties) {
        super(microserviceProperties);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }
}