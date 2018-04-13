package com.andersonmarques.cursomc.services.validation;

import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.dto.ClienteDTO;
import com.andersonmarques.cursomc.repositories.ClienteRepository;
import com.andersonmarques.cursomc.resources.exceptions.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator <ClienteUpdate, ClienteDTO> {

    @Autowired
    private ServletRequest servletRequest;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void initialize(ClienteUpdate ann) {

    }
    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
        //Suprime o warning da IDE
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) servletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Integer uriID = Integer.valueOf(map.get("id"));



        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
        if ( aux != null && !aux.getId().equals(uriID)) {
            list.add(new FieldMessage("email","Email já existente"));
        }


        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
