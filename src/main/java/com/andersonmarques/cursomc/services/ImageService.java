package com.andersonmarques.cursomc.services;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.andersonmarques.cursomc.services.exceptions.FileException;

@Service
public class ImageService {
	
	public BufferedImage getJpgImageFromFile(MultipartFile multipartFile) {
		String extensao = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
		
		
		//Se não for png ou jpg lançar uma exception
		if(!extensao.equals("jpg") && !extensao.equals("png")) {
			throw new FileException("Formato não suportado, escolha uma imagem jpg ou png.");
		}
		
		try {
			BufferedImage image = ImageIO.read(multipartFile.getInputStream());
			if("png".equals(extensao)) {
				image = pngToJpg(image);
			}
			return image;
		} catch (IOException e) {
			throw new FileException("Não foi possível fazer a leitura da imagem");
		}
	}

	
	//Converter uma imagem png em jpg
	public BufferedImage pngToJpg(BufferedImage image) {
		BufferedImage jpgImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
		return jpgImage;
	} 
	
	public InputStream getInputStram(BufferedImage img, String extensao) {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			ImageIO.write(img, extensao, output);
			return new ByteArrayInputStream(output.toByteArray());
		}catch(IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
	
	//Corta a imagem para fazer um quadrado
	public BufferedImage cropImageEmQuadro(BufferedImage image) {
		//Pega o menor tamanho da imagem
		int minimo = (image.getWidth() <= image.getHeight()) ? image.getWidth() : image.getHeight();
		
		//Realiza o crop da imagem
		return Scalr.crop(image,
				(image.getWidth()/2 - (minimo/2)),
				(image.getHeight()/2 - (minimo/2)),
				minimo, minimo);
	}
	
	
	//muda o tamanho da imagem
	public BufferedImage resize(BufferedImage image, int tamanho) {
		return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, tamanho);
	}
	
	
}
