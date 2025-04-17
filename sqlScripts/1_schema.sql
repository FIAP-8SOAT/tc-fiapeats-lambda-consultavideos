CREATE TABLE IF NOT EXISTS upload_arquivos
(
    id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    nome_video character varying(255) COLLATE pg_catalog."default" NOT NULL,
    id_usuario character varying(255) COLLATE pg_catalog."default" NOT NULL,
    status_processamento character varying(255) COLLATE pg_catalog."default" NOT NULL,
    url_download_imagens character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT id_pkey PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE IF EXISTS upload_arquivos OWNER to sa;