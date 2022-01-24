package org.br.foodjet.resource.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@RegisterForReflection
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetailTO {

    private String code;
    private String message;

}
