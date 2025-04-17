package br.com.fiap.consultavideos.repository;

import br.com.fiap.consultavideos.models.VideoDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoRepository {

    private final String jdbcUrl = "jdbc:postgresql://fiapeatsdb.cwz64iwcg37p.us-east-1.rds.amazonaws.com:5432/fiapeatsdb";
    private final String user = "sa";
    private final String password = "fiapeatsdb-pass";

    public List<VideoDTO> buscarVideosPorSub(String sub) throws SQLException {
        List<VideoDTO> videos = new ArrayList<>();

        String sql = "SELECT nome_video, status_processamento, url_download_imagens FROM upload_arquivos WHERE id_usuario = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, sub);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String nome = rs.getString("nome_video");
                String status = rs.getString("status_processamento");
                String url = rs.getString("url_download_imagens");

                videos.add(new VideoDTO(nome, status, url));
            }
        }

        return videos;
    }
}
