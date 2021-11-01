import java.util.Scanner;

class Car{
    public int pass; // Passageiros
    public int passMax; // limite de Passageiros
    public int gas; // tanque
    public int gasMax; // limite do tanque
    public int km; // quantidade de quilometragem

    public Car() {
        this.pass = 0; // Passageiros
        this.passMax = 2; // limite de Passageiros
        this.gas = 0; // tanque
        this.gasMax = 100; // limite do tanque
        this.km = 0; // quantidade de quilometragem
    }

    public String toString() {
        return "pass: " + pass + ", gas: " + gas + ", km: " + km;
    }

    public void enter() {
        if (pass < passMax) {
            pass += 1;
            return;
        }
        System.out.println("fail: limite de pessoas atingido");
    }

    public void leave() {
        if (pass > 0) {
            pass-=1;
            return;
        }
        System.out.println("fail: nao ha ninguem no carro");
    }
    
    public void fuel(int gas) {
        this.gas += gas;
        if(this.gas > gasMax)
            this.gas = gasMax;
    }

    public void drive (int km) {
        if(this.pass == 0) {
            System.out.println("fail: nao ha ninguem no carro");
        } else if(this.gas == 0) {
            System.out.println("fail: tanque vazio");
        }
        else if(this.gas < km) {
            System.out.println("fail: tanque vazio apos andar " + this.gas + " km");
            this.km += this.gas;
            this.gas = 0;
        } else{
            this.gas = gas - km;
            this.km = this.km + km;
        }
    }    
};
//!KEEP
class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Car car = new Car();

        while(true) {
            String line = scanner.nextLine();
            String ui[] = line.split(" ");
            System.out.println("$" + line);
            if(ui[0].equals("end")) {
                break;
            } else if(ui[0].equals("enter")) {
                car.enter();
            } else if(ui[0].equals("leave")) {
                car.leave();
            } else if(ui[0].equals("show")) {
                System.out.println(car.toString());
            } else if (ui[0].equals("drive")) {//km
                car.drive(Integer.parseInt(ui[1]));
            } else if (ui[0].equals("fuel")) {//gas
                car.fuel(Integer.parseInt(ui[1]));
            } else{
                System.out.println("fail: comando invalido");
            }
        }
    }
}
class Manual {
    public static void main(String[] args) {
        Car car = new Car();
        System.out.println(car);
        //pass: 0, gas: 0, km: 0
        car.enter();
        car.enter();
        System.out.println(car);
        //pass: 2, gas: 0, km: 0
        car.enter();
        //fail: limite de pessoas atingido
        System.out.println(car);
        //pass: 2, gas: 0, km: 0
        car.leave();
        car.leave();
        car.leave();
        //fail: nao ha ninguem no carro
        System.out.println(car);
        //pass: 0, gas: 0, km: 0

        car = new Car();
        car.fuel(60);
        System.out.println(car);
        //pass: 0, gas: 60, km: 0

        car.drive(10);
        //fail: nao ha ninguem no carro

        car.enter();
        car.drive(10);
        System.out.println(car);
        //pass: 1, gas: 50, km: 10

        car.drive(70);
        //fail: tanque vazio apos andar 50 km
        car.drive(10);
        //fail: tanque vazio
        System.out.println(car);
        //pass: 1, gas: 0, km: 60

        car.fuel(200);
        System.out.println(car);
        //pass: 1, gas: 100, km: 60
    }
}
//!OFF
