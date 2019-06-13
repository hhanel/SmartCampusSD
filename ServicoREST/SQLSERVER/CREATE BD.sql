USE [BaseSensores]
GO

---------------------------------------------------------------------------------------------
-- Criação da tabela de TIPO_SENSOR
---------------------------------------------------------------------------------------------
CREATE TABLE [TIPO_SENSOR](
	[COD_TIPO_SENSOR] [int] NOT NULL,
	[DES_TIPO_SENSOR] [nvarchar](30) NOT NULL,
	[COMUNICACAO] [char](1) NOT NULL,
	[MINIMO] [int] NOT NULL,
	[MAXIMO] [int] NOT NULL,
	[INTERVALO] [int] NOT NULL
	
	CONSTRAINT [PK_TIPO_SENSOR] PRIMARY KEY CLUSTERED ([COD_TIPO_SENSOR] ASC)
)
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tabela de registro dos tipos de sensores', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TIPO_SENSOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Codigo do tipo do sensor', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TIPO_SENSOR', @level2type=N'COLUMN', @level2name=N'COD_TIPO_SENSOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Descrição do tipo de sensor', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TIPO_SENSOR', @level2type=N'COLUMN', @level2name=N'DES_TIPO_SENSOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Comunicação utilizada pelo sensor (U - UDP, T - TCP)', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TIPO_SENSOR', @level2type=N'COLUMN', @level2name=N'COMUNICACAO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Valor minimo a ser gerado pelo sensor', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TIPO_SENSOR', @level2type=N'COLUMN', @level2name=N'MINIMO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Valor maximo a ser gerado pelo sensor', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TIPO_SENSOR', @level2type=N'COLUMN', @level2name=N'MAXIMO'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Intervalo em segundos de amostragem', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'TIPO_SENSOR', @level2type=N'COLUMN', @level2name=N'INTERVALO'
GO

---------------------------------------------------------------------------------------------
-- Criação da tabela de SENSOR
---------------------------------------------------------------------------------------------
CREATE TABLE [SENSOR](
	[COD_SENSOR] [int] NOT NULL,	
	[DES_SENSOR] [nvarchar](30) NOT NULL,
	[LATITUDE] [float] NOT NULL,	
	[LONGITUDE] [float] NOT NULL,	
	[TIP_SENSOR] [int] NOT NULL
	
	CONSTRAINT [PK_SENSOR] PRIMARY KEY CLUSTERED ([COD_SENSOR] ASC)
)
GO

ALTER TABLE [SENSOR]  WITH CHECK ADD  CONSTRAINT [FK_SENSOR] FOREIGN KEY([TIP_SENSOR])
REFERENCES [TIPO_SENSOR] ([COD_TIPO_SENSOR])
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tabela de registro dos sensores', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SENSOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Chave primaria da tabela de sensores', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SENSOR', @level2type=N'COLUMN', @level2name=N'COD_SENSOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Descrição do sensor', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SENSOR', @level2type=N'COLUMN', @level2name=N'DES_SENSOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Latitude da localização do sensor', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SENSOR', @level2type=N'COLUMN', @level2name=N'LATITUDE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Longitude da localização do sensor', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SENSOR', @level2type=N'COLUMN', @level2name=N'LONGITUDE'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tipo do sensor, chave estrangeira de tipo de sensor', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'SENSOR', @level2type=N'COLUMN', @level2name=N'TIP_SENSOR'
GO

---------------------------------------------------------------------------------------------
-- Criação da tabela de DADOS
---------------------------------------------------------------------------------------------
CREATE TABLE [DADOS](
	[COD_SENSOR] [int] NOT NULL,
	[SEQ_DADOS] [int] NOT NULL,
	[DATAHORA] [datetime] NOT NULL,
	[VALOR] [float] NOT NULL
	
	CONSTRAINT [PK_DADOS] PRIMARY KEY CLUSTERED ([SEQ_DADOS] ASC)
)
GO

ALTER TABLE [DADOS]  WITH CHECK ADD CONSTRAINT [FK_DADOS] FOREIGN KEY([COD_SENSOR])
REFERENCES [SENSOR] ([COD_SENSOR])
GO

EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Tabela de registro dos dados dos sensores', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DADOS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Codigo do sensor ao que os dados pertencem', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DADOS', @level2type=N'COLUMN', @level2name=N'COD_SENSOR'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Sequence da tabela de dados', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DADOS', @level2type=N'COLUMN', @level2name=N'SEQ_DADOS'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'Data e hora do registro', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DADOS', @level2type=N'COLUMN', @level2name=N'DATAHORA'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'valor do dado recebido do sensor', @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'DADOS', @level2type=N'COLUMN', @level2name=N'VALOR'
GO

-- Cria sequence SEQ_SENSOR
CREATE SEQUENCE [SEQ_DADOS]
	START WITH 1
	INCREMENT BY 1 ;
GO







