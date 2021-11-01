import java.text.DecimalFormat;
import java.util.Scanner;

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
    public Grafite grafite;

    public Lapiseira(float calibre) {
        this.calibre = calibre;
        this.bico = null;
    }

    public String toString() {
        String saida = "calibre: " + calibre + ", grafite: ";
        if (bico != null)
            saida += "[" + bico + "]";
        else
            saida += "null";
        return saida;
    }

    public boolean inserir(Grafite grafite) {
        if(this.bico == null) {
            if(this.calibre != grafite.calibre) {
                System.out.println("fail: calibre incompatível");
                return false;
            } else {
                this.bico = grafite;
                return true;
            }
        }
        System.out.println("fail: ja existe grafite");
        return false;
    }

    public Grafite remover() {
        if(this.bico == null) {
            System.out.println("fail: nao existe grafite");
            return null;
        }
        Grafite backup = this.bico;
        this.bico = null;
        return backup;
    }

    public void writePage() {
        if(this.bico == null) {
            System.out.println("fail: nao existe grafite");
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
            } else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }
}

class Manual {
    public static void main(String[] args) {
        //case inserindo grafites
        Lapiseira lapiseira = new Lapiseira(0.5f);
        System.out.println(lapiseira);
        //calibre: 0.5, grafite: null
        lapiseira.inserir(new Grafite(0.7f, "2B", 50));
        //fail: calibre incompatível
        lapiseira.inserir(new Grafite(0.5f, "2B", 50));
        System.out.println(lapiseira);
        //calibre: 0.5, grafite: [0.5:2B:50]

        //case inserindo e removendo
        lapiseira = new Lapiseira(0.3f);
        lapiseira.inserir(new Grafite(0.3f, "2B", 50));
        System.out.println(lapiseira);
        //calibre: 0.3, grafite: [0.3:2B:50]
        lapiseira.inserir(new Grafite(0.3f, "4B", 70));
        //fail: ja existe grafite
        System.out.println(lapiseira);
        //calibre: 0.3, grafite: [0.3:2B:50]
        lapiseira.remover();
        lapiseira.inserir(new Grafite(0.3f, "4B", 70));
        System.out.println(lapiseira);
        //calibre: 0.3, grafite: [0.3:4B:70]

        //case escrevendo 1
        lapiseira = new Lapiseira(0.9f);
        lapiseira.inserir(new Grafite(0.9f, "4B", 4));
        lapiseira.writePage();
        //warning: grafite acabou
        System.out.println(lapiseira);
        //calibre: 0.9, grafite: null
        lapiseira.inserir(new Grafite(0.9f, "4B", 30));
        lapiseira.writePage();
        System.out.println(lapiseira);
        //calibre: 0.9, grafite: [0.9:4B:6]
        lapiseira.writePage();
        lapiseira.writePage();
        lapiseira.writePage();
        //fail: folhas escritas completas: 1
        //warning: grafite acabou
        System.out.println(lapiseira);
        //calibre: 0.9, grafite: null

        //case escrevendo 2
        lapiseira = new Lapiseira(0.9f);
        lapiseira.inserir(new Grafite(0.9f, "2B", 15));
        System.out.println(lapiseira);
        //calibre: 0.9, grafite: [0.9:2B:15]
        lapiseira.writePage();
        lapiseira.writePage();
        lapiseira.writePage();
        lapiseira.writePage();
        System.out.println(lapiseira);
        //calibre: 0.9, grafite: [0.9:2B:7]
        lapiseira.writePage();
        lapiseira.writePage();
        lapiseira.writePage();
        lapiseira.writePage();
        //fail: folhas escritas completas: 3
        //warning: grafite acabou
        System.out.println(lapiseira);
        //calibre: 0.9, grafite: null
    }
}
//!OFF