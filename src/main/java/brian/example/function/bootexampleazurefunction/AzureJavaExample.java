package brian.example.function.bootexampleazurefunction;

import brian.example.function.bootexampleazurefunction.model.User;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AzureJavaExample {

    @Value("${spring.application.name}")
    private String appName;

    @FunctionName("httpWithName")
    public String httpWithNameMethod(
            @HttpTrigger(name = "req", methods = { HttpMethod.GET,
                    HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {

        String reqName = request.getQueryParameters().get("name");

        // Use plain Spring Beans.
        return "Hello "+new User(reqName)+" from "+appName;
    }

    @FunctionName("httpWithAge")
    public String httpWithAgeMethod(
            @HttpTrigger(name = "req", methods = { HttpMethod.GET,
                    HttpMethod.POST }, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            ExecutionContext context) {

        // Use SCF composition.
        String age = request.getQueryParameters().get("age");

        if( age == null ){
            return "You didn't provide age on "+appName;
        }else{
            return "You are "+age+" years old on "+appName;
        }
    }
}
