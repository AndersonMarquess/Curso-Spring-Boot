package com.andersonmarques.cursomc.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

    //Recebe um parâmetro da url e decodifica com os padrões do UTF-8
    public static String decodeParam(String param) {
        try {
            return URLDecoder.decode(param, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static List<Integer> decodeURL(String url){
        String[] catNumero = url.split(",");
        List<Integer> numeros = new ArrayList<>();
        for (int i = 0; i < catNumero.length; i++) {
            numeros.add(Integer.parseInt(catNumero[i]));
        }
        return numeros;

        //Usando lambda para realizar a mesma operação de split e retorno como lista
//        return Arrays.asList(url.split(",")).stream().map(qualquer -> Integer.parseInt(qualquer)).collect(Collectors.toList());
    }
}
