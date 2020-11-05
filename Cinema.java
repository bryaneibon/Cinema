package cinema_v14;

import java.util.Scanner;

public class Cinema {
    private double rows = 0;
    private double seatsPerRow = 0;
    private double frontHalfRows;
    private double backHalfRows;
    private int rowNumber;
    private int seatNumber;
    private String [][] savedSeat;
    private static boolean emptyRoom = true;
    private int occupiedSeats = 0;
    private int currentIncome = 0;
    private static Scanner scanner = new Scanner(System.in);

    public double getRows() {
        return rows;
    }

    public double getSeatsPerRow() {
        return seatsPerRow;
    }

    public double getBackHalfRows() {
        return backHalfRows;
    }

    public double getFrontHalfRows() {
        return frontHalfRows;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String[][] getSavedSeat() {
        return savedSeat;
    }

    public int getOccupiedSeats() {
        return occupiedSeats;
    }

    public int getCurrentIncome() {
        return currentIncome;
    }

    public void setCurrentIncome(int currentIncome) {
        this.currentIncome = currentIncome;
    }

    public void setOccupiedSeats(int occupiedSeats) {
        this.occupiedSeats = occupiedSeats;
    }

    public void setSavedSeat(String[][] savedSeat) {
        this.savedSeat = savedSeat;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setBackHalfRows(double backHalfRows) {
        this.backHalfRows = backHalfRows;
    }

    public void setFrontHalfRows(double frontHalfRows) {
        this.frontHalfRows = frontHalfRows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    protected Cinema (double rows, double seatsPerRow){
        this.rows = rows;
        this.seatsPerRow = seatsPerRow;
    }

    protected static void checkSeat(Cinema cinema){
        System.out.print("\nCinema:\n ");
        for (int i = 1; i < cinema.getSeatsPerRow()+1; i++) {
            System.out.print(" " + i);
        }
        System.out.println();
        int x = 1;
        if (emptyRoom){
            for (int i = 0; i < cinema.getRows(); i++) {
                System.out.print(x++);
                for (int j = 0; j < cinema.getSeatsPerRow(); j++) {
                    cinema.getSavedSeat()[i][j]=" S";
                    System.out.print(cinema.getSavedSeat()[i][j]);
                }
                System.out.print(x <= cinema.getSeatsPerRow() ? "\n" : "");
            }
        } else {
            for (int i = 0; i < cinema.getRows(); i++) {
                System.out.print(x++);
                for (int j = 0; j < cinema.getSeatsPerRow(); j++) {
                    System.out.print(cinema.getSavedSeat()[i][j]);
                }
                System.out.print(x <= cinema.getSeatsPerRow() ? "\n" : "");
            }
        }
    }

    protected static void buyTicket(Cinema cinema){
        boolean alreadyTaken;
        boolean wrongInput;
        /*Make sure to draw an empty room if no seat has been purchased yet*/
        if (emptyRoom){
            for (int i = 0; i < cinema.getRows(); i++) {
                for (int j = 0; j < cinema.getSeatsPerRow(); j++) {
                    cinema.getSavedSeat()[i][j]=" S";
                }
            }
        }

        do {
            System.out.println("\nEnter a row number:");
            cinema.setRowNumber(scanner.nextInt());
            System.out.println("Enter a seat number in that row:");
            cinema.setSeatNumber(scanner.nextInt());

            /*Re-initialize the both variable at every loop*/
            alreadyTaken = false;
            wrongInput = false;

            /*Save the user entry in the savedSeat array*/
            if(cinema.getRowNumber() > cinema.getRows() || cinema.getSeatNumber() > cinema.getSeatsPerRow()) {
                System.out.println("Wrong input!");
                wrongInput = true;
            }

            for (int i = 0; i < cinema.getRows(); i++) {
                if (!wrongInput){
                    for (int j = 0; j < cinema.getSeatsPerRow(); j++) {
                        if (i==cinema.getRowNumber()-1 && j==cinema.getSeatNumber()-1 && cinema.getSavedSeat()[i][j].equals(" S")){
                            cinema.getSavedSeat()[i][j]=" B";
                            cinema.setOccupiedSeats(cinema.getOccupiedSeats()+1);
                        } else if (i==cinema.getRowNumber()-1 && j==cinema.getSeatNumber()-1 && cinema.getSavedSeat()[i][j].equals(" B")) {
                            System.out.println("That ticket has already been purchased!");
                            alreadyTaken = true;
                            break;
                        }
                    }
                }
            }
        } while(alreadyTaken || wrongInput);

        ticketPrice(cinema);
    }

    public static void ticketPrice(Cinema cinema){
        int price = 0;
        /*Define Back and front seats*/
        cinema.setFrontHalfRows(Math.floor((cinema.getRows() / 2)));
        cinema.setBackHalfRows(Math.ceil((cinema.getRows() / 2)));

        if (cinema.getRows()*cinema.getSeatsPerRow() <= 60){
            price = 10;
        } else if (cinema.getRowNumber() <= cinema.getFrontHalfRows() && cinema.getRows()*cinema.getSeatsPerRow() > 60){
            price = 10;
        } else if (cinema.getRowNumber() > cinema.getFrontHalfRows() && cinema.getRows()*cinema.getSeatsPerRow() > 60){
            price = 8;
        }

        cinema.setCurrentIncome(cinema.getCurrentIncome()+price); /*Here is our current income*/
        System.out.println("Ticket price: $"+price);
    }

    protected static int estimateTotalIncome(Cinema cinema){
        double result;
        cinema.setFrontHalfRows(Math.floor((cinema.getRows() / 2)));
        cinema.setBackHalfRows(Math.ceil((cinema.getRows() / 2)));

        if (cinema.getRows()*cinema.getSeatsPerRow() <= 60){
            result = cinema.getRows()*cinema.getSeatsPerRow()*10;
        } else {
            result = (((cinema.getFrontHalfRows())*cinema.getSeatsPerRow()*10) + ((cinema.getBackHalfRows())*cinema.getSeatsPerRow()*8));
        }
        return (int) result;
    }

    private static void currentStats(Cinema cinema) {
        System.out.printf("Number of purchased tickets: %d%n" +
                          "Percentage: %.2f%%%n"              +
                          "Current income: $%d%n"             +
                          "Total income: $%d%n",
                          cinema.getOccupiedSeats(),
                          (cinema.getOccupiedSeats()/(cinema.getRows()*cinema.getSeatsPerRow()))*100,
                          cinema.getCurrentIncome(),
                          estimateTotalIncome(cinema));
    }

    public static void computeSelection(Cinema cinema) {
        /*We start by initializing the seats first by defining the coordinate that can be saved*/
        String [][] room = new String[(int) cinema.getRows()][(int) cinema.getSeatsPerRow()];
        cinema.setSavedSeat(room);

        displayMenu();
        boolean exit = false;
        do {
            String userChoice = scanner.next();
            switch (userChoice){
                case "1":
                    checkSeat(cinema);
                    System.out.println();
                    displayMenu();
                    break;
                case "2":
                    buyTicket(cinema);
                    emptyRoom = false;
                    displayMenu();
                    break;
                case "3":
                    currentStats(cinema);
                    displayMenu();
                    break;
                case "0":
                    exit = true;
                    break;
                default:
                    System.out.println("Unknown Command, choose between option 1 to 2, or 0 to exit the program");
                    displayMenu();
            }
        }while (!exit);
    }

    private static void displayMenu(){
        System.out.println("\n1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit");
    }

    public static void main(String[] args) {
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seatsPerRows = scanner.nextInt();
        Cinema cinema = new Cinema(rows, seatsPerRows);
        computeSelection(cinema);
    }
}
