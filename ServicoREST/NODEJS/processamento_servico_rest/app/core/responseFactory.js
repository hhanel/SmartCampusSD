'use strict';

const
    logger = require('../core/logFactory'),
    moment = require('moment');

// HTTP 1.1 / 200
module.exports.ok = function (req, res, jsonResponse, json) {
    if (json)
        res.status(200).json(jsonResponse);
    else if (typeof jsonResponse.mensagem !== 'undefined') {
        res.status(200).json(jsonResponse.mensagem);
    } else if (typeof jsonResponse.Response[0].dados === 'undefined') {
        res.status(200).json(jsonResponse.Response);
    } else {
        let filtroData = '';
        // Validacao de filtro de data
        if (req.hasOwnProperty('data')) {
            filtroData = '<p>Dados filtrados pela data ' + req.data + '.</p>';
        }

        // Media dos dados cadastrados e ultimos dados cadastrados nos sensores
        let
            ultimoDado = [],
            mostrarUltimoDado = '';

        for (let l = 0; l < jsonResponse.Response.length; l++) {
            let total = 0;
            for (let n = 0; n < jsonResponse.Response[l].dados.length; n++)
                total = total + jsonResponse.Response[l].dados[n].Valor;

            let resultado = parseFloat((total / jsonResponse.Response[l].dados.length).toFixed(2));

            logger.debug('Média dos dados cadastrados: ' + total + ' / ' + jsonResponse.Response[l].dados.length + ' = ' + resultado);

            ultimoDado[l] =
                'Sensor: ' + jsonResponse.Response[l].codSensor + '-' + jsonResponse.Response[l].desTipoSensor +
                ', ultimo dado cadastrado: ' + jsonResponse.Response[l].dados[jsonResponse.Response[l].dados.length - 1].Valor +
                ', média dos valores cadastrados: ' + resultado
        }

        for (let m = 0; m < ultimoDado.length; m++) {
            logger.debug(ultimoDado[m]);
            mostrarUltimoDado += '<p>' + ultimoDado[m] + '</p>';
        }

        // Estruturando o Dashboard
        let
            categoria = '[',
            series = [],
            i, j;

        for (j = 0; j < jsonResponse.Response.length; j++) {
            let
                dados = jsonResponse.Response[j].dados,
                sensor = [];

            for (i = 0; i < dados.length; i++) {
                sensor.push(dados[i].Valor);
                if (i + 1 < dados.length)
                    categoria += '\'' + moment(new Date(dados[i].Data).toISOString()).utc().format('DD-MM-YYYY HH:mm') + '\',';
                else if (j + 1 === jsonResponse.Response.length)
                    categoria += '\'' + moment(new Date(dados[i].Data).toISOString()).utc().format('DD-MM-YYYY HH:mm') + '\']';
                else if (moment(new Date(dados[i].Data).toISOString()).utc().format('DD-MM-YYYY HH:mm') !== moment(new Date(dados[i - 1].Data).toISOString()).utc().format('DD-MM-YYYY HH:mm'))
                    categoria += '\'' + moment(new Date(dados[i].Data).toISOString()).utc().format('DD-MM-YYYY HH:mm') + '\',';
            }
            series[j] = {
                name: jsonResponse.Response[j].codSensor + '-' + jsonResponse.Response[j].desTipoSensor,
                data: sensor
            }
        }

        res.writeHead(200);
        res.write(
            `
        <!DOCTYPE HTML>
        <html>
        
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <title>Sistemas Distribuídos</title>
        
            <style type="text/css">
        
            </style>
        </head>
        
        <body>
            <H1> Sistemas Distribuídos </H1>

            <H2> Ultimos dados cadastrados: </H2>
            ` + filtroData + `
            ` + mostrarUltimoDado + `

            <script src="../../dashboard/code/highcharts.js"></script>
            <script src="../../dashboard/code/modules/exporting.js"></script>
            <script src="../../dashboard/code/modules/export-data.js"></script>
        
            <div id="container" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
        
            <script type="text/javascript">
                Highcharts.chart('container', {
                    chart: {
                        type: 'line'
                    },
                    title: {
                        text: 'Dashboard Dados Registrados'
                    },
                    xAxis: {
                        categories: ` + categoria + `
                    },
                    yAxis: {
                        title: {
                            text: 'Dados'
                        }
                    },
                    plotOptions: {
                        line: {
                            dataLabels: {
                                enabled: true
                            },
                            enableMouseTracking: false
                        }
                    },
                    series: ` + JSON.stringify(series) + `
                });
            </script>
        </body>
        
        </html>
        `
        );
        res.end();
    }
};

// HTTP 1.1 / 400
module.exports.badRequest = function (req, res, err) {
    let msg = err.details[0].message,
        status =
        {
            'erros': [
                {
                    "mensagem": 'Parâmetros de entrada inválidos, ' + msg
                }
            ]
        };

    logger.debug('Response - notFound: ' + JSON.stringify(status) + err);
    // logger.error(error); detalhes do erro

    res.status(400).json(status);
};

// HTTP 1.1 / 500
module.exports.serverError = function (req, res, error) {
    let status =
    {
        'erros': [
            {
                "mensagem": error.message !== 'undefined' ? error.message : error
            }
        ]
    };

    logger.debug('Response - serverError: ' + JSON.stringify(status));
    // logger.error(error); detalhes do erro

    res.status(500).json(status);
};

module.exports.okPostDados = function (res, jsonResponse) {
    res.status(200).json(jsonResponse);
};