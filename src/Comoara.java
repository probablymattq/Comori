import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

//Clasa pentru coordonatele in planu cartezian
class Coordonate {
    int x = 0, y = 0;

    Coordonate() {
        this.x = x;
        this.y = y;
    }
}

public class Comoara extends Coordonate {

    //Variabilele care vor fi folosite pentru a adauga culori output urilor din terminal
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";

    int d, p;

    Comoara(int d, int p) {
        this.d = d;
        this.p = p;
    }

    //Metoda de citire din fisier
    static void fileReader(ArrayList<Comoara> date) {
        try {
            date.clear(); //Se șterg datele actuale din arraylist, ca să se scrie fișierul actual
            File file = new File("Comori.in");
            Scanner comori = new Scanner(file);
            if (!comori.hasNextLine()) {
                System.out.println("(!) Fisierul de intrare este gol. Introduceti mai intai date in fisier, pentru a putea continua sarcinile.");
            }
            comori.nextLine(); //Se trece la următoarea linie
            while (comori.hasNext()) { //Citirea fișierului
                int d = Integer.parseInt(comori.next());
                int p = Integer.parseInt(comori.next());
                date.add(new Comoara(d, p)); //Introducerea in array a datelor citite
            }
            comori.close();
        } catch (FileNotFoundException e) { //Exceptii
            System.out.println("(!) Fisierul n-a fost gasit.");
        } catch (InputMismatchException e) {
            System.out.println("(!) Tipul datelor nu corespund.");
        }
    }

    //Sarcinile 1-7
    static void newValue(ArrayList<Comoara> date, int n) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println(GREEN + "(*) Introduceti 2 numere, primul reprezentand directia si al doilea, numarul de pasi." + RESET);
        System.out.print("> "); int dir = scanner.nextInt();
        System.out.print("> "); int steps = scanner.nextInt();

        if (dir < 1 || dir > 8) { //Restrictiile impuse de către sarcina de lucru
            System.out.println(RED + "(!) Restrictiile impuse nu sunt respectate (directia = " + dir + ") "  + RESET + "\n1 <= numarul de pasi <= 1000\n1 <= n <= 40");

        } else if (steps < 1 || steps > 1000) {
            System.out.println(RED + "(!) Restrictiile impuse nu sunt respectate (numarul de pasi = " + steps + ") "  + RESET + "\n1 <= directia <= 8\n1 <= numarul de pasi <= 1000\n1 <= n <= 40");

        } else if (n + 1 > 40) {
            System.out.println(RED + "(!) Restrictiile impuse nu sunt respectate (n = " + n + ") "  + RESET + "\n1 <= directia <= 8\n1 <= numarul de pasi <= 1000\n1 <= n <= 40");

        } else {
            date.add(new Comoara(dir, steps)); //Adaugarea datelor in arraylist

            //Rescrierea datelor noi in fisier
            BufferedWriter writer = new BufferedWriter(new FileWriter("Comori.in"));
            writer.write(n + 1 + "");
            for (int i = 0; i < date.size(); i++) {
                writer.append("\n" + date.get(i).d + " " + date.get(i).p);
            }

            System.out.println(GREEN + "(*) Datele introduse au fost inscrise in fisier." + RESET);
            writer.close();
        }
    }
    static void removeValue(ArrayList<Comoara> date, int n) throws IOException {
        if (n == 1) { //Restrictie ca sa nu poata fi stearsa singura linie existenta
            System.out.println(RED + "(!) Nu sunt linii care pot fi sterse." + RESET);
        } else {

            date.remove(date.size() - 1); //Stergerea ultimului element din arraylist

            //Rescrierea arraylist-ului nou in fisier
            BufferedWriter writer = new BufferedWriter(new FileWriter("Comori.in"));
            writer.write(n - 1 + "");
            for (int i = 0; i < date.size(); i++) {
                writer.append("\n" + date.get(i).d + " " + date.get(i).p);
            }

            System.out.println(GREEN + "(*) Ultima linie din fisier a fost stearsa." + RESET);
            writer.close();
        }
    }
    static int steps(ArrayList<Comoara> date) {
        int totalSteps = 0;

        //Calcularea numarului de pasi facuti
        for (int i = 0; i < date.size(); i++) {
            totalSteps += date.get(i).p;
        }

        return totalSteps; //Returnarea integerului
    }
    static int dirrectionChange(ArrayList<Comoara> date) {
        int amount = 0;

        //Calcularea schimbarilor de directie
        for (int i = 0; i < date.size(); i++) {
            if (i < (date.size() - 1)) {
                if (date.get(i).d != date.get(i + 1).d) amount++;
            }
        }

        return amount; //Returnarea integerului
    }
    static void fileWithCertainValues(ArrayList<Comoara> date) throws IOException {
        File newFile = new File("UnPas.txt");

        //Verificare daca fisierul a fost creat de catre program, sau daca e deja existent
        if (newFile.createNewFile()) {
            System.out.println(GREEN + "(*) Fisierul 'UnPas.txt' a fost creat cu succes." + RESET);
        } else {
            System.out.println(RED + "(!) Fisierul 'UnPas.txt' este deja existent." + RESET);
        }

        //Scrierea sau rescrierea datelor in fisierul creat / existent
        BufferedWriter writer = new BufferedWriter(new FileWriter("UnPas.txt"));
        for (int i = 0; i < date.size(); i++) {
            if (date.get(i).p == 1) {
                writer.append(date.get(i).d + " " + date.get(i).p + "\n");
            }
        }

        System.out.println(GREEN + "(*) Datele au fost inscrise in fisier cu succes." + RESET);
        writer.close();
    }
    static void bubbleSort(ArrayList<Comoara> date) {

        //Loop pentru sortarea prin metoda bulelor
        for (int i = 0; i < date.size() - 1; i++)
            for (int j = 0; j < date.size() - i - 1; j++)

                if (date.get(j).d > date.get(j + 1).d) {
                    int tempD = date.get(j).d;
                    int tempP = date.get(j).p;

                    date.get(j).p = date.get(j + 1).p;
                    date.get(j).d = date.get(j + 1).d;

                    date.get(j + 1).p = tempP;
                    date.get(j + 1).d = tempD;
                }
    }
    static String rectangleArea(ArrayList<Comoara> date) {

        Coordonate corner = new Coordonate();
        AtomicInteger xMin = new AtomicInteger(0);
        AtomicInteger yMin = new AtomicInteger(0);
        AtomicInteger xMax = new AtomicInteger(0);
        AtomicInteger yMax = new AtomicInteger(0);
        date.forEach(values -> {
            switch (values.d) {
                case 1:
                    corner.x += 0 * values.p;
                    corner.y += 1 * values.p;
                    xMax.set(Math.max(xMax.get(), corner.x));
                    yMax.set(Math.max(yMax.get(), corner.y));
                    yMin.set(Math.min(yMin.get(), corner.y));
                    xMin.set(Math.min(xMin.get(), corner.x));
                    break;

                case 2:
                    corner.x += 1 * values.p;
                    corner.y += 1 * values.p;
                    xMax.set(Math.max(xMax.get(), corner.x));
                    yMax.set(Math.max(yMax.get(), corner.y));
                    yMin.set(Math.min(yMin.get(), corner.y));
                    xMin.set(Math.min(xMin.get(), corner.x));
                    break;

                case 3:
                    corner.x += 1 * values.p;
                    corner.y += 0 * values.p;
                    xMax.set(Math.max(xMax.get(), corner.x));
                    yMax.set(Math.max(yMax.get(), corner.y));
                    yMin.set(Math.min(yMin.get(), corner.y));
                    xMin.set(Math.min(xMin.get(), corner.x));
                    break;

                case 4:
                    corner.x += 1 * values.p;
                    corner.y -= 1 * values.p;
                    xMax.set(Math.max(xMax.get(), corner.x));
                    yMax.set(Math.max(yMax.get(), corner.y));
                    yMin.set(Math.min(yMin.get(), corner.y));
                    xMin.set(Math.min(xMin.get(), corner.x));
                    break;

                case 5:
                    corner.x += 0 * values.p;
                    corner.y -= 1 * values.p;
                    xMax.set(Math.max(xMax.get(), corner.x));
                    yMax.set(Math.max(yMax.get(), corner.y));
                    yMin.set(Math.min(yMin.get(), corner.y));
                    xMin.set(Math.min(xMin.get(), corner.x));
                    break;

                case 6:
                    corner.x -= 1 * values.p;
                    corner.y -= 1 * values.p;
                    xMax.set(Math.max(xMax.get(), corner.x));
                    yMax.set(Math.max(yMax.get(), corner.y));
                    yMin.set(Math.min(yMin.get(), corner.y));
                    xMin.set(Math.min(xMin.get(), corner.x));
                    break;

                case 7:
                    corner.x -= 1 * values.p;
                    corner.y += 0 * values.p;
                    xMax.set(Math.max(xMax.get(), corner.x));
                    yMax.set(Math.max(yMax.get(), corner.y));
                    yMin.set(Math.min(yMin.get(), corner.y));
                    xMin.set(Math.min(xMin.get(), corner.x));
                    break;

                case 8:
                    corner.x -= 1 * values.p;
                    corner.y += 1 * values.p;
                    xMax.set(Math.max(xMax.get(), corner.x));
                    yMax.set(Math.max(yMax.get(), corner.y));
                    yMin.set(Math.min(yMin.get(), corner.y));
                    xMin.set(Math.min(xMin.get(), corner.x));
                    break;
            }
        });
        return GREEN + "[Aria dreptunghiului = " + Math.abs(xMax.intValue() - xMin.intValue()) * Math.abs(yMax.intValue() - yMin.intValue()) + "]\n\n" + RESET +
               GREEN +  "(*) Coltul Stanga-Jos = (" + xMin + ", " + yMin + ")\n" + RESET +
               GREEN + "(*) Coltul Dreapta-Sus = (" + xMax + ", " + yMax + ")" + RESET;
    }

    //Problema 8
    static String increaseValues(ArrayList<Comoara> date) {
        Coordonate coord = new Coordonate();
        date.forEach(incrementValue -> {
            switch (incrementValue.d) {
                case 1:
                    coord.x += 0 * incrementValue.p;
                    coord.y += 1 * incrementValue.p;
                    break;
                case 2:
                    coord.x += 1 * incrementValue.p;
                    coord.y += 1 * incrementValue.p;
                    break;
                case 3:
                    coord.x += 1 * incrementValue.p;
                    coord.y += 0 * incrementValue.p;
                    break;
                case 4:
                    coord.x += 1 * incrementValue.p;
                    coord.y -= 1 * incrementValue.p;
                    break;
                case 5:
                    coord.x += 0 * incrementValue.p;
                    coord.y -= 1 * incrementValue.p;
                    break;
                case 6:
                    coord.x -= 1 * incrementValue.p;
                    coord.y -= 1 * incrementValue.p;
                    break;
                case 7:
                    coord.x -= 1 * incrementValue.p;
                    coord.y += 0 * incrementValue.p;
                    break;
                case 8:
                    coord.x -= 1 * incrementValue.p;
                    coord.y += 1 * incrementValue.p;
                    break;
            }
        });
        return GREEN + "(*) Coordonatele unde e ascunsa comoara = (" + coord.x + ", " + coord.y + ")" + RESET;
    }

    //Main
    public static void main(String[] args) throws IOException {
        ArrayList<Comoara> date = new ArrayList<>();
        File file = new File("Comori.in");

        // MENIU
        System.out.println();
        System.out.println("│ " + RED + "                                            [ MENIU ]                                             " + RESET + " │");
        System.out.println("├────────────────────────────────────────────────────────────────────────────────────────────────────┤                        ");
        System.out.println("│ " + RED + "Sarcina 1" + RESET + " - Extinde traseul spre comoara cu o noua indicatie;                                      │");
        System.out.println("│ " + RED + "Sarcina 2" + RESET + " - Exclude ultima indicatie din traseul spre comoara;                                     │");
        System.out.println("│ " + RED + "Sarcina 3" + RESET + " - Determina numarul total de pasi;                                                       │");
        System.out.println("│ " + RED + "Sarcina 4" + RESET + " - Determina numarul schimbarilor de directii;                                            │");
        System.out.println("│ " + RED + "Sarcina 5" + RESET + " - Creeaza fisierul UnPas.txt, unde se vor copia datele unde p=1;                         │");
        System.out.println("│ " + RED + "Sarcina 6" + RESET + " - Afiseaza lista codurilor directiilor deplasarilor in ordinea ascendenta                │");
        System.out.println("│ " + RED + "Sarcina 7" + RESET + " - Afiseaza aria si coordonatele colturilor stanga-jos si dreapta-sus ale dreptunghiului; │");
        System.out.println("│                                                                                                    │                        ");
        System.out.println("│ " + RED + "Problema 8" + RESET + " - Determina punctul in care este ascunsa comoara;                                       │");
        System.out.println("├────────────────────────────────────────────────────────────────────────────────────────────────────┤                        ");
        System.out.println("│ " + RED + "                                   [ 0 - INCHIDEREA MENIULUI ]                                    " + RESET + " │");
        Scanner keyValue = new Scanner(System.in);

        int number = 0;

        //Loop pentru citirea numarului, care va reprezenta sarcina
        do {
            System.out.print("\n> Sarcina ");
            try {
                number = keyValue.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(RED + "(!) Tipul de date introdus trebuie sa fie integer." + RESET);
                number = 0;
            }

            //Switch care va apela sarcinile
            switch (number) {
                case 0:
                    System.out.println(RED + "(!) Meniul a fost inchis." + RESET);
                    break;

                case 1:
                    fileReader(date);
                    Scanner scanner1 = new Scanner(file);
                    int n1 = scanner1.nextInt();
                    scanner1.close();
                    newValue(date, n1);
                    break;

                case 2:
                    fileReader(date);
                    Scanner scanner2 = new Scanner(file);
                    int n2 = scanner2.nextInt();
                    scanner2.close();
                    removeValue(date, n2);
                    break;

                case 3:
                    fileReader(date);
                    System.out.println(GREEN + "(*) Trebuie de efectuat " + steps(date) + " pasi pentru a gasi comoara." + RESET);
                    break;

                case 4:
                    fileReader(date);
                    System.out.println(GREEN + "(*) Au fost efectuate " + dirrectionChange(date) + " schimbari de directie." + RESET);
                    break;

                case 5:
                    fileReader(date);
                    fileWithCertainValues(date);
                    break;

                case 6:
                    fileReader(date);
                    bubbleSort(date);
                    System.out.println(GREEN + "(*) Datele din fisier, sortate ascendent dupa directie" + RESET);
                    date.forEach(data -> System.out.println(data.d + " " + data.p));
                    break;

                case 7:
                    fileReader(date);
                    System.out.println(rectangleArea(date));
                    break;

                case 8:
                    fileReader(date);
                    System.out.println(increaseValues(date));
                    break;

                default:
                    System.out.println(RED + "(!) Numarul introdus nu corespunde unei sarcini cunoscute." + RESET);
                    break;
            }
        } while (number != 0);
    }
}