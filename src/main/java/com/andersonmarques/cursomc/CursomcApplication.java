package com.andersonmarques.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.andersonmarques.cursomc.domain.Categoria;
import com.andersonmarques.cursomc.domain.Cidade;
import com.andersonmarques.cursomc.domain.Cliente;
import com.andersonmarques.cursomc.domain.Endereco;
import com.andersonmarques.cursomc.domain.Estado;
import com.andersonmarques.cursomc.domain.Produto;
import com.andersonmarques.cursomc.domain.enums.TipoCliente;
import com.andersonmarques.cursomc.repositories.CategoriaRepository;
import com.andersonmarques.cursomc.repositories.CidadeRepository;
import com.andersonmarques.cursomc.repositories.ClienteRepository;
import com.andersonmarques.cursomc.repositories.EnderecoRepository;
import com.andersonmarques.cursomc.repositories.EstadoRepository;
import com.andersonmarques.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.0d);
		Produto p2 = new Produto(null, "Impressora", 800.0d);
		Produto p3 = new Produto(null, "Mouse", 80.0d);
		
		//Colocando na categoria os produtos
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Colocando nos produtos as categorias
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		//Estados e Cidades
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "Campinas", est2);
		Cidade c3 = new Cidade(null, "São Paulo", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		//Clientes
		Cliente cli1 = new Cliente(null, "Maria Silva", "mariasilva@email.com", "12345678912", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("11-11112222","11-33334444"));

		Endereco e1 = new Endereco(null, "Rua flores", "12", "jardim", "1234-999", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105","Centro","5678-000", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}
}
