function loadBar(categoryDataLoad, seriePlaneadoLoad, titleGra, subTitle,
		labelY) {
	var categoryData = categoryDataLoad;
	var listCategoryData = new Array();
	listCategoryData = categoryData.split('~');

	$(document).ready(
			function() {
				chart = new Highcharts.Chart({
					colors : [ '#058DC7', '#50B432', '#ED561B', '#DDDF00',
							'#24CBE5', '#64E572', '#FF9655', '#FFF263',
							'#6AF9C4' ],
					chart : {
						type : 'column',
						renderTo : 'container',
						marginBottom : 125
					},
					title : {
						text : titleGra
					},
					yAxis : {
						min : 0,
						title : {
							text : labelY
						}
					},
					subtitle : {
						text : subTitle
					},
					plotOptions : {
						column : {
							pointPadding : 0.2,
							borderWidth : 0
						}
					},
					xAxis : {
						categories : listCategoryData,
						labels : {
							rotation : listCategoryData.length > 10 ? -90 : 0,
							align : 'right',
						}

					},
					credits : {
						enabled : false
					},
					legend : {
						layout : 'vertical',
						align : 'right',
						verticalAlign : 'top',
						x : -100,
						y : 100,
						floating : true,
						borderWidth : 1,
						backgroundColor : '#FFFFFF',
						shadow : false
					},
					series : seriePlaneadoLoad
				});
			});
}

function loadPie(seriesData, title, nameSerie){
	
	$(document).ready(
			function() {
				chart = new Highcharts.Chart({
	        chart: {
	        	renderTo : 'container',
	            plotBackgroundColor: null,
	            plotBorderWidth: null,
	            plotShadow: false
	        },
	        title: {
	            text: title
	        },
	        tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	        },
			credits : {
				enabled : false
			},
	        plotOptions: {
	            pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: nameSerie,
	            data: seriesData
	               
	        }]
	    });
	});
}

function loadLine(categoryDataLoad, seriePlaneadoLoad, titleVar, titleSerie) {
	var categoryData = categoryDataLoad;
	var listCategoryData = new Array();
	listCategoryData = categoryData.split('~');

	var seriePlaneado = seriePlaneadoLoad;
	var listseriePlaneado = new Array();
	listseriePlaneado = seriePlaneado.split('~');

	var planeado = new Array();
	for ( var int = 0; int < listseriePlaneado.length - 1; int++) {
		planeado[int] = parseFloat(listseriePlaneado[int]);
	}

	$(document).ready(
			function() {
				chart = new Highcharts.Chart({
					colors : [ '#058DC7', '#50B432', '#ED561B', '#DDDF00',
							'#24CBE5', '#64E572', '#FF9655', '#FFF263',
							'#6AF9C4' ],
					chart : {
						renderTo : 'container',
						marginBottom : 125
					},
					title : {
						text : titleVar
					},
					xAxis : {
						categories : listCategoryData,

						labels : {
							rotation : -90,
							align : 'right',
						}
					},
					credits : {
						enabled : false
					},
					legend : {
						layout : 'vertical',
						align : 'right',
						verticalAlign : 'top',
						x : -100,
						y : 100,
						floating : true,
						borderWidth : 1,
						backgroundColor : '#FFFFFF',
						shadow : false
					},
					series : [ {
						type : 'line',
						name : titleSerie,
						data : planeado
					} ]
				});
			});
}

function start(defaultSeriesTypeField, titleField, sourceField,
		categoriesField, xAxisTitle, yAxisTitle, unityMeasureTooltip,
		reportData) {
	var listData = new Array();
	listData = reportData.split('~');
	if (listData.length > 1) {
		var i = 0;
		var series = new Array();
		for (i = 0; i < listData.length; i++) {
			var objectSerie = new Object();
			objectSerie.name = listData[i];
			var objectData = null;
			objectData = listData[i + 1];
			var arrayObjectData = new Array();
			arrayObjectData = objectData.split(',');
			var intData = new Array();
			for ( var j = 0; j < arrayObjectData.length; j++) {
				intData[j] = parseFloat(arrayObjectData[j]);
			}
			objectSerie.data = intData;
			series[i / 2] = objectSerie;
			i++;
		}

		var categoryData = new Array();
		categoryData = categoriesField.split(',');

		$(document).ready(
				function() {
					var chart = new Highcharts.Chart({
						chart : {
							renderTo : 'container',
							defaultSeriesType : defaultSeriesTypeField,
							margin : [ 50, 150, 130, 80 ]
						},
						title : {
							text : titleField,
							style : {
								margin : '10px 100px 0 0' // center it
							}
						},
						subtitle : {
							text : sourceField,
							style : {
								margin : '0 100px 0 0' // center it
							}
						},

						xAxis : {
							categories : categoryData,
							title : {
								text : xAxisTitle
							},
							labels : {
								rotation : -90,
								align : 'right',
								style : {
									font : 'normal 10px Verdana, sans-serif'
								}
							}
						},
						yAxis : {
							title : {
								text : yAxisTitle
							},
							plotLines : [ {
								value : 0,
								width : 1,
								color : '#808080'
							} ]
						},
						tooltip : {
							formatter : function() {
								return '<b>' + this.series.name + '</b><br/>'
										+ this.x + ': ' + this.y
										+ unityMeasureTooltip;
							}
						},
						legend : {
							layout : 'vertical',
							backgroundColor : '#FFFFFF',
							borderWidth : 1,
							style : {
								left : '665px',
								top : '50px',
								bottom : 'auto'
							}

						},
						credits : {
							enabled : false
						},

						series : series
					});

				});
	}
}

function startMultipleSeries(seriesObj, seriesFlag) {
	var seriesMFlag = new Array();
	seriesMFlag = seriesFlag.split('~');
	var milestoneList = new Array();

	for ( var j = 0; j < seriesMFlag.length - 1; j++) {
		var milestone = new Array();
		milestone = seriesMFlag[j].split(':');
		var arrayDate = new Array();
		arrayDate = milestone[1].split('/');
		var number = parseInt(arrayDate[1]) - 1;

		milestoneList[j] = {
			title : milestone[0],
			x : Date.UTC(arrayDate[0], number, arrayDate[2])
		};
	}

	var seriesOptions = new Array();
	var seriesFinal = new Array();
	var arrayObjectData = new Array();
	arrayObjectData = seriesObj.split('~');

	for ( var i = 0; i < arrayObjectData.length; i++) {

		var arrayObjectDataInside = new Array();
		arrayObjectDataInside = arrayObjectData[i].split(':');

		for ( var k = 0; k < arrayObjectDataInside.length; k++) {
			seriesFinal.push(formatValues(arrayObjectDataInside[1]),
					arrayObjectDataInside[0]);
			k++;
		}

	}

	var k = 0;
	for ( var i = 0; i < seriesFinal.length; i++) {

		seriesOptions[k] = {
			name : seriesFinal[i + 1],
			data : seriesFinal[i],
			id : 'dateSerie' + i
		};

		i++;
		k++;
	}

	jQuery(function() {
		// Create the chart
		window.chart = new Highcharts.StockChart({
			chart : {
				renderTo : 'container'
			},

			rangeSelector : {

				inputEnabled : true,
				selected : 4,
				buttons : [ {
					type : 'month',
					count : 3,
					text : '3m'

				}, {
					type : 'month',
					count : 6,
					text : '6m'
				}, {
					type : 'year',
					count : 1,
					text : '1y'
				}, {
					type : 'year',
					count : 2,
					text : '2y'
				}, {
					type : 'year',
					count : 5,
					text : '5y'
				}, {
					type : 'all',
					text : 'All'
				} ]
			},

			title : {
				text : 'Valor Ganado'
			},

			xAxis : {
				maxZoom : 30 * 24 * 3600000
			// fourteen days
			},
			yAxis : {
				title : {
					text : 'Dinero'
				}
			},
			credits : {
				enabled : false
			},
			series : [ {
				name : 'Planeado',
				data : seriesOptions[1].data,
				id : 'dataseries',
			}, {
				name : 'Ejecutado',
				data : seriesOptions[0].data,
				id : 'dataseries1',
			}, {
				name : 'Proyectado',
				data : seriesOptions[2].data,
				id : 'dataseries2',
			}, {
				type : 'flags',
				name : 'Hitos',
				data : milestoneList,
				shape : 'squarepin'
			} ]

		});
	});
}

function formatValues(valuesChart) {

	var arrayObjectData = new Array();
	arrayObjectData = valuesChart.split(',');
	var intData = new Array();
	for ( var j = 0; j < arrayObjectData.length; j++) {
		var arrayDate = new Array();
		arrayDate = arrayObjectData[j].split('/');
		var number = parseInt(arrayDate[1]) - 1;
		intData.push(new Array(Date.UTC(arrayDate[0], number, arrayDate[2]),
				parseFloat(arrayObjectData[j + 1])));
		j++;
	}
	return intData;
}

function chartEV(categoryDataLoad, serieEarnedValue, tittleChart, container) {
	var chart;

	var categoryData = categoryDataLoad;
	var listCategoryData = new Array();
	listCategoryData = categoryData.split('~');

	var serieEarned = serieEarnedValue;
	var listserieExecute = new Array();
	listserieExecute = serieEarned.split('~');

	var projection = new Array();
	for ( var int = 0; int < listserieExecute.length - 1; int++) {
		if (int == 0) {
			projection[int] = {
				y : parseFloat(listserieExecute[int]),
				color : '#7EA202'
			};
		} else if (int == 1) {
			projection[int] = {
				y : parseFloat(listserieExecute[int]),
				color : '#ACC454'
			};
		} else if (int == 2) {
			projection[int] = {
				y : parseFloat(listserieExecute[int]),
				color : '#0F2851'
			};
		}
	}

	$(document).ready(function() {
		chart = new Highcharts.Chart({
			chart : {
				renderTo : container,
				type : 'column',
			},
			title : {
				text : tittleChart,
				x : -20
			// center
			},
			xAxis : {
				categories : listCategoryData,

				labels : {
					rotation : -90,
					align : 'right',
				}
			},
			yAxis : {
				title : {
					text : 'Pesos'
				}
			},
			credits : {
				enabled : false
			},
			series : [ {
				name : 'Proyeccion',
				data : projection
			} ]
		});
	});
}

function chartEVComplete(categoryDataLoad, seriePlaneadoLoad, serieExecuteLoad,
		serieAcumulateExecuteLoad, titleVar, container) {
	var categoryData = categoryDataLoad;
	var listCategoryData = new Array();
	listCategoryData = categoryData.split('~');

	var seriePlaneado = seriePlaneadoLoad;
	var listseriePlaneado = new Array();
	listseriePlaneado = seriePlaneado.split('~');

	var serieExecute = serieExecuteLoad;
	var listserieExecute = new Array();
	listserieExecute = serieExecute.split('~');

	var serieAcumulateExecute = serieAcumulateExecuteLoad;
	var listserieAcumulateExecute = new Array();
	listserieAcumulateExecute = serieAcumulateExecute.split('~');

	var planeado = new Array();
	for ( var int = 0; int < listseriePlaneado.length - 1; int++) {
		planeado[int] = parseFloat(listseriePlaneado[int]);
	}

	var execute = new Array();
	for ( var int = 0; int < listserieExecute.length - 1; int++) {
		execute[int] = parseFloat(listserieExecute[int]);
	}

	var acumulateExecute = new Array();
	for ( var int = 0; int < listserieAcumulateExecute.length - 1; int++) {
		acumulateExecute[int] = parseFloat(listserieAcumulateExecute[int]);
	}

	$(document).ready(function() {
		chart = new Highcharts.Chart({
			chart : {
				renderTo : container,
				marginBottom : 175
			},
			title : {
				text : titleVar
			},
			xAxis : {
				categories : listCategoryData,

				labels : {
					rotation : -90,
					align : 'right',
				}
			},
			yAxis : {
				title : {
					text : 'Pesos'
				}
			},
			credits : {
				enabled : false
			},
			series : [ {
				type : 'column',
				name : 'Planeado',
				data : planeado
			}, {
				type : 'column',
				name : 'Ejecutado',
				data : execute
			}, {
				type : 'column',
				name : 'Proyectado',
				data : acumulateExecute
			}, ]
		});
	});
}

function chartEVCompleteChart(categoryDataLoad, seriePlaneadoLoad,
		serieExecuteLoad, serieProyected, seriePlaneadoLoadAccumulated,
		serieExecuteLoadAccumulated, serieProyectedLoadAccumulated, titleVar,
		container) {

	var categoryData = categoryDataLoad;
	var listCategoryData = new Array();
	listCategoryData = categoryData.split('~');

	var seriePlaneado = seriePlaneadoLoad;
	var listseriePlaneado = new Array();
	listseriePlaneado = seriePlaneado.split('~');

	var serieExecute = serieExecuteLoad;
	var listserieExecute = new Array();
	listserieExecute = serieExecute.split('~');

	var serieAcumulateExecute = serieProyected;
	var listserieAcumulateExecute = new Array();
	listserieAcumulateExecute = serieAcumulateExecute.split('~');

	var seriePlaneadoAccumulated = seriePlaneadoLoadAccumulated;
	var listseriePlaneadoAccumulated = new Array();
	listseriePlaneadoAccumulated = seriePlaneadoAccumulated.split('~');

	var serieExecutedAccumulated = serieExecuteLoadAccumulated;
	var listserieExecutedAccumulated = new Array();
	listserieExecutedAccumulated = serieExecutedAccumulated.split('~');

	var serieExecutedProyected = serieProyectedLoadAccumulated;
	var listserieProyectedAccumulated = new Array();
	listserieProyectedAccumulated = serieExecutedProyected.split('~');

	var planeadoA = new Array();
	for ( var int = 0; int < listseriePlaneadoAccumulated.length - 1; int++) {
		planeadoA[int] = parseFloat(listseriePlaneadoAccumulated[int]);
	}

	var executedA = new Array();
	for ( var int = 0; int < listserieExecutedAccumulated.length - 1; int++) {
		executedA[int] = parseFloat(listserieExecutedAccumulated[int]);
	}

	var planeado = new Array();
	for ( var int = 0; int < listseriePlaneado.length - 1; int++) {
		planeado[int] = parseFloat(listseriePlaneado[int]);
	}

	var execute = new Array();
	for ( var int = 0; int < listserieExecute.length - 1; int++) {
		execute[int] = parseFloat(listserieExecute[int]);
	}

	var acumulateExecute = new Array();
	for ( var int = 0; int < listserieAcumulateExecute.length - 1; int++) {
		acumulateExecute[int] = parseFloat(listserieAcumulateExecute[int]);
	}

	var acumulateProyected = new Array();
	for ( var int = 0; int < listserieProyectedAccumulated.length - 1; int++) {
		acumulateProyected[int] = parseFloat(listserieProyectedAccumulated[int]);
	}

	$(document).ready(function() {
		chart = new Highcharts.Chart({
			chart : {
				renderTo : container,
				marginBottom : 175
			},
			title : {
				text : titleVar
			},
			xAxis : {
				categories : listCategoryData,

				labels : {
					rotation : -90,
					align : 'right',
				}
			},
			yAxis : {
				title : {
					text : 'Pesos'
				}
			},
			credits : {
				enabled : false
			},
			series : [ {
				type : 'column',
				name : 'Planeación Mensual',
				data : planeado
			}, {
				type : 'column',
				name : 'Ejecución Mensual',
				data : execute
			}, {
				type : 'column',
				name : 'Costo Mensual',
				data : acumulateExecute
			}, {
				type : 'line',
				name : 'CPTR',
				data : planeadoA
			}, {
				type : 'line',
				name : 'CATR',
				data : executedA
			}, {
				type : 'line',
				name : 'CPTP',
				data : acumulateProyected
			}, ]
		});
	});
}

function chartEVMilestone(categoryDataLoad, seriePlaneadoLoad,
		serieExecuteLoad, serieProyected, seriePlaneadoLoadAccumulated,
		serieExecuteLoadAccumulated, serieProyectedLoadAccumulated,
		serieMilestone, titleVar, container) {

	var categoryData = categoryDataLoad;
	var listCategoryData = new Array();
	listCategoryData = categoryData.split('~');

	var seriePlaneado = seriePlaneadoLoad;
	var listseriePlaneado = new Array();
	listseriePlaneado = seriePlaneado.split('~');

	var serieExecute = serieExecuteLoad;
	var listserieExecute = new Array();
	listserieExecute = serieExecute.split('~');

	var serieAcumulateExecute = serieProyected;
	var listserieAcumulateExecute = new Array();
	listserieAcumulateExecute = serieAcumulateExecute.split('~');

	var seriePlaneadoAccumulated = seriePlaneadoLoadAccumulated;
	var listseriePlaneadoAccumulated = new Array();
	listseriePlaneadoAccumulated = seriePlaneadoAccumulated.split('~');

	var serieExecutedAccumulated = serieExecuteLoadAccumulated;
	var listserieExecutedAccumulated = new Array();
	listserieExecutedAccumulated = serieExecutedAccumulated.split('~');

	var serieExecutedProyected = serieProyectedLoadAccumulated;
	var listserieProyectedAccumulated = new Array();
	listserieProyectedAccumulated = serieExecutedProyected.split('~');

	var serieTotalMilestone = serieMilestone;
	var serieMilestoneArray = new Array();
	serieMilestoneArray = serieTotalMilestone.split('~');

	var planeadoA = new Array();
	for ( var int = 0; int < listseriePlaneadoAccumulated.length - 1; int++) {
		planeadoA[int] = parseFloat(listseriePlaneadoAccumulated[int]);
	}

	var executedA = new Array();
	for ( var int = 0; int < listserieExecutedAccumulated.length - 1; int++) {
		executedA[int] = parseFloat(listserieExecutedAccumulated[int]);
	}

	var planeado = new Array();
	for ( var int = 0; int < listseriePlaneado.length - 1; int++) {
		planeado[int] = parseFloat(listseriePlaneado[int]);
	}

	var execute = new Array();
	for ( var int = 0; int < listserieExecute.length - 1; int++) {
		execute[int] = parseFloat(listserieExecute[int]);
	}

	var acumulateExecute = new Array();
	for ( var int = 0; int < listserieAcumulateExecute.length - 1; int++) {
		acumulateExecute[int] = parseFloat(listserieAcumulateExecute[int]);
	}

	var acumulateProyected = new Array();
	for ( var int = 0; int < listserieProyectedAccumulated.length - 1; int++) {
		acumulateProyected[int] = parseFloat(listserieProyectedAccumulated[int]);
	}

	for ( var j = 0; j < serieMilestoneArray.length; j++) {
		var orderP = serieMilestoneArray[j].substring(0, serieMilestoneArray[j]
				.indexOf("++"))
		acumulateProyected[orderP] = {
			y : parseFloat(acumulateProyected[orderP]),
			marker : {
				symbol : 'url(/pring/css/images/milestone.png)'
			}
		}
	}

	$(document).ready(function() {
		chart = new Highcharts.Chart({
			chart : {
				renderTo : container,
				marginBottom : 175
			},
			title : {
				text : titleVar
			},
			xAxis : {
				categories : listCategoryData,

				labels : {
					rotation : -90,
					align : 'right',
				}
			},
			yAxis : {
				title : {
					text : 'Pesos'
				}
			},
			tooltip : {
				crosshairs : true,
				shared : true
			},
			credits : {
				enabled : false
			},
			series : [ {
				type : 'column',
				name : 'Planeación Mensual',
				data : planeado
			}, {
				type : 'column',
				name : 'Ejecución Mensual',
				data : execute
			}, {
				type : 'column',
				name : 'Costo Mensual',
				data : acumulateExecute
			}, {
				type : 'line',
				name : 'CPTR',
				data : planeadoA
			}, {
				type : 'line',
				name : 'CATR',
				data : executedA
			}, {
				type : 'line',
				name : 'CPTP',
				data : acumulateProyected
			}, ]
		});
	});
}

function loadBarPurchase(categoryDataLoad, seriePlaneadoLoad, serieExecuteLoad,
		serieProjectedLoad, serieAcumulateExecuteLoad,
		serieAcumulatePlannedLoad, serieAcumulateProjectedLoad, titleVar,
		containerDiv) {

	var categoryData = categoryDataLoad;
	var listCategoryData = new Array();
	listCategoryData = categoryData.split('~');

	var seriePlaneado = seriePlaneadoLoad;
	var listseriePlaneado = new Array();
	listseriePlaneado = seriePlaneado.split('~');

	var serieExecute = serieExecuteLoad;
	var listserieExecute = new Array();
	listserieExecute = serieExecute.split('~');

	var serieProjected = serieProjectedLoad;
	var listserieProjected = new Array();
	listserieProjected = serieProjected.split('~');

	var serieAcumulateExecute = serieAcumulateExecuteLoad;
	var listserieAcumulateExecute = new Array();
	listserieAcumulateExecute = serieAcumulateExecute.split('~');

	var serieAcumulatePlanned = serieAcumulatePlannedLoad;
	var listserieAcumulatePlanned = new Array();
	listserieAcumulatePlanned = serieAcumulatePlanned.split('~');

	var serieAcumulateProjected = serieAcumulateProjectedLoad;
	var listserieAcumulateProjected = new Array();
	listserieAcumulateProjected = serieAcumulateProjected.split('~');

	var planeado = new Array();
	for ( var int = 0; int < listseriePlaneado.length - 1; int++) {
		planeado[int] = parseFloat(listseriePlaneado[int]);
	}

	var execute = new Array();
	for ( var int = 0; int < listserieExecute.length - 1; int++) {
		execute[int] = parseFloat(listserieExecute[int]);
	}

	var projected = new Array();
	for ( var int = 0; int < listserieProjected.length - 1; int++) {
		projected[int] = parseFloat(listserieProjected[int]);
	}

	var acumulateExecute = new Array();
	for ( var int = 0; int < listserieAcumulateExecute.length - 1; int++) {
		acumulateExecute[int] = parseFloat(listserieAcumulateExecute[int]);
	}

	var acumulatePlanned = new Array();
	for ( var int = 0; int < listserieAcumulatePlanned.length - 1; int++) {
		acumulatePlanned[int] = parseFloat(listserieAcumulatePlanned[int]);
	}

	var acumulateProjected = new Array();
	for ( var int = 0; int < listserieAcumulateProjected.length - 1; int++) {
		acumulateProjected[int] = parseFloat(listserieAcumulateProjected[int]);
	}

	$(document).ready(function() {
		chart = new Highcharts.Chart({
			chart : {
				renderTo : containerDiv,
				marginBottom : 175
			},
			title : {
				text : 'Compra tiempo de ' + titleVar
			},
			xAxis : {
				categories : listCategoryData,

				labels : {
					rotation : -90,
					align : 'right',
				}
			},
			credits : {
				enabled : false
			},
			series : [ {
				type : 'column',
				name : 'Planeado',
				data : planeado
			}, {
				type : 'column',
				name : 'Ejecutado',
				data : execute
			}, {
				type : 'column',
				name : 'Proyectado',
				data : projected
			}, {
				type : 'spline',
				name : 'Acumulado Ejecutado',
				data : acumulateExecute
			}, {
				type : 'spline',
				name : 'Acumulado Planeado',
				data : acumulatePlanned
			}, {
				type : 'spline',
				name : 'Acumulado Proyectado',
				data : acumulateProjected
			} ]
		});
	});
}

/**
 * Esta funcion genera las graficas para el reporte de variables del inidcador
 * de ventas
 * 
 * @author JRodriguez
 */
function chartReport(target, chartTitile, axisYTitle, chartXCategories,
		seriesName, chartData) {
	chartXCategories = chartXCategories.replace("]", "").replace("[", "")
			.split(",");
	for (j = 0; j < chartData.length; j++)
		for (i = 0; i < chartData[j].length; i++) {
			chartData[j][i] = parseInt(chartData[j][i], 10);
		}
	$(document)
			.ready(
					function() {
						chart = new Highcharts.Chart(
								{
									chart : {

										renderTo : target,
										type : 'column'
									},
									colors : [ '#FFDD44', '#55FF55', '#8bbc21',
											'#910000', '#1aadce', '#492970',
											'#f28f43', '#77a1e5', '#c42525',
											'#a6c96a' ],
									title : {
										text : chartTitile
									},
									xAxis : {
										categories : chartXCategories
									},
									yAxis : {
										min : 0,
										title : {
											text : axisYTitle,
											align : 'high'
										},
										stackLabels : {
											enabled : true,
											style : {
												fontWeight : 'bold',
												color : (Highcharts.theme && Highcharts.theme.textColor)
														|| '#494949'
											}
										}
									},

									tooltip : {
										style : {
											fontWeight : 'bold'
										}
									},
									plotOptions : {
										column : {
											stacking : 'normal',
											dataLabels : {
												enabled : true,
												color : (Highcharts.theme && Highcharts.theme.dataLabelsColor)
														|| '#494949',

												style : {
													fontWeight : 'bold'
												}
											}
										}
									},
									series : [ {
										name : seriesName[1],
										data : chartData[1]

									}, {
										name : seriesName[0],
										data : chartData[0]

									} ]
								});
					});
}