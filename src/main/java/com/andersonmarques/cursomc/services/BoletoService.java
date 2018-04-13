package com.andersonmarques.cursomc.services;

import com.andersonmarques.cursomc.domain.PagamentoComBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preeencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instante) {
        Calendar calendar = Calendar.getInstance();
        //Adiciona a data da criação do pedido
        calendar.setTime(instante);
        //No campo Dia da semana será adicionado mais 7 dias
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        pagto.setDataVencimento(calendar.getTime());
    }
}
