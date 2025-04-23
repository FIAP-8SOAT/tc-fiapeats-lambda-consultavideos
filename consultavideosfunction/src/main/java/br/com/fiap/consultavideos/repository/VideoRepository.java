package br.com.fiap.consultavideos.repository;

import br.com.fiap.consultavideos.models.VideoDTO;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VideoRepository {

    private final DynamoDB dynamo;
    private final Table table;
    //private final Index subIndex;

    public VideoRepository() {
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        this.dynamo = new DynamoDB(client);
        this.table = dynamo.getTable("fiapeatsdb");
        //this.subIndex = table.getIndex("SubIndex");
    }

    public List<VideoDTO> buscarVideosPorSub(String sub) {
        List<VideoDTO> videos = new ArrayList<>();

        try {
            videos = new ArrayList<>();

            for (Item item : table.scan()) {
                if (sub.equals(item.getString("ID_USUARIO"))) {
                    videos.add(new VideoDTO(
                            item.getString("NOME_VIDEO"),
                            item.getString("STATUS_PROCESSAMENTO"),
                            item.getString("URL_DOWNLOAD")
                    ));
                }
            }


        } catch (Exception e) {
            System.err.println("Erro ao consultar vídeos por SUB: " + e.getMessage());
            e.printStackTrace();
        }

        return videos;
    }

}
