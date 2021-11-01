var Grafite = /** @class */ (function () {
    function Grafite(calibre, dureza, tamanho) {
        this.calibre = calibre;
        this.dureza = dureza;
        this.tamanho = tamanho;
    }
    Grafite.prototype.gastoPorFolha = function () {
        if (this.dureza === 'HB')
            return 1;
        if (this.dureza === '2B')
            return 2;
        if (this.dureza === '4B')
            return 4;
        if (this.dureza === '6B')
            return 6;
        return 0;
    };
    Grafite.prototype.toString = function () {
        //return "Grafite: " + this.calibre + ":" + this.dureza + ":" + this.tamanho;
        return "Grafite " + this.calibre + ":" + this.dureza + ":" + this.tamanho;
    };
    return Grafite;
}());
//agregação
var Lapiseira = /** @class */ (function () {
    function Lapiseira(calibre) {
        this.calibre = calibre;
        this.grafite = null;
    }
    Lapiseira.prototype.setGrafite = function (grafite) {
        if (this.grafite != null) {
            console.log("A lapiseira já possui um grafite");
            return false;
        }
        if (grafite.calibre != this.calibre) {
            console.log("O grafite não é compatível com a lapiseira");
            return false;
        }
        this.grafite = grafite;
        return true;
    };
    Lapiseira.prototype.removerGrafite = function () {
        if (this.grafite == null) {
            console.log("A lapiseira não possui um grafite");
            return null;
        }
        var grafite = this.grafite;
        this.grafite = null;
        return grafite;
    };
    Lapiseira.prototype.escrever = function (folhas) {
        //verificar se existe grafite
        if (this.grafite == null) {
            console.log("A lapiseira não possui um grafite");
            return false;
        }
        var gasto = this.grafite.gastoPorFolha() * folhas;
        if (gasto <= this.grafite.tamanho) {
            console.log("Escrita concluida");
            this.grafite.tamanho -= gasto;
        }
        else {
            var realizado = this.grafite.tamanho / this.grafite.gastoPorFolha();
            console.log("Escrita parcial: " + realizado + " folhas");
            this.grafite.tamanho = 0;
        }
        if (this.grafite.tamanho == 0) {
            this.grafite = null;
        }
    };
    return Lapiseira;
}());
var pentel = new Lapiseira(0.5);
pentel.setGrafite(new Grafite(0.5, "HB", 40));
pentel.escrever(10);
pentel.escrever(40);
console.log(pentel);
