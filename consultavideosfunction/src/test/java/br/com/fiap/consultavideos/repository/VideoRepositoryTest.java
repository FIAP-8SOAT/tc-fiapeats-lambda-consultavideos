package br.com.fiap.consultavideos.repository;

import br.com.fiap.consultavideos.models.VideoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class VideoRepositoryTest {

    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws Exception {
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Intercepta a chamada ao DriverManager.getConnection(...)
        DriverManager.setLoginTimeout(0); // evita delays
    }

    //@Test
    void testBuscarVideosPorSub_comResultados() throws Exception {
        // Mocks da sequência de chamadas
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Simula uma linha retornada
        when(mockResultSet.next()).thenReturn(true, false); // 1 linha, depois termina
        when(mockResultSet.getString("nome_video")).thenReturn("video.mp4");
        when(mockResultSet.getString("status_processamento")).thenReturn("processado");
        when(mockResultSet.getString("url_download_imagens")).thenReturn("http://url.com");

        // Faz o "hack" para injetar a conexão falsa no DriverManager
        try (var driverMocked = mockStatic(DriverManager.class)) {
            driverMocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            // Executa
            VideoRepository repo = new VideoRepository();
            List<VideoDTO> videos = repo.buscarVideosPorSub("sub123");

            assertEquals(1, videos.size());
            VideoDTO video = videos.get(0);
            assertEquals("video.mp4", video.getNome());
            assertEquals("processado", video.getStatus());
            assertEquals("http://url.com", video.getUrl());
        }
    }

    //@Test
    void testBuscarVideosPorSub_semResultados() throws Exception {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        // Nenhum resultado
        when(mockResultSet.next()).thenReturn(false);

        try (var driverMocked = mockStatic(DriverManager.class)) {
            driverMocked.when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenReturn(mockConnection);

            VideoRepository repo = new VideoRepository();
            List<VideoDTO> videos = repo.buscarVideosPorSub("sub456");

            assertTrue(videos.isEmpty());
        }
    }

    //@Test
    void testBuscarVideosPorSub_lancaSQLException() throws Exception {
        SQLException fakeEx = new SQLException("Falha de conexão");

        try (MockedStatic<DriverManager> driverMocked = mockStatic(DriverManager.class)) {
            driverMocked
                    .when(() -> DriverManager.getConnection(anyString(), anyString(), anyString()))
                    .thenThrow(fakeEx);

            VideoRepository repo = new VideoRepository();

            SQLException thrown = assertThrows(SQLException.class, () -> repo.buscarVideosPorSub("sub789"));
            assertEquals("Falha de conexão", thrown.getMessage());
        }
    }
}