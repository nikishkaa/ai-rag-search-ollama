package com.nikishka.ai.rag_search.ingestion;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentIngestionService implements CommandLineRunner {
    @Value("classpath:/pdf/spring-boot-reference.pdf")
    private Resource resource;

    private final VectorStore vectorStore;

    public DocumentIngestionService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(String... args) throws Exception {
        // Read the PDF
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        //Split the PDF Contents Chunks
        TextSplitter textSplitter = new TokenTextSplitter();
        List<Document> documents = textSplitter.split(tikaDocumentReader.read());
        //Store the data in the vector database
        vectorStore.accept(documents);
    }
}
