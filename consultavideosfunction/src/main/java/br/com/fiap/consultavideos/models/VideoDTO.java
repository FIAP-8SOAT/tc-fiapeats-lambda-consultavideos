package br.com.fiap.consultavideos.models;

public class VideoDTO {
    private String nome;
    private String status;
    private String url;

    public VideoDTO() {
    }

    public VideoDTO(String nome, String status, String url) {
        this.nome = nome;
        this.status = status;
        this.url = url;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
