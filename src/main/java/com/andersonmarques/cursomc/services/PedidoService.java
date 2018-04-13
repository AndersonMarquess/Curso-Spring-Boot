package com.andersonmarques.cursomc.services;

import com.andersonmarques.cursomc.domain.ItemPedido;
import com.andersonmarques.cursomc.domain.PagamentoComBoleto;
import com.andersonmarques.cursomc.domain.Pedido;
import com.andersonmarques.cursomc.domain.enums.EstadoPagamento;
import com.andersonmarques.cursomc.repositories.ItemPedidoRepository;
import com.andersonmarques.cursomc.repositories.PagamentoRepository;
import com.andersonmarques.cursomc.repositories.PedidoRepository;
import com.andersonmarques.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {
	
	//Essa anotação Autowired instância automaticamente a classe PedidoRepository
	@Autowired 
	private PedidoRepository repositorio;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private BoletoService boletoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	//Faz a busca no repositorio com base no id
	public Pedido find(Integer id) {
		Optional<Pedido> objetoRecebido = repositorio.findById(id);
		
		//Se o objeto não for encontrado, é lançado uma exception através de uma lambda para informar o problema.
		return objetoRecebido.orElseThrow(()-> new ObjectNotFoundException("O Objeto não foi contrado, ID: "+id+
				", Pedido: "+Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		//Se o pagamento do obj é uma instancia de PagamentoComBoleto
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preeencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		repositorio.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip :obj.getItens()) {
			ip.setDesconto(0d);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}
}
