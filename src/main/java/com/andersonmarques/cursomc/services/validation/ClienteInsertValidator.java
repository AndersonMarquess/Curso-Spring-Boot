package com.andersonmarques.cursomc.services.validation;

import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.domain.enums.TipoCliente;
import com.andersonmarques.cursomc.dto.ClienteNewDTO;
import com.andersonmarques.cursomc.repositories.ClienteRepository;
import com.andersonmarques.cursomc.resources.exceptions.FieldMessage;
import com.andersonmarques.cursomc.services.validation.utils.BR.BR;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator <ClienteInsert, ClienteNewDTO> {
    @Autowired
    private ClienteRepository clienteRepository;

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

        Cliente aux = clienteRepository.findByEmail(objDto.getEmail());
        if ( aux != null) {
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
