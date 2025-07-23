package br.com.cesarmontaldi.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.cesarmontaldi.exceptions.LojaVirtualException;
import br.com.cesarmontaldi.model.Produto;
import br.com.cesarmontaldi.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	private Integer statusCode;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EmailSendService emailService;
	


	public Produto salvar(Produto produto) throws IOException {
		
		if (produto.getNome().length() < 10) {
			throw new LojaVirtualException("Nome do produto deve conter pelo menos 10 letras.", statusCode = 422);
		}
		
		if (produto.getEmpresa() == null || (produto.getEmpresa().getId() == null)) {
			throw new LojaVirtualException("A empresa deve ser informada!", statusCode = 422);
		}
		
		if (produto.getId() == null && produtoRepository.existsProduto(produto.getNome().toUpperCase(), produto.getEmpresa().getId())) {
			throw new LojaVirtualException("Já existe um produto com o nome: " + produto.getNome(), statusCode = 409);
		}
		
		if (produto.getEmpresa() == null || produto.getEmpresa().getId() <= 0) {
			throw new LojaVirtualException("A empresa responsavel deve ser informada!", statusCode = 422);
		}
		
		if (produto.getCategoriaProduto() == null || produto.getCategoriaProduto().getId() <= 0) {
			throw new LojaVirtualException("Categoria deve ser informada!", statusCode = 422);
		}
		
		if (produto.getMarcaProduto() == null || produto.getMarcaProduto().getId() <= 0) {
			throw new LojaVirtualException("Marca do Produto deve ser informada!", statusCode = 422);
		}
		
		if (produto.getTipoUnidade() == null || produto.getTipoUnidade().trim().isEmpty()) {
			throw new LojaVirtualException("Tipo da unidade deve ser informada!", statusCode = 422);
		}
		
		if (produto.getQuantidadeEstoque() < 1) {
			throw new LojaVirtualException("O produto deve ter no minimo 1 em estoque.");
		}
		
		if (produto.getImagens() == null || produto.getImagens().isEmpty() || produto.getImagens().size() == 0) {
			throw new LojaVirtualException("Ao salvar um produto as imagens são obrigatorio.");
		}
		
		if (produto.getImagens().size() < 3) {
			throw new LojaVirtualException("Deve ser informado pelo menos 3 imagens para o Produto.");
		}
		
		if (produto.getImagens().size() > 6) {
			throw new LojaVirtualException("Deve ser informado no máximo 6 imagens.");
		}
		
		if (produto.getId() == null) {
			
			for (int images = 0; images < produto.getImagens().size(); images++) {
				produto.getImagens().get(images).setProduto(produto);
				produto.getImagens().get(images).setEmpresa(produto.getEmpresa());
				
				String base64Image = "";
				
				if (produto.getImagens().get(images).getImagemOriginal().contains("data:image")) {
					base64Image = produto.getImagens().get(images).getImagemOriginal().split(",")[1];
				} else {
					base64Image = produto.getImagens().get(images).getImagemOriginal();
				}
				
				byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64Image);
				
				BufferedImage imageBuffered = ImageIO.read(new ByteArrayInputStream(imageBytes));
				
				if (imageBuffered != null) {
					
					int type = imageBuffered.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : imageBuffered.getType();
					int largura = Integer.parseInt("800");
					int altura = Integer.parseInt("600");
					
					BufferedImage resizeImage = new BufferedImage(largura, altura, type);
					Graphics2D graphic = resizeImage.createGraphics();
					graphic.drawImage(imageBuffered, 0, 0, largura, altura, null);
					graphic.dispose();
					
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
					ImageIO.write(resizeImage, "png", outputStream);
					
					String miniImageBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(outputStream.toByteArray());
					
					produto.getImagens().get(images).setImagemMiniatura(miniImageBase64);
					
					imageBuffered.flush();
					resizeImage.flush();
					outputStream.flush();
					outputStream.close();
				}
			}
		}
		
		Produto newProduto = produtoRepository.save(produto);
		
		if (produto.getAlertaQuantidadeEstoque() && produto.getQuantidadeEstoque() < produto.getQuantidadeAlertaEstoque()) {
			StringBuilder alertaProduto = new StringBuilder();
			alertaProduto.append("<h2>")
			.append("Produto: " + produto.getNome())
			.append(" está com o estoque abaixo do esperado: " + produto.getQuantidadeEstoque());
			alertaProduto.append("<p> ID Prod.: ").append(produto.getId()).append("</p>");
			
			if (produto.getEmpresa().getEmail() != null) {
				emailService.sendEmailHtml("Produto sem estoque!", alertaProduto.toString(), produto.getEmpresa().getEmail());
			}
			
		}
		
		return newProduto;
	}


	public List<Produto> buscarPorNome(String nome) {
		
		return produtoRepository.buscarProdutoNome(nome)
				.stream()
				.toList();
	}
	
	
	public Produto buscarProduto(Long idProduto) {
		
		Produto produto  = produtoRepository
				.findById(idProduto)
				.orElseThrow(()-> new LojaVirtualException("Produto não encontrado!", statusCode = 404));
		
		return produto;
	}
	
	
	public void deletar(Long idProduto) {
		
		if (!produtoRepository.existsById(idProduto)) {
			throw new LojaVirtualException("Produto não encontrado!", statusCode = 404);
		} else {
			produtoRepository.deleteById(idProduto);
		}
	}

}
