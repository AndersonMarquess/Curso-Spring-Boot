package com.andersonmarques.cursomc.services.validation;

import com.andersonmarques.cursomc.domain.enums.TipoCliente;
import com.andersonmarques.cursomc.dto.ClienteNewDTO;
import com.andersonmarques.cursomc.resources.exceptions.FieldMessage;
import com.andersonmarques.cursomc.services.validation.utils.BR.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator <ClienteInsert, ClienteNewDTO> {
    @Override
    public void initialize(ClienteInsert ann) {

    }
    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();


        if((objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()))&&(!BR.isValidCPF(objDto.getCpfOuCnpj()))){
            list.add(new FieldMessage("CpfOuCnpj","CPF inválido"));
        }
        if((objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()))&&(!BR.isValidCNPJ(objDto.getCpfOuCnpj()))){
            list.add(new FieldMessage("CpfOuCnpj","CNPJ inválido"));
        }


        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldName()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
