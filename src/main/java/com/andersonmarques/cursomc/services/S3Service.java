package com.andersonmarques.cursomc.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.andersonmarques.cursomc.services.exceptions.FileException;

@Service
public class S3Service {

	private static final Logger LOG = LoggerFactory.getLogger(S3Service.class);
	
	@Autowired
	private AmazonS3 s3cliente;
	
	@Value("${s3.bucket}")
	private String bucketName;
	
	public URI uploadFile(MultipartFile multipartFile) {
		try {
			String nomeArquivo = multipartFile.getName();
			InputStream iStream = multipartFile.getInputStream();
			String tipoDoConteudo = multipartFile.getContentType();
			return uploadFile(iStream, nomeArquivo, tipoDoConteudo);
		}catch(IOException erroIO) {
			throw new FileException("Erro de IO "+erroIO.getMessage());
		}
	}

	public URI uploadFile(InputStream inputStream, String fileName, String tipo) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentType(tipo);

			LOG.info("Iniciando upload");
			s3cliente.putObject(bucketName, fileName, inputStream, meta);
			LOG.info("Upload finalizado");

			return s3cliente.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao tentar converter URL em URI");
		}
	}

}
