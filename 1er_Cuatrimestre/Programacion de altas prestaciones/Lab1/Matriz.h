//
// Created by Andoni on 26/09/2018.
//

#ifndef LAB1_MATRIZ_H
#define LAB1_MATRIZ_H

class matriz {
public:
    matriz(int filas, int columnas);
    matriz();
    void pintar_matriz();
    inline void set_bsize(const int &bsize) {this->bsize = bsize;};
    void rellenar_matriz();
    double calcular_diagonal();
    double &operator ()(double f, double c);
    matriz operator +(matriz &m);
    matriz operator *(matriz &m);
private:
    int filas, columnas;
    int bsize = 0;
    double *valores;
};


#endif //LAB1_MATRIZ_H
