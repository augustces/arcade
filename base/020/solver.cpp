#include <iostream>
#include <cstdio>
#include <clocale>
#include <sstream>
#include <iomanip> //setprecision

using namespace std;

struct Grafite{
    float calibre;
    string dureza;
    int tamanho;

    Grafite(float calibre, string dureza, int tamanho) {
        this->calibre = calibre;
        this->dureza = dureza;
        this->tamanho = tamanho;
    }
    friend ostream& operator<<(ostream& os, Grafite g) {
        os << std::fixed << setprecision(1) << g.calibre << ":" << g.dureza << ":" << g.tamanho;
        return os;
    }
    int desgastePorFolha() {
        if (dureza == "HB")
            return 1;
        else if (dureza == "2B")
            return 2;
        else if (dureza == "4B")
            return 4;
        else
            return 6;
    }
};

struct Lapiseira{
    float calibre {0.f};
    Grafite * grafite {nullptr};

    Lapiseira(float calibre = 0.0): calibre{calibre} {
    }
    ~Lapiseira() {
        if (this->grafite != nullptr)
            delete this->grafite;
    }

    friend ostream& operator<<(ostream& os, const Lapiseira& l) {
        os << "calibre: " << l.calibre << ", grafite: ";
        if (l.grafite != nullptr)
            os << "[" << *l.grafite << "]";
        else
            os << "null";
        return os;
    }

    bool inserir(Grafite * grafite) {
        if (this->grafite == nullptr) {
            if (this->calibre != grafite->calibre) {
                cout << "fail: calibre incompatível\n";
                return false;
            } else{
                this->grafite = grafite;
                return true;
            }
        }
        cout << "fail: ja existe grafite\n";
        return false;
    }

    Grafite * remover() {
        if (this->grafite == nullptr) {
            cout << "fail: nao existe grafite\n";
            return nullptr;
        }
        return std::exchange(this->grafite, nullptr);
    }

    void write() {
        if (this->grafite == nullptr) {
            cout << "fail: nao existe grafite\n";
            return;
        }
        auto& tamanho = this->grafite->tamanho;
        tamanho -= this->grafite->desgastePorFolha();
        if (tamanho < 10) {
            cout << "fail: folha incompleta\n";
            tamanho = 10;
        }
        if (tamanho == 10)
            cout << "warning: grafite acabou\n";
    }
};

int main() {
    Lapiseira lapiseira;
    string line;
    while (true) {
        getline(cin, line);
        stringstream ss(line);
        string cmd;
        cout << "$" << line << endl;
        ss >> cmd;
        if (line == "end")
            break;
        else if (cmd == "help") {
            cout << "init _calibre; inserir _calibre _dureza _tamanho; remover; write _folhas" << endl;
        } else if (cmd == "init") {
            float calibre;
            ss >> calibre;
            lapiseira = Lapiseira(calibre);
        } else if (cmd == "inserir") {
            float calibre;
            string dureza;
            int tamanho;
            ss >> calibre >> dureza >> tamanho;
            auto grafite = new Grafite(calibre, dureza, tamanho);
            if (!lapiseira.inserir(grafite))
                delete grafite;
        } else if (cmd == "remover") {
            auto gr = lapiseira.remover();
            if (gr != nullptr)
                delete gr;
        } else if (cmd == "show") {
            cout << lapiseira << endl;
        } else if (cmd == "write") {
            lapiseira.write();
        } else{
            cout << "fail: comando invalido" << endl;
        }
    }
    return 0;
}
