package view;

import service.MainService;

import java.util.Scanner;

public class Menu {
    private final MainService service;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(MainService service) {
        this.service = service;
    }

    private void waitRead(){
        System.out.println("\n Для продожения нажмите enter");
        scanner.nextLine();
    }

    public void run() {
        showMenu();

    }

    private void showMenu() {
    }

    private void schowSubMunu(int choice){

    }

    private void showUserMenu(){

    }

    private void handelUserMenuChoice(int input){

    }

}
