package br.com.fiap.consultavideos.models;

import java.util.List;

public class VideoResponse {
    private List<VideoDTO> data;

    public VideoResponse() {
    }

    public VideoResponse(List<VideoDTO> data) {
        this.data = data;
    }

    public List<VideoDTO> getData() {
        return data;
    }

    public void setData(List<VideoDTO> data) {
        this.data = data;
    }
}
