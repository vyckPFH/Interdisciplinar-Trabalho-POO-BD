package br.edu.ifpr.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileManager {

    public static void readStream(Path path) throws IOException {
        /*
         * O que faz?
         * • Lê o arquivo usando Stream, linha por linha – ideal para arquivos grandes.
         */
        try (var stream = Files.lines(path, StandardCharsets.UTF_8)) {
            stream.forEach(System.out::println);
        }
    }

    public static void replaceText(Path path, String target, String replacement) throws IOException {
        /*
         * O que faz?
         * • Procura e substitui um texto dentro do arquivo
         * o Cria um arquivo temporário(temp-...)
         * o Abre o arquivo original para leitura (BufferedReader)
         * o Lê linha a linha, substituindo target por replacement com:
         * § line.replace(target, replacement)
         * o Escreve o resultado no arquivo temporário (BufferedWriter)
         * o No final, substitui o arquivo original pelo novo
         * (Files.move(...,REPLACE_EXISTING))
         */
        Path temp = Files.createTempFile("temp-", ".txt");
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                BufferedWriter writer = Files.newBufferedWriter(temp, StandardCharsets.UTF_8)) {
            String line;

            while ((line = reader.readLine()) != null) {
                writer.write(line.replace(target, replacement));
                writer.newLine();
            }
        }
        Files.move(temp, path, StandardCopyOption.REPLACE_EXISTING);
    }

    public static List<String> readAll(Path path) throws IOException {
        /*
         * O que faz?
         * • Lê todas as linhas de um arquivo texto e retorna uma lista de String
         */
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    public static void append(String content, Path path) throws IOException {
        /*
         * O que faz?
         * • Adiciona novas linhas ao final de um arquivo já existente.
         * • Se o arquivo não existir, ele será criado
         */
        Files.createDirectories(path.getParent());
        Files.writeString(path, content, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void write(String content, Path path) throws IOException {
        /*
         * O que faz?
         * • Grava um novo conteúdo no arquivo indicado
         * • Se o arquivo não existir, ele será criado.
         * • Se já existir, será sobrescrito (apagado e regravado)
         */
        Files.createDirectories(path.getParent());
        Files.writeString(path, content, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }


}
