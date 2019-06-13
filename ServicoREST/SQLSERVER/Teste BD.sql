-- INSERT exemplo para testes

INSERT INTO BaseSensores.dbo.TIPO_SENSOR (COD_TIPO_SENSOR, DES_TIPO_SENSOR, COMUNICACAO, MINIMO, MAXIMO, INTERVALO)
VALUES ('10', 'Sensor de ar', 'U', '10', '100', '1');

INSERT INTO BaseSensores.dbo.SENSOR (COD_SENSOR, DES_SENSOR, LATITUDE, LONGITUDE, TIP_SENSOR)
VALUES ('11', 'Sensor do norte', '10.50', '20.60', '10');

INSERT INTO BaseSensores.dbo.DADOS (COD_SENSOR, SEQ_DADOS, DATAHORA, VALOR)
OUTPUT INSERTED.SEQ_DADOS VALUES
('11', NEXT VALUE FOR BaseSensores.dbo.SEQ_DADOS, CURRENT_TIMESTAMP, '350');


-- SELECT 

-- pesquisa todos os tipos de sensores
SELECT * FROM BaseSensores.dbo.TIPO_SENSOR tip;

-- pesquisa tipo de sensor especifico
SELECT * FROM BaseSensores.dbo.TIPO_SENSOR WHERE COD_TIPO_SENSOR = '1';

-- pesquisar os sensores cadastrados
SELECT * FROM BaseSensores.dbo.SENSOR sen JOIN BaseSensores.dbo.TIPO_SENSOR tip ON sen.TIP_SENSOR = tip.COD_TIPO_SENSOR;

-- pesquisar sensor especifico
SELECT * FROM BaseSensores.dbo.SENSOR sen JOIN BaseSensores.dbo.TIPO_SENSOR tip ON sen.TIP_SENSOR = tip.COD_TIPO_SENSOR WHERE sen.COD_SENSOR = '1';

-- pesquisar sensor por tipo
SELECT * FROM BaseSensores.dbo.SENSOR sen JOIN BaseSensores.dbo.TIPO_SENSOR tip ON sen.TIP_SENSOR = tip.COD_TIPO_SENSOR WHERE tip.COD_TIPO_SENSOR = '1';

-- pesquisar os dados para algum determinado sensor
SELECT * FROM BaseSensores.dbo.SENSOR sen JOIN BaseSensores.dbo.TIPO_SENSOR tip ON sen.TIP_SENSOR = tip.COD_TIPO_SENSOR JOIN BaseSensores.dbo.DADOS dad ON dad.COD_SENSOR = sen.COD_SENSOR WHERE sen.COD_SENSOR = '1';










