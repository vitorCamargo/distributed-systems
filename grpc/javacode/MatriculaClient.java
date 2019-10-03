/*
    Description: This is the Java Client for a Data Foreigner Representation, where it's called functions for the server to use the database.
    Author: Gabriel David Sacca, Vitor Bueno de Camargo
    Created at: September, 26th. 2019
    Updated at: September, 30th. 2019
*/
// package io.grpc.examples.matricula;
import io.grpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.matricula.MatriculaServiceGrpc.MatriculaServiceBlockingStub;
import io.grpc.examples.matricula.MatriculaServiceGrpc.MatriculaServiceStub;
import io.grpc.stub.StreamObserver;

import java.net.*;
import java.io.*;
import java.util.*;

public class MatriculaClient {

    private final ManagedChannel channel;
    private final MatriculaServiceBlockingStub blockingStub;
    private final MatriculaServiceStub asyncStub;

    public MatriculaClient(String host, int port) {
        this(ManagedChannelBuilder.forAddress(host, port).usePlaintext());
    }

    public MatriculaClient(ManagedChannelBuilder<?> channelBuilder) {
        channel = channelBuilder.build();
        blockingStub = MatriculaServiceGrpc.newBlockingStub(channel);
        asyncStub = MatriculaServiceGrpc.newStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public void cadastraNota(int RA, String cod_disciplina, int ano, int semestre, Double nota, int faltas) {
        Matricula request = Matricula.newBuilder();
        request.setRA(RA);
        request.setCod_disciplina(cod_disciplina);
        request.setAno(ano);
        request.setSemestre(semestre);
        request.setNota(nota);
        request.setFaltas(faltas);

        request.build();

        Mess msg;
        try {
            msg = blockingStub.cadastraNota(request);
            System.out.print(new String(msg.getMess()));
        } catch(IOException err) {
            System.out.println(err);
        }
    }

    public static void main(String[] args) throws InterruptedException {
    
        MatriculaClient client = new MatriculaClient("localhost", 50051);
        Scanner reader = new Scanner(System.in);
        String buffer = "";
        try{
            while(true) {
                System.out.print("/> ");
                buffer = reader.nextLine();


                if(buffer.equals("EXIT")) break;
                if(buffer.equals("HELP")){
                    System.out.println("POST [RA] [cod_disciplina] [ano] [semestre] [nota] [falta]");
                    System.out.println("PUT [nota] [RA] [cod_disciplina] [ano] [semestre]");
                    System.out.println("REMOVE [RA] [cod_disciplina] [ano] [semestre]");
                    System.out.println("GETMYNOTA [RA]");
                    System.out.println("GETNOTABYSEMESTRE [cod_disciplina] [ano] [semestre]");
                    System.out.println("GETALUNOSBYANO [cod_disciplina] [ano] [semestre]");
                } else {
                    if(buffer.startsWith("POST ")) {
                        client.cadastraNota(1921959, "MB", 2019, 2, 9.5, 0);
                        // String[] request = buffer.split(" ");
                        // if(request.length == 7) {
                        //     byte[] data = new byte[100];
                        //     in.read(data);
                        //     System.out.println(new String(data));
                        // } else{
                        //     System.out.println("deu errado aqui");
                        // }
                    }
                    // if(buffer.startsWith("PUT ")){
                    //     String[] request = buffer.split(" ");
                    //     if(request.length == 6) {
                    //         byte[] data = new byte[100];
                    //         in.read(data);
                    //         System.out.println(new String(data));
                    //     } else{
                    //         System.out.println("deu errado aqui");
                    //     }
                    // }
                    // if(buffer.startsWith("REMOVE ")){
                    //     String[] request = buffer.split(" ");
                    //     if(request.length == 5) {
                    //         byte[] data = new byte[100];
                    //         in.read(data);
                    //         System.out.println(new String(data));
                    //     } else{
                    //         System.out.println("deu errado aqui");
                    //     }
                    // }
                    // if(buffer.startsWith("GETMYNOTA ")){
                    //     String[] request = buffer.split(" ");
                    //     if(request.length == 2) {
                    //         byte[] data = new byte[100];
                    //         in.read(data);
                    //         System.out.println(new String(data));
                    //     } else{
                    //         System.out.println("deu errado aqui");
                    //     }
                    // }
                    // if(buffer.startsWith("GETNOTABYSEMESTRE ")){
                    //     String[] request = buffer.split(" ");
                    //     if(request.length == 4) {
                    //         byte[] data = new byte[100];
                    //         System.out.println("chegou");
                    //         in.read(data);
                    //         System.out.println("passou");
                    //         System.out.println(new String(data));
                    //     } else{
                    //         System.out.println("deu errado aqui");
                    //     }
                    // }
                    // if(buffer.startsWith("GETALUNOSBYANO ")){
                    //     String[] request = buffer.split(" ");
                    //     if(request.length == 4) {
                    //         byte[] data = new byte[100];
                    //         in.read(data);
                    //         System.out.println(new String(data));
                    //     } else{
                    //         System.out.println("deu errado aqui");
                    //     }
                    // }
                }
            }
            // msg = in.read();      // aguarda resposta do servidor
            // System.out.println("Server disse: " + msg);
        } catch (EOFException eofe){
            System.out.println("EOF:" + eofe.getMessage());
        } catch (IOException ioe){
            System.out.println("IO:" + ioe.getMessage());
        } finally {
            client.shutdown();
        }
        // try {
        //   // Looking for a valid feature
        //   client.getFeature(409146138, -746188906);
    
        //   // Feature missing.
        //   client.getFeature(0, 0);
    
        //   // Looking for features between 40, -75 and 42, -73.
        //   client.listFeatures(400000000, -750000000, 420000000, -730000000);
    
        //   // Record a few randomly selected points from the features file.
        //   client.recordRoute(features, 10);
    
        //   // Send and receive some notes.
        //   CountDownLatch finishLatch = client.routeChat();
    
        //   if (!finishLatch.await(1, TimeUnit.MINUTES)) {
        //     client.warning("routeChat can not finish within 1 minutes");
        //   }
        // } finally {
        //   client.shutdown();
        // }
    }
} //class    

