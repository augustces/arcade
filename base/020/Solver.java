import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.ArrayList;

class Grafite {
    public float calibre;
    public String dureza;
    public int tamanho;

    public Grafite(float calibre, String dureza, int tamanho) {
        this.calibre = calibre;
        this.dureza = dureza;
        this.tamanho = tamanho;
    }
    public String toString() {
        DecimalFormat form = new DecimalFormat("0.0");
        return form.format(calibre) + ":" + dureza + ":" + tamanho;
    }
    public int desgastePorFolha() {
        if(dureza.equals("HB"))
            return 1;
        else if(dureza.equals("2B"))
            return 2;
        else if(dureza.equals("4B"))
            return 4;
        else
            return 6;
    }
}

class Lapiseira {
    public float calibre;
    public Grafite bico;
    public ArrayList<Grafite> tambor;

    public Lapiseira(float calibre) {
        this.calibre = calibre;
        this.tambor = new ArrayList<>();
    }

    public String toString() {
        String saida = "calibre: " + calibre + ", bico: ";
        if (this.bico != null)
            saida += "[" + this.bico + "]";
        else
            saida += "[]";
        saida += ", tambor: {";
            for (Grafite g : tambor)
                saida += "[" + g + "]";
        return saida + "}";
    }

    public boolean inserir(Grafite grafite) {
        if(this.calibre != grafite.calibre) {
            System.out.println("fail: calibre incompatível");
            return false;
        }
        this.tambor.add(grafite);
        return true;
    }

    public Grafite remover() {
        if(this.bico == null) {
            System.out.println("fail: nao existe grafite no bico");
            return null;
        }
        Grafite backup = this.bico;
        this.bico = null;
        return backup;
    }

    public boolean pull() {
        if (this.bico != null) {
            System.out.println("fail: ja existe grafite no bico");
            return false;
        }
        if (this.tambor.size() == 0) {
            System.out.println("fail: nao existe grafite no tambor");
            return false;
        }
        this.bico = this.tambor.remove(0);
        return true;
    }

    public void writePage() {
        if(this.bico == null) {
            System.out.println("fail: nao existe grafite no bico");
            return;
        }
        this.bico.tamanho -= this.bico.desgastePorFolha();
        if(this.bico.tamanho < 10) {
            System.out.println("fail: folha incompleta");
        }
        if(this.bico.tamanho <= 10) {
            this.bico.tamanho = 10;
            System.out.println("warning: grafite acabou");
        }
    }
}
//!KEEP
class Solver{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Lapiseira lapiseira = new Lapiseira(0.5f);
        while(true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String ui[] = line.split(" ");
            if(ui[0].equals("end")) {
                break;
            } else if(ui[0].equals("help")) {
                System.out.println("init _calibre; inserir _calibre _dureza _tamanho; remover; writePage _folhas");
            } else if(ui[0].equals("init")) { //calibre
                lapiseira = new Lapiseira(Float.parseFloat(ui[1]));
            } else if(ui[0].equals("inserir")) {//calibre dureza tamanho
                float calibre = Float.parseFloat(ui[1]);
                String dureza  = ui[2];
                int tamanho = Integer.parseInt(ui[3]);
                lapiseira.inserir(new Grafite(calibre, dureza, tamanho));
            } else if(ui[0].equals("remover")) {
                lapiseira.remover();
            } else if(ui[0].equals("show")) {
                System.out.println(lapiseira);
            } else if (ui[0].equals("write")) {
                lapiseira.writePage();
            } else if (ui[0].equals("puxar")) {
                lapiseira.pull();
            }  else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}


//!OFF