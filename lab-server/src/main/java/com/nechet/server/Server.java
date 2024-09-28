package com.nechet.server;

import com.nechet.common.util.model.Vehicle;
import com.nechet.server.commandLogic.CommandsRealisator;
import com.nechet.server.connectionLogic.RequestHandler;
import com.nechet.server.connectionLogic.TCPserver;
import com.nechet.server.databaseLogic.TableMaker;
import com.nechet.server.fileManipulation.ReadFromFileObject;
import com.nechet.server.fileManipulation.ReadJSONCollection;
import com.nechet.server.system.VehiclesManager;
import com.nechet.server.system.Utils;

import java.sql.SQLException;
import java.util.LinkedList;


public class Server {

    public static CommandsRealisator realisator = new CommandsRealisator();

        public static void main(String[] args) throws Exception {
            String login;
            String password;

            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
            while (true) {
                //System.out.println("Введите логин и пароль к бд через пробел:");
                //Scanner sc = new Scanner(System.in);
                //String[] line = sc.nextLine().split(" ");
                //login = line[0]; password = line[1];
                try {
                    TableMaker maker = new TableMaker(Utils.getUrl(), "s409427","bcWQPoi6Q1UFrwVT");
                    maker.createTable();

                    break;
                } catch (SQLException e) {
                    System.out.println("Не удалось подключиться к бд: "+e.getMessage());
                    e.printStackTrace();
                }
            }
            String path = System.getenv("lab5");
            Utils.setEnv(path);
            RequestHandler requestHandler = new RequestHandler();
            TCPserver server = new TCPserver(requestHandler);
            VehiclesManager vehicleManager = VehiclesManager.getInstance();
            ReadFromFileObject<LinkedList<Vehicle>> reader = new ReadJSONCollection();
            LinkedList<Vehicle> vehicles = new LinkedList<Vehicle>(); //reader.read(System.getenv(Utils.getEnv())+"/Vehicles.json");
            vehicleManager.setCollection(vehicles);
            try {
                server.openConnection();
                System.out.println("Сервер запущен");
                server.run();
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    {
                        System.out.println("Получен сигнал завершения работы (Ctrl+D).");
                        System.out.println("Закрываем программу");
                        server.close();
                        System.exit(1);
                    }

                    @Override
                    public void run() {
                    }
                });
            } finally {
                server.close();
            }
        }
    }

