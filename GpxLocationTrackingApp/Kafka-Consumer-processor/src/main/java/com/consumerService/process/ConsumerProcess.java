package com.consumerService.process;

import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KGroupedStream;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Printed;

import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consumerService.converter.Conversion;
import com.consumerService.model.GpxLocationData;
import com.consumerService.model.GpxTrackingData;
import com.consumerService.serialization.SerdesMain;


@Service
public class ConsumerProcess implements PropertiesKafka{
			
		@Autowired
		private Conversion convert;

		@PostConstruct
		public void consumeData() {
			try {
				Properties props = new Properties();
				props.put(StreamsConfig.APPLICATION_ID_CONFIG, APPLICATIONID);
				props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAPSERVERS);
				props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
				props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
				props.put("value.serializer", "com.consumerService.serialization.LocationSerializer");
			    props.put("value.deserializer", "com.consumerService.serialization.LocationDeserializer");

				StreamsBuilder streamBuilder = new StreamsBuilder();
				KStream<Integer, String> kstream = streamBuilder.stream(TOPIC);

				// Mapping the stream to transformed result data object
				KStream<String, GpxLocationData> kstreamValues = kstream
						.map((k, v) -> new KeyValue<>("gpxLocationData", convert.convertTrackingData(v)));
			
				KGroupedStream<String,GpxLocationData> kGroupStream =  kstreamValues.groupByKey(Grouped.with(Serdes.String(), SerdesMain.GpxLocationData()));				
				
				KTable<String,GpxTrackingData> kTable = kGroupStream
						 .aggregate(
					                ()-> new GpxTrackingData()
					                .addTotalTime(0)
					                .addTotalDistance(0),
			                        (k,v,aggregateValue) -> { 
			                          System.out.println(aggregateValue);
	                                  return new GpxTrackingData()
			                            .addTotalTime(aggregateValue.getTotalTime()+v.getTimeTracker())
			                            .addTotalDistance(aggregateValue.getTotalDistance() + v.getDistanceTracker());
			                        },		                        	
			                        Materialized.<String,GpxTrackingData,KeyValueStore<Bytes, byte[]>>
			                                        as("gpxlocation_aggregate")
			                                        .withKeySerde(Serdes.String())
			                                                .withValueSerde(SerdesMain.GpxTrackingData())
			                );
				
				kTable.toStream().print(Printed.<String, GpxTrackingData>toSysOut());
				
				Topology topology = streamBuilder.build();
				KafkaStreams streams = new KafkaStreams(topology, props);

				System.out.println("Starting the stream");
				streams.cleanUp();

				streams.start();

				Runtime.getRuntime().addShutdownHook(new Thread(() -> {
					System.out.println("Shutting down stream");
					streams.close();
				}));

				
			}catch(Exception ex) {
				ex.printStackTrace();
			}

		}
			
	}